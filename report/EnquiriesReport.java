package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;
import infoexchange.EnqReplies;
import infoexchange.Enquiries;
import infoexchange.EnquiriesArray;
import users.Staff;
import users.Student;
import users.Users;
import users.UsersDatabase;
import utils.inputInt;

public class EnquiriesReport implements Report {
    private Staff userStaff;
    private EnquiriesArray enquiriesArray;
    private static String folderPath = "EnquiriesReport\\";
    private ArrayList<String> createdCamps;
    private Scanner sc = new Scanner(System.in);

    public EnquiriesReport(Users user, EnquiriesArray enquiriesArray) {
        if (user instanceof Staff) {
            userStaff = (Staff) user;
        } else {
            System.out.println("You are not allowed to access Enquiries Report!");
            return;
        }
        this.enquiriesArray = enquiriesArray;
        this.createdCamps = userStaff.getCampsInCharge();
    }

    public void generateReport() {
        if (createdCamps.size() == 0) {
            System.out.println("You have not created any camps!");
            return;
        }
        System.out.println("Select a camp to generate Enquiries Report for:");
        for (String camp : createdCamps) {
            System.out.println(createdCamps.indexOf(camp) + 1 + ". " + camp);
        }
        int choice;
        while (true) {
            try {
                System.out.printf("Enter choice: ");
                choice = inputInt.nextInt(sc);
                if (choice == -1) {
                    return;
                }
                if (choice > createdCamps.size() + 1 || choice <= 0)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, try again! (Key in -1 to leave)");
                System.out.println("Enter choice: ");
            }
        }

        String selectedCamp = createdCamps.get(--choice);
        generateEnquriesReport(selectedCamp);
        System.out.println("Generating report for " + selectedCamp + "...");
        System.out.println("Find the report in EnquiriesReport");
    }

    private void generateEnquriesReport(String fileName) {
        File file = new File(folderPath + fileName + "_EnquiriesReport.csv");
        file.getParentFile().mkdirs(); // Ensure the parent directories exist
        int i, j;
        i = j = 0;
        try (FileWriter csvWriter = new FileWriter(file)) {
            for (Enquiries enquiry : enquiriesArray.getEnquiries()) {
                if (enquiry.getCampName().equals(fileName)) {
                    i++;
                    csvWriter.append("Sender,");
                    csvWriter.append(enquiry.getSender() + "\n");
                    csvWriter.append("Enquiry,");
                    csvWriter.append(enquiry.getEnquiry() + "\n");
                    j = 0;
                    for (EnqReplies reply : enquiriesArray.getReplies()) {
                        if (reply.getEnquiryID().equals(enquiry.getEnquiryID())) {
                            j++;
                            csvWriter.append("Reply by,");
                            csvWriter.append(reply.getReplyCreator() + "\n");
                            csvWriter.append("Reply,");
                            csvWriter.append(reply.getReplyString() + "\n");
                            csvWriter.append("\n");
                        }
                    }
                    // No replies for the enquiry
                    if (j == 0) {
                        csvWriter.append("There are 0 replies for this enquiry");
                        csvWriter.append("\n");
                    }

                }
            }
            // No Enquiries
            if (i == 0)
                csvWriter.append("There are 0 enquiries for this Camp");

            csvWriter.append("\n");
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
