package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.w3c.dom.UserDataHandler;

import camdate.CAMDate;
import camps.Camp;
import users.UsersDatabase;

public class CampReport implements Report {

    Camp camp;
    int choice;
    String fileName;

    public CampReport(Camp camp, int choice) {
        this.camp = camp;
        this.choice = choice;
        this.fileName = "CampReports\\" + camp.getCampName() + "_Report.csv";
    }

    public void generateReport(UsersDatabase userDB) {
        System.out.println("Generating report for " + camp.getCampName() + "...");
        if (choice == 1) {
            generateCampReport(userDB);
        } else if (choice == 2) {
            generateCampAttendeesReport(userDB);
        } else if (choice == 3) {
            generateCampCommitteeMembersReport(userDB);
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void generateCampReport(UsersDatabase userDB) {
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
                
                csvWriter.append(userDB.getFirstName(attendee));
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

    private void generateCampAttendeesReport(UsersDatabase userDB) {
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
                csvWriter.append(userDB.getFirstName(attendee));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCampCommitteeMembersReport(UsersDatabase userDB) {
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
                csvWriter.append(userDB.getFirstName(committeeMember));
                csvWriter.append(";");
            }
            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
