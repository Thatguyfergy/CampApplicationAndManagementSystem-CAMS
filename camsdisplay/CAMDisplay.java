package camsdisplay;

import users.*;
import java.util.Scanner;

import camps.CampArray;

public class CAMDisplay {

    private Scanner sc = new Scanner(System.in);
    private UsersDatabase UserDB = new UsersDatabase("csvfiles\\usersStudent.csv", "csvfiles\\usersStaff.csv");
    private CampArray campArray = new CampArray("csvfiles\\camps.csv");
    private Users user;

    public void startScreen() {
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Reset Password");
        System.out.println("3. Exit");
        System.out.printf("Enter your choice: ");
        int choice = sc.nextInt();
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
        sc.nextLine();
    }

    public void loginScreen() {
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
        }

        // Prompt to change password
        if (password == "password") {
            System.out.println("You are recommended to change your password.");
            System.out.println(
                    "If you set it to the default password again, we will remind you to change your password again.");
            resetPassword(user);
        }

    }

    public void resetPasswordScreen() {
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
        while (true) {
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
                    "8. View Suggestions\n" +
                    "9. Approve Suggestions\n" +
                    "10. Generate Camp Report\n" +
                    "11. Generate Performance Report\n" +
                    "12. Logout\n" +
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
                    // Add the code for option 9 here
                    break;
                case 10:
                    // Add the code for option 10 here
                    break;
                case 11:
                    // Add the code for option 11 here
                    break;
                case 12:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void deleteCampScreen (Staff staff) {
        while (true) {
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
            break; // Exit the viewCampsScreen and return to the main menu
        }
    }

    private void editCampScreen(Staff staff) {
        while (true) {
            System.out.print("\033[H\033[2J"); // Clear the entire screen
            System.out.print(
                    "╔═══════════════════════════════════════════════════════════════╗\n" +
                            "║ Camp Application & Management System - Edit Camp              ║\n" +
                            "╚═══════════════════════════════════════════════════════════════╝\r\n");

            campArray.editCamp(staff.getFirstName()); // TODO

            System.out.print("Press Enter to return to the main menu...");
            sc.nextLine(); // Wait for Enter key
            break; // Exit the viewCampsScreen and return to the main menu
        }
    }

    private void createCampScreen(Staff staff) {
        while (true) {
            System.out.print("\033[H\033[2J"); // Clear the entire screen
            System.out.print(
                    "╔═══════════════════════════════════════════════════════════════╗\n" +
                            "║ Camp Application & Management System - Create Camp            ║\n" +
                            "╚═══════════════════════════════════════════════════════════════╝\r\n");

            campArray.createCamp(staff.getFirstName());

            System.out.print("Press Enter to return to the main menu...");
            sc.nextLine(); // Wait for Enter key
            break; // Exit the viewCampsScreen and return to the main menu
        }
    }

    private void viewCampsScreen(Users user) {
        while (true) {
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
            break; // Exit the viewCampsScreen and return to the main menu
        }
    }

    private void viewCampDetailsScreen(Users user) {
        while (true) {
            System.out.print("\033[H\033[2J"); // Clear the entire screen

            System.out.print(
                    "╔═══════════════════════════════════════════════════════════════╗\n" +
                            "║ Camp Application & Management System - View Camp Details      ║\n" +
                            "╚═══════════════════════════════════════════════════════════════╝\r\n");

            String buffer;
            System.out.print("Enter the name of Camp to view details: ");
            buffer = sc.nextLine();
            campArray.viewCampDetails(buffer);
            System.out.print("Press Enter to return to the main menu...");
            sc.nextLine(); // Wait for Enter key
            break; // Exit the viewCampsScreen and return to the main menu
        }
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