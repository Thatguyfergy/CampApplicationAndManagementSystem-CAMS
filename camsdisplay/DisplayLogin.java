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

public class DisplayLogin {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Users user;

    // Only initialize this once your else you will get nightmares
    public DisplayLogin(Boolean bool) {
        campArray = new CampArray("csvfiles\\camps.csv");
        enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv", "csvfiles\\replies.csv");
        UserDB = new UsersDatabase("csvfiles\\usersStudent.csv",
                "csvfiles/usersStaff.csv", campArray);
        suggestionArray = new SuggestionArray("csvfiles\\suggestions.csv", UserDB, campArray);
    }

    public DisplayLogin() {
        // Empty so that the shit won't get reinitialised again
    }

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

    public void userScreen(Users user) {
        if (user instanceof Staff) {
            new DisplayStaff((Staff) user, campArray, enquiriesArray, UserDB,
                    suggestionArray);

        } else if (user instanceof Student) {
            new DisplayStudent((Student) user, campArray, enquiriesArray, UserDB,
                    suggestionArray);
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
}
