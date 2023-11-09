package InfoExchange;

import camps.Camp;
import users.*;

public class Enquiries implements Comparable<Enquiries> {
    private String enquiryID;
    private String enquiryString;
    private String sender;
    private String campName;
    private boolean submitted=false;

    public Enquiries(String enqString, String sender, String campName) {
        this.enquiryString = enqString;
        this.sender = sender;
        this.campName = campName;
    }

    public String getEnquiry() {
        return enquiryString;
    }

    public String getSender() {
        return sender;
    }

    public String getCampName() {
        return campName;
    }

    public void modifyEnquiry(String enqString) {
        enquiryString = enqString;
    }

    public void setEnquiryID(String id) {
        this.enquiryID = id;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public boolean getSubmitted(){
        return submitted;
    }

    public void setSubmittedTrue(){
        submitted = true;
    }

    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
