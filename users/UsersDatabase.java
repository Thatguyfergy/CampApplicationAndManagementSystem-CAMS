package users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import camdate.CAMDate;
import camps.*;

public class UsersDatabase {
    private static ArrayList<Users> users = new ArrayList<Users>();
    private static String studentFile = "csvfiles\\students.csv";
    private static String staffFile = "csvfiles\\staff.csv";

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
        printUsers();
    }

    private void printUsers() {
        System.out.println("Printing Users");
        for (Users user : users) {
            System.out.println(user.getID() + "|" + user.getPassword());
        }
    }

    public String getFirstName(String userID) {
        for (Users user : users) {
            if (user.getID().equals(userID))
                return user.getFirstName();
        }
        return null;
    }

    // Overloaded function for CAMDisplay

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

    private String extractUserIDString(String NTUEmail) {
        return NTUEmail.split("@")[0];
    }

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

    public void changePassword(Users user, String newpassword) {
        user.setPassword(newpassword);
        updateFile();
    }

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
}
