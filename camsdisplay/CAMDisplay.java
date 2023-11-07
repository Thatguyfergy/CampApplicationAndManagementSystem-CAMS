package camsdisplay;

import users.*;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import InfoExchange.EnquiriesArray;
import camps.Camp;
import camps.CampArray;
import report.CampReport;
import utils.*;

public class CAMDisplay {

    private Scanner sc = new Scanner(System.in);
    private CampArray campArray = new CampArray("csvfiles\\camps.csv");
    private EnquiriesArray enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv", "csvfiles\\replies.csv");
    private UsersDatabase UserDB = new UsersDatabase("csvfiles\\usersStudent.csv",
            "csvfiles/usersStaff.csv", campArray);

    private Users user;

    // Hello enric here, need to add the main enquiry array being used, similar
    // concept to camparray just for enquiry

    public void startScreen() {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Reset Password");
        System.out.println("3. Exit");
        System.out.printf("Enter your choice: ");
        int choice = inputInt.nextInt(sc);
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
                    "8. Approve Suggestions\n" +
                    "9. Generate Camp Report\n" +
                    "10. Generate Performance Report\n" +
                    "11. Logout\n" +
                    "Enter your choice: ";
            System.out.print(menu);

            int choice = inputInt.nextInt(sc);
            sc.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    viewCampsScreen(staff);
                    return;
                case 2:
                    viewCampDetailsScreen(staff);
                    return;
                case 3:
                    createCampScreen(staff);
                    return;
                case 4:
                    editCampScreen(staff);
                    return;
                case 5:
                    deleteCampScreen(staff);
                    return;
                case 6:
                    // Add the code for option 6 here
                    return;
                case 7:
                    // Add the code for option 7 here
                    return;
                case 8:
                    // Add the code for option 8 here
                    return;
                case 9:
                    generateCampReportScreen(staff);
                    return;
                case 10:

                    return;
                case 11:
                    startScreen();
                    return;
                default:
                    System.out.println("Invalid choice!");
                    System.out.print("Press Enter to continue...");
                    sc.nextLine(); // Wait for Enter key
                    break;
            }
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
        choice = inputInt.nextInt(sc);
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
        if (campArray.checkCampExists(buffer)) {
            campArray.deleteCamp(buffer);
        } else {
            System.out.println("Camp does not exist!");
        }
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
            int choice = inputInt.nextInt(sc);
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
        String sortBy;
        switch (choice) {
            case "1":
                sortBy = "campName";
                break;
            case "2":
                sortBy = "registrationClosingDate";
                break;
            case "3":
                sortBy = "campVisibility";
                break;
            case "4":
                sortBy = "location";
                break;
            case "5":
                sortBy = "staffInCharge";
                break;
            default:
                System.out.println("Invalid choice! - Sorting by Camp Name");
                sortBy = "campName";
                break;
        }

        campArray.viewCamps(user, sortBy);

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
        if (campArray.checkCampExists(buffer)) {
            campArray.viewCampDetails(buffer);
        } else {
            System.out.println("Camp does not exist!");
        }
        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        userScreen(user);
    }

    private void studentScreen(Student student) {
        int choice = -1;
        int logout = (student.IsCampComm()) ? 14 : 8;
        Scanner sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Home                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Welcome back to CAMs, Student " + student.getID()
                + "! Where we get to explore the amazing camps planned!");
        do {
            System.out.println("1. View Camps");
            System.out.println("2. View Remaining Camp Slots");
            System.out.println("3. Register for Camp");
            System.out.println("4. Submit Enquiry");
            System.out.println("5. Manage your Enquiries");
            System.out.println("6. View Registered Camps");
            System.out.println("7. Withdraw from Camp");
            if (student.IsCampComm()) {
                System.out.println(
                        "\nCommittee Member Options for Camp " + student.getCampCommitteeRole().getCampName() + ":");
                System.out.println("8. View Camp Details");
                System.out.println("9. Submit Suggestion");
                System.out.println("10. View Suggestions");
                System.out.println("11. View Enquiries");
                System.out.println("12. Reply Enquiries");
                System.out.println("13. Generate Camp Report");
                System.out.println("14. Logout");
            } else
                System.out.println("8. Logout");
            System.out.printf("Enter your choice: ");

            choice = inputInt.nextInt(sc);
            System.out.println();
            switch (choice) {
                case 1:
                    student.viewAvailAndRegCamps(campArray);
                    break;
                case 2:
                    student.viewAvailAndRegCamps(campArray);
                    break;
                case 3:
                    System.out.printf("Which camp do you want to register for? ");
                    sc.nextLine(); // flush nextline char
                    String campname = sc.nextLine();
                    System.out.printf("Do you want to register as committee (1) or attendee (2)? ");
                    boolean comm = (inputInt.nextInt(sc) == 1) ? true : false;
                    Camp campPtr = campArray.getCamp(campname);
                    student.registerCamp(campPtr, comm);
                    break;

                case 4:
                    student.listEnquiries();
                    System.out.printf("Select Enquiry to submit, input camp index: ");
                    int campindex = inputInt.nextInt(sc);
                    System.out.printf("Input Enquiry index: ");
                    int enqindex = inputInt.nextInt(sc);
                    student.getAttendee(campindex - 1).submitEnquiry(enquiriesArray, enqindex - 1);
                    break;
                case 5:
                    System.out.printf(
                            "W: WRITE new Enquiry\nV: VIEW current Enquiries\nE: Edit an Enquiry\nEnter your choice: ");
                    String enqChoice = sc.nextLine();
                    switch (enqChoice) {
                        case "W", "w":
                            System.out.println("Write Enquiry under which camp?");
                            for (int i = 0; i < student.getAttendeeArray().size(); i++) {
                                String campAttending = student.getAttendee(i).getCampAttending();
                                System.out.println((i + 1) + ": " + campAttending);
                            }
                            System.out.printf("Select camp index:");
                            int campenqindex = inputInt.nextInt(sc);
                            System.out.println("Please input the Enquiry:");
                            String enq = sc.nextLine();
                            student.getAttendee(campenqindex - 1).createEnquiry(enq);
                            break;
                        case "V", "v":
                            student.listEnquiries();
                            break;
                        case "E", "e":
                            student.listEnquiries();
                            System.out.printf("Select Enquiry to edit, input camp index: ");
                            int campindex1 = inputInt.nextInt(sc);
                            System.out.printf("Input Enquiry index: ");
                            int enqindex1 = inputInt.nextInt(sc);
                            System.out.println("Please input the edited Enquiry:");
                            String newenq = sc.nextLine();
                            student.getAttendee(campindex1 - 1).editEnquiry(newenq, enqindex1 - 1);
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
                case 6:
                    student.viewAvailAndRegCamps(campArray);
                    break;
                case 7:
                    System.out.printf("Enter the name of the camp you are withdrawing from: ");
                    String remCampString = sc.nextLine();
                    Camp remCamp = campArray.getCamp(remCampString);
                    student.withdrawFromCamp(remCamp);
                    break;
                case 8:
                    if (student.IsCampComm()) {
                        // view camp details for own camp
                        student.getCampCommitteeRole().displayCampInfo();

                    } else
                        System.out.println("Logging out... Thank you!");
                    break;

                case 9:
                    if (student.IsCampComm()) {
                        // submit suggestions

                    } else
                        System.out.println("Invalid choice");
                    break;

                case 10:
                    if (student.IsCampComm()) {
                        // view suggestions

                    } else
                        System.out.println("Invalid choice");
                    break;

                case 11:
                    if (student.IsCampComm()) {
                        // view enquiries

                    } else
                        System.out.println("Invalid choice");
                    break;

                case 12:
                    if (student.IsCampComm()) {
                        // reply enquiries

                    } else
                        System.out.println("Invalid choice");
                    break;

                case 13:
                    if (student.IsCampComm()) {
                        // generate camp report
                        student.getCampCommitteeRole().generateReport();

                    } else
                        System.out.println("Invalid choice");
                    break;

                case 14:
                    if (student.IsCampComm()) {
                        System.out.println("Logging out... Thank you!");
                    } else
                        System.out.println("Invalid choice");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != logout);
    }

    // private void committeeMemberScreen(Student student) {
    // System.out.println("╔═════════════════════════════════════════════════════════════════╗");
    // System.out.println("║ Camp Application & Management System - Home ║");
    // System.out.println("╚═════════════════════════════════════════════════════════════════╝");
    // System.out.println("Welcome back to CAMs, Student " + student.getID()
    // + "! Where we get to explore the amazing camps planned!");
    // System.out.println("1. View Camps");
    // System.out.println("2. View Remaining Camp Slots");
    // System.out.println("3. Submit Enquiry");
    // System.out.println("4. View your Enquiries");
    // System.out.println("6. View Registered Camps");
    // System.out.println("7. Withdraw from Camp");
    // System.out.println("Committee Member Options:");
    // System.out.println("8. View Camp Details");
    // System.out.println("9. Submit Suggestion");
    // System.out.println("10. View Suggestions");
    // System.out.println("11. View Enquiries");
    // System.out.println("12. Reply Enquiries");
    // System.out.println("13. Generate Camp Report");
    // System.out.println("14. Logout");
    // System.out.printf("Enter your choice: ");
    // }

}