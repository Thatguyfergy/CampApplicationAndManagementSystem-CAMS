package users;

import java.util.ArrayList;
import java.util.Scanner;

import camps.*;
import infoexchange.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<String> campsInCharge = new ArrayList<String>();

    public Staff(String FirstName, String userID, String facultyInfo, String campsStaff) {
        super(FirstName, userID, facultyInfo);
        if (!campsStaff.equals("")) {
            for (String camp : campsStaff.split(";")) {
                campsInCharge.add(camp);
            }
        }
    }

    public Boolean checkStaffInCharge(String campName) {
        for (String camp : campsInCharge) {
            if (camp.equals(campName))
                return true;
        }
        return false;
    }

    public ArrayList<String> getCampsInCharge() {
        return campsInCharge;
    }

    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        try {
            String campName = campArray.createCamp(this.getFirstName());
            if (!campName.equals(null))
                campsInCharge.add(campName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void editCamp(CampArray campArray) {
        campArray.editCamp(this);
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
}