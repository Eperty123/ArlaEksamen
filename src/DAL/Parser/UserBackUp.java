package DAL.Parser;


import BE.CSVUser;
import BE.Department;
import BE.User;
import GUI.Model.DepartmentModel;
import GUI.Model.UserModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * This class creates a backup of users, parsing the User objects to csv format, and writing them to a new csv file.
 */
public class UserBackUp {
    private final String HEADER_INFO_FORMAT = "Id;FirstName;LastName;UserName;Email;PhoneNumber;UserRole;Password;Gender;DepartmentId;Title\n";

    public List<CSVUser> importUsers(String filePath) {
        ArrayList<CSVUser> importedUsers = new ArrayList<>();
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

                // Check if an existing user with the id exists, if none import it.
                // If no id, check for first, last name, department and phone number.
                var existing = userModel.getUser(id);
                if (existing == null) {
                    // No existing found, check some other criteria in case a user is found with the same name.
                    if (userModel.getUser(parsedUser.getFirstName(), parsedUser.getLastName()) == null && departmentModel.getUser(parsedUser.getUserName()) == null && departmentModel.getUser(parsedUser.getFirstName(), parsedUser.getLastName()) == null) {
                        importedUsers.add(parsedUser);
                        System.out.println(String.format("User: %s %s (%d) imported.", parsedUser.getFirstName(), parsedUser.getLastName(), parsedUser.getId()));
                    }
                } else
                    System.out.println(String.format("User: %s %s (%d) already exists! Ignored.", parsedUser.getFirstName(), parsedUser.getLastName(), parsedUser.getId()));

                //System.out.println(String.format("User: %s %s (%d).", parsedUser.getFirstName(), parsedUser.getLastName(), parsedUser.getPhone()));
            }
        } else System.out.println(String.format("The backup file: %s doesn't exist!", filePath));

        return importedUsers;
    }

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

    private String getFileName() {
        return "src/Resources/User_backup_" + LocalDate.now() + "_" + LocalTime.now().hashCode() + ".csv";
    }


}
