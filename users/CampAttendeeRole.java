package users;

import java.util.ArrayList;

import camps.Camp;
import infoexchange.*;

public class CampAttendeeRole {

    private String CampAttending;
    private Student StudentAttending;

    // The code `public CampAttendeeRole(String camp, Student student)` is a
    // constructor for the
    // `CampAttendeeRole` class. It takes two parameters: `camp` and `student`.
    public CampAttendeeRole(String camp, Student student) {
        CampAttending = camp;
        StudentAttending = student;
    }

    /**
     * The function returns the value of the CampAttending variable.
     * 
     * @return The method is returning the value of the variable "CampAttending".
     */
    public String getCampAttending() {
        return CampAttending;
    }

    /**
     * The function sets the value of the CampAttending variable to a new name.
     * 
     * @param newName The new name that will be assigned to the CampAttending
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
