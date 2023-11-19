package camps;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import camdate.CAMDate;

public class Camp {
    private CampInfo campInfo;
    private List<String> attendees;
    private List<String> committeeMembers;
    private Set<String> withdrawnStudents;
    private String manualVisibility;

    // This is the constructor for the Camp class. It is used to create a new
    // instance of the Camp class with the specified parameters.
    public Camp(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        // Create campInfo
        this.campInfo = new CampInfo(campName, registrationClosingDate, campVisibility,
                location, totalSlots, committeeMembersSlots, campDescription, staffInCharge);
        this.attendees = new ArrayList<>();
        this.committeeMembers = new ArrayList<>();
        this.withdrawnStudents = new HashSet<>();
        this.manualVisibility = "on";
    }

    /**
     * The function returns the name of a camp.
     * 
     * @return The method is returning the camp name.
     */
    public String getCampName() {
        return campInfo.getCampName();
    }

    /**
     * The function returns an ArrayList of CAMDate objects.
     * 
     * @return An ArrayList of CAMDate objects is being returned.
     */
    public ArrayList<CAMDate> getDates() {
        return campInfo.getDates();
    }

    /**
     * The function returns the formatted dates from the campInfo object.
     * 
     * @return The method is returning the formatted dates from the campInfo object.
     */
    public String getFormatedDates() {
        return campInfo.getFormatedDates();
    }

    /**
     * The function returns the registration closing date for a camp.
     * 
     * @return The method is returning a CAMDate object, which represents the
     *         registration closing date for a camp.
     */
    public CAMDate getRegistrationClosingDate() {
        return campInfo.getRegistrationClosingDate();
    }

    /**
     * The function returns the availability of a camp for the faculties.
     * 
     * @return The method is returning the camp availability as a String.
     */
    public String getCampAvailability() {
        return campInfo.getCampAvailability();
    }

    /**
     * The getLocation() function returns the location of a camp.
     * 
     * @return The getLocation() method is returning the location of the camp.
     */
    public String getLocation() {
        return campInfo.getLocation();
    }

    /**
     * The function returns the camp description.
     * 
     * @return The method is returning the camp description.
     */
    public String getCampDescription() {
        return campInfo.getCampDescription();
    }

    /**
     * The function returns the staff in charge of a camp.
     * 
     * @return The method is returning the value of the staffInCharge variable from the campInfo object.
     */
    public String getStaffInCharge() {
        return campInfo.getStaffInCharge();
    }

    /**
     * The function returns the number of available slots for committee members in a
     * camp.
     * 
     * @return The method is returning the number of slots available for committee
     *         members in the camp.
     */
    public int getCommitteeMembersSlots() {
        return campInfo.getCommitteeMembersSlots();
    }

    /**
     * The function returns the total number of slots available in a camp.
     * 
     * @return The method is returning the total number of slots available in the
     *         camp.
     */
    public int getTotalSlots() {
        return campInfo.getTotalSlots();
    }

    /**
     * The function returns the camp information.
     * 
     * @return The method is returning an object of type CampInfo.
     */
    public CampInfo getCampInfo() {
        return campInfo;
    }

    /**
     * The function "displayCampInfo" calls the "displayCampInfo" method of the
     * "campInfo" object.
     */
    public void displayCampInfo() {
        campInfo.displayCampInfo();
    }

    /**
     * The function sets the description of a camp.
     * 
     * @param newDescription The new description that you want to set for the camp.
     */
    public void setCampDescription(String newDescription) {
        campInfo.setCampDescription(newDescription);
    }

    /**
     * The function returns a list of attendees.
     * 
     * @return A List of Strings is being returned.
     */
    public List<String> getAttendees() {
        return attendees;
    }

    /**
     * The function returns a list of committee members.
     * 
     * @return A List of Strings representing the committee members.
     */
    public List<String> getCommitteeMembers() {
        return committeeMembers;
    }

    /**
     * The function `toggleVisibility()` determines whether the visibility of a camp
     * should be set to "on" or "off" based on the number of occupied slots and the current date.
     * 
     * @return The method returns a String value, either "on" or "off".
     */
    public String toggleVisibility() {
        int totalSlots = campInfo.getTotalSlots();
        int occupiedSlots = getAttendees().size() + getCommitteeMembers().size();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = today.format(formatter);
        CAMDate currentDate = new CAMDate(formattedDate);
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

    /**
     * The function sets the visibility of a manual to "on" if the input is null,
     * "off" if the input is
     * "off" or "on", and the input value otherwise.
     * 
     * @param manualVisibility The parameter "manualVisibility" is a string that
     *                         represents the visibility setting for a manual. It can have three
     *                         possible values: "on", "off", or any other string value.
     */
    public void setManualVisibility(String manualVisibility) {
        if (manualVisibility == null) {
            this.manualVisibility = "on"; // Update the instance field
        } else if (manualVisibility.equalsIgnoreCase("on") || manualVisibility.equalsIgnoreCase("off")) {
            this.manualVisibility = manualVisibility.toLowerCase(); // Update the instance field
        } else {
            this.manualVisibility = manualVisibility; // Update the instance field
        }
    }

    /**
     * The function returns the value of the manualVisibility variable.
     * 
     * @return The method is returning the value of the variable "manualVisibility".
     */
    public String getManualVisibility() {
        return manualVisibility;
    }

    /**
     * The function `registerStudent` registers a student for a camp either as a
     * camp committee member
     * or as an attendee, based on the provided parameters.
     * 
     * @param StudentID       The unique identifier for the student. It could be a
     *                        string or an integer value.
     * @param FirstName       The first name of the student.
     * @param isCampCommittee A boolean value indicating whether the student is
     *                        registering as a camp
     *                        committee member or not. If true, the student is
     *                        registering as a camp committee member. If
     *                        false, the student is registering as an attendee.
     * @param campName        The name of the camp that the student is registering
     *                        for.
     */
    public void registerStudent(String StudentID, String FirstName, boolean isCampCommittee, String campName) {
        if (isCampCommittee) {
            // Register as a camp committee member
            List<String> committeeMembers = getCommitteeMembers();
            if (!committeeMembers.contains(StudentID)) {
                // Check if the committeeMembers list is not already at the limit
                if (committeeMembers.size() < campInfo.getCommitteeMembersSlots()) {
                    // Add the student to the committeeMembers list
                    committeeMembers.add(StudentID);
                } else {
                    System.out.println("No more camp committee slots!");
                }
            }
        } else {
            // Register as an attendee
            List<String> attendees = getAttendees();
            if (!attendees.contains(StudentID) && !hasWithdrawn(StudentID)) {
                if (getRemainingAttendeeSlots() > 0) {
                    // Add the student to the attendees list
                    attendees.add(StudentID);
                } else {
                    System.out.println("No more attendee slots!");
                }
            } else if (hasWithdrawn(StudentID)) {
                System.out.println("Student has been withdrawn from the camp and cannot be registered again.");
            } else {
                System.out.println("Student is already registered as an attendee for this camp.");
            }

        }
    }

    /**
     * The function withdrawFromCamp takes a student ID as input and calls the withdrawAttendee function.
     * 
     * @param StudentID The StudentID parameter is a unique identifier for a student
     *                  attending a camp.
     */
    public void withdrawFromCamp(String StudentID) {
        withdrawAttendee(StudentID);
    }

    /**
     * The function withdrawAttendee removes a student from the attendee list if
     * they are registered,
     * and adds them to a withdrawn list.
     * 
     * @param StudentID The StudentID parameter is a unique identifier for each
     *                  student. It is used to
     *                  identify the student who wants to withdraw from the attendee
     *                  list.
     */
    public void withdrawAttendee(String StudentID) {
        List<String> attendees = this.getAttendees();

        if (attendees.contains(StudentID)) {
            attendees.remove(StudentID);
            withdrawnStudents.add(StudentID); // Add the student to withdrawn list
            System.out.println("Withdrawal successful. Student removed from the attendee list.");

        } else {
            System.out.println("Student is not registered as an attendee for this camp.");
        }
    }

    /**
     * The function checks if a student with a given ID has withdrawn from a camp.
     * 
     * @param StudentID The StudentID parameter is a String that represents the
     *                  unique identifier of a student.
     * @return The method is returning a boolean value.
     */
    public boolean hasWithdrawn(String StudentID) {
        return this.withdrawnStudents.contains(StudentID);
    }

    /**
     * The function adds a CAMDate object to the campInfo collection.
     * 
     * @param date The "date" parameter is an object of type CAMDate, which
     *             represents a specific date.
     */
    public void addDate(CAMDate date) {
        campInfo.addDate(date);
    }

    /**
     * The function adds a date range to a camp information object.
     * 
     * @param startDate The startDate parameter is an instance of the CAMDate class,
     *                  representing the
     *                  start date of a camp event.
     * @param endDate   The endDate parameter is an object of the CAMDate class,
     *                  which represents the end
     *                  date of a camp.
     */
    public void addDate(CAMDate startDate, CAMDate endDate) {
        campInfo.addDate(startDate, endDate);
    }

    /**
     * The toString() function returns the string representation of the campInfo
     * object.
     * 
     * @return The toString() method is returning the string representation of the
     *         campInfo object.
     */
    public String toString() {
        return campInfo.toString();
    }

    /**
     * The function compares two Camp objects based on the specified sortBy
     * parameter and returns an integer value indicating their relative order.
     * 
     * @param other  The "other" parameter is an instance of the Camp class that we
     *               are comparing the current instance to.
     * @param sortBy The "sortBy" parameter is a String that specifies the attribute
     *               by which the Camp objects should be sorted.
     * @return The method is returning an integer value.
     */
    public int compareTo(Camp other, String sortBy) {
        if (sortBy.equals("campName")) {
            return this.getCampName().compareTo(other.getCampName());
        } else if (sortBy.equals("registrationClosingDate")) {
            return this.getRegistrationClosingDate().compareTo(other.getRegistrationClosingDate());
        } else if (sortBy.equals("campVisibility")) {
            return this.getCampAvailability().compareTo(other.getCampAvailability());
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

    /**
     * The function returns the number of attendees.
     * 
     * @return The method is returning the number of attendees.
     */
    public int getNumOfAttendees() {
        return getAttendees().size();
    }

    // Helper method to calculate remaining slots for attendees
    /**
     * The function calculates the number of remaining attendee slots by subtracting
     * the total occupied slots from the total slots available.
     * 
     * @return The method is returning the number of remaining attendee slots.
     */
    public int getRemainingAttendeeSlots() {
        int totalSlots = getTotalSlots();
        int occupiedAttendeeSlots = getAttendees().size();
        int committeeMemberSlots = getCommitteeMembersSlots();
        int totalOccupiedSlots = occupiedAttendeeSlots + committeeMemberSlots;

        return totalSlots - totalOccupiedSlots;
    }

    // Helper method to calculate remaining slots for committee members
    /**
     * The function calculates the number of remaining slots in a committee by
     * subtracting the number of occupied slots from the total number of slots.
     * 
     * @return The method is returning the number of remaining committee slots.
     */
    public int getRemainingCommitteeSlots() {
        int committeeMembersSlots = getCommitteeMembersSlots();
        int occupiedCommitteeSlots = getCommitteeMembers().size();

        return committeeMembersSlots - occupiedCommitteeSlots;
    }

    /**
     * The function calculates the number of remaining slots by subtracting the
     * total occupied slots from the total slots available.
     * 
     * @return The method is returning the number of remaining slots available.
     */
    public int getRemainingSlots() {
        int totalSlots = getTotalSlots();
        int occupiedAttendeeSlots = getAttendees().size();
        int occupiedCommitteeSlots = getCommitteeMembers().size();
        int totalOccupiedSlots = occupiedAttendeeSlots + occupiedCommitteeSlots;

        return totalSlots - totalOccupiedSlots;
    }
}
