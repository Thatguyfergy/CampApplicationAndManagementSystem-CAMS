package InfoExchange;

public class Enquiries implements Comparable<Enquiries> {
    private String enquiryID;
    private String enquiryString;
    private String sender;
    private String campName;

    public Enquiries(String enqString, String senderName, String campName) {
        this.enquiryString = enqString;
        this.sender = senderName;
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

    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
