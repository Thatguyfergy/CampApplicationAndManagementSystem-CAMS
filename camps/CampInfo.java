package camps;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CampInfo {
    private String campName;
    private ArrayList<Date> dates;
    private String registrationClosingDate;
    private String campVisibility;
    private String location;
    private String campDescription;
    private String staffInCharge;
    private String[] committeeMembers;

    // Default constructor - Set all to empty string and create comitteeMembers array
    public CampInfo () {
        this.campName = "";
        this.dates = new ArrayList<Date>();
        this.registrationClosingDate = "";
        this.campVisibility = "";
        this.location = "";
        this.campDescription = "";
        this.staffInCharge = "";
        this.committeeMembers = new String[10];
    }

    // Constructor with all fields & create comitteeMembers array
    public CampInfo(String campName, String dates, String registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge, String [] committeeMembers) {
        this.campName = campName;
        this.dates = new ArrayList<Date>();
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.committeeMembers = new String[10];
    }

    // Getter methods for all individual fields
    public String getCampName() {
        return campName;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public String getRegistrationClosingDate() {
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

    public String[] getCommitteeMembers() {
        return committeeMembers;
    }

    // Setter methods for all individual fields
    public void setCampName(String campName) {
        this.campName = campName;
    }

    public void setRegistrationClosingDate(String registrationClosingDate) {
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
        for (int i = 0; i < committeeMembers.length; i++) {
            if (committeeMembers[i] != null){
                committeeMembers[i] = committeeMember;
                return;
            }
        }
    }

    // used to add dates that the camp is running for
    public void addDate(Date startDate, Date endDate) {
        if (startDate.compareTo(endDate) <= 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while (calendar.getTime().compareTo(endDate) <= 0) {
                dates.add(calendar.getTime());
                calendar.add(Calendar.DATE, 1);
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
    public void setCampInfo(String campName, String registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge) {
        this.campName = campName;
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }
}
