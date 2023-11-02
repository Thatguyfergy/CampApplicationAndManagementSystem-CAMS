package users;

import java.util.Scanner;

import camdate.CAMDate;
import camps.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);

    public Staff(String userID, String facultyInfo) {
        super(userID, facultyInfo);
    }

    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        System.out.println("Registration closing date (dd/mm/yyyy):");
        CAMDate registrationClosingDate = new CAMDate(scanner.nextLine());
        System.out.println("Camp Visibility (Y/N):");
        String campVisibility = scanner.nextLine().toUpperCase();
        System.out.println("Location:");
        String location = scanner.nextLine().toUpperCase();
        System.out.println("Total Slots:");
        int totalSlots = scanner.nextInt();
        scanner.nextLine(); // Flush
        System.out.println("Committee Memebers Slots:");
        int committeeMembersSlots = scanner.nextInt();
        scanner.nextLine(); // Flush;
        System.out.println("Camp Description:");
        String campDescription = scanner.nextLine();
        String staffInCharge = this.getID();

        campArray.createCamp(new Camp(campName, registrationClosingDate, campVisibility, location, totalSlots,
                committeeMembersSlots, campDescription, staffInCharge));
    }

    public void editCamp(CampArray campArray) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        campArray.editCamp(campName);
    }

    public void deleteCamp(CampArray campArray) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        campArray.deleteCamp(campName);
    }

    public void viewCamp(CampArray campArray) {
        campArray.viewCamps(this);
    }

    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    // Suggestion nonsense will do next time

    // Student report generation

    // Performance report
}
