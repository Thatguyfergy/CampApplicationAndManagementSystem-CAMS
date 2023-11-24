package users;

import java.util.ArrayList;
import java.util.List;

import camdate.CAMDate;
import camps.Camp;

/**
 * CampCommitteeRole class is used to store all attributes and methods
 * of a student who is a camp committee member.
 * Each student can only have 1 instance of a CampCommitteeRole.
 * 
 * @author Tan Jun Kiat
 * @version 1.0
 */
public class CampCommitteeRole {
    /**
     * stores the camp object that this student is a committee member for
     */
    private Camp camp;
    /**
     * stores the amount of points this student/camp committee member has
     */
    private int points;

    /**
     * Constructor for when a student signs up as a committee member.
     * points is initialised to 0 by calling method when student signs up for the
     * role.
     * This constructor is also used to load student data from external file to
     * program,
     * so the points can be non-zero if this object is created from external file
     * 
     * @param cmp camp that the student is a committee member for
     * @param pts number of points the student has for this role
     */
    public CampCommitteeRole(Camp cmp, int pts) {
        this.camp = cmp;
        points = pts;
    }

    /**
     * This method returns the name of the camp which the student is a camp comm
     * for,
     * by calling the camp method.
     * 
     * @return string containing name of camp
     */
    public String getCampName() {
        return camp.getCampName();
    }

    /**
     * This method returns an ArrayList of CAMDate objects containing the dates of
     * the camp.
     * Represents when the student/camp comm is busy due to this camp.
     * Calls the camp method.
     * 
     * @return ArrayList of CAMDate objects with dates of camp
     */
    public ArrayList<CAMDate> getCampDates() {
        return camp.getDates();
    }

    /**
     * This method returns the registration closing date for the camp
     * that the student is a camp comm for.
     * Calls the camp method.
     * 
     * @return CAMDate containing the registration closing date
     */
    public CAMDate getCampRegistrationClosingDate() {
        return camp.getRegistrationClosingDate();
    }

    /**
     * This method returns the availability of the camp
     * that the student is a camp comm for.
     * Calls the camp method.
     * 
     * @return String representing availability of the camp
     */
    public String getCampAvailability() {
        return camp.getCampAvailability();
    }

    /**
     * This method returns the location of the camp.
     * Calls the camp method.
     * 
     * @return String containing location of the camp
     */
    public String getCampLocation() {
        return camp.getLocation();
    }

    /**
     * This method returns the total number of slots in the camp.
     * Calls the camp method.
     * 
     * @return number of total slots in the camp
     */
    public int getCampTotalSlots() {
        return camp.getTotalSlots();
    }

    /**
     * This method returns the number of available slots for committee members
     * in the camp.
     * Calls the camp method.
     * 
     * @return number of slots available for committee members in the camp
     */
    public int getCampCommitteeMembersSlots() {
        return camp.getCommitteeMembersSlots();
    }

    /**
     * This method returns the description of the camp.
     * Calls the camp method.
     * 
     * @return String containing camp description
     */
    public String getCampDescription() {
        return camp.getCampDescription();
    }

    /**
     * This method returns the name of the staff in charge of the camp.
     * Calls the camp method.
     * 
     * @return String containing name of staff in charge
     */
    public String getCampStaffInCharge() {
        return camp.getStaffInCharge();
    }

    /**
     * This method prints the information for the camp which the student
     * is a camp comm for.
     * Calls the camp method.
     */
    public void displayCampInfo() {
        camp.displayCampInfo();
    }

    /**
     * This method returns a list of attendees for the camp.
     * Calls the camp method.
     * 
     * @return List of Strings containing names of the attendees of the camp
     */
    public List<String> getCampAttendees() {
        return camp.getAttendees();
    }

    /**
     * This method returns a list of committee members for the camp.
     * Calls the camp method.
     * 
     * @return List of Strings containing names of the committee members of the camp
     */
    public List<String> getCampCommitteeMembers() {
        return camp.getCommitteeMembers();
    }

    /**
     * This method increases the attribute points by one.
     * Called when a student submits a suggestion, answers an enquiry,
     * and when their suggestion is accepted.
     */
    public void addOnePoint() {
        points++;
    }

    /**
     * This method returns the amount of points the student/camp comm member has.
     * 
     * @return number of points the student has
     */
    public int getPoints() {
        return points;
    }

}
