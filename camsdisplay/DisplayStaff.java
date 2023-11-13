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

public class DisplayStaff extends DisplayLogin implements viewCampsScreen, ScreenClearFn, replyEnquiries {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Staff user;

    public DisplayStaff(Staff _user, CampArray _campArray, EnquiriesArray _enquiriesArray, UsersDatabase _UserDB,
            SuggestionArray _suggestionArray) {

        this.campArray = _campArray;
        this.enquiriesArray = _enquiriesArray;
        this.UserDB = _UserDB;
        this.suggestionArray = _suggestionArray;

        this.user = _user;

        staffScreen(user);
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
                    "12. Generate Enquiries Report\n" +
                    "13. Logout\n" +
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
                    viewEnquiriesScreen(staff);
                    break;
                case 7:
                    // reply enquiries
                    replyEnquiriesScreen(staff);
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
                    generateEnquiriesReportScreen(staff);
                    break;
                case 13:
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

    public void viewCampsScreen(Users user) {

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

    public void viewCampDetailsScreen(Users user) {

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

    private void viewSuggestionsScreen(Staff staff) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Suggestions                   ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        suggestionArray.viewSuggestions(staff);
        ScreenClearFn();
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

    public void generateEnquiriesReportScreen(Users user) {
        if (!(user instanceof Staff)) return;
        Staff staff = (Staff) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Enquiries Report   ║\n" +
                        "╚════════════════════════════════════════════════════════════════════╝\r\n");

        EnquiriesReport enquiriesReport = new EnquiriesReport(staff, enquiriesArray);
        enquiriesReport.generateReport();

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

    private void deleteCampScreen(Staff staff) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔═══════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Delete Camp            ║\n" +
                        "╚═══════════════════════════════════════════════════════════════╝\r\n");

        campArray.deleteCamp(staff, UserDB, enquiriesArray, suggestionArray);

        UserDB.updateFile();

        System.out.print("Press Enter to return to the main menu...");
        sc.nextLine(); // Wait for Enter key
        // Exit the viewCampsScreen and return to the main menu
        return;

    }

    public void viewEnquiriesScreen(Users user) {
        if (!(user instanceof Staff)) return;
        Staff staff = (Staff) user;
        System.out.print("\033[H\033[2J");
        enquiriesArray.viewEnquiries(staff);
        ScreenClearFn();
    }

    public void replyEnquiriesScreen(Users user) {
        if (!(user instanceof Staff)) return;
        Staff staff = (Staff) user;
        System.out.print("\033[H\033[2J");
        enquiriesArray.replyEnquiry(staff);
        ScreenClearFn();
    }
}
