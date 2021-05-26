package DAL;

import BE.Department;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

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
    private final ResultSetParser resultSetParser = new ResultSetParser();

    public Department addDepartment(Department department) {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Department VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pSql.setString(1, department.getName());
            pSql.setString(2,department.getManager().getUserName());
            pSql.executeUpdate();
            var generatedKeys = pSql.getGeneratedKeys();
            generatedKeys.next();
            department.setId(generatedKeys.getInt(1));
            System.out.println(department.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new department " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
        return department;
    }

    public void deleteDepartment(Department department) {

        try (Connection con = dbCon.getConnection()) {
            deleteDepartmentUserAssociations(con, department);
            PreparedStatement pSql = con.prepareStatement("DELETE FROM Department WHERE Id=?");
            pSql.setInt(1, department.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to delete a department " +
                    "from the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
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
                            "SubDepartment.SubDpt AS subDpt " +
                            "FROM Department " +
                            "LEFT OUTER JOIN DepartmentUser " +
                            "ON Department.Id = DepartmentUser.DepartmentId " +
                            "LEFT OUTER JOIN [User] " +
                            "ON [User].UserName = DepartmentUser.UserName " +
                            "LEFT OUTER JOIN SubDepartment " +
                            "ON SubDepartment.DptName = Department.Name",
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

        while (rs.next()) {
            User user = resultSetParser.getUser(rs);

            Department newDepartment = new Department(rs.getInt("dptId"), rs.getString("dptName"), new User(rs.getString("Manager")));
            if (departments.stream().noneMatch(o -> o.getId() == newDepartment.getId())) {
                departments.add(newDepartment);
            }

        }
        rs.beforeFirst();

        while (rs.next()) {

            User user = resultSetParser.getUser(rs);
            for (Department d : departments) {
                if (d.getManager().getUserName().equals(user.getUserName())) {
                    d.setManager(user);
                }
                if(user.getUserName()!=null)
                if (rs.getString("dptName").equals(d.getName()) && d.getUsers().stream().noneMatch(o -> o.getUserName().equals(user.getUserName()))) {
                    d.addUser(user);
                }
            }
        }

        rs.beforeFirst();

        while (rs.next()) {

            for (Department dpt : departments) {
                if (dpt.getName().equals(rs.getString("dptName"))) {
                    for (Department subDpt : departments) {
                        if (subDpt.getName().equals(rs.getString("subDpt"))) {
                            if (!dpt.getSubDepartments().contains(subDpt)) {
                                dpt.addSubDepartment(subDpt);
                            }
                        }
                    }
                }
            }

        }
    }

    public void updateDepartment(Department oldDepartment, Department department) {
        try (Connection con = dbCon.getConnection()) {
            PreparedStatement pSql = con.prepareStatement("UPDATE Title SET Title=?, Manager=? WHERE Id=?");
            pSql.setString(1, department.getName());
            pSql.setString(2, department.getManager().getUserName());
            pSql.setInt(3, oldDepartment.getId());
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
                    sb.append(String.format("%s     %s      %d\n", user.getFirstName(), user.getLastName(), user.getPhone()));
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

}
