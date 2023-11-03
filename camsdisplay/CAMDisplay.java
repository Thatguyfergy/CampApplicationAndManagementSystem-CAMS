package camsdisplay;

import users.*;
import java.util.Scanner;

public class CAMDisplay {

    private Scanner sc = new Scanner(System.in);
    private UserDatabase UserDB = new UserDatabase("csvfiles\\usersStudent.csv", "csvfiles\\usersStaff.csv");
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
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Home                     ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("Welcome back to CAMs, Staff " + staff.getID());
        System.out.println("1. View Camps");
        System.out.println("2. View Camp Details");
        System.out.println("3. Create Camp");
        System.out.println("4. Edit Camp");
        System.out.println("5. Delete Camp");
        System.out.println("6. View enquiries");
        System.out.println("7. View Suggestions");
        System.out.println("8. View Suggestions");
        System.out.println("9. Approve Suggestions");
        System.out.println("10. Generate Camp Report");
        System.out.println("11. Generate Performance Report");
        System.out.println("12. Logout");
        System.out.printf("Enter your choice: ");

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