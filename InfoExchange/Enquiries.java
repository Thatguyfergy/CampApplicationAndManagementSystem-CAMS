package InfoExchange;

public class Enquiries implements Comparable<Enquiries> {
    private String enquiryString;
    private String sender;
    private String campName;
    private Boolean submitted = false;

    public Enquiries(String enqString, String senderName, String campName) {
        this.enquiryString = enqString;
        this.sender = senderName;
        this.campName = campName;
    }

    public Enquiries() {
        this(null, null, null);
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

    public void submit() {
        submitted = true;
    }

    public boolean getSubmitStatus() {
        return submitted;
    }

    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
