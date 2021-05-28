package BLL;

import BE.Department;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DepartmentExtension {

    /**
     * Export a number of departments to the Resource folder.
     *
     * @param departments The departments to export phone numbers of.
     */
    public static void exportPhoneNumbers(List<Department> departments) {
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

    /**
     * Export a number of departments to the given output file path.
     *
     * @param departments The departments to export phone numbers of.
     * @param outputFile  The output file path to save the backup.
     */
    public static void exportPhoneNumbers(List<Department> departments, String outputFile) {
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

    /**
     * Export a number of departments to the given File instance.
     *
     * @param departments The departments to export phone numbers of.
     * @param outputFile  The output File instance to use to write the backup.
     */
    public static void exportPhoneNumbers(List<Department> departments, File outputFile) {
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
