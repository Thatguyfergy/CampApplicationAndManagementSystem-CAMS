package camps;

import java.util.ArrayList;

import camdate.CAMDate;

public class CampInfo {
    //private int campID;
    private String campName;
    private ArrayList<CAMDate> dates;
    private CAMDate registrationClosingDate;
    private String campVisibility;
    private String location;
    private int totalSlots;
    private int committeeMembersSlots;
    private String campDescription;
    private String staffInCharge;

    // Default constructor - Set all to empty string and create comitteeMembers
    // array
    public CampInfo() {
        //this.campID = 0;
        this.campName = "";
        this.dates = new ArrayList<CAMDate>();
        this.campVisibility = "";
        this.location = "";
        this.totalSlots = 0;
        this.committeeMembersSlots = 0;
        this.campDescription = "";
        this.staffInCharge = "";
    }

    // Constructor with all fields & create comitteeMembers array
    public CampInfo(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        
        //this.campID = campID;
        this.campName = campName;
        this.dates = new ArrayList<CAMDate>();
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeMembersSlots = committeeMembersSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }

    // Getter methods for all individual fields
    // public int getCampID() {
    //     return campID;
    // }

    public String getCampName() {
        return campName;
    }

    public ArrayList<CAMDate> getDates() {
        return dates;
    }

    public CAMDate getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    public String getCampVisibility() {
        return campVisibility;
    }

    public String getLocation() {
        return location;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public int getCommitteeMembersSlots() {
        return committeeMembersSlots;
    }

    public String getCampDescription() {
        return campDescription;
    }

    public String getStaffInCharge() {
        return staffInCharge;
    }

    // Setter methods for all individual fields
    // public void setCampID(int campID) {
    //     this.campID = campID;
    // }


    public void setCampName(String campName) {
        this.campName = campName;
    }

    public void setRegistrationClosingDate(CAMDate registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    public void setCampVisibility(String campVisibility) {
        this.campVisibility = campVisibility;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTotalSlots(int TotalSlots) {
        this.totalSlots = TotalSlots;
    }

    public void setCommitteeMembersSlots(int committeeMembersSlots) {
        this.committeeMembersSlots = committeeMembersSlots;
    }

    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    // used to add dates that the camp is running for
    public void addDate(CAMDate startDate, CAMDate endDate) {
        CAMDate temp = startDate;
        if (startDate.compareTo(endDate) <= 0) {
            while (temp.compareTo(endDate) <= 0) {
                dates.add(temp);
                temp = new CAMDate(temp.getDay(), temp.getMonth(), temp.getYear());
                temp.nextDay();
            }
        } else {
            System.out.println("Error: Start date cannot be after end date");
        }
    }

    public void addDate(CAMDate date) {
        dates.add(date);
    }

    // Get all camp info in a single string
    private String getCampInfo() {
        return "Camp Name: " + campName + "\n" + "Camp Dates: " + dates + "\n" + "Registration Closing Date: "
                + registrationClosingDate + "\n" + "Camp Visibility: " + campVisibility + "\n" + "Location: " + location
                + "\n" + "Total Slots: " + totalSlots + "\n" + "Committee Members Slots: " + committeeMembersSlots
                + "\n" + "Camp Description: " + campDescription + "\n" + "Staff In Charge: " + staffInCharge + "\n";
    }

    // Override toString method to return getCampInfo
    @Override
    public String toString() {
        return getCampInfo();
    }

    // Set all camp info except commitee members and camp dates in a single method
    public void setCampInfo(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        this.campName = campName;
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeMembersSlots = committeeMembersSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }
}
