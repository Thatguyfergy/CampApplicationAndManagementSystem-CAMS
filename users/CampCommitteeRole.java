package users;

import java.util.ArrayList;
import java.util.List;

import camdate.CAMDate;
import camps.Camp;

public class CampCommitteeRole {
    private Camp camp;
    private int points;
    // private ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

    // The code `public CampCommitteeRole(Camp cmp, int pts)` is a constructor for
    // the
    // `CampCommitteeRole` class. It takes two parameters: `cmp` of type `Camp` and
    // `pts` of type
    // `int`.
    public CampCommitteeRole(Camp cmp, int pts) {
        this.camp = cmp;
        points = pts;
    }

    /**
     * The function returns the name of a camp.
     * 
     * @return The method is returning the camp name.
     */
    public String getCampName() {
        return camp.getCampName();
    }

    /**
     * The function returns an ArrayList of CAMDate objects from the camp.
     * 
     * @return An ArrayList of CAMDate objects.
     */
    public ArrayList<CAMDate> getCampDates() {
        return camp.getDates();
    }

    /**
     * The function returns the registration closing date for a camp.
     * 
     * @return The method is returning a CAMDate object, which represents the
     *         registration closing date
     *         for a camp.
     */
    public CAMDate getCampRegistrationClosingDate() {
        return camp.getRegistrationClosingDate();
    }

    /**
     * The function returns the availability of a camp.
     * 
     * @return The method is returning the availability of a camp.
     */
    public String getCampAvailability() {
        return camp.getCampAvailability();
    }

    /**
     * The function returns the location of a camp.
     * 
     * @return The method is returning the location of the camp.
     */
    public String getCampLocation() {
        return camp.getLocation();
    }

    /**
     * The function returns the total number of slots in a camp.
     * 
     * @return The method is returning the total number of slots in a camp.
     */
    public int getCampTotalSlots() {
        return camp.getTotalSlots();
    }

    /**
     * The function returns the number of available slots for committee members in a
     * camp.
     * 
     * @return The method is returning the number of slots available for committee
     *         members in a camp.
     */
    public int getCampCommitteeMembersSlots() {
        return camp.getCommitteeMembersSlots();
    }

    /**
     * The function returns the description of a camp.
     * 
     * @return The method is returning the camp description.
     */
    public String getCampDescription() {
        return camp.getCampDescription();
    }

    /**
     * The function returns the staff in charge of a camp.
     * 
     * @return The method is returning the value of the staffInCharge variable from
     *         the camp object.
     */
    public String getCampStaffInCharge() {
        return camp.getStaffInCharge();
    }

    /**
     * The function "displayCampInfo" calls the "displayCampInfo" method of the
     * "camp" object.
     */
    public void displayCampInfo() {
        camp.displayCampInfo();
    }

    /**
     * The function returns a list of attendees for a camp.
     * 
     * @return A List of Strings representing the attendees of a camp.
     */
    public List<String> getCampAttendees() {
        return camp.getAttendees();
    }

    /**
     * The function returns a list of committee members for a camp.
     * 
     * @return A List of Strings representing the committee members of a camp.
     */
    public List<String> getCampCommitteeMembers() {
        return camp.getCommitteeMembers();
    }

    /**
     * The function "addOnePoint" increments the value of the variable "points" by
     * one.
     */
    public void addOnePoint() {
        points++;
    }

    /**
     * The function returns the value of the variable "points".
     * 
     * @return The method is returning the value of the variable "points".
     */
    public int getPoints() {
        return points;
    }

}
