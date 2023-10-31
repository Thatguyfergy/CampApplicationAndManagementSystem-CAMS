package InfoExchange;

public class Enquiries {
    private String enquiryString;
    public Enquiries(String enqString){
        enquiryString = enqString;
    }
    public Enquiries(){
        this("NULL");
    }
    public String getEnquiry(){
        return enquiryString;
    }
    public void modifyEnquiry(String enqString){
        enquiryString = enqString;
    }
    public void removeEnquiry(){
        enquiryString = "NULL";
    }
    public void submitEnquiry(){
        // pass enquiry to staff
    }
}
