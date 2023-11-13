import camsdisplay.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CAMSapp {
    public static void main(String[] args) throws IOException {

        // Now there are 2 implementations, DisplayLogin that use DIP and Noob
        // CAMDisplay
        // Comment out the one you dw to use, tyvm

        // DisplayLogin cams = new DisplayLogin(true);

        CAMDisplay cams = new CAMDisplay();

        if (cams instanceof CAMDisplay) {
            System.out.println("Currently running CampDisplay!");
        } else {
            System.out.println("Currently running DisplayLogin!");
        }

        System.out.println("This shit is running on CAMSapp.java, go and dig for this file.");

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (true) {

            cams.startScreen();
        }

    }
}
