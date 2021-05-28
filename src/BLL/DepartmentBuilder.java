package BLL;

import BE.Department;
import BE.User;
import DAL.ResultSetParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentBuilder implements IDepartmentBuilder{

    private final ResultSetParser resultSetParser = new ResultSetParser();

    private List<Department> departments = new ArrayList<>();


    @Override
    public void makeDepartment(ResultSet rs) throws SQLException {
        User placeHolderUser = new User("PlaceHolder");

        while (rs.next()) {
            Department newDepartment = new Department(rs.getInt("dptId"), rs.getString("dptName"), placeHolderUser);
            if (departments.stream().noneMatch(o -> o.getId() == newDepartment.getId())) {
                departments.add(newDepartment);
            }
        }
        rs.beforeFirst();
    }

    @Override
    public void addUsers(ResultSet rs) throws SQLException {

        while (rs.next()) {

            User user = resultSetParser.getUser(rs);
            for (Department d : departments) {
                if (d.getManager().getUserName().equals(user.getUserName())) {
                    d.setManager(user);
                }
                if (user.getUserName() != null)
                    if (rs.getString("dptName").equals(d.getName()) && d.getUsers().stream().noneMatch(o -> o.getUserName().equals(user.getUserName()))) {
                        d.addUser(user);
                    }
            }
        }
        rs.beforeFirst();
    }

    @Override
    public void addSubdepartments(ResultSet rs) throws SQLException {
        while (rs.next()) {
            for (Department dpt : departments) {
                if (dpt.getId() == rs.getInt("dptId")) {
                    for (Department subDpt : departments) {
                        if (subDpt.getId() == (rs.getInt("subDpt"))) {
                            if (!dpt.getSubDepartments().contains(subDpt)) {
                                dpt.addSubDepartment(subDpt);
                            }
                        }
                    }
                }
            }
        }
    }

    public List<Department> getResult(){
        return this.departments;
    }
}
