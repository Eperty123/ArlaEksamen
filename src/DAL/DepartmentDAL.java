package DAL;

import BE.Department;
import BE.Title;
import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import GUI.Controller.PopupControllers.WarningController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAL {

    private final DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final ResultSetParser resultSetParser = new ResultSetParser();

    public void addDepartment(String newnewDepartment){

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("INSERT INTO Department VALUES(?)");
            pSql.setString(1, newnewDepartment);
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to add a new department " +
                    "to the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

    public void deleteDepartment(Department department){

        try(Connection con = dbCon.getConnection()){
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
    public List<Department> getDepartments(){
        List<Department> departments = new ArrayList<>();

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement(
                    "SELECT " +
                            "Department.Id AS dptId, " +
                            "Department.Name AS dptName, " +
                            "Department.Manager, " +
                            "[User].* " +
                            "FROM Department " +
                            "LEFT OUTER JOIN DepartmentUser " +
                            "ON Department.Id = DepartmentUser.DepartmentId " +
                            "LEFT OUTER JOIN [User] " +
                            "ON [User].UserName = DepartmentUser.UserName",
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

    private void addDepartmentsAndUsers(List<Department> department, ResultSet rs) throws SQLException {

        while(rs.next()){
            User user = resultSetParser.getUser(rs);

            Department newDepartment = new Department(rs.getInt("dptId"), rs.getString("dptName"), new User(rs.getString("Manager")));
            if(department.stream().noneMatch(o -> o.getId() == newDepartment.getId())){
                department.add(newDepartment);
            }

        }
        rs.first();

        while(rs.next()){

            User user = resultSetParser.getUser(rs);
            for(Department d : department){
                if(d.getManager().getUserName().equals(user.getUserName())){
                    d.setManager(user);
                } else if(rs.getString("dptName").equals(d.getName())){
                    d.addUser(user);
                }
            }

        }

        /*

        if(department.stream().noneMatch(o -> o.getId().equals(newDepartment.getId()))){
            if(user.getUserName() != null && user.getUserName().equals(rs.getString("Manager"))){
                newDepartment.setManager(user);
            } else if(user.getUserName() != null && !user.getUserName().equals(rs.getString("Manager"))){
                newDepartment.addUser(user);
            }
            department.add(newDepartment);

        } else if (user.getUserName() != null && user.getUserName().equals(rs.getString("Manager"))){
            for(Department d : department){
                if(d.getManager().getUserName().equals(user.getUserName())){
                    d.setManager(user);
                }
            }
        } else if (user.getUserName() != null){
            for(Department d : department){
                if(d.getId().equals(newDepartment.getId())){
                    d.addUser(user);
                }
            }
        }

         */
    }

    public void updateDepartment(Department oldDepartment, Department department){

        try(Connection con = dbCon.getConnection()){
            PreparedStatement pSql = con.prepareStatement("UPDATE Title SET Title=? WHERE Id=?");
            pSql.setString(1, department.getName());
            pSql.setInt(2, oldDepartment.getId());
            pSql.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            WarningController.createWarning("Oh no! Something went wrong when attempting to update a title " +
                    "in the Database. Please try again, and if the problem persists, contact an IT Administrator.");
        }
    }

}
