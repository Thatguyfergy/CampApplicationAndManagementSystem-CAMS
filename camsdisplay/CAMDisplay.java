package camsdisplay;

import users.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import camps.Camp;
import camps.CampArray;
import report.CampReport;

public class CAMDisplay {

    private Scanner sc = new Scanner(System.in);
    private UsersDatabase UserDB = new UsersDatabase("SC2002Project\\csvfiles\\usersStudent.csv",
            "SC2002Project/csvfiles/usersStaff.csv");
    private CampArray campArray = new CampArray("SC2002Project\\csvfiles\\camps.csv");
    private Users user;

    public CAMDisplay() {
        try {
            // Attempt to initialize with the default file paths
            UserDB = new UsersDatabase("csvfiles\\usersStudent.csv", "csvfiles\\usersStaff.csv");
            campArray = new CampArray("csvfiles\\camps.csv");
        } catch (FileNotFoundException e) {
            // If FileNotFoundException occurs, handle it
            System.out.println("File not found. Please enter the correct file paths:");

            // Prompt the user to enter the correct file paths
            System.out.print("Enter path for usersStudent.csv: ");
            String usersStudentPath = sc.nextLine();
            System.out.print("Enter path for usersStaff.csv: ");
            String usersStaffPath = sc.nextLine();
            System.out.print("Enter path for camps.csv: ");
            String campsPath = sc.nextLine();

            // Attempt to initialize again with the user-provided file paths
            UserDB = new UsersDatabase(usersStudentPath, usersStaffPath);
            campArray = new CampArray(campsPath);
        }

    }

    public void startScreen() {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Reset Password");
        System.out.println("3. Exit");
        System.out.printf("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        switch (choice) {
            case 1:
                loginScreen();
                break;
            case 2:
                resetPasswordScreen();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        // sc.nextLine();
        userScreen(user);

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void loginScreen() {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        String userID, password;
        // sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Login                    ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Enter your userID: ");
        userID = sc.nextLine();
        System.out.println("Enter your password: ");
        password = sc.nextLine();
        // ==TODO==: Search database if got that userID & match password
        // if got, then set user to that user & print out login success

        user = UserDB.login(userID, password);

        if (user == null) {
            System.out.println("UserID cannot be found/Password is incorrect.");
            System.out.println("Please login again.");
            return;
        }

        // Prompt to change password
        if (password.equals("password")) {
            System.out.println("You are recommended to change your password.");
            System.out.println(
                    "If you set it to the default password again, we will remind you to change your password again.");
            resetPassword(user);
        }

    }

    public void resetPasswordScreen() {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        String userID, oldPassword;
        // Scanner sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Password Reset           ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");

        System.out.println("Enter your userID: ");
        userID = sc.nextLine();
        System.out.println("Enter your previous password: ");
        oldPassword = sc.nextLine();
        user = UserDB.login(userID, oldPassword);

        if (user == null) {
            System.out.println("UserID cannot be found/Password is incorrect.");
            System.out.println("Please try again.");
            return;
        }

        resetPassword(user);
    }

    private void resetPassword(Users user) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        String newPassword1, newPassword2;
        do {
            System.out.println("Enter your new password: ");
            newPassword1 = sc.nextLine();
            System.out.println("Enter your new password again: ");
            newPassword2 = sc.nextLine();

            // ==TODO==: Search database if got that userID & match password
            // if got, check if the entered password are the same if same then set new
            // password
            if (newPassword1.equals(newPassword2)) {
                UserDB.changePassword(user, newPassword1);
            } else {
                System.out.println("Your passwords are different. Try again.");
            }
        } while (!newPassword1.equals(newPassword2));
    }

    public void userScreen(Users user) {
        if (user instanceof Staff) {
            staffScreen((Staff) user);
        } else if (user instanceof Student) {
            studentScreen((Student) user);
        }
    }

    private void staffScreen(Staff staff) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen

        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Home                   ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");
        System.out.print("Welcome back to CAMs, Staff " + staff.getID() + "\r\n");
        String menu = "1. View Camps\n" +
                "2. View Camp Details\n" +
                "3. Create Camp\n" +
                "4. Edit Camp\n" +
                "5. Delete Camp\n" +
                "6. View Enquiries\n" +
                "7. View Suggestions\n" +
                "8. Approve Suggestions\n" +
                "9. Generate Camp Report\n" +
                "10. Generate Performance Report\n" +
                "11. Logout\n" +
                "Enter your choice: ";
        System.out.print(menu);

        int choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        switch (choice) {
            case 1:
                viewCampsScreen(staff);
                break;
            case 2:
                viewCampDetailsScreen(staff);
                break;
            case 3:
                createCampScreen(staff);
                break;
            case 4:
                editCampScreen(staff);
                break;
            case 5:
                deleteCampScreen(staff);
                break;
            case 6:
                // Add the code for option 6 here
                break;
            case 7:
                // Add the code for option 7 here
                break;
            case 8:
                // Add the code for option 8 here
                break;
            case 9:
                generateCampReportScreen(staff);
                break;
            case 10:
                
                break;
            case 11:
                startScreen();
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private void generateCampReportScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Camp Report   ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");
        String campName;
        int choice;
        System.out.print("Enter the name of Camp to generate report: ");
        campName = sc.nextLine();
        System.out.print("Filter:\n" +
                "1. None\n" +
                "2. Camp Attendees\n" +
                "3. Camp Committee Members\n" +
                "Enter your choice: ");
        choice = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        System.out.println(); // for readability

        Camp camp = campArray.getCamp(campName);
        if (camp == null) {
            System.out.println("Camp does not exist!");
        } else {
            CampReport campReport = new CampReport(camp, choice);
            campReport.generateReport();
        }

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);
    }

    private void deleteCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Delete Camp            ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        String buffer;
        System.out.print("Enter the name of Camp to delete: ");
        buffer = sc.nextLine();

        campArray.deleteCamp(buffer);
        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        // Exit the viewCampsScreen and return to the main menu
        userScreen(user);

    }

    private void editCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Edit Camp              ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        System.out.print("Enter the name of Camp to edit: ");
        String buffer = sc.nextLine();
        if (campArray.checkCampExists(buffer)) {
            campArray.viewCampDetails(buffer);
            System.out.print("What field would you like to edit?\n" +
                    "1. Camp Name\n" +
                    "2. Registration Closing Date\n" +
                    "3. Camp Visibility\n" +
                    "4. Location\n" +
                    "5. Total Slots\n" +
                    "6. Committee Members Slots\n" +
                    "7. Camp Description\n" +
                    "8. Staff In Charge\n" +
                    "9. Add Dates\n" +
                    "10. Remove Dates\n" +
                    "11. Exit\n" +
                    "Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume the newline character
            staff.editCamp(campArray, choice, buffer);
        } else {
            System.out.println("Camp does not exist!");
        }

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);

    }

    private void createCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Create Camp            ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        campArray.createCamp(staff.getFirstName());

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);
    }

    private void viewCampsScreen(Users user) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen

        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camps             ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        String choice;
        System.out.print("Sort by:\n" +
                "1. Camp Name\n" +
                "2. Camp Registration Closing Date\n" +
                "3. Camp Visibility\n" +
                "4. Camp location\n" +
                "5. Camp Staff-In-Charge\n" +
                "Enter your choice: ");
        choice = sc.nextLine();
        System.out.println();
        switch (choice) {
            case "1":
                campArray.sortCamps("campName");
                break;
            case "2":
                campArray.sortCamps("registrationClosingDate");
                break;
            case "3":
                campArray.sortCamps("campVisibility");
                break;
            case "4":
                campArray.sortCamps("location");
                break;
            case "5":
                campArray.sortCamps("staffInCharge");
                break;
            default:
                System.out.println("Invalid choice! - Sorting by Camp Name");
                break;
        }

        campArray.viewCamps(user);

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);
    }

    private void viewCampDetailsScreen(Users user) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen

        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camp Details      ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        String buffer;
        System.out.print("Enter the name of Camp to view details: ");
        buffer = sc.nextLine();
        System.out.println(); // for readability
        campArray.viewCampDetails(buffer);
        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);
    }

    public void studentScreen(Student student) {
        // ==TODO==: if student is committeMember use committeeMemberScreen else use
        // attendeeScreen
    }

    private void attendeeScreen(Student student) {
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Home                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Welcome back to CAMs, Student " + student.getID()
                + "! Where we get to explore the amazing camps planned!");
        System.out.println("1. View Camps");
        System.out.println("2. View Remaining Camp Slots");
        System.out.println("3. Submit Enquiry");
        System.out.println("4. View your Enquiries");
        System.out.println("6. View Registered Camps");
        System.out.println("7. Withdraw from Camp");
        System.out.println("8. Logout");
        System.out.printf("Enter your choice: ");
    }

    private void committeeMemberScreen(Student student) {
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Home                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Welcome back to CAMs, Student " + student.getID()
                + "! Where we get to explore the amazing camps planned!");
        System.out.println("1. View Camps");
        System.out.println("2. View Remaining Camp Slots");
        System.out.println("3. Submit Enquiry");
        System.out.println("4. View your Enquiries");
        System.out.println("6. View Registered Camps");
        System.out.println("7. Withdraw from Camp");
        System.out.println("Committee Member Options:");
        System.out.println("8. View Camp Details");
        System.out.println("9. Submit Suggestion");
        System.out.println("10. View Suggestions");
        System.out.println("11. View Enquiries");
        System.out.println("12. Reply Enquiries");
        System.out.println("13. Generate Camp Report");
        System.out.println("14. Logout");
        System.out.printf("Enter your choice: ");
    }

}