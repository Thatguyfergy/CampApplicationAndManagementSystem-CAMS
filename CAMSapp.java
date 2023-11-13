import camsdisplay.*;

import java.io.IOException;

public class CAMSapp {
    public static void main(String[] args) throws IOException {
        DisplayLogin cams = new DisplayLogin(true);
        // CAMDisplay cams = new CAMDisplay();
        while (true) {
            cams.startScreen();
        }

    }
}
