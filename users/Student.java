package users;

import InfoExchange.Enquiries;
import camps.CampArray;

public class Student extends Users {
    private boolean campCommittee = false; // !!!NOTE!!! cannot just have a boolean check for camp committee member
                                           // since a student can be a camp attendee and camp committee member at the
                                           // same time
    private String campCommitteeNOC; // NOC: Name of Camp
    private Enquiries[] enquiriesArray = new Enquiries[10]; // max 10 enquiries per student
    private int enquiriesIndex = 0; // to keep track of which index the enquiry is filled to

    public Student(String FirstName, String userID, String facultyInfo) {
        super(FirstName, userID, facultyInfo);
    }

    // Hello enric, please create an overloaded constructor so that UserDatabase can
    // use.
    // The purpose is to read the data from the csv file and create the Student objs
    // Make sure that EVERY (or most) of the inputs are String type.
    // So you might need to do convertion of String to wtv type u wan urself here
    public Student(String FirstName, String userID, String facultyInfo, String otherInformation) {
        this(FirstName, userID, facultyInfo);
    }

    public Student(String userID, String facultyInfo, String password) {
        super(userID, facultyInfo, password);
    }

    /*
     * this method can return the list of camps that student is a camp committee
     * member of if student class keeps an array of camps that student is registered
     * for
     */
    // public boolean getCampCommitteeStatus(){
    // return campCommittee;
    // }

    public String getFacultyInfo() {
        return this.getFacultyInfo();
    }

    public String getStudentID() {
        return this.getID();
    }

    public void getAvailCamps(CampArray currentCamps) {
        currentCamps.viewCamps(this);
    }

    public void campCommitteeInfo(String campName) {
        campCommitteeNOC = campName;
    }
    // camp committee methods can have a check at the start if every method to see
    // if campCommittee is true, then execute the method based on campCommitteeNOC

    public void registerCamp(String campName, boolean campCommittee) {
        if (campCommittee) {
            this.campCommittee = campCommittee;
            this.campCommitteeInfo(campName);
        }
        // add method to register for camp from camp class
    }

    public void viewRegCamps() {
        // need to ask if want to store an array of camps that student is currently
        // registered for in student class itself
    }

    public void withdrawFromCamp(String campName) {
        // call withdraw from camp function from camp
    }

    // =======================================================================================================
    // Enquiry handling for students
    // -------------------------------------------------------------------------------------------------------
    // There shld be a camp Tag for each enquiry.
    // This Camptag is auto generated when the Student is viewing a particular camp
    // details. ie. They can only make an enquiry if he/she is viewing the details
    // of a particular camp details.
    // -------------------------------------------------------------------------------------------------------

    // public void createEnquiry(String enqString, String campName) {
    // enquiriesArray[enquiriesIndex] = new Enquiries(enqString);
    // enquiriesIndex++;
    // }

    // public String viewEnquiry(int i) {
    // return enquiriesArray[i - 1].getEnquiry();
    // }

    // public void editEnquiry(String enqString, int i) {
    // enquiriesArray[i - 1].modifyEnquiry(enqString);
    // }

    // public void submitEnquiry(int index) {
    // // call function from Enquiry class to submit enquiry to staff
    // }

    // =======================================================================================================

    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
