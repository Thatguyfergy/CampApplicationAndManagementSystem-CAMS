package users;

import java.util.ArrayList;

import InfoExchange.*;
import camps.Camp;

public class CampAttendeeRole {
    
    private String CampAttending;
    private Student StudentAttending;

    public CampAttendeeRole(String camp, Student student) {
        CampAttending = camp;
        StudentAttending = student;
    }

    public String getCampAttending(){
        return CampAttending;
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
