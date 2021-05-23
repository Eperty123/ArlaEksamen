package DAL.Parser;


import BE.User;
import DAL.DbConnector.DbConnectionHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * This class creates a backup of users, parsing the User objects to csv format, and writing them to a new csv file.
 */
public class UserBackUp {

    private DbConnectionHandler dbCon = DbConnectionHandler.getInstance();
    private final String HEADER_INFO_FORMAT = "id;FirstName;LastName;UserName;Email;PhoneNumber;UserRole;Password;Gender;DepartmentId;Title\n";


    public void backUpUsers(List<User> users){

        File csvOutputFile = new File(getFileName());

        try(FileWriter csvWriter = new FileWriter(csvOutputFile)){
            csvWriter.append(HEADER_INFO_FORMAT);

            for(User u : users){
                csvWriter.append(u.toString() + "\n");
            }

            csvWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void writeHeaderData(FileWriter csvWriter) {

    }

    private String getFileName() {
        return "User_backup_" + LocalDate.now() + "_" + LocalTime.now().hashCode() + ".csv";
    }




}
