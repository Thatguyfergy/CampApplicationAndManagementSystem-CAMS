package camsdisplay;

import users.*;
import java.util.Scanner;

public class CAMDisplay {

    public void startScreen() {
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System                            ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("1. Login");
        System.out.println("2. Reset Password");
        System.out.println("3. Exit");
        System.out.printf("Enter your choice: ");
        // Scanner sc = new Scanner(System.in);
        // int choice = sc.nextInt();
        // switch (choice) {
        // case 1:
        // loginScreen();
        // break;
        // case 2:
        // resetPasswordScreen();
        // break;
        // case 3:
        // System.exit(0);
        // break;
        // default:
        // System.out.println("Invalid choice!");
        // break;
    }

    public void loginScreen() {
        String userID, password;
        // sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Login                    ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        // System.out.println("Enter your userID: ");
        // userID = sc.nextLine();
        // System.out.println("Enter your password: ");
        // password = sc.nextLine();
        // ==TODO==: Search database if got that userID & match password
        // if got, then set user to that user & print out login success
    }

    public void resetPasswordScreen() {
        String userID, oldPassword, newPassword1, newPassword2;
        // Scanner sc = new Scanner(System.in);
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Camp Application & Management System - Password Reset           ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");

        // System.out.println("Enter your userID: ");
        // userID = sc.nextLine();
        // System.out.println("Enter your previous password: ");
        // oldPassword = sc.nextLine();
        // System.out.println("Enter your new password: ");
        // newPassword1 = sc.nextLine();
        // System.out.println("Enter your new password again: ");
        // newPassword2 = sc.nextLine();

        // ==TODO==: Search database if got that userID & match password
        // if got, check if the entered password are the same if same then set new
        // password
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