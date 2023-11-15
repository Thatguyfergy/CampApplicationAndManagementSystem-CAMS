package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;
import users.Staff;
import users.Student;
import users.Users;
import users.UsersDatabase;

public class CampReport implements Report {

    private Camp camp;
    private Scanner sc;
    private Users user;
    private ArrayList<String> createdCamps;
    private CampArray campArray;
    private String fileName;

    // The `CampReport` constructor is initializing the `user` and `campArray`
    // variables with the
    // provided parameters. It also checks if the `user` is an instance of `Staff`
    // or `Student` and
    // assigns the appropriate value to the `createdCamps` variable. Finally, it
    // initializes the `sc`
    // variable with a new `Scanner` object.
    public CampReport(Users user, CampArray campArray) {
        this.user = user;
        this.campArray = campArray;
        if (user instanceof Staff)
            createdCamps = ((Staff) user).getCampsInCharge();
        else
            createdCamps.add(((Student) user).getCampCommitteeRole().getCampName());
        sc = new Scanner(System.in);
    }

    // The `public CampReport(Users usr, Camp cmp)` constructor is initializing the
    // `user`, `camp`, and
    // `sc` variables with the provided parameters. It is used to create a
    // `CampReport` object with a
    // specific `user` and `camp` for generating a report.
    public CampReport(Users usr, Camp cmp) {
        user = usr;
        camp = cmp;
        sc = new Scanner(System.in);
    }

    /**
     * The function generates a report based on the user's choice and saves it to a
     * file.
     * 
     * @param choice The parameter "choice" is an integer that represents the user's
     *               selection for the
     *               type of report to generate. The possible values for "choice"
     *               are:
     */
    public void generateReportforComm(int choice) {
        switch (choice) {
            case 1:
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName() + "_Report.csv";
                generateCampReport();
                break;
            case 2:
                fileName = "CampReports\\" + camp.getCampName() + "_Attendees_Report.csv";
                generateCampAttendeesReport();
                break;
            case 3:
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
     * The function generates a report for a selected camp based on the user's
     * choice of filter.
     */
    public void generateReport() {

        if (createdCamps.size() == 0) {
            System.out.println("You have not created any camps!");
            return;
        }
        System.out.println("Select a camp to generate report for: ");
        for (String camp : createdCamps) {
            System.out.println(createdCamps.indexOf(camp) + 1 + ". " + camp);
        }
        System.out.println(createdCamps.size() + 1 + ". Exit (not a camp name)");

        String choice;
        try {
            System.out.printf("Enter choice: ");
            choice = sc.nextLine();
            if (Integer.parseInt(choice) == createdCamps.size() + 1)
                return;
            if (Integer.parseInt(choice) > createdCamps.size() + 1)
                throw new NumberFormatException();

        } catch (NumberFormatException e) {
            System.out.println("Invalid choice!");
            return;
        }

        camp = campArray.getCamp(createdCamps.get(Integer.parseInt(choice) - 1));

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
     * The function `generateCampReport()` generates a CSV report file containing
     * information about a
     * camp.
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
     * The function generates a CSV report containing information about a camp and
     * its attendees.
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
     * The function generates a report in CSV format containing information about a
     * camp and its
     * committee members.
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
