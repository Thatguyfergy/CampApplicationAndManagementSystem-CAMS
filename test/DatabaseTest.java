package test;

import java.io.IOException;

import camps.CampArray;
import users.Staff;

public class DatabaseTest {
    public static void main(String[] args) throws NumberFormatException, IOException {
        CampArray campArray = new CampArray("csvfiles\\camps.csv");
        // for (int i = 0; i < CampArray.camps.size(); i++) {
        //     System.out.println(CampArray.camps.get(i));
        // }
        Staff drloke = new Staff("lokeyman", "gayschool");
        campArray.viewCamps(drloke);
        
    }
}
