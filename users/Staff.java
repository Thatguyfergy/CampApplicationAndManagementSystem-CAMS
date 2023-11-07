package users;

import java.util.Scanner;

import camps.*;
import InfoExchange.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);

    public Staff(String FirstName, String userID, String facultyInfo) {
        super(FirstName, userID, facultyInfo);
    }

    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        String staffInCharge = this.getID();
        try {
            campArray.createCamp(staffInCharge);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void editCamp(CampArray campArray, int choice, String campName) {
        campArray.editCamp(campName, choice);
    }

    public void deleteCamp(CampArray campArray) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        try {
            campArray.deleteCamp(campName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // public void viewCamp(CampArray campArray, String sortby) {
    // campArray.viewCamps(this, sortby);
    // }

    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    // Suggestion nonsense will do next time
    public void viewSuggestions(CampArray campArray) {
        // TODO: let campArray.viewSuggestions(this);
    }

    public void manageSuggestion(Suggestion s, boolean accept) {
        // TODO: let campArray.manageSuggestions(this);
    }
    // Student report generation

    // Performance report
}
