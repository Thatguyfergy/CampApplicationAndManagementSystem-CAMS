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

/**
 * The DisplayStaff class extends DisplayLogin and implements ViewCampsScreen,
 * ScreenClearFn, and EnquiriesScreen.
 * 
 * @author Enric Tan, Kok Chin Yi, Ferguson Chiew
 * @version 1.0
 */
public class DisplayStaff extends DisplayLogin implements ViewCampsScreen, ScreenClearFn, EnquiriesScreen {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Staff user;

    /**
     * 
     * The above code is a constructor for a class called "DisplayStaff". It
     * takes in several parameters, including a Staff object, CampArray object,
     * EnquiriesArray object, UsersDatabase object, and SuggestionArray object. It
     * assigns these objects to instance variables within the
     * class. It also assigns the Staff object passed as a parameter to the
     * "user" instance variable.
     * Finally, it calls the "staffScreen" method, passing in the "user" object.
     * 
     * @param _user
     * @param _campArray
     * @param _enquiriesArray
     * @param _UserDB
     * @param _suggestionArray
     */
    public DisplayStaff(Staff _user, CampArray _campArray, EnquiriesArray _enquiriesArray, UsersDatabase _UserDB,
            SuggestionArray _suggestionArray) {

        this.campArray = _campArray;
        this.enquiriesArray = _enquiriesArray;
        this.UserDB = _UserDB;
        this.suggestionArray = _suggestionArray;

        this.user = _user;

        staffScreen(user);
    }

    /**
     * The `staffScreen` function displays a menu for staff members in a camp
     * management system and
     * allows them to perform various actions based on their choice.
     * 
     * @param staff The staff parameter is an object of the Staff class, which
     *              represents a staff
     *              member in the system.
     */
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

            int choice = InputInt.nextInt(sc);
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

    /**
     * The function "viewCampsScreen" displays a screen that allows the user to view
     * camps and then
     * returns to the main menu when the user presses Enter.
     * 
     * @param user The "user" parameter is an object of the "Users" class. It
     *             represents the user who
     *             is currently using the camp application and management system.
     */
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

    /**
     * The function allows a user to view the details of a camp in a camp
     * application and management
     * system.
     * 
     * @param user The "user" parameter is an object of the "Users" class. It is
     *             used to pass the user
     *             information to the method.
     */
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

    /**
     * The function "viewSuggestionsScreen" clears the screen, displays a header for
     * the suggestions
     * screen, calls a method to view suggestions specific to a staff member, and
     * clears the screen
     * again.
     * 
     * @param staff The "staff" parameter is an object of the "Staff" class. It
     *              represents a staff
     *              member in the camp application and management system.
     */
    private void viewSuggestionsScreen(Staff staff) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Suggestions                   ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        suggestionArray.viewSuggestions(staff);
        ScreenClearFn();
    }

    /**
     * The function processes suggestions in a camp application and management
     * system, updates the user
     * database, and clears the screen.
     * 
     * @param staff The staff parameter is an object of the Staff class. It
     *              represents the staff member
     *              who is processing the suggestions.
     */
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

    /**
     * The function generates a camp report screen for a given staff member and
     * waits for the user to
     * press Enter to return to the main menu.
     * 
     * @param staff The staff parameter is an object of the Staff class. It
     *              represents the staff member
     *              who is generating the camp report.
     */
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

    /**
     * The function `campPerfReportScreen` generates a performance report for a
     * selected camp based on
     * user input.
     * 
     * @param staff The "staff" parameter is an object of the Staff class. It
     *              represents the staff
     *              member who is accessing the camp performance report screen.
     */
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
            campChoice = InputInt.nextInt(sc);
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
        choice = InputInt.nextInt(sc);
        sc.nextLine(); // Consume the newline character
        System.out.println(); // for readability

        PerfReport perfReport = new PerfReport(camp, choice, UserDB, staff);
        perfReport.generateReport();

        ScreenClearFn();
        return;
    }

    /**
     * The function generates an enquiries report screen for a staff user in a camp
     * application and
     * management system.
     * 
     * @param user The user parameter is an instance of the Users class, which is a
     *             superclass for
     *             different types of users in the system. In this method, we are
     *             checking if the user is an
     *             instance of the Staff class before proceeding with generating the
     *             enquiries report.
     */
    public void generateEnquiriesReportScreen(Users user) {
        if (!(user instanceof Staff))
            return;
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

    /**
     * The function creates a camp screen for the camp application and management
     * system, allowing
     * staff to create a new camp and update the camp array.
     * 
     * @param staff The staff parameter is an object of the Staff class. It is used
     *              to access the
     *              createCamp method of the Staff class.
     */
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

    /**
     * The function "editCampScreen" clears the screen, displays a menu for editing
     * a camp, updates the
     * camp information, saves the changes to a file, and waits for the user to
     * press Enter to return
     * to the main menu.
     * 
     * @param staff The staff parameter is an object of the Staff class. It is used
     *              to pass the staff
     *              information to the editCamp method.
     */
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

    /**
     * The function `deleteCampScreen` clears the screen, displays a header, deletes
     * a camp from an
     * array, updates a user database, and waits for the user to press Enter before
     * returning to the
     * main menu.
     * 
     * @param staff The staff object represents the staff member who is currently
     *              logged in and
     *              performing the delete camp operation.
     */
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

    /**
     * The function displays the enquiries screen for a staff user and clears the
     * screen.
     * 
     * @param user The user parameter is an instance of the Users class, which
     *             represents a user in the
     *             system.
     */
    public void viewEnquiriesScreen(Users user) {
        if (!(user instanceof Staff))
            return;
        Staff staff = (Staff) user;
        System.out.print("\033[H\033[2J");
        enquiriesArray.viewEnquiries(staff);
        ScreenClearFn();
    }

    /**
     * The function replies to enquiries on the screen if the user is a staff
     * member.
     * 
     * @param user The user parameter is an instance of the Users class, which
     *             represents a user in the
     *             system.
     */
    public void replyEnquiriesScreen(Users user) {
        if (!(user instanceof Staff))
            return;
        Staff staff = (Staff) user;
        System.out.print("\033[H\033[2J");
        enquiriesArray.replyEnquiry(staff);
        ScreenClearFn();
    }
}
