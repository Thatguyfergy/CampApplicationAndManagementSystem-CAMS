package users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import camdate.CAMDate;
import camps.*;

/**
 * The UsersDatabase class is used to manage and store user information.
 * 
 * @author Kok Chin Yi
 * @version 1.0
 */
public class UsersDatabase {
    private static ArrayList<Users> users = new ArrayList<Users>();
    private static String studentFile = "csvfiles\\students.csv";
    private static String staffFile = "csvfiles\\staff.csv";

    /**
     * The `UsersDatabase` constructor is responsible for initializing the
     * `UsersDatabase` object. It
     * takes three parameters: `studentFile`, `staffFile`, and `campArray`.
     * 
     * @param studentFile
     * @param staffFile
     * @param campArray
     */
    public UsersDatabase(String studentFile, String staffFile, CampArray campArray) {
        UsersDatabase.studentFile = studentFile;
        UsersDatabase.staffFile = staffFile;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(studentFile))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                // CSV Format: Name, Email, Faculty, Password, CampCommCamp,
                // CampA;CampB,busyDates

                // data[4] having issues
                Users user = new Student(data[0].trim(), extractUserIDString(data[1].trim()), data[2].trim(),
                        data[4].trim(), data[5].trim(), data[6].trim(), campArray);
                user.setPassword(data[3].trim());
                users.add(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader csvReader = new BufferedReader(new FileReader(staffFile))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",", -1);
                // CSV Format: Name, Email, Faculty, Password, Staff-in-charge
                Users user = new Staff(data[0], extractUserIDString(data[1]), data[2], data[4]);
                user.setPassword(data[3]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // printUsers();
    }

    /**
     * The function "getFirstName" takes a userID as input and returns the first
     * name of the user with
     * that ID, or null if no user is found.
     * 
     * @param userID The userID parameter is a String that represents the unique
     *               identifier of a user.
     * @return The method is returning the first name of the user with the specified
     *         userID. If a user
     *         with the given userID is found in the "users" list, their first name
     *         is returned. If no user is
     *         found, null is returned.
     */
    public static String getFirstName(String userID) {
        for (Users user : users) {
            if (user.getID().equals(userID))
                return user.getFirstName();
        }
        return null;
    }

    // Overloaded function for CAMDisplay

    /**
     * The function `updateFile()` writes the information of students and staff to
     * separate CSV files.
     */
    public void updateFile() {
        try (FileWriter csvWriterStudent = new FileWriter(studentFile)) {
            for (Users user : users) {
                if (user instanceof Student) {
                    Student userStudent = (Student) user;
                    csvWriterStudent.append(userStudent.getFirstName());
                    csvWriterStudent.append(",");
                    csvWriterStudent.append(userStudent.getID() + "@e.ntu.edu.sg");
                    csvWriterStudent.append(",");
                    csvWriterStudent.append(userStudent.getFacultyInfo());
                    csvWriterStudent.append(",");
                    csvWriterStudent.append(userStudent.getPassword());
                    csvWriterStudent.append(",");
                    if (userStudent.IsCampComm()) {
                        csvWriterStudent.append(userStudent.getCampCommitteeRole().getCampName());
                        csvWriterStudent.append(";");
                        csvWriterStudent.append(String.valueOf(userStudent.getCampCommitteeRole().getPoints()));
                    }

                    csvWriterStudent.append(",");

                    for (CampAttendeeRole attendee : userStudent.getAttendeeArray()) {
                        csvWriterStudent.append(attendee.getCampAttending());
                        csvWriterStudent.append(";");
                    }
                    csvWriterStudent.append(",");
                    for (CAMDate date : userStudent.getBusyDates()) {
                        csvWriterStudent.append(date.toString());
                        csvWriterStudent.append(";");
                    }
                    csvWriterStudent.append("\n");
                }
            }
            csvWriterStudent.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter csvWriterStaff = new FileWriter(staffFile)) {
            for (Users user : users) {
                if (user instanceof Staff) {
                    Staff userStaff = (Staff) user;
                    csvWriterStaff.append(userStaff.getFirstName());
                    csvWriterStaff.append(",");
                    csvWriterStaff.append(userStaff.getID() + "@NTU.EDU.SG");
                    csvWriterStaff.append(",");
                    csvWriterStaff.append(userStaff.getFacultyInfo());
                    csvWriterStaff.append(",");
                    csvWriterStaff.append(userStaff.getPassword());
                    csvWriterStaff.append(",");
                    for (String camp : userStaff.getCampsInCharge()) {
                        csvWriterStaff.append(camp);
                        csvWriterStaff.append(";");
                    }
                    csvWriterStaff.append("\n");
                }
            }
            csvWriterStaff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function extracts the user ID string from an NTU email address.
     * 
     * @param NTUEmail The NTUEmail parameter is a string that represents an email
     *                 address with the
     *                 format "username@ntu.edu.tw".
     * @return The method is returning the user ID string extracted from the
     *         NTUEmail.
     */
    private String extractUserIDString(String NTUEmail) {
        return NTUEmail.split("@")[0];
    }

    /**
     * The login function checks if a user with the given username and password
     * exists and returns the
     * user if found, otherwise it prints an error message and returns null.
     * 
     * @param username The username parameter is a String that represents the
     *                 username entered by the
     *                 user during the login process.
     * @param password The "password" parameter is a String that represents the
     *                 password entered by the
     *                 user during the login process.
     * @return The method is returning an instance of the Users class if the
     *         username and password
     *         match with a user in the users list. If the password is incorrect, it
     *         returns null and prints
     *         "Wrong password!". If the username is not found, it returns null and
     *         prints "Username not
     *         found".
     */
    public Users login(String username, String password) {
        for (Users user : users) {
            if (user.getID().equals(username)) {
                if (user.checkPassword(password.trim())) {
                    return user;
                } else {
                    // Wrong password
                    System.out.println("Wrong password!");
                    return null;
                }
            }
        }
        // User cannot be found
        System.out.println("Username not found");
        return null;
    }

    /**
     * The function changes the password of a user and updates the file.
     * 
     * @param user        The user parameter is an instance of the Users class,
     *                    which represents a user in the
     *                    system. It contains information such as the user's
     *                    username, password, and other details.
     * @param newpassword The new password that the user wants to set for their
     *                    account.
     */
    public void changePassword(Users user, String newpassword) {
        user.setPassword(newpassword);
        updateFile();
    }

    /**
     * The function "getStudentbyID" returns a Student object based on the provided
     * ID if it exists in
     * the "users" list.
     * 
     * @param id The parameter "id" is a String representing the ID of the student
     *           we want to retrieve.
     * @return The method is returning a Student object.
     */
    public Student getStudentbyID(String id) {
        for (Users user : users) {
            if (user.getID().equals(id)) {
                if (user instanceof Student) {
                    return (Student) user;
                }
            }
        }
        return null;
    }

    /**
     * The function deletes a camp by removing it from the staff in charge and
     * deleting it from the
     * students' schedules.
     * 
     * @param campName     The name of the camp that needs to be deleted.
     * @param deletedDates The deletedDates parameter is an ArrayList of CAMDate
     *                     objects. It is used to
     *                     specify the dates that should be deleted for the camp.
     */
    public void deleteCamp(String campName, ArrayList<CAMDate> deletedDates) {
        for (Users user : users) {
            if (user instanceof Staff) {
                Staff userStaff = (Staff) user;
                if (userStaff.checkStaffInCharge(campName)) {
                    userStaff.deleteCamp(campName);
                }
            } else if (user instanceof Student) {
                Student userStudent = (Student) user;
                userStudent.deleteCamp(campName, deletedDates);
            }
        }
    }

    /**
     * The function iterates through a list of users and if a user is a student, it
     * calls the editCamp
     * method on that student object.
     * 
     * @param oldName The old name of the camp that needs to be edited.
     * @param newName The new name that will replace the old name in the student's
     *                camp.
     */
    public void studentEditCamp(String oldName, String newName) {
        for (Users user : users) {
            if (user instanceof Student) {
                Student userStudent = (Student) user;
                userStudent.editCamp(oldName, newName);
            }
        }
    }
}
