package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

import camps.Camp;
import users.UsersDatabase;
import users.Staff;

/**
 * PerfReport class is used to generate performance report for a camp.
 * Only a staff can generate performance reports.
 * Report contains list of Student names and their corresponding points.
 * 
 * @author Tan Jun Kiat
 * @version 1.0
 */
public class PerfReport implements Report {
    /**
     * stores the reference of the camp to generate a performance report for
     */
    private Camp camp;
    /**
     * stores integer corresponding to how the report is to be sorted
     * 1: by name; 2: by points
     */
    private int choice;
    /**
     * stores a string containing name of the output file
     */
    private String fileName;
    /**
     * stores the usersDatabase of users to access points of students in the camp
     */
    private UsersDatabase users;

    /**
     * Constructor to initialise fields for generating performance report 
     * based on specific staff, camp and sorting.
     * 
     * @param cmp   camp to generate report for
     * @param c     choice of sorting by name or points
     * @param usrs  usersDatabase reference with information on points
     * @param staff staff that wants to generate the report
     */
    public PerfReport(Camp cmp, int c, UsersDatabase usrs, Staff staff) {
        camp = cmp;
        choice = c;
        users = usrs;
        fileName = "PerfReports\\" + camp.getCampName() + "_for" + staff.getFirstName() + "_PerfReport_by" +
                ((choice == 1) ? "Name" : (choice == 2) ? "Points" : "ERROR") + ".csv";
    }

    /**
     * Method called to actually generate the report 
     * based on the set conditions in this object.
     * Calls private methods to generate report based on sorting chosen.
     * Prints error statement if invalid sorting choice was passed into constructor.
     */
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

    /**
     * Private method to generate a performance report sorted by name.
     */
    private void repByName() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        List<String> list = camp.getCommitteeMembers();
        Collections.sort(list);

        try (FileWriter w = new FileWriter(file)) {
            // w.append(" NAME || POINTS ");
            // w.append("==================================");
            w.append("NAME,POINTS\n");
            for (int i = 0; i < list.size(); i++) {
                // w.append(String.format("%20s||", list.get(i)) +
                // String.format("%10d\n",
                // users.getStudentbyID(list.get(i)).getCampCommitteeRole().getPoints()));
                w.append(list.get(i) + "," + users.getStudentbyID(list.get(i)).getCampCommitteeRole().getPoints()
                        + "\n");
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private method to generate a performance report sorted by points.
     */
    private void repByPoints() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        List<String> list = camp.getCommitteeMembers();

        try (FileWriter w = new FileWriter(file)) {
            // w.append(" NAME || POINTS ");
            // w.append("==================================");
            w.append("NAME,POINTS\n");
            for (int i = list.size(); i > 0; i--) {
                int max = users.getStudentbyID(list.get(0)).getCampCommitteeRole().getPoints();
                int maxind = 0;
                for (int j = 1; j < i; j++) {
                    // select highest points
                    int x = users.getStudentbyID(list.get(j)).getCampCommitteeRole().getPoints();
                    if (x > max) {
                        max = x;
                        maxind = j;
                    }
                }

                // append and remove
                w.append(list.get(maxind) + "," + max + "\n");
                list.remove(maxind);
            }
            w.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
