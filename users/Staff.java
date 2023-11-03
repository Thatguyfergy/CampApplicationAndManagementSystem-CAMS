package users;

import java.util.Scanner;

import camps.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);

    public Staff(String FirstName, String userID, String facultyInfo) {
        super(FirstName, userID, facultyInfo);
    }

    public Staff(String userID, String facultyInfo, String password) {
        super(userID, facultyInfo, password);
    }

    public void createCamp(CampArray campArray) throws Exception  {
        // Create a Camp object using CampInfo
        String staffInCharge = this.getID();
        try {
            campArray.createCamp(staffInCharge);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void editCamp(CampArray campArray) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        campArray.editCamp(campName);
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
