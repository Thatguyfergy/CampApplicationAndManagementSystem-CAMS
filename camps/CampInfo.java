package camps;

import java.util.ArrayList;

import CAMDate.CAMDate;

public class CampInfo {
    private String campName;
    private ArrayList<CAMDate> dates;
    private CAMDate registrationClosingDate;
    private String campVisibility;
    private String location;
    private String campDescription;
    private String staffInCharge;
    private ArrayList<String> committeeMembers;

    // Default constructor - Set all to empty string and create comitteeMembers array
    public CampInfo () {
        this.campName = "";
        this.dates = new ArrayList<CAMDate>();
        this.campVisibility = "";
        this.location = "";
        this.campDescription = "";
        this.staffInCharge = "";
        this.committeeMembers = new ArrayList<String>();
    }

    // Constructor with all fields & create comitteeMembers array
    public CampInfo(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge) {
        this.campName = campName;
        this.dates = new ArrayList<CAMDate>();
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.committeeMembers = new ArrayList<String>();
    }

    // Getter methods for all individual fields
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

    public String getCampDescription() {
        return campDescription;
    }

    public String getStaffInCharge() {
        return staffInCharge;
    }

    public ArrayList<String> getCommitteeMembers() {
        return committeeMembers;
    }

    // Setter methods for all individual fields
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

    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    // used to add committee members into the committeeMembers array
    public void addCommitteeMembers(String committeeMember) {
        if (committeeMembers.size() < 10) {
            committeeMembers.add(committeeMember);
        }
        else {
            System.out.println("Error: Committee members cannot exceed 10");
        }
    }

    // used to add dates that the camp is running for
    public void addDate(CAMDate startDate,  CAMDate endDate) {
        CAMDate temp = startDate;
        if (startDate.compareTo(endDate) <= 0) {
            while (temp.compareTo(endDate) <= 0) {
                dates.add(temp);
                temp = new CAMDate(temp.getDay(), temp.getMonth(), temp.getYear());
                temp.nextDay();
            }
        } 
        else {
            System.out.println("Error: Start date cannot be after end date");
        }
    }

    // Get all camp info in a single string
    private String getCampInfo () {
        return "Camp Name: " + campName + "\n" +
                "Dates: " + dates + "\n" +
                "Registration Closing Date: " + registrationClosingDate + "\n" +
                "Camp Visibility: " + campVisibility + "\n" +
                "Location: " + location + "\n" +
                "Camp Description: " + campDescription + "\n" +
                "Staff In Charge: " + staffInCharge + "\n" +
                "Committee Members: " + committeeMembers + "\n";
    }

    // Override toString method to return getCampInfo
    @Override
    public String toString() {
        return getCampInfo();
    }

    // Set all camp info except commitee members and camp dates in a single method
    public void setCampInfo(String campName, CAMDate registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge) {
        this.campName = campName;
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }
}
