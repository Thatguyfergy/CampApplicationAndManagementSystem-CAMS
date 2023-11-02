package database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// //import camps.Camp;
import camps.CampArray;
import camps.Camp;
import camdate.CAMDate;

public class Database {
    public static CampArray initialiseCampArray() {
        CampArray campArray = new CampArray();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("csvfiles\\camps.csv"));
            String header = reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(",");
                Camp temp = new Camp(Integer.parseInt(data[0]), data[1], new CAMDate(data[3]), data[4], data[5], Integer.parseInt(data[6]), Integer.parseInt(data[8]), data[9], data[10]);
                
                String[] dates = data[2].split(";");
                for (String date : dates) {
                    temp.addDate(new CAMDate(date));
                }
                
                // String[] campComMem = data[7].split(";"); 
                // for (String comMem : campComMem) {
                //     temp.registerStudent(comMem, true, temp.getCampName());
                // }

                // String[] campAtt = data[11].split(";");
                // for (String att : campAtt) {
                //     temp.registerStudent(att, false, temp.getCampName());
                // }
                campArray.createCamp(temp);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return campArray;
    }
}
