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
 * The DisplayLogin class is used to display the login interface.
 * 
 * @author Kok Chin Yi, Ferguson Chiew
 * @version 1.0
 */
public class DisplayLogin {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Users user;

    /**
     * The `DisplayLogin(Boolean bool)` constructor initializes the `campArray`,
     * `enquiriesArray`, `UserDB`, and `suggestionArray` objects.
     * Only initialize this once your else you will get nightmares
     */
    public DisplayLogin(Boolean bool) {
        campArray = new CampArray("csvfiles\\camps.csv");
        enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv", "csvfiles\\replies.csv");
        UserDB = new UsersDatabase("csvfiles\\usersStudent.csv",
                "csvfiles/usersStaff.csv", campArray);
        suggestionArray = new SuggestionArray("csvfiles\\suggestions.csv", UserDB, campArray);
    }

    /**
     * The `public DisplayLogin()` constructor is an empty constructor that does not
     * have any parameters or code inside it. It is used to create an instance of
     * the `DisplayLogin` class without initializing any objects or performing any
     * actions. Empty so that the derived classes won't reinitialise the superclass
     * again.
     */
    public DisplayLogin(CampArray campArray, EnquiriesArray enquiriesArray, UsersDatabase UserDB,
            SuggestionArray suggestionArray) {
        this.campArray = campArray;
        this.enquiriesArray = enquiriesArray;
        this.UserDB = UserDB;
        this.suggestionArray = suggestionArray;

    }

    /**
     * The startScreen() function displays a menu for a camp application and
     * management system, allowing
     * the user to login, reset their password, or exit the program.
     */
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
        int choice = InputInt.nextInt(sc);
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

    /**
     * The loginScreen() function displays a login screen for a camp application and
     * management system,
     * prompts the user to enter their userID and password, and checks if the
     * credentials are valid.
     */
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

    /**
     * The function `resetPassword` allows a user to enter and confirm a new
     * password, and if the
     * passwords match, it updates the user's password in the database.
     * 
     * @param user The user object represents the user whose password needs to be
     *             reset. It contains
     *             information about the user, such as their ID, username, and
     *             current password.
     */
    public void resetPassword(Users user) {
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

    /**
     * The userScreen function takes a user object as input and displays a specific
     * screen based on the
     * type of user (staff or student).
     * 
     * @param user The user object represents a user in the system. It can be either
     *             a Staff or a
     *             Student object.
     */
    public void userScreen(Users user) {
        if (user instanceof Staff) {
            new DisplayStaff((Staff) user, campArray, enquiriesArray, UserDB,
                    suggestionArray);

        } else if (user instanceof Student) {
            new DisplayStudent((Student) user, campArray, enquiriesArray, UserDB,
                    suggestionArray);
        }
    }

    /**
     * The function `resetPasswordScreen` displays a password reset screen for a
     * user in a camp application and management system.
     * 
     * @param user The "user" parameter is an object of the "Users" class. It
     *             represents the user for
     *             whom the password reset screen is being displayed.
     */
    public void resetPasswordScreen(Users user) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        // Scanner sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Password Reset           ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");

        resetPassword(user);
    }
}
