package users;

import camps.*;

public class Staff extends Users {
    public Staff(String userID, String facultyInfo) {
        super(userID, facultyInfo);
    }

    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        // Camp obj = new Camp();

        // campArray.createCamp(obj);
    }

    public void editCamp(CampArray campArray, String campName) {
        campArray.editCamp(campName);
    }

    public void deleteCamp(CampArray campArray, String campName) {
        campArray.deleteCamp(campName);
    }

    // Edit next time
    public void viewCamp(CampArray campArray) {
        campArray.viewCamps(null);
    }

    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    // Suggestion nonsense will do next time

    // Student report generation

    // Performance report
}
