package InfoExchange;

public class EnqReplies implements Comparable<Enquiries> {
    private String enquiryID;
    private String replyID;
    private String replyString;
    private String recipient;

    public EnqReplies(String replyString, String enquiryID, String recipient, String replyID) {
        this.replyString = replyString;
        this.enquiryID = enquiryID;
        this.recipient = recipient;
        this.replyID = replyID;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public String getReplyString() {
        return replyString;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getReplyID() {
        return replyID;
    }

    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
