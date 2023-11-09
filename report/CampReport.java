package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.w3c.dom.UserDataHandler;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;
import users.Staff;
import users.Users;
import users.UsersDatabase;


public class CampReport implements Report {

    Camp camp;
    Scanner sc = new Scanner(System.in);
    Staff user;
    String[] createdCamps = user.getCampsInCharge();
    String fileName;

    public CampReport(Staff user) {
        this.user = user;
    }

    public void generateReport() {
        System.out.println("Select a camp to generate report for: ");
        for (int i = 0; i < user.get.length; i++) {
            System.out.println(i + 1 + ". " + createdCamps[i]);
        }
        System.out.printf("Enter choice: ");
        String choice = sc.nextLine();
        camp = camps.getCamp(createdCamps[Integer.parseInt(choice) - 1]);

        System.out.println("Select Filter: ");
        System.out.println("1. No Filter");
        System.out.println("2. Attendees");
        System.out.println("3. Committee Members");
        System.out.printf("Enter choice: ");
        choice = sc.nextLine();

        switch (choice) {
            case "1":
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName() +"_Report.csv";
                generateCampReport();
                break;
            case "2":
                fileName = "CampReports\\" + camp.getCampName() + "_Attendees_Report.csv";
                generateCampAttendeesReport();
                break;
            case "3":
                fileName = "CampReports\\" + camp.getCampName() + "_" + user.getFirstName() + "_CommitteeMembers_Report.csv";
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

    private void generateCampReport( ) {
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
            for (CAMDate date : camp.getDates()) {
                csvWriter.append(date.toString());
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.append("Camp Visibility,");
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
            csvWriter.append("Attendee,");
            for (String attendee : camp.getAttendees()) {
                
                csvWriter.append(UsersDatabase.getFirstName(attendee));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.append("Committee Member,");
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

    private void generateCampAttendeesReport( ) {
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
            for (CAMDate date : camp.getDates()) {
                csvWriter.append(date.toString());
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.append("Camp Visibility,");
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
            csvWriter.append("Attendee,");
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
            for (CAMDate date : camp.getDates()) {
                csvWriter.append(date.toString());
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.append("Camp Visibility,");
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
            csvWriter.append("Committee Member,");
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
