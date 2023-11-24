package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import camps.Camp;
import camps.CampArray;
import users.Staff;
import users.Student;
import users.Users;
import users.UsersDatabase;

/**
 * The CampReport class implements the Report interface.
 * It generates the camp report for Staff and Camp Comm
 * 
 * @author Tan Ying Hao
 * @version 1.0
 */
public class CampReport implements Report {

    private Camp camp;
    private Scanner sc;
    private Users user;
    private ArrayList<String> campOptions;
    private CampArray campArray;
    private String fileName;

    /**
     * The constructor for the `CampReport` class.
     * 
     * @param user      The parameter 'user' represents the User object that is
     *                  generating the report.
     * @param campArray The parameter 'campArray' represents the CampArray object
     *                  that contains all the camps.
     */
    public CampReport(Users user, CampArray campArray) {
        this.user = user;
        this.campArray = campArray;
        if (user instanceof Staff)
            campOptions = ((Staff) user).getCampsInCharge();
        else {
            campOptions = new ArrayList<String>();
            campOptions.add(((Student) user).getCampCommitteeRole().getCampName());
        }

        sc = new Scanner(System.in);
    }

    /**
     * The method `generateReport()` prompts the user to select a camp to generate
     * the report for.
     * It then prompts the user to select a filter. The method then calls the
     * appropriate private method according to the filter selected
     * and generates a report in CSV format.
     */
    public void generateReport() {

        if (campOptions.size() == 0) {
            System.out.println("You have not created any camps!");
            return;
        }
        System.out.println("Select a camp to generate report for: ");
        for (String camp : campOptions) {
            System.out.println(campOptions.indexOf(camp) + 1 + ". " + camp);
        }
        System.out.println(campOptions.size() + 1 + ". Exit (not a camp name)");

        String choice;
        try {
            System.out.printf("Enter choice: ");
            choice = sc.nextLine();
            if (Integer.parseInt(choice) == campOptions.size() + 1)
                return;
            if (Integer.parseInt(choice) > campOptions.size() + 1)
                throw new NumberFormatException();

        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }
        System.out.println();

        camp = campArray.getCamp(campOptions.get(Integer.parseInt(choice) - 1));

        System.out.println("Select Filter: ");
        System.out.println("1. No Filter");
        System.out.println("2. Attendees");
        System.out.println("3. Committee Members");
        System.out.printf("Enter choice: ");
        choice = sc.nextLine();

        switch (choice) {
            case "1":
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName() + "_Report.csv";
                generateCampReport();
                break;
            case "2":
                fileName = "CampReports\\" + camp.getCampName() + "_Attendees_Report.csv";
                generateCampAttendeesReport();
                break;
            case "3":
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName()
                        + "_CommitteeMembers_Report.csv";
                generateCampCommitteeMembersReport();
                break;
            default:
                System.out.println("Invalid choice! - Applying no filter");
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName() + "_Report.csv";
                generateCampReport();
                break;
        }

        System.out.println("Generating report for " + camp.getCampName() + "...");
    }

    /**
     * The method 'generateCampReport()' generates a report in CSV format containing
     * the camp information, list of attendees and list of committee members.
     */
    private void generateCampReport() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        try (FileWriter csvWriter = new FileWriter(file)) {
            csvWriter.append("Camp Name,");
            csvWriter.append(camp.getCampName());
            csvWriter.append("\n");
            csvWriter.append("Registration Closing Date,");
            csvWriter.append(camp.getRegistrationClosingDate().toString());
            csvWriter.append("\n");
            csvWriter.append("Camp Dates,");
            csvWriter.append(camp.getFormatedDates());
            csvWriter.append("\n");
            csvWriter.append("Camp Availability,");
            csvWriter.append(camp.getCampAvailability());
            csvWriter.append("\n");
            csvWriter.append("Location,");
            csvWriter.append(camp.getLocation());
            csvWriter.append("\n");
            csvWriter.append("Total Slots,");
            csvWriter.append(Integer.toString(camp.getTotalSlots()));
            csvWriter.append("\n");
            csvWriter.append("Committee Members Slots,");
            csvWriter.append(Integer.toString(camp.getCommitteeMembersSlots()));
            csvWriter.append("\n");
            csvWriter.append("Camp Description,");
            csvWriter.append(camp.getCampDescription());
            csvWriter.append("\n");
            csvWriter.append("Staff In Charge,");
            csvWriter.append(camp.getStaffInCharge());
            csvWriter.append("\n");
            csvWriter.append("Attendees,");
            for (String attendee : camp.getAttendees()) {
                csvWriter.append(UsersDatabase.getFirstName(attendee));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.append("Committee Members,");
            for (String committeeMember : camp.getCommitteeMembers()) {
                csvWriter.append(committeeMember);
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method 'generateCampAttendeesReport()' generates a CSV report containing
     * the camp information and list of attendees.
     */
    private void generateCampAttendeesReport() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        try (FileWriter csvWriter = new FileWriter(file)) {
            csvWriter.append("Camp Name,");
            csvWriter.append(camp.getCampName());
            csvWriter.append("\n");
            csvWriter.append("Registration Closing Date,");
            csvWriter.append(camp.getRegistrationClosingDate().toString());
            csvWriter.append("\n");
            csvWriter.append("Camp Dates,");
            csvWriter.append(camp.getFormatedDates());
            csvWriter.append("\n");
            csvWriter.append("Camp Availability,");
            csvWriter.append(camp.getCampAvailability());
            csvWriter.append("\n");
            csvWriter.append("Location,");
            csvWriter.append(camp.getLocation());
            csvWriter.append("\n");
            csvWriter.append("Total Slots,");
            csvWriter.append(Integer.toString(camp.getTotalSlots()));
            csvWriter.append("\n");
            csvWriter.append("Committee Members Slots,");
            csvWriter.append(Integer.toString(camp.getCommitteeMembersSlots()));
            csvWriter.append("\n");
            csvWriter.append("Camp Description,");
            csvWriter.append(camp.getCampDescription());
            csvWriter.append("\n");
            csvWriter.append("Staff In Charge,");
            csvWriter.append(camp.getStaffInCharge());
            csvWriter.append("\n");
            csvWriter.append("Attendees,");
            for (String attendee : camp.getAttendees()) {
                csvWriter.append(UsersDatabase.getFirstName(attendee));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method 'generateCampCommitteeMembersReport()' generates a CSV report
     * containing
     * the camp information and list of committee members.
     */
    private void generateCampCommitteeMembersReport() {
        File file = new File(fileName);
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        try (FileWriter csvWriter = new FileWriter(file)) {
            csvWriter.append("Camp Name,");
            csvWriter.append(camp.getCampName());
            csvWriter.append("\n");
            csvWriter.append("Registration Closing Date,");
            csvWriter.append(camp.getRegistrationClosingDate().toString());
            csvWriter.append("\n");
            csvWriter.append("Camp Dates,");
            csvWriter.append(camp.getFormatedDates());
            csvWriter.append("\n");
            csvWriter.append("Camp Availability,");
            csvWriter.append(camp.getCampAvailability());
            csvWriter.append("\n");
            csvWriter.append("Location,");
            csvWriter.append(camp.getLocation());
            csvWriter.append("\n");
            csvWriter.append("Total Slots,");
            csvWriter.append(Integer.toString(camp.getTotalSlots()));
            csvWriter.append("\n");
            csvWriter.append("Committee Members Slots,");
            csvWriter.append(Integer.toString(camp.getCommitteeMembersSlots()));
            csvWriter.append("\n");
            csvWriter.append("Camp Description,");
            csvWriter.append(camp.getCampDescription());
            csvWriter.append("\n");
            csvWriter.append("Staff In Charge,");
            csvWriter.append(camp.getStaffInCharge());
            csvWriter.append("\n");
            csvWriter.append("Committee Members,");
            for (String committeeMember : camp.getCommitteeMembers()) {
                csvWriter.append(UsersDatabase.getFirstName(committeeMember));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
