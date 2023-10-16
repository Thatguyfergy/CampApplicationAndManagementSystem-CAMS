package cams;

public class CampInfo {
    private String campName;
    private String dates;
    private String registrationClosingDate;
    private String campVisibility;
    private String location;
    private String campDescription;
    private String staffInCharge;
    private String[] committeeMembers;

    public CampInfo(String campName, String dates, String registrationClosingDate, String campVisibility,
            String location, String campDescription, String staffInCharge, String[] committeeMembers) {
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

    public String getStaffInCharge() {
        return staffInCharge;
    }

}
