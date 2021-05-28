package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentCRUD {

    List<Department> getSuperDepartments();

    ObservableList<Department> getAllDepartments();

    void addDepartment(Department department) throws SQLException;

    void removeDepartment(Department department);

    void editDepartment(Department department) throws SQLException;

    void deleteDepartment(Department d) throws SQLException;

    void addSubDepartment(Department department, Department subDepartment) throws SQLException;

    boolean hasDepartmentsLoaded();
}
