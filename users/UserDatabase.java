package users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class UserDatabase {
    private static String usersStudentFile;
    private static String usersStaffFile;

    private static ArrayList<Users> UserDB = new ArrayList<Users>();

    public UserDatabase(String usersStudentFile, String usersStaffFile) {
        // Need to wait for enric
        UserDatabase.usersStudentFile = usersStudentFile;
        UserDatabase.usersStaffFile = usersStaffFile;

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(usersStudentFile));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Users user;
                // CSV Format: KIV
                user = new Student(data[0], "nimama", "womama");
                user.setPassword(data[0]); // dummy index
                UserDB.add(user);
            }
            csvReader.close();

            csvReader = new BufferedReader(new FileReader(usersStaffFile));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Users user;
                // CSV Format: First Name (not used) | NTUEmail | FacultyInfo | password
                user = new Staff(data[0], extractUserIDString(data[1]), data[2]);
                user.setPassword(data[3]);
                UserDB.add(user);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Users login(String username, String password) {
        for (Users user : UserDB) {
            if (user.getID() == username) {
                if (user.checkPassword(password)) {
                    return user;
                } else {
                    // Wrong password
                    return null;
                }
            }
        }
        // User cannot be found
        return null;
    }

    public void changePassword(Users user, String newpassword) {
        user.setPassword(newpassword);
    }

    private String extractUserIDString(String NTUemail) {
        NTUemail = NTUemail.replace("@NTU.EDU.SG", "");
        NTUemail = NTUemail.replace("@e.ntu.edu.sg", "");
        return NTUemail;
    }

    private void updateFile() throws Exception {
        for (Users user : UserDB) {
            FileWriter csvWriterStudent = new FileWriter(usersStudentFile);
            FileWriter csvWriterStaff = new FileWriter(usersStaffFile);
            if (user instanceof Student) {
                // Do urself @enric
            } else if (user instanceof Staff) {
                csvWriterStaff.append(user.getFirstName());
                csvWriterStaff.append(",");
                csvWriterStaff.append(user.getID() + "@NTU.EDU.SG");
                csvWriterStaff.append(",");
                csvWriterStaff.append(user.getFacultyInfo());
                csvWriterStaff.append(",");
                csvWriterStaff.append(user.getPassword());
                csvWriterStaff.append("\n");
            }
        }
    }

}
