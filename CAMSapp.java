import camsdisplay.*;

import java.io.IOException;

/**
 * The CAMSapp class contains the main method to start the application for
 * managing various camps, CAMS.
 * You may check the README.md file for more information.
 * 
 * @author Ferguson Chiew
 * @version 1.0
 */
public class CAMSapp {
    /**
     * Main function to enter app program
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        DisplayLogin cams = new DisplayLogin();

        while (true) {

            cams.startScreen();
        }

    }
}
