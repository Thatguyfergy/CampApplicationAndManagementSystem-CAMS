package users;

import java.util.Scanner;

import camps.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);

    public Staff(String userID, String facultyInfo) {
        super(userID, facultyInfo);
    }

    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        String staffInCharge = this.getID();
        campArray.createCamp(staffInCharge);
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
