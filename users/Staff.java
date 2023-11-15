package users;

import java.util.ArrayList;
import java.util.Scanner;

import camps.*;
import infoexchange.*;

public class Staff extends Users {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<String> campsInCharge = new ArrayList<String>();

    // The `public Staff(String FirstName, String userID, String facultyInfo, String
    // campsStaff)`
    // constructor is used to create a new Staff object. It takes four parameters:
    // `FirstName`,
    // `userID`, `facultyInfo`, and `campsStaff`.
    public Staff(String FirstName, String userID, String facultyInfo, String campsStaff) {
        super(FirstName, userID, facultyInfo);
        if (!campsStaff.equals("")) {
            for (String camp : campsStaff.split(";")) {
                campsInCharge.add(camp);
            }
        }
    }

    /**
     * The function checks if a given camp name is in the list of camps in charge
     * and returns true if
     * it is, otherwise it returns false.
     * 
     * @param campName The campName parameter is a String that represents the name
     *                 of a camp.
     * @return The method is returning a Boolean value, either true or false.
     */
    public Boolean checkStaffInCharge(String campName) {
        for (String camp : campsInCharge) {
            if (camp.equals(campName))
                return true;
        }
        return false;
    }

    /**
     * The getCampsInCharge function returns an ArrayList of Strings representing
     * the camps in charge.
     * 
     * @return An ArrayList of Strings is being returned.
     */
    public ArrayList<String> getCampsInCharge() {
        return campsInCharge;
    }

    /**
     * The function changes the name of a camp in a list of camps.
     * 
     * @param oldName The old name of the camp that you want to change.
     * @param newName The new name that you want to change the camp name to.
     */
    public void changeCampName(String oldName, String newName) {
        campsInCharge.set(campsInCharge.indexOf(oldName), newName);
    }

    /**
     * The function creates a Camp object using CampInfo and adds the camp name to a
     * list if successful.
     * 
     * @param campArray The campArray parameter is an object of type CampArray. It
     *                  is used to access
     *                  methods and properties related to managing an array of
     *                  camps.
     */
    public void createCamp(CampArray campArray) {
        // Create a Camp object using CampInfo
        try {
            String campName = campArray.createCamp(this.getFirstName());
            if (!campName.equals(null))
                campsInCharge.add(campName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("Camp Creation unsuccessful.");
        }
    }

    /**
     * The function deletes a camp from a list of camps.
     * 
     * @param campName The parameter "campName" is a String that represents the name
     *                 of the camp that
     *                 needs to be deleted.
     */
    public void deleteCamp(String campName) {
        campsInCharge.remove(campName);
    }

    /**
     * The function compareTo is not implemented and throws an
     * UnsupportedOperationException.
     * 
     * @param o The parameter "o" is of type "Users".
     * @return The method is not returning anything. It is throwing an
     *         UnsupportedOperationException.
     */
    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }

    // Deprecated Methods

    // public void viewCamp(CampArray campArray, String sortby) {
    // campArray.viewCamps(this, sortby);
    // }

    // public void editCamp(CampArray campArray, EnquiriesArray enquiriesArray) {
    // campArray.editCamp(this, enquiriesArray);
    // }
}