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

public class DisplayStudent extends DisplayLogin
        implements viewCampsScreen, ScreenClearFn, registerCamps, replyEnquiries {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Student user;

    // The above code is defining a constructor for a class called DisplayStudent.
    // The constructor
    // takes in several parameters, including a Student object, CampArray object,
    // EnquiriesArray
    // object, UsersDatabase object, and SuggestionArray object. It assigns these
    // objects to instance
    // variables within the class. Finally, it calls the studentScreen method,
    // passing in the user
    // object.
    public DisplayStudent(Student _user, CampArray _campArray, EnquiriesArray _enquiriesArray, UsersDatabase _UserDB,
            SuggestionArray _suggestionArray) {

        this.campArray = _campArray;
        this.enquiriesArray = _enquiriesArray;
        this.UserDB = _UserDB;
        this.suggestionArray = _suggestionArray;

        this.user = _user;

        studentScreen(user);
    }

    /**
     * The `studentScreen` function displays a menu for a student in a camp
     * management system and
     * allows them to perform various actions based on their role.
     * 
     * @param student The parameter "student" is an object of the class "Student".
     *                It represents a
     *                student user in the Camp Application & Management System.
     */
    private void studentScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        int choice = -1;
        int logout = (student.IsCampComm()) ? 12 : 5;
        Scanner sc = new Scanner(System.in);

        do {
            logout = (student.IsCampComm()) ? 12 : 5;
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
                System.out.println("11. Generate Enquiries Report");
                System.out.println("12. Logout");
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
                    viewCampsScreen(student);
                    break;
                case 2:
                    registerCampsScreen(student);
                    break;
                case 3:
                    manageEnquiriesScreen(student);
                    break;
                case 4:
                    withdrawFromCampScreen(student);
                    break;

                case 5:
                    if (student.IsCampComm())
                        viewCampDetailsScreen(student);
                    else {
                        System.out.println("Logging out... Thank you!");
                        user = null;
                    }
                    break;
                case 6:
                    if (student.IsCampComm())
                        viewPointsScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 7:
                    if (student.IsCampComm())
                        manageSuggestionsScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 8:
                    if (student.IsCampComm())
                        viewEnquiriesScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 9:
                    if (student.IsCampComm())
                        replyEnquiriesScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 10:
                    if (student.IsCampComm())
                        generateReportScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 11:
                    if (student.IsCampComm())
                        generateEnquiriesReportScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 12:
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

    /**
     * The function "viewCampsScreen" displays a screen for viewing camps in a camp
     * application and
     * management system.
     * 
     * @param user The "user" parameter is of type "Users", which suggests that it
     *             represents a user in
     *             the system. It could be any type of user, such as a student,
     *             teacher, or administrator.
     */
    public void viewCampsScreen(Users user) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camps                    ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        ((Student) user).viewAvailAndRegCamps(campArray);
    }

    /**
     * The function "registerCampsScreen" allows a user to register for a camp as
     * either a committee
     * member or an attendee.
     * 
     * @param user The user object represents the user who is registering for camps.
     *             It is of type
     *             Users, which is a superclass for different types of users (e.g.,
     *             Student, Staff, etc.).
     */
    public void registerCampsScreen(Users user) {
        Student student = new Student(null, null, null, null, null, null, campArray);
        if (user instanceof Student) {
            student = (Student) user;
        }
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Register Camps                ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
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
    }

    /**
     * The function "manageEnquiriesScreen" is a Java method that displays a menu
     * for managing
     * enquiries and performs various actions based on the user's choice.
     * 
     * @param student The "student" parameter is an object of the Student class. It
     *                represents the
     *                student who is using the application and managing their
     *                enquiries.
     */
    private void manageEnquiriesScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Enquiries              ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        System.out.println(
                "W: WRITE new Enquiry\n" +
                        "V: VIEW current Enquiries\n" +
                        "E: EDIT an Enquiry\nD: DELETE an Enquiry\n" +
                        "S: SUBMIT an Enquiry\n" +
                        "R: View submitted Enquiries and REPLIES\n" +
                        "Enter your choice: ");
        String enqChoice = sc.nextLine();
        System.out.println();
        switch (enqChoice) {
            case "W", "w":
                System.out.println("\nFILTERING and SORTING Camps to View.");
                student.viewAvailAndRegCamps(campArray);
                System.out.println();
                String enqcamp;
                do {
                    System.out.printf("Write Enquiry under which camp?\nPlease input Camp Name (Key in -1 to leave): ");
                    // sc.nextLine();
                    enqcamp = sc.nextLine();
                    // System.out.println(campArray.checkCampExists(enqcamp) + "
                    // "+campArray.checkEligibleCamp(student, enqcamp));
                    if (enqcamp.equals("-1")) {
                        return;
                    }

                    if (student.IsCampComm() && student.getCampCommitteeRole().getCampName().equals(enqcamp)) {
                        System.out.println("Unable to enquiry for this camp as you are the camp committee member\n");
                        continue;
                    }

                    if (campArray.checkCampExists(enqcamp)
                            && campArray.checkEligibleCamp(student, enqcamp)) {
                        break;
                    } else {
                        System.out.println("You are not eligible for that camp or camp does not exist!\n");
                    }
                } while (true);
                System.out.printf("Please input the Enquiry: ");
                // sc.nextLine();
                String enq = sc.nextLine();
                student.createEnquiry(enq, enqcamp);
                break;
            case "V", "v":
                student.viewEnquiries();
                System.out.println("\n===== END of Enquiries =====\n");
                break;
            case "E", "e":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to EDIT ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex1;
                do {
                    System.out.printf("Select Enquiry to edit, input Enquiry index: ");
                    enqindex1 = inputInt.nextInt(sc);
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex1 >= 1 && enqindex1 <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                System.out.printf("Please input the edited Enquiry: ");
                sc.nextLine();
                String newenq = sc.nextLine();
                student.editEnquiry(newenq, enqindex1 - 1);
                break;
            case "D", "d":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to DELETE ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex2;
                do {
                    System.out.printf("Select Enquiry to delete, input Enquiry index: ");
                    enqindex2 = inputInt.nextInt(sc);
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex2 >= 1 && enqindex2 <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                student.deleteEnquiry(enqindex2 - 1);
                break;
            case "S", "s":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to SUBMIT ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex;
                do {
                    System.out.printf("Select Enquiry to submit, input Enquiry index: ");
                    enqindex = inputInt.nextInt(sc);
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex >= 1 && enqindex <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                student.submitEnquiry(enquiriesArray, enqindex - 1);
                break;
            case "R", "r":
                student.viewEnquiriesReplies(enquiriesArray);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    /**
     * The function withdraws a student from a camp in a camp management system.
     * 
     * @param student The student object represents a student who wants to withdraw
     *                from a camp.
     */
    private void withdrawFromCampScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Withdraw from Camp            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        ArrayList<String> studentRegCamps = student.getRegCampsArray();
        int remCampIndex;
        for (int i = 0; i < studentRegCamps.size(); i++) {
            System.out.println((i + 1) + ": " + studentRegCamps.get(i));
        }
        System.out.println("0: Exit to menu");
        do {
            System.out.printf("Enter the index of the camp you are withdrawing from: ");
            sc.nextLine();
            remCampIndex = inputInt.nextInt(sc) - 1;
            if (remCampIndex == -1)
                break;
            if (remCampIndex >= 0 && remCampIndex <= (studentRegCamps.size() - 1))
                break;
            System.out.println("Invalid index! Please try again.");
        } while (true);
        if (remCampIndex == -1)
            return;
        Camp remCamp = campArray.getCamp(studentRegCamps.get(remCampIndex));
        student.withdrawFromCamp(remCamp, campArray);
        UserDB.updateFile();
    }

    /**
     * The function "viewCampDetailsScreen" displays the camp details for a specific
     * user.
     * 
     * @param user The "user" parameter is an instance of the "Users" class, which
     *             represents a user in
     *             the system. It is used to determine the type of user (e.g.,
     *             student, staff, etc.) and access
     *             their specific information. In this case, the "user" parameter is
     *             cast to
     */
    public void viewCampDetailsScreen(Users user) {
        System.out.print("\033[H\033[2J");
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camp Details             ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        campArray.viewCampDetails(((Student) user).getCampCommitteeRole().getCampName(), ((Student) user));
    }

    /**
     * The function "viewPointsScreen" displays the number of points a student has
     * in a camp committee
     * role.
     * 
     * @param student The student object represents a student in the camp
     *                application system. It
     *                contains information about the student, including their camp
     *                committee role and the number of
     *                points they have earned.
     */
    private void viewPointsScreen(Student student) {
        System.out.print("\033[H\033[2J");
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Committee Points              ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        System.out.printf("You have %d points!\n", student.getCampCommitteeRole().getPoints());
    }

    /**
     * The function "manageSuggestionsScreen" is a Java method that displays a menu
     * for managing
     * suggestions and performs various actions based on the user's choice.
     * 
     * @param student The "student" parameter is an object of the Student class. It
     *                represents the
     *                student who is accessing the manage suggestions screen in the
     *                camp application and management
     *                system.
     */
    private void manageSuggestionsScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Suggestions            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        System.out.print(
                "V: VIEW all Suggestions\nP: view PROCESSED Suggestions\nW: WRITE new Suggestion\nE: EDIT a Suggestion\n"
                        +
                        "D: DELETE a Suggestion\nS: SUBMIT a Suggestion\n\nEnter your choice: ");
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
            case "P", "p":
                suggestionArray.viewProcessedSuggestions(student);
                break;
            case "W", "w":
                System.out.println("Please input the Suggestion: ");
                String sug = sc.nextLine();
                suggestionArray.createSuggestion(sug, student,
                        campArray.getCamp(student.getCampCommitteeRole().getCampName()));
                break;
            case "E", "e":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to edit, input Suggestion index: ");
                    int sugindex1 = inputInt.nextInt(sc);
                    sc.nextLine();
                    if (suggestionArray.suggestionCanEdit(student, sugindex1) == 1) {
                        System.out.printf("Please input the edited Suggestion: ");
                        String newsug = sc.nextLine();
                        suggestionArray.editSuggestion(student, sugindex1, newsug);
                    } else if (suggestionArray.suggestionCanEdit(student, sugindex1) == 0)
                        System.out.println("Suggestion cannot be edited as it has been processed!");
                    else
                        System.out.println("Suggestion does not exist!");
                } else
                    System.out.println("No Suggestions to Edit!");
                break;
            case "D", "d":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to delete, input Suggestion index: ");
                    int delindex = inputInt.nextInt(sc);
                    sc.nextLine();
                    suggestionArray.deleteSuggestion(student, delindex);
                } else
                    System.out.println("No Suggestions to Delete!");
                break;
            case "S", "s":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to submit, input Suggestion index: ");
                    int sugindex2 = inputInt.nextInt(sc);
                    sc.nextLine();
                    suggestionArray.submitSuggestion(student, sugindex2);
                    UserDB.updateFile(); // for change in points
                } else
                    System.out.println("No Suggestions to Submit!");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    /**
     * The function "viewEnquiriesScreen" displays the view enquiries screen for a
     * student user in a
     * camp application and management system.
     * 
     * @param user The "user" parameter is an instance of the "Users" class, which
     *             is a superclass. It
     *             represents a user of the system.
     */
    public void viewEnquiriesScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Enquiries                ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        enquiriesArray.viewEnquiries(student);
    }

    /**
     * The function "replyEnquiriesScreen" displays a screen for replying to
     * enquiries in a camp
     * application and management system, specifically for students.
     * 
     * @param user The parameter "user" is an instance of the "Users" class, which
     *             represents a user in
     *             the system. It is used to determine if the user is a student or
     *             not.
     */
    public void replyEnquiriesScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Reply to Enquiries            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        enquiriesArray.replyEnquiry(student);
        UserDB.updateFile(); // for change in points
    }

    /**
     * The function generates a report screen for a student in a camp management
     * system, allowing them
     * to select a filter and generate a report based on their role in the camp.
     * 
     * @param student The student object represents a specific student for whom the
     *                report is being
     *                generated. It contains information about the student, such as
     *                their name, ID, and camp committee
     *                role.
     */
    private void generateReportScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Camp Report          ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        CampReport campReport = new CampReport(student, campArray);
        campReport.generateReport();
    }

    /**
     * The function generates an enquiries report screen for a student user in a
     * camp application and
     * management system.
     * 
     * @param user The user parameter is of type Users, which is a superclass. It is
     *             used to check if
     *             the user is an instance of the Student class. If it is not, the
     *             method returns without
     *             generating the report.
     */
    public void generateEnquiriesReportScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Enquiries Report   ║\n" +
                        "╚════════════════════════════════════════════════════════════════════╝\r\n");

        EnquiriesReport enquiriesReport = new EnquiriesReport(student, enquiriesArray);
        enquiriesReport.generateReport();

        return;
    }

}
