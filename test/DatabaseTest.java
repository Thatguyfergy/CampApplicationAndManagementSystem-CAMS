package test;

import camps.CampArray;
import database.Database;

public class DatabaseTest {
    public static void main(String[] args) {
        CampArray campArray = Database.initialiseCampArray();
        // System.out.println(campArray.camps.get(0).campInfo);
        for (int i = 0; i < campArray.camps.size(); i++) {
            System.out.println(campArray.camps.get(i));
        }
        // System.out.println(campArray.camps.get(0));

    }
}
