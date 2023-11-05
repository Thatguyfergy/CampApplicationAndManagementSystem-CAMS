package InfoExchange;

import camps.Camp;
import users.*;

public class Enquiries implements Comparable<Enquiries> {
    private String enquiryID;
    private String enquiryString;
    private Users sender;
    private Camp camp;


    public Enquiries(String enqString, Users sender, Camp camp) {
        this.enquiryString = enqString;
        this.sender = sender;
        this.camp = camp;
    }

    public String getEnquiry() {
        return enquiryString;
    }

    public Users getSender() {
        return sender;
    }

    public String getCampName() {
        return camp.getCampName();
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

    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
