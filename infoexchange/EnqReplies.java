package infoexchange;

public class EnqReplies implements Comparable<Enquiries> {
    private String enquiryID;
    private String replyID;
    private String replyString;
    private String recipient;
    private String replyCreator;

    /**
     * The `EnqReplies` class has a constructor that takes in five parameters:
     * `replyString`,
     * `enquiryID`, `recipient`, `replyID`, and `replyCreator`.
     * 
     * @param replyString
     * @param enquiryID
     * @param recipient
     * @param replyID
     * @param replyCreator
     */
    public EnqReplies(String replyString, String enquiryID, String recipient, String replyID, String replyCreator) {
        this.replyString = replyString;
        this.enquiryID = enquiryID;
        this.recipient = recipient;
        this.replyID = replyID;
        this.replyCreator = replyCreator;
    }

    /**
     * The function returns the enquiry ID as a string.
     * 
     * @return The method is returning the value of the enquiryID variable.
     */
    public String getEnquiryID() {
        return enquiryID;
    }

    /**
     * The function returns a string value.
     * 
     * @return The method is returning a String value.
     */
    public String getReplyString() {
        return replyString;
    }

    /**
     * The function "getRecipient" returns the recipient of a message.
     * 
     * @return The recipient.
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * The function returns the reply ID as a string.
     * 
     * @return The method is returning a String value.
     */
    public String getReplyID() {
        return replyID;
    }

    /**
     * The function "getReplyCreator" returns the reply creator.
     * 
     * @return The method is returning a String value.
     */
    public String getReplyCreator() {
        return replyCreator;
    }

    /**
     * The function compareTo is not implemented and throws an
     * UnsupportedOperationException.
     * 
     * @param o The parameter 'o' is an object of type 'Enquiries'.
     * @return The method is throwing an UnsupportedOperationException, so nothing
     *         is being returned.
     */
    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
