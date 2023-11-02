package camps;

import java.util.ArrayList;
import java.util.List;
import camdate.CAMDate;

public class Camp {
    private CampInfo campInfo;
    private List<String> attendees;
    private List<String> committeeMembers;


    public Camp(int campID, String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        // Create campInfo
        this.campInfo = new CampInfo(campID, campName, registrationClosingDate, campVisibility,
                location, totalSlots, committeeMembersSlots, campDescription, staffInCharge);
                this.attendees = new ArrayList<>();
                this.committeeMembers = new ArrayList<>();

    }

    public int getCampID() {
        return campInfo.getCampID();
    }

    public String getCampName() {
        return campInfo.getCampName();
    }

    public ArrayList<CAMDate> getDates() {
        return campInfo.getDates();
    }

    public CAMDate getRegistrationClosingDate() {
        return campInfo.getRegistrationClosingDate();
    }

    public String getCampVisibility() {
        return campInfo.getCampVisibility();
    }

    public String getLocation() {
        return campInfo.getLocation();
    }

    public String getCampDescription() {
        return campInfo.getCampDescription();
    }

    public String getStaffInCharge() {
        return campInfo.getStaffInCharge();
    }

    public int getCommitteeMembersSlots() {
        return campInfo.getCommitteeMembersSlots();
    }

    public int getTotalSlots() {
        return campInfo.getTotalSlots();
    }

    public void setCampDescription(String newDescription) {
        campInfo.setCampDescription(newDescription);
    }

    // Method to get the list of attendees
    public List<String> getAttendees() {
        return attendees;
    }

    // Method to get the list of Committee Members
    public List<String> getCommitteeMembers() {
        return committeeMembers;
    }

    public String toggleVisibility() {
        int totalSlots = campInfo.getTotalSlots();
        int occupiedSlots = getAttendees().size() + getCommitteeMembers().size();
        CAMDate currentDate = new CAMDate();
        if (currentDate.compareTo(campInfo.getRegistrationClosingDate()) > 0) {
            // Past registration closing date, set visibility to "off"
            return "off";
        } else {

            if (occupiedSlots < totalSlots) {
                // There are available slots, so set visibility to "on"
                return "on";
            } else {
                // All slots are taken, set visibility to "off"
                return "off";
            }
        }
    }

    public void registerStudent(String StudentID, boolean isCampCommittee, String campName) {

        // Update the camp's lists based on the registration type
        if (isCampCommittee) {
            // Register as a camp committee member
            committeeMembers.add(StudentID);
        } else {
           // Register as an attendee
           attendees.add(StudentID);
        }
        }
    
    public void addDate(CAMDate date) {
        campInfo.addDate(date);
    }

    public String toString(){
        return campInfo.toString();
    }
    }

    

