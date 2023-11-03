package test;

import camps.Camp;
import camps.CampArray;
import users.Staff;
import users.UsersDatabase;

public class CampCsvTest {
    public static void main(String[] args) throws Exception {
        CampArray camps = new CampArray("csvfiles\\camps.csv");
        // camps.createCamp("Loke");
        Staff staff = new Staff("lokelokeloke", "Loke", "SCSE");
        camps.sortCamps("campName");
        // camps.viewCamps(staff);
        // camps.sortCamps( "campName");
        // camps.viewCamps(staff);

        UsersDatabase users = new UsersDatabase("");
    }
}
