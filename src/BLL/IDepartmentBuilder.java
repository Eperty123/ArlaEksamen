package BLL;

import BE.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface IDepartmentBuilder {

    void makeDepartment(ResultSet rs) throws SQLException;

    void addUsers(ResultSet rs) throws SQLException;

    void addSubdepartments(ResultSet rs) throws SQLException;

}
