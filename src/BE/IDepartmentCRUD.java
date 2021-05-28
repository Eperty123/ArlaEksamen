package BE;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface IDepartmentCRUD {

    List<Department> getSuperDepartments();

    ObservableList<Department> getAllDepartments();

    /**
     * Add a new department to the database.
     *
     * @param department The department to add.
     * @throws SQLException
     */
    void addDepartment(Department department) throws SQLException;

    /**
     * Remove the given department from the database.
     *
     * @param department The department to remove.
     */
    void removeDepartment(Department department);

    /**
     * Edit the given department.
     *
     * @param department The department to edit.
     * @throws SQLException
     */
    void editDepartment(Department department) throws SQLException;

    /**
     * Remove the specified department from the database.
     *
     * @param d The department to remove.
     * @throws SQLException
     */
    void deleteDepartment(Department d) throws SQLException;

    /**
     * Add a sub department to the given main department.
     *
     * @param department    The main department to add the sub department to.
     * @param subDepartment The sub department to add to the main department.
     * @throws SQLException
     */
    void addSubDepartment(Department department, Department subDepartment) throws SQLException;

    /**
     * Does departments exist in the database at all?
     *
     * @return Returns true if yes otherwise false.
     */
    boolean hasDepartmentsLoaded();
}
