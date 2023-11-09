package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

import camps.Camp;
import users.UsersDatabase;

public class PerfReport implements Report {

    private Camp camp;
    private int choice;
    private String fileName;
    private UsersDatabase users;

    public PerfReport(Camp cmp, int c, UsersDatabase usrs) {
        camp = cmp;
        choice = c;
        users = usrs;
        fileName = "PerfReports\\" + camp.getCampName() + "_PerfReport_by" + 
        ((choice==1)? "Name":(choice==2)? "Points":"ERROR") + ".csv";
    }

    public void generateReport() {
        System.out.println("Generating performance report for " + camp.getCampName() + "...");
        switch (choice) {
            case 1:
                repByName();
                break;
            case 2:
                repByPoints();
                break;
            default:
                System.out.println("Error! Invalid Sorting!");
        }
    }
    
    private void repByName() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        List<String> list = camp.getCommitteeMembers();
        Collections.sort(list);

        try (FileWriter w = new FileWriter(file)) {
            // w.append("        NAME        ||  POINTS  ");
            // w.append("==================================");
            w.append("NAME,POINTS\n");
            for (int i=0; i<list.size(); i++) {
                // w.append(String.format("%20s||", list.get(i)) + 
                // String.format("%10d\n", users.getStudentbyID(list.get(i)).getCampCommitteeRole().getPoints()));
                w.append(list.get(i)+","+users.getStudentbyID(list.get(i)).getCampCommitteeRole().getPoints()+"\n");
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void repByPoints() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        List<String> list = camp.getCommitteeMembers();

        try (FileWriter w = new FileWriter(file)) {
            // w.append("        NAME        ||  POINTS  ");
            // w.append("==================================");
            w.append("NAME,POINTS\n");
            for (int i=list.size(); i>0; i--) {
                int max = users.getStudentbyID(list.get(0)).getCampCommitteeRole().getPoints();
                int maxind = 0;
                for (int j=1; j<i; j++) {
                    // select highest points
                    int x = users.getStudentbyID(list.get(j)).getCampCommitteeRole().getPoints();
                    if (x > max) {
                        max = x;
                        maxind = j;
                    }
                }
                
                // append and remove
                w.append(list.get(maxind)+","+ max +"\n");
                list.remove(maxind);
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
