package camps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import camdate.CAMDate;

public class Camp {
    private CampInfo campInfo;
    private List<String> attendees;
    private List<String> committeeMembers;
    private Set<String> withdrawnStudents = new HashSet<>();

    public Camp(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        // Create campInfo
        this.campInfo = new CampInfo(campName, registrationClosingDate, campVisibility,
                location, totalSlots, committeeMembersSlots, campDescription, staffInCharge);
        this.attendees = new ArrayList<>();
        this.committeeMembers = new ArrayList<>();

    }

    // public Camp(String campName, CAMDate registrationClosingDate, String
    // campVisibility,
    // String location, int totalSlots, int committeeMembersSlots, String
    // campDescription, String staffInCharge) {
    // // Create campInfo
    // this(0, campName, registrationClosingDate, campVisibility, location,
    // totalSlots, committeeMembersSlots,
    // campDescription, staffInCharge);

    // }

    // public int getCampID() {
    // return campInfo.getCampID();
    // }

    public String getCampName() {
        return campInfo.getCampName();
    }

    public ArrayList<CAMDate> getDates() {
        return campInfo.getDates();
    }

    public String getStartToEndDate() {
        return campInfo.getStartToEndDate();
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

    public CampInfo getCampInfo() {
        return campInfo;
    }

    public void displayCampInfo() {
        campInfo.displayCampInfo();
    }

    public void setCampDescription(String newDescription) {
        campInfo.setCampDescription(newDescription);
    }

    public void setVisibility(String toggle){
    
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

    public void registerStudent(String StudentID, String FirstName, boolean isCampCommittee, String campName) {
        if (isCampCommittee) {
            // Register as a camp committee member
            List<String> committeeMembers = getCommitteeMembers();
            if (!committeeMembers.contains(StudentID) && !hasWithdrawn(StudentID)) {
                // Check if the committeeMembers list is not already at the limit
                if (committeeMembers.size() < campInfo.getCommitteeMembersSlots()) {
                    // Add the student to the committeeMembers list
                    committeeMembers.add(StudentID);
                }
            } 
        }
        else {
                // Register as an attendee
                List<String> attendees = getAttendees();
                if (!attendees.contains(StudentID) && !hasWithdrawn(StudentID)) {
                    if (getTotalSlots() - getAttendees().size() - getCommitteeMembers().size() > 0) {
                        // Add the student to the attendees list
                        attendees.add(StudentID);
                    }

                }
            }
    }

    public void withdrawFromCamp(String StudentID) {
        withdrawAttendee(StudentID);
    }

    private void withdrawAttendee(String StudentID) {
        List<String> attendees = this.getAttendees();

        if (attendees.contains(StudentID)) {
            attendees.remove(StudentID);
            withdrawnStudents.add(StudentID); // Add the student to withdrawn list
            System.out.println("Withdrawal successful. Student removed from the attendee list.");

        } else {
            System.out.println("Student is not registered as an attendee for this camp.");
        }
    }

    private boolean hasWithdrawn(String StudentID) {
        return withdrawnStudents.contains(StudentID);
    }

    public void addDate(CAMDate date) {
        campInfo.addDate(date);
    }

    public void addDate(CAMDate startDate, CAMDate endDate) {
        campInfo.addDate(startDate, endDate);
    }

    public String toString() {
        return campInfo.toString();
    }

    public int compareTo(Camp other, String sortBy) {
        if (sortBy.equals("campName")) {
            return this.getCampName().compareTo(other.getCampName());
        } else if (sortBy.equals("registrationClosingDate")) {
            return this.getRegistrationClosingDate().compareTo(other.getRegistrationClosingDate());
        } else if (sortBy.equals("campVisibility")) {
            return this.getCampVisibility().compareTo(other.getCampVisibility());
        } else if (sortBy.equals("location")) {
            return this.getLocation().compareTo(other.getLocation());
        } else if (sortBy.equals("totalSlots")) {
            return -(this.getTotalSlots() - other.getTotalSlots());
        } else if (sortBy.equals("committeeMembersSlots")) {
            return this.getCommitteeMembersSlots() - other.getCommitteeMembersSlots();
        } else if (sortBy.equals("campDescription")) {
            return this.getCampDescription().compareTo(other.getCampDescription());
        } else if (sortBy.equals("staffInCharge")) {
            return this.getStaffInCharge().compareTo(other.getStaffInCharge());
        } else if (sortBy.equals("startDate")) {
            String startDate = this.getDates().get(0).toString();
            return startDate.compareTo(other.getDates().get(0).toString());
        } else if (sortBy.equals("popularity")) {
            return 1;
        } else {
            return 0;
        }
    }
// Helper method to calculate remaining slots for attendees
public int getRemainingAttendeeSlots() {
    int totalSlots = getTotalSlots();
    int occupiedAttendeeSlots = getAttendees().size();
    int occupiedCommitteeSlots = getCommitteeMembers().size();
    int totalOccupiedSlots = occupiedAttendeeSlots + occupiedCommitteeSlots;

    return totalSlots - totalOccupiedSlots;
}

// Helper method to calculate remaining slots for committee members
public int getRemainingCommitteeSlots() {
    int committeeMembersSlots = getCommitteeMembersSlots();
    int occupiedCommitteeSlots = getCommitteeMembers().size();

    return committeeMembersSlots - occupiedCommitteeSlots;
}
}
