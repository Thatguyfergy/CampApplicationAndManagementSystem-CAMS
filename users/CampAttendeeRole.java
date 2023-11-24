package users;

import java.util.ArrayList;

import camps.Camp;
import infoexchange.*;

public class CampAttendeeRole {

    private String CampAttending;
    private Student StudentAttending;

    /**
     * The code below is a constructor for the
     * `CampAttendeeRole` class. It takes two parameters: `camp` and `student`.
     * 
     * @param camp
     * @param student
     */
    public CampAttendeeRole(String camp, Student student) {
        CampAttending = camp;
        StudentAttending = student;
    }

    /**
     * The function returns the value of the CampAttending variable.
     * 
     * @return The value of the variable "CampAttending".
     */
    public String getCampAttending() {
        return CampAttending;
    }

    /**
     * The function modifies the value of the CampAttending variable.
     * 
     * @param newName The modified value that will be assigned to the CampAttending
     *                variable.
     */
    public void setCampAttending(String newName) {
        CampAttending = newName;

    }

}

// =======================================================================================================
// Enquiry handling for students
// -------------------------------------------------------------------------------------------------------
// There shld be a camp Tag for each enquiry.
// This Camptag is auto generated when the Student is viewing a particular camp
// details. ie. They can only make an enquiry if he/she is viewing the details
// of a particular camp details.
// -------------------------------------------------------------------------------------------------------
