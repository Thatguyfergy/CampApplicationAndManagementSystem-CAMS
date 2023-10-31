package users;

import InfoExchange.Enquiries;
import camps.CampArray;

public class Student extends Users {
    //private boolean campCommittee = false;    // !!!NOTE!!! cannot just have a boolean check for camp committee member since a student can be a camp attendee and camp committee member at the same time
    private Enquiries[] enquiriesArray = new Enquiries[10];     // max 10 enquiries per student
    private int enquiriesIndex = 0;     // to keep track of which index the enquiry is filled to

    public Student(String userID, String facultyInfo) {
        super(userID, facultyInfo);
    }
    
    /* 
     this method can return the list of camps that student is a camp committee member of if student class keeps an array of camps that student is registered for
    */
    // public boolean getCampCommitteeStatus(){
    //     return campCommittee;
    // }

    public String getFacultyInfo(){
        return this.getFacultyInfo();
    }

    public String getStudentID(){
        return this.getID();
    }

    public void getAvailCamps(CampArray currentCamps){
        currentCamps.viewCamps(this);        
    }

    public void registerCamp(String campName, boolean campCommittee){
        //this.campCommittee = campCommittee;
        // add method to register for camp from camp class
    }

    public void viewRegCamps(){
        // need to ask if want to store an array of camps that student is currently registered for in student class itself
    }

    public void withdrawFromCamp(String campName){
        // call withdraw from camp function from camp
    }

//=======================================================================================================
//  Enquiry handling for students
//-------------------------------------------------------------------------------------------------------

    public void createEnquiry(String enqString){
        enquiriesArray[enquiriesIndex] = new Enquiries(enqString);
        enquiriesIndex++;
    }

    public String viewEnquiry(int i){
        return enquiriesArray[i-1].getEnquiry();
    }

    public void editEnquiry(String enqString, int i){
        enquiriesArray[i-1].modifyEnquiry(enqString);        
    }

    public void delEnquiry(int i){
        enquiriesArray[i-1].removeEnquiry();
        while (enquiriesArray[i].getEnquiry() != "NULL" || i<10){
            enquiriesArray[i-1].modifyEnquiry(enquiriesArray[i].getEnquiry());
            enquiriesArray[i].removeEnquiry();
            i++;
        }
        enquiriesIndex--;
    }

    public void submitEnquiry(int index){
        //call function from Enquiry class to submit enquiry to staff
    }

//=======================================================================================================


    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
