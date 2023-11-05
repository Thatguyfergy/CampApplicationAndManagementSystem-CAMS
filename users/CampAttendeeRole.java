package users;

import java.util.ArrayList;

import InfoExchange.*;
import camps.Camp;

public class CampAttendeeRole {
    private ArrayList<Enquiries> PendingEnquiries;
    private Camp CampAttending;
    private Student StudentAttending;

    public CampAttendeeRole(Camp camp, Student student){
        CampAttending = camp;
        StudentAttending = student;
    }

    public void createEnquiry(String enqString, Camp camp){
        Enquiries newEnquiry = new Enquiries(enqString,this.StudentAttending,this.CampAttending);
        PendingEnquiries.add(newEnquiry);
    }

    public void viewEnquiries(){
        for (int i=0;i<PendingEnquiries.size();i++){
            System.out.print(i + ": ");
            System.out.println(PendingEnquiries.get(i));
        }
    }

    public void editEnquiry(String enqString, int index){
        PendingEnquiries.get(index).modifyEnquiry(enqString);
    }

    public void submitEnquiry(EnquiriesArray enqArray, int index){
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
