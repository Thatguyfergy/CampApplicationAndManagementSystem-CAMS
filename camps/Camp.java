package camps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import camdate.CAMDate;

public class Camp{
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

    // public Camp(String campName, CAMDate registrationClosingDate, String campVisibility,
    //         String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
    //     // Create campInfo
    //     this(0, campName, registrationClosingDate, campVisibility, location, totalSlots, committeeMembersSlots,
    //             campDescription, staffInCharge);

    // }

    // public int getCampID() {
    //     return campInfo.getCampID();
    // }

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

    public CampInfo getCampInfo() {
        return campInfo;
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

    public void registerStudent(String StudentID, String FirstName, boolean isCampCommittee, String campName) {

        if (isCampCommittee) {
            // Register as a camp committee member
            List<String> committeeMembers = getCommitteeMembers();
            if (!committeeMembers.contains(FirstName) && !hasWithdrawn(FirstName)) {
            // Check if the committeeMembers list is not already at the limit
            if (committeeMembers.size() < campInfo.getCommitteeMembersSlots()) {
                // Add the student to the committeeMembers list
                committeeMembers.add(FirstName);
            }
        } else {
            // Register as an attendee
            List<String> attendees = getAttendees();
            if (!attendees.contains(FirstName) && !hasWithdrawn(FirstName)) {
            // Add the student to the attendees list
            attendees.add(FirstName);
        
    }
}
    }
        }

        public void withdrawFromCamp(String FirstName, Camp camp, boolean isCampCommittee) {
    
            if (camp != null) {
                if (isCampCommittee) {
                    withdrawAttendee(camp, FirstName);
                } else{
                    withdrawCommitteeMember(camp, FirstName);
                } 
                    }
             else {
                System.out.println("Camp not found.");
            }
        }
    
        private void withdrawAttendee(Camp camp, String FirstName) {
            List<String> attendees = camp.getAttendees();
    
            if (attendees.contains(FirstName)) {
                attendees.remove(FirstName);
                withdrawnStudents.add(FirstName); // Add the student to withdrawn list
                System.out.println("Withdrawal successful. Student removed from the attendee list.");
    
            } else {
                System.out.println("Student is not registered as an attendee for this camp.");
            }
        }
    
        private void withdrawCommitteeMember(Camp camp, String FirstName) {
            List<String> committeeMembers = camp.getCommitteeMembers();
    
            if (committeeMembers.contains(FirstName)) {
                committeeMembers.remove(FirstName);
                withdrawnStudents.add(FirstName); // Add the student to withdrawn list
                System.out.println("Withdrawal successful. Student removed from the committee member list.");
    
            } 
            else{
                System.out.println("Student is not registered as a committee member for this camp.");
            }
        }

    private boolean hasWithdrawn(String FirstName) {
        return withdrawnStudents.contains(FirstName);
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
        } else {
            return 0;
        }
    }
    
}
