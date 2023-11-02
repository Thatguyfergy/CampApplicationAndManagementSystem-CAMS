import camps.*;
import users.*;
import camsdisplay.*;

import java.io.IOException;
import java.util.ArrayList;

import InfoExchange.Enquiries;
import InfoExchange.EnquiriesArray;

public class CAMSapp {
    public static void main(String[] args) throws IOException {
        // CAMDisplay cams = new CAMDisplay();
        // cams.startScreen();
        // EnquiriesArray enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv",
        // "csvfiles\\replies.csv");

        // Student yinghaoisgay = new Student("YH", "Gaysch");
        CampArray campArray = new CampArray("csvfiles\\camps.csv");

        Staff drloke = new Staff("lokeyman", "gayschool");

        // enquiriesArray.submitEnquiry(new Enquiries("Ying hao is gay",
        // yinghaoisgay.getID(), "gay camp"));

        // try {
        // enquiriesArray.viewEnquiries(drloke);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // enquiriesArray.replyEnquiry(drloke);

        drloke.createCamp(campArray);
        drloke.viewCamp(campArray);
    }
}
