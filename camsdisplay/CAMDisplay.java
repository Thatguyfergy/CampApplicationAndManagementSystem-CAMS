package camsdisplay;

import users.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;
import infoexchange.EnquiriesArray;
import infoexchange.Suggestion;
import infoexchange.SuggestionArray;
import report.*;
import utils.*;

public class CAMDisplay {

    private Scanner sc = new Scanner(System.in);
    private CampArray campArray = new CampArray("csvfiles\\camps.csv");
    private EnquiriesArray enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv", "csvfiles\\replies.csv");
    private UsersDatabase UserDB = new UsersDatabase("csvfiles\\usersStudent.csv",
            "csvfiles/usersStaff.csv", campArray);
    private SuggestionArray suggestionArray = new SuggestionArray("csvfiles\\suggestions.csv", UserDB, campArray);

    private Users user;

    // Hello enric here, need to add the main enquiry array being used, similar
    // concept to camparray just for enquiry

    public void startScreen() {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.println();
        System.out.println(" ██████╗ █████╗ ███╗   ███╗███████╗");
        System.out.println("██╔════╝██╔══██╗████╗ ████║██╔════╝");
        System.out.println("██║     ███████║██╔████╔██║███████╗");
        System.out.println("██║     ██╔══██║██║╚██╔╝██║╚════██║");
        System.out.println("╚██████╗██║  ██║██║ ╚═╝ ██║███████║");
        System.out.println(" ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝");
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("1. Login");
        // System.out.println("2. Reset Password");
        System.out.println("2. Exit");
        System.out.printf("Enter your choice: ");
        int choice = inputInt.nextInt(sc);
        sc.nextLine(); // Consume the newline character
        switch (choice) {
            case 1:
                loginScreen();
                break;
            // case 2:
            // resetPasswordScreen();
            // break;
            case 2:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        // sc.nextLine();
        userScreen(user);

        try {
            TimeUnit.SECONDS.sleep(1);
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
            System.out.println();
            System.out.println("You are recommended to change your password.");
            System.out.println(
                    "If you set it to the default password again, we will remind you to change your password again.");
            resetPassword(user);
            return;
        }

    }

    public void resetPasswordScreen(Users user) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        // Scanner sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Password Reset           ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");

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
            System.out.print("Welcome back to CAMs, Staff " + UsersDatabase.getFirstName(staff.getID()) + " of "
                    + staff.getFacultyInfo() + "\r\n");
            String menu = "0. Reset Password\n" +
                    "1. View Camps\n" +
                    "2. View Camp Details\n" +
                    "3. Create Camp\n" +
                    "4. Edit Camp\n" +
                    "5. Delete Camp\n" +
                    "6. View Enquiries\n" +
                    "7. Reply Enquiries\n" +
                    "8. View Suggestions\n" +
                    "9. Process Suggestions\n" +
                    "10. Generate Camp Report\n" +
                    "11. Generate Performance Report\n" +
                    "12. Logout\n" +
                    "Enter your choice: ";
            System.out.print(menu);

            int choice = inputInt.nextInt(sc);
            sc.nextLine(); // Consume the newline character
            switch (choice) {
                case 0:
                    resetPasswordScreen(user);
                    System.out.println("Please login again!");
                    return;
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
                    // View enquiries
                    System.out.print("\033[H\033[2J");
                    enquiriesArray.viewEnquiries(staff);
                    ScreenClearFn();
                    break;
                case 7:
                    // reply enquiries
                    System.out.print("\033[H\033[2J");
                    enquiriesArray.replyEnquiry(staff);
                    ScreenClearFn();
                    break;
                case 8:
                    // View Suggestions
                    viewSuggestionsScreen(staff);
                    break;
                case 9:
                    // Process Suggestions
                    processSuggestionScreen(staff);
                    break;
                case 10:
                    generateCampReportScreen(staff);
                    break;
                case 11:
                    // Generate performance report
                    campPerfReportScreen(staff);
                    break;
                case 12:
                    this.user = null;
                    return;
                default:
                    System.out.println("Invalid choice!");
                    System.out.print("Press Enter to continue...");
                    sc.nextLine(); // Wait for Enter key
                    break;
            }
        }
    }

    private void processSuggestionScreen(Staff staff) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Process Suggestions           ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");


        suggestionArray.processSuggestion(staff);
        UserDB.updateFile(); // for change in points
        ScreenClearFn();
    }

    private void viewSuggestionsScreen(Staff staff) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Suggestions                   ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");


        suggestionArray.viewSuggestions(staff);
        ScreenClearFn();
    }

    private void campPerfReportScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Performance Report   ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        ArrayList<String> createdCamps = staff.getCampsInCharge();
        int campChoice;
        int choice;

        if (createdCamps.size() == 0) {
            System.out.println("You have not created any camps!");
            ScreenClearFn();
            return;
        }
        System.out.println("Select a camp to generate report for: ");
        for (String camp : createdCamps) {
            System.out.println(createdCamps.indexOf(camp) + 1 + ". " + camp);
        }
        System.out.println(createdCamps.size() + 1 + ". Exit (not a camp name)");

        do {
            System.out.printf("Enter choice: ");
            campChoice = inputInt.nextInt(sc);
            if (campChoice == createdCamps.size() + 1) {
                System.out.print("\033[H\033[2J"); // Clear the entire screen
                return;
            }
        } while ((campChoice > createdCamps.size() + 1) || (campChoice <= 0));

        Camp camp = campArray.getCamp(createdCamps.get(campChoice - 1));

        System.out.print("Sort By:\n" +
                "1. Name\n" +
                "2. Points\n" +
                "Enter your choice: ");
        choice = inputInt.nextInt(sc);
        sc.nextLine(); // Consume the newline character
        System.out.println(); // for readability

        PerfReport perfReport = new PerfReport(camp, choice, UserDB, staff);
        perfReport.generateReport();

        ScreenClearFn();
        return;
    }

    private void generateCampReportScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Camp Report   ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        CampReport campReport = new CampReport(staff, campArray);
        campReport.generateReport();

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        return;
    }

    private void deleteCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Delete Camp            ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        campArray.deleteCamp(staff);
        UserDB.updateFile();

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        // Exit the viewCampsScreen and return to the main menu
        return;

    }

    private void editCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Edit Camp              ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");
        campArray.editCamp(staff, enquiriesArray, UserDB);
        UserDB.updateFile();

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        return;
    }

    private void createCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Create Camp            ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        staff.createCamp(campArray);
        UserDB.updateFile();
        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        return;
    }

    private void viewCampsScreen(Users user) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen

        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camps             ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        campArray.viewCamps(user);

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        return;
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
            campArray.viewCampDetails(buffer, user);
        } else {
            System.out.println("Camp does not exist!");
        }
        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        return;
    }

    private void studentScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        int choice = -1;
        int logout = (student.IsCampComm()) ? 11 : 5;
        Scanner sc = new Scanner(System.in);

        do {
            logout = (student.IsCampComm()) ? 11 : 5;
            // System.out.println(logout);
            // System.out.print("\033[H\033[2J"); // Clear the entire screen
            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Camp Application & Management System - Home                     ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            System.out.println("Welcome back to CAMs, Student " + UsersDatabase.getFirstName(student.getID()) + " of "
                    + student.getFacultyInfo() + "!");
            System.out.println("0. Reset password");
            System.out.println("1. View Available/Registered Camps");
            System.out.println("2. Register for Camp");
            System.out.println("3. Manage your Enquiries");
            System.out.println("4. Withdraw from Camp");
            if (student.IsCampComm()) {
                System.out.println(
                        "\nCommittee Member Options for Camp " + student.getCampCommitteeRole().getCampName() + ":");
                System.out.println("5. View Camp Details");
                System.out.println("6. View Points");
                System.out.println("7. Manage Suggestions");
                System.out.println("8. View Enquiries");
                System.out.println("9. Reply Enquiries");
                System.out.println("10. Generate Camp Report");
                System.out.println("11. Logout");
            } else
                System.out.println("5. Logout");
            System.out.printf("Enter your choice: ");

            choice = inputInt.nextInt(sc);
            System.out.println();
            switch (choice) {
                case 0:
                    resetPasswordScreen(user);
                    System.out.println("Please login again!");
                    return;
                case 1:
                    student.viewAvailAndRegCamps(campArray);
                    break;
                case 2:
                    student.viewAvailAndRegCamps(campArray);
                    String campname;
                    System.out.printf("\nWhich camp do you want to register for? ");
                    sc.nextLine(); // flush nextline char
                    campname = sc.nextLine();
                    do {
                        if (campArray.checkCampExists(campname))
                            break;
                        System.out.println("No such Camp! Please try again.");
                        System.out.printf("\nWhich camp do you want to register for? ");
                        campname = sc.nextLine();
                    } while (true);
                    System.out.printf("Do you want to register as committee (1) or attendee (2)? ");
                    boolean comm = (inputInt.nextInt(sc) == 1) ? true : false;
                    Camp campPtr = campArray.getCamp(campname);
                    if (comm)
                        student.registerCampCommittee(campPtr, campArray);
                    else
                        student.registerCampAttendee(campPtr, campArray);
                    UserDB.updateFile();
                    break;
                case 3:
                    System.out.println(
                            "W: WRITE new Enquiry\nV: VIEW current Enquiries\nE: Edit an Enquiry\n" +
                                    "S: Submit an Enquiry\nR: View submitted Enquiries and REPLIES\nEnter your choice: ");
                    sc.nextLine();
                    String enqChoice = sc.nextLine();
                    System.out.println();
                    switch (enqChoice) {
                        case "W", "w":
                            System.out.println("\nFILTERING and SORTING Camps to View.");
                            student.viewAvailAndRegCamps(campArray);
                            System.out.println();
                            System.out.printf("Write Enquiry under which camp?\nPlease input Camp Name: ");
                            // sc.nextLine();
                            String enqcamp = sc.nextLine();
                            System.out.printf("Please input the Enquiry: ");
                            // sc.nextLine();
                            String enq = sc.nextLine();
                            student.createEnquiry(enq, enqcamp);
                            break;
                        case "V", "v":
                            student.viewEnquiries();
                            System.out.println("\nEND of Enquiries\n");
                            break;
                        case "E", "e":
                            student.viewEnquiries();
                            System.out.println();
                            System.out.printf("Select Enquiry to edit, input Enquiry index: ");
                            int enqindex1 = inputInt.nextInt(sc);
                            System.out.printf("Please input the edited Enquiry: ");
                            sc.nextLine();
                            String newenq = sc.nextLine();
                            student.editEnquiry(newenq, enqindex1 - 1);
                            break;
                        case "S", "s":
                            student.viewEnquiries();
                            System.out.println();
                            System.out.printf("Select Enquiry to submit, input Enquiry index: ");
                            int enqindex = inputInt.nextInt(sc);
                            student.submitEnquiry(enquiriesArray, enqindex - 1);
                            break;
                        case "R", "r":
                            student.viewEnquiriesReplies(enquiriesArray);
                            break;
                        default:
                            System.out.println("Invalid choice");
                    }
                    break;
                case 4:
                    ArrayList<String> studentRegCamps = student.getRegCampsArray();
                    int remCampIndex;
                    for (int i=0; i<studentRegCamps.size();i++){
                        System.out.println((i+1)+": "+studentRegCamps.get(i));
                    }
                    do {
                        System.out.printf("Enter the index of the camp you are withdrawing from: ");
                        sc.nextLine();
                        remCampIndex = inputInt.nextInt(sc) -1;
                        if (remCampIndex >=0 && remCampIndex<=(studentRegCamps.size()-1))
                            break;
                        System.out.println("Invalid index! Please try again.");
                    } while (true);
                    Camp remCamp = campArray.getCamp(studentRegCamps.get(remCampIndex));
                    student.withdrawFromCamp(remCamp, campArray);
                    UserDB.updateFile();
                    break;
                
                case 5:
                    if (student.IsCampComm()) {
                        // view camp details for own camp
                        System.out.print("\033[H\033[2J");
                        // student.getCampCommitteeRole().displayCampInfo();
                        campArray.viewCampDetails(student.getCampCommitteeRole().getCampName(), student);

                    } else {
                        System.out.println("Logging out... Thank you!");
                        user = null;
                    }
                    break;
                case 6:
                    if (student.IsCampComm()) {
                        // view points
                        System.out.print("\033[H\033[2J");
                        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Committee Points              ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
                        System.out.printf("You have %d points!\n", student.getCampCommitteeRole().getPoints());
                    } else
                        System.out.println("Invalid choice");
                    break;
                case 7:
                    if (student.IsCampComm()) {
                        // manage suggestions
                        manageSuggestionsScreen(student);
                    } else
                        System.out.println("Invalid choice");
                    break;
                case 8:
                    if (student.IsCampComm()) {
                        // view enquiries
                        System.out.print("\033[H\033[2J"); // Clear the entire screen
                        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Enquiries                ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
                        enquiriesArray.viewEnquiries(student);
                    } else
                        System.out.println("Invalid choice");
                    break;
                case 9:
                    if (student.IsCampComm()) {
                        // reply enquiries
                        System.out.print("\033[H\033[2J"); // Clear the entire screen
                        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Reply to Enquiries            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
                        enquiriesArray.replyEnquiry(student);
                        UserDB.updateFile(); // for change in points
                    } else
                        System.out.println("Invalid choice");
                    break;
                case 10:
                    if (student.IsCampComm()) {
                        // generate camp report
                        System.out.print("\033[H\033[2J"); // Clear the entire screen
                        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Camp Report          ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
                        System.out.print("1: All\n2: Attendees\n3: Committee Members\n\nGenerate report for: ");
                        int repchoice = inputInt.nextInt(sc);
                        student.getCampCommitteeRole().generateReport(repchoice);
                    } else
                        System.out.println("Invalid choice");
                    break;
                case 11:
                    if (student.IsCampComm()) {
                        System.out.println("Logging out... Thank you!");
                        this.user = null;
                    } else
                        System.out.println("Invalid choice");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            ScreenClearFn();
        } while (choice != logout);
    }

    private void manageSuggestionsScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Suggestions            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        
        System.out.print("V: VIEW all Suggestions\nW: WRITE new Suggestion\nE: EDIT a Suggestion\n" +
                                "S: SUBMIT a Suggestion\n\nEnter your choice: ");
        String sugChoice = sc.nextLine();
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                        "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Suggestions            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        switch (sugChoice) {
            case "V", "v":
                suggestionArray.viewSuggestions(student);
                break;
            case "W", "w":
                System.out.println("Please input the Suggestion: ");
                String sug = sc.nextLine();
                suggestionArray.createSuggestion(sug, student,
                        campArray.getCamp(student.getCampCommitteeRole().getCampName()));
                break;
            case "E", "e":
                suggestionArray.viewSuggestions(student);
                System.out.printf("Select Suggestion to edit, input Suggestion index: ");
                int sugindex1 = inputInt.nextInt(sc);
                sc.nextLine();
                if (suggestionArray.suggestionExists(student, sugindex1)) {
                    System.out.printf("Please input the edited Suggestion: ");
                    String newsug = sc.nextLine();
                    suggestionArray.editSuggestion(student, sugindex1, newsug);
                } else System.out.println("Suggestion does not exist!");
                break;
            case "S", "s":
                suggestionArray.viewSuggestions(student);
                System.out.printf("Select Suggestion to submit, input Suggestion index: ");
                int sugindex2 = inputInt.nextInt(sc);
                sc.nextLine();
                suggestionArray.submitSuggestion(student, sugindex2);
                UserDB.updateFile(); // for change in points
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void ScreenClearFn() {
        System.out.printf("\nPress Enter to return to menu.");
        sc.nextLine();
        // String temp = sc.nextLine();
        System.out.print("\033[H\033[2J");
    }

}