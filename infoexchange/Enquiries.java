package infoexchange;

import camps.Camp;
import users.*;

public class Enquiries implements Comparable<Enquiries> {
    private String enquiryID;
    private String enquiryString;
    private String sender;
    private String campName;
    private boolean submitted = false;

    public Enquiries(String enqString, String sender, String campName) {
        this.enquiryString = enqString;
        this.sender = sender;
        this.campName = campName;
    }

    /**
     * The function "getEnquiry" returns the enquiry string.
     * 
     * @return The method is returning the value of the variable "enquiryString".
     */
    public String getEnquiry() {
        return enquiryString;
    }

    /**
     * The function "getSender" returns the value of the variable "sender".
     * 
     * @return The method is returning the value of the variable "sender".
     */
    public String getSender() {
        return sender;
    }

    /**
     * The function returns the name of a camp.
     * 
     * @return The method is returning the value of the variable "campName".
     */
    public String getCampName() {
        return campName;
    }

    /**
     * The function modifies the value of the enquiryString variable.
     * 
     * @param enqString The enqString parameter is a string that represents the
     *                  modified enquiry.
     */
    public void modifyEnquiry(String enqString) {
        enquiryString = enqString;
    }

    /**
     * The function sets the enquiryID variable to the provided id value.
     * 
     * @param id The id parameter is a String that represents the enquiry ID.
     */
    public void setEnquiryID(String id) {
        this.enquiryID = id;
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
     * The function returns the value of the submitted variable.
     * 
     * @return The method is returning the value of the variable "submitted".
     */
    public boolean getSubmitted() {
        return submitted;
    }

    /**
     * The function sets the value of the variable "submitted" to true.
     */
    public void setSubmittedTrue() {
        submitted = true;
    }

    /**
     * The function changes the name of a camp.
     * 
     * @param newName The new name that you want to assign to the camp.
     */
    public void changeCampName(String newName) {
        campName = newName;
    }

    /**
     * The function compareTo is not implemented and throws an
     * UnsupportedOperationException.
     * 
     * @param o The parameter 'o' is an object of type 'Enquiries'.
     * @return The method is not implemented, so it throws an
     *         UnsupportedOperationException.
     */
    @Override
    public int compareTo(Enquiries o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
