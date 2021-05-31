package DAL.Parser;


import BE.CSVUser;
import BE.Department;
import BE.User;
import GUI.Controller.PopupControllers.WarningController;
import GUI.Model.DataModel;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class creates a backup of users, parsing the User objects to csv format, and writing them to a new csv file.
 */
public class UserBackUp {
    private final String HEADER_INFO_FORMAT = "Id;FirstName;LastName;UserName;Email;PhoneNumber;UserRole;Password;Gender;DepartmentId;Title\n";

    /**
     * Import a csv backup from the given file path.
     *
     * @param filePath The file path of the csv file to import.
     * @return Returns list of CSVUser.
     */
    public List<CSVUser> importUsers(String filePath) throws Exception {
        List<CSVUser> importedUsers = new ArrayList<>();
        var userModel = UserModel.getInstance();
        var departmentModel = DepartmentModel.getInstance();

        var file = new File(filePath);
        if (file.exists()) {
            // Read csv and import the users.
            var csvReader = new CSVParser(filePath);
            var parsedData = csvReader.getParsedData();
            for (int i = 1; i < parsedData.size(); i++) {
                var line = parsedData.get(i);

                // Get the individual columns...
                int id = Integer.parseInt(line[0]);
                String firstName = line[1];
                String lastName = line[2];
                String userName = line[3];
                String email = line[4];
                int phoneNumber = Integer.parseInt(line[5]);
                int userRole = Integer.parseInt(line[6]);
                int password = Integer.parseInt(line[7]);
                int gender = Integer.parseInt(line[8]);
                int department = Integer.parseInt(line[9]);
                String title = line[10];
                CSVUser parsedUser = new CSVUser(id, firstName, lastName, userName, email, phoneNumber, userRole, password, gender, department, title);

                parsedUser.setDepartment(DepartmentModel.getInstance().getDepartment(department));

                // Check if an existing user with the id exists, if none import it.
                // If no id, check for first, last name, department and phone number.
                var existing = userModel.getUserById(id);
                if (existing == null) {

                    Department desiredDepartment = DepartmentModel.getInstance().getDepartment(department);

                    // I
                    if (desiredDepartment != null) {
                        // No existing found, check some other criteria in case a user is found with the same name.
                        if (userModel.getUserByFirstLastName(parsedUser.getFirstName(), parsedUser.getLastName()) == null && userModel.getUserByUsername(parsedUser.getUserName()) == null && departmentModel.getUser(parsedUser.getUserName()) == null && userModel.getUserByEmail(parsedUser.getEmail()) == null) {
                            importedUsers.add(parsedUser);
                        }
                    } else {
                        if (userModel.getUserByFirstLastName(parsedUser.getFirstName(), parsedUser.getLastName()) == null && userModel.getUserByUsername(parsedUser.getUserName()) == null && userModel.getUserByEmail(parsedUser.getEmail()) == null) {
                            importedUsers.add(parsedUser);
                        }
                    }
                }

                //System.out.println(String.format("User: %s %s (%d).", parsedUser.getFirstName(), parsedUser.getLastName(), parsedUser.getPhone()));
            }
            importedUsers.forEach(u -> {
                try {
                    DataModel.getInstance().addUser(u, u.getDepartment());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }

        return importedUsers;
    }

    /**
     * Export all the departments's users to the given file path.
     *
     * @param departments The departments to export.
     * @param outputFile  The output file path to save the backup.
     */
    public void exportUsers(List<Department> departments, String outputFile) {
        //File csvOutputFile = new File(getFileName());
        File csvOutputFile = new File(outputFile);

        try (FileWriter csvWriter = new FileWriter(csvOutputFile)) {
            csvWriter.append(HEADER_INFO_FORMAT);

            for (Department department : departments) {
                for (User u : department.getUsers()) {
                    var csvUser = new CSVUser(u, department.getId());
                    csvWriter.append(csvUser.toCSV() + "\n");
                }
            }

            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export all the departments's users to the given file path.
     *
     * @param departments The departments to export.
     * @param outputFile  The File instance to use for export.
     */
    public void exportUsers(List<Department> departments, File outputFile) {
        //File csvOutputFile = new File(getFileName());

        try (FileWriter csvWriter = new FileWriter(outputFile)) {
            csvWriter.append(HEADER_INFO_FORMAT);

            for (Department department : departments) {
                for (User u : department.getUsers()) {
                    var csvUser = new CSVUser(u, department.getId());
                    csvWriter.append(csvUser.toCSV() + "\n");
                }
            }

            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export all the departments's users to the Resource folder.
     *
     * @param departments The departments to export.
     */
    public void exportUsers(List<Department> departments) {
        File csvOutputFile = new File(getFileName());
        try (FileWriter csvWriter = new FileWriter(csvOutputFile)) {
            csvWriter.append(HEADER_INFO_FORMAT);

            for (Department department : departments) {
                for (User u : department.getUsers()) {
                    var csvUser = new CSVUser(u, department.getId());
                    csvWriter.append(csvUser.toCSV() + "\n");
                }
            }

            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Export the department's users to the Resource folder.
     *
     * @param department The department to export.
     */
    public void exportUsers(Department department) {

        File csvOutputFile = new File(getFileName());

        try (FileWriter csvWriter = new FileWriter(csvOutputFile)) {
            csvWriter.append(HEADER_INFO_FORMAT);

            for (User u : department.getUsers()) {
                var csvUser = new CSVUser(u, department.getId());
                csvWriter.append(csvUser.toCSV() + "\n");
            }

            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a random generated file name.
     *
     * @return Returns the generated file name.
     */
    private String getFileName() {
        return "src/Resources/User_backup_" + LocalDate.now() + "_" + LocalTime.now().hashCode() + ".csv";
    }


}
