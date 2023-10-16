package camps;

import java.util.ArrayList;

public class Camp {
   private CampInfo campInfo;
   private String[] attendees;
   
   public Camp() {
        //Create campInfo
        this.campInfo = new CampInfo();
        this.attendees = new String[10]; // Adjust the size as needed
        
    
        
    }

    public String getCampName() {
        return campInfo.getCampName();
    }

    public ArrayList<String> getDates() {
        return campInfo.getDates();
    }

    public String getRegistrationClosingDate() {
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

    public String[] getCommitteeMembers() {
        return campInfo.getCommitteeMembers();
    }

// Method to get the list of attendees
public String[] getAttendees() {
    return attendees;
}

}
    