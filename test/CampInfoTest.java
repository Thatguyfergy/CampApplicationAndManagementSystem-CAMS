package test;

import camps.*;
import camdate.CAMDate;

public class CampInfoTest {
    public static void main(String[] args) {
        CampInfo campInfo = new CampInfo("Gulag", new CAMDate(1, 1, 2020), "NTI", "Gulag", 100, 10, "Concentration Camp", "Stalin");
        campInfo.addDate(new CAMDate(15, 2, 2020), new CAMDate(20, 2, 2020));
        System.out.println(campInfo);

    }
}
