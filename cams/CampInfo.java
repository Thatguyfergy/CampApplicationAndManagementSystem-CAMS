package cams;

import java.util.ArrayList;

public class CampInfo {
    private String campName;
    private String dates;
    private String registrationClosingDate;
    private String campVisibility;
    private String location;
    private String campDescription;
    private String staffInCharge;
    private String[] committeeMembers;

    public CampInfo () {
        this.campName = "";
        this.dates = "";
        this.registrationClosingDate = "";
        this.campVisibility = "";
        this.location = "";
        this.campDescription = "";
        this.staffInCharge = "";
        this.committeeMembers = null;
    }

    public CampInfo(String campName, String dates, String registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge, String [] committeeMembers) {
        this.campName = campName;
        this.dates = dates;
        this.registrationClosingDate = registrationClosingDate;
        this.campVisibility = campVisibility;
        this.location = location;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
        this.committeeMembers = committeeMembers;
    }

    public String getCampName() {
        return campName;
    }

    public String getDates() {
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

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public void setDates(String dates) {
        this.dates = dates;
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

    public void setCommitteeMembers(ArrayList committeeMembers) {
        this.committeeMembers = committeeMembers;
    }

}
