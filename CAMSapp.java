import camsdisplay.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * The CAMSapp class is a Java program that represents an application for
 * managing various camps.
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

        DisplayLogin cams = new DisplayLogin(true);

        while (true) {

            cams.startScreen();
        }

    }
}
