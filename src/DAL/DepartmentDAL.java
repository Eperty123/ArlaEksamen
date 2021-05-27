package DAL;

import BE.Department;
import BE.User;
import BLL.DepartmentBuilder;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();
    private final DepartmentBuilder departmentBuilder = new DepartmentBuilder();

    public void addDepartment(Department department) {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Department VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            if (department.getManager() == null) {
                User placeholderUser = new User();
                placeholderUser.setUserName("admtest");
                department.setManager(placeholderUser);
            }
            pSql.setString(1, department.getName());
            pSql.setString(2, department.getManager().getUserName());
            pSql.executeUpdate();
            var generatedKeys = pSql.getGeneratedKeys();
            generatedKeys.next();
            department.setId(generatedKeys.getInt(1));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new department " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void addSubDepartment(Department department, Department subDepartment) {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO SubDepartment VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pSql.setInt(1, department.getId());
            pSql.setInt(2, subDepartment.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new department " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void deleteDepartment(Department department) {

        try (Connection con = dbCon.getConnection()) {
            deleteDepartmentUserAssociations(con, department);
            deleteDepartmentSubDepartmentAssociations(con, department);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Department WHERE Id=?");
            pSql.setInt(1, department.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a department " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    private void deleteDepartmentSubDepartmentAssociations(Connection con, Department department) throws SQLException {
        PreparedStatement pSql = con.prepareStatement("DELETE FROM SubDepartment WHERE DptId=? OR SubDptId=?");
        pSql.setInt(1, department.getId());
        pSql.setInt(2, department.getId());
        pSql.execute();
    }

    private void deleteDepartmentUserAssociations(Connection con, Department department) throws SQLException {

        PreparedStatement pSql = con.prepareStatement("DELETE FROM DepartmentUser WHERE DepartmentId=?");
        pSql.setInt(1, department.getId());
        pSql.execute();
    }

    // TODO join with users.
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to retrieve all titles " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return departments;
    }

    private void addDepartmentsAndUsers(List<Department> departments, ResultSet rs) throws SQLException {

        departmentBuilder.makeDepartment(rs);
        departmentBuilder.addUsers(rs);
        departmentBuilder.addSubdepartments(rs);
        departments.addAll(departmentBuilder.getResult());

    }

    public void updateDepartment(Department department) {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Department SET Name=?, Manager=? WHERE Id=?");
            department.setManager(department.getUsers().get(0));
            pSql.setString(1, department.getName());
            pSql.setString(2, department.getManager().getUserName());
            pSql.setInt(3, department.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a title " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

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
