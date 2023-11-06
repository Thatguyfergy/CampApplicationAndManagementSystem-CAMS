package users;

import java.util.ArrayList;

import InfoExchange.*;
import camps.Camp;

public class CampAttendeeRole {
    private ArrayList<Enquiries> PendingEnquiries = new ArrayList<Enquiries>();
    private String CampAttending;
    private Student StudentAttending;

    public CampAttendeeRole(String camp, Student student) {
        CampAttending = camp;
        StudentAttending = student;
    }

    public String getCampAttending(){
        return CampAttending;
    }

    public void createEnquiry(String enqString) {
        Enquiries newEnquiry = new Enquiries(enqString, this.StudentAttending.getID(), CampAttending);
        PendingEnquiries.add(newEnquiry);
    }

    public void viewEnquiries() {
        for (int i = 0; i < PendingEnquiries.size(); i++) {
            System.out.print((i+1) + ": ");
            System.out.println(PendingEnquiries.get(i));
        }
    }

    public void editEnquiry(String enqString, int index) {
        PendingEnquiries.get(index).modifyEnquiry(enqString);
    }

    public void submitEnquiry(EnquiriesArray enqArray, int index) {
        enqArray.submitEnquiry(PendingEnquiries.get(index));
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
