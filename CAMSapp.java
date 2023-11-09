import camps.*;
import users.*;
import camsdisplay.*;
import infoexchange.Enquiries;
import infoexchange.EnquiriesArray;

import java.io.IOException;
import java.util.ArrayList;

public class CAMSapp {
    public static void main(String[] args) throws IOException {
        // CAMDisplay cams = new CAMDisplay();
        // cams.startScreen();
        // EnquiriesArray enquiriesArray = new EnquiriesArray("csvfiles\\enquiries.csv",
        // "csvfiles\\replies.csv");

        // Student yinghaoisgay = new Student("YH", "Gaysch");
        // CampArray campArray = new CampArray("csvfiles\\camps.csv");

        // Staff drloke = new Staff("lokelokeloke", "lokeyman", "gayschool");

        // enquiriesArray.submitEnquiry(new Enquiries("Ying hao is gay",
        // yinghaoisgay.getID(), "gay camp"));

        // try {
        // enquiriesArray.viewEnquiries(drloke);
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // enquiriesArray.replyEnquiry(drloke);

        // drloke.createCamp(campArray);
        // drloke.viewCamp(campArray);

        CAMDisplay cams = new CAMDisplay();
        while (true) {
            cams.startScreen();
            // cams.userScreen(new Staff("Loke", "LYW023", "SCSE"));
        }

    }
}
