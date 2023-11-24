package report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import infoexchange.EnqReplies;
import infoexchange.Enquiries;
import infoexchange.EnquiriesArray;
import users.Staff;
import users.Student;
import users.Users;
import utils.InputInt;

/**
 * The EnquiriesReport class implements the Report interface.
 * It generates the enquiries report for Staff and Camp Comm
 * 
 * @author Kok Chin Yi
 * @version 1.0
 */
public class EnquiriesReport implements Report {
    private Staff userStaff;
    private Student userStudent;
    private EnquiriesArray enquiriesArray;
    private static String folderPath = "EnquiriesReport\\";
    private ArrayList<String> createdCamps;
    private Scanner sc = new Scanner(System.in);

    /**
     * The `EnquiriesReport` constructor is responsible for initializing the
     * `EnquiriesReport` object.
     * It takes two parameters: `user` of type `Users` and `enquiriesArray` of type
     * `EnquiriesArray`.
     * 
     * @param user
     * @param enquiriesArray
     */
    public EnquiriesReport(Users user, EnquiriesArray enquiriesArray) {
        if (user instanceof Staff) {
            userStaff = (Staff) user;
            this.createdCamps = userStaff.getCampsInCharge();
        } else if (user instanceof Student && ((Student) user).IsCampComm()) {
            userStudent = (Student) user;
            this.createdCamps = new ArrayList<String>();
            this.createdCamps.add(userStudent.getCampCommitteeRole().getCampName());
        } else {
            System.out.println("You are not allowed to access Enquiries Report!");
            return;
        }
        this.enquiriesArray = enquiriesArray;
    }

    /**
     * The function generates a report for a selected camp, displaying a list of
     * created camps and
     * allowing the user to choose one.
     */
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
                System.out.printf("Enter choice (Key in -1 to leave): ");
                choice = InputInt.nextInt(sc);
                if (choice == -1) {
                    return;
                }
                if (choice > createdCamps.size() + 1 || choice <= 0)
                    throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, try again! (Key in -1 to leave)");
            }
        }

        String selectedCamp = createdCamps.get(--choice);
        generateEnquriesReport(selectedCamp);
        System.out.println("Generating report for " + selectedCamp + "...");
        System.out.println("Find the report in EnquiriesReport");
    }

    /**
     * The function generates a CSV report of enquiries and their replies for a
     * specific camp.
     * 
     * @param fileName The fileName parameter is a String that represents the name
     *                 of the camp for
     *                 which the enquiries report is being generated.
     */
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
