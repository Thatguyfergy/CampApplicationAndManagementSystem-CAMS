package camps;

import java.util.Calendar;

public class CampInfoTest {
    public static void main (String args[]){
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 11, 31);
        CampInfo campInfo = new CampInfo("Gulag", calendar.getTime(), "NTU", "NTU", "Concnetration camp", "Stalin");
        System.out.println(campInfo.getRegistrationClosingDate());
        
    }
}
