package test;

import camps.Camp;
import camps.CampArray;

public class CampCsvTest {
    public static void main(String[] args) throws Exception {
        CampArray camps = new CampArray("csvfiles\\camps.csv");
        camps.viewCamps();

    }
}
