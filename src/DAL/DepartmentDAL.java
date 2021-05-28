package DAL;

import BE.Department;
import BLL.DepartmentBuilder;
import DAL.DbConnector.DbConnectionHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final DepartmentBuilder departmentBuilder = new DepartmentBuilder();

    /**
     * Adds a department to the database, and sets the id of the department to the identity given in the Database
     * @param department the department you want to add
     */
    public void addDepartment(Department department) throws SQLException {

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Department VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pSql.setString(1, department.getName());
            pSql.setString(2, department.getManager().getUserName());
            pSql.executeUpdate();
            var generatedKeys = pSql.getGeneratedKeys();
            generatedKeys.next();
            department.setId(generatedKeys.getInt(1));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

    }

    /**
     * Adds a SubDepartment into the database, given by the ids of department and subdepartment
     * @param department the superDepartment
     * @param subDepartment the subDepartment
     */

    public void addSubDepartment(Department department, Department subDepartment) throws SQLException {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO SubDepartment VALUES(?,?)");
            pSql.setInt(1, department.getId());
            pSql.setInt(2, subDepartment.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Removes the department with the given id, and removes the user and subDepartment assoiciations.
     * @param department the department you want to remove
     * @throws SQLException
     */
    public void deleteDepartment(Department department) throws SQLException {

        try (Connection con = dbCon.getConnection()) {

            con.setAutoCommit(false); // Enable transaction
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            deleteDepartmentUserAssociations(con, department);
            deleteDepartmentSubDepartmentAssociations(con, department);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Department WHERE Id=?");
            pSql.setInt(1, department.getId());

            try {
                pSql.execute();
            } catch (SQLException throwables) {
                con.rollback();
                con.setAutoCommit(true);
                con.setTransactionIsolation(Connection.TRANSACTION_NONE);
                throw throwables;
            }

            con.commit();
            con.setAutoCommit(true);
            con.setTransactionIsolation(Connection.TRANSACTION_NONE);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }

    /**
     * Removes columns in the database, where either the subDepartment's id or the superDepartment's id is the given department's id
     * @param con the connection you want to use
     * @param department the departments associations you want to remove
     * @throws SQLException
     */
    private void deleteDepartmentSubDepartmentAssociations(Connection con, Department department) throws SQLException {
        PreparedStatement pSql = con.prepareStatement("DELETE FROM SubDepartment WHERE DptId=? OR SubDptId=?");
        pSql.setInt(1, department.getId());
        pSql.setInt(2, department.getId());
        pSql.execute();
    }

    /**
     * Removes columns in the database, where a Department user is bound to the given department
     * @param con the connection you want to use
     * @param department the department of which you want the user associations to be removed
     * @throws SQLException
     */
    private void deleteDepartmentUserAssociations(Connection con, Department department) throws SQLException {
        PreparedStatement pSql = con.prepareStatement("DELETE FROM DepartmentUser WHERE DepartmentId=?");
        pSql.setInt(1, department.getId());
        pSql.execute();
    }


    /**
     * Gets a list of all the departments in the database
     * @return the Departments
     */
    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();

        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT " +
                            "Department.Id AS dptId, " +
                            "Department.Name AS dptName, " +
                            "Department.Manager, " +
                            "[User].*, " +
                            "SubDepartment.SubDptId AS subDpt " +
                            "FROM Department " +
                            "LEFT OUTER JOIN DepartmentUser " +
                            "ON Department.Id = DepartmentUser.DepartmentId " +
                            "LEFT OUTER JOIN [User] " +
                            "ON [User].UserName = DepartmentUser.UserName " +
                            "LEFT OUTER JOIN SubDepartment " +
                            "ON SubDepartment.DptId = Department.Id",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            pSql.execute();

            addDepartmentsAndUsers(departments, pSql.getResultSet());
            return departments;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Sends the departments and the result set to the DepartmentBuilder
     * @param departments the departments
     * @param rs the result set
     * @throws SQLException if something went wrong
     */
    private void addDepartmentsAndUsers(List<Department> departments, ResultSet rs) throws SQLException {
        departmentBuilder.makeDepartment(rs);
        departmentBuilder.addUsers(rs);
        departmentBuilder.addSubdepartments(rs);
        departments.addAll(departmentBuilder.getResult());

    }


    /**
     * Updates the given department with the given parameters in the department
     * @param department the department
     */
    public void updateDepartment(Department department) throws SQLException {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Department SET Name=?, Manager=? WHERE Id=?");
            pSql.setString(1, department.getName());
            pSql.setString(2, department.getManager().getUserName());
            pSql.setInt(3, department.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }
    }


    // TODO: Add to a seperate class (PhoneList)
    public void exportPhoneNumbers(List<Department> departments) {
        try {
            var sb = new StringBuilder();
            for (int i = 0; i < departments.size(); i++) {
                var department = departments.get(i);
                var users = department.getUsers();

                sb.append(String.format("====== %s ======\n", department.getName()));

                int userCount = 0;
                for (int u = 0; u < users.size(); u++) {
                    userCount++;
                    var user = users.get(u);
                    int phone = user.getPhone() < 0 ? user.getPhone() * -1 : user.getPhone();
                    sb.append(String.format("%s     %s      %d\n", user.getFirstName(), user.getLastName(), phone));
                }

                sb.append("\n\n");
            }

            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            var file = new File(String.format("src/Resources/phonelist_%s_%s.txt", LocalDateTime.now().format(format), LocalDateTime.now().hashCode()));
            var writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO extract duplicate method
    public void exportPhoneNumbers(List<Department> departments, String outputFile) {
        try {
            var sb = new StringBuilder();
            for (int i = 0; i < departments.size(); i++) {
                var department = departments.get(i);
                var users = department.getUsers();

                sb.append(String.format("====== %s ======\n", department.getName()));

                int userCount = 0;
                for (int u = 0; u < users.size(); u++) {
                    userCount++;
                    var user = users.get(u);
                    int phone = user.getPhone() < 0 ? user.getPhone() * -1 : user.getPhone();
                    sb.append(String.format("%s     %s      %d\n", user.getFirstName(), user.getLastName(), phone));
                }

                sb.append("\n\n");
            }

            var file = new File(outputFile);
            var writer = new BufferedWriter(new FileWriter(file));
            writer.write(sb.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportPhoneNumbers(List<Department> departments, File outputFile) {
        try {
            var sb = new StringBuilder();
            for (int i = 0; i < departments.size(); i++) {
                var department = departments.get(i);
                var users = department.getUsers();

                sb.append(String.format("============ %s ============\n", department.getName()));

                int userCount = 0;
                for (int u = 0; u < users.size(); u++) {
                    userCount++;
                    var user = users.get(u);
                    int phone = user.getPhone() < 0 ? user.getPhone() * -1 : user.getPhone();
                    sb.append(String.format("%s     %s      %d\n", user.getFirstName(), user.getLastName(), phone));
                }

                sb.append("\n\n");
            }

            var writer = new BufferedWriter(new FileWriter(outputFile));
            writer.write(sb.toString());

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
