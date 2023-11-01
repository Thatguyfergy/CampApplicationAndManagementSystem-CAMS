package InfoExchange;

import java.util.ArrayList;
import java.util.Scanner;
import users.*;

public class EnquiriesArray {
    private static ArrayList<Enquiries> enquiries = new ArrayList<Enquiries>();
    private Scanner scanner = new Scanner(System.in);

    public void createEnquiry(String senderName, String campName) {
        System.out.printf("What would you like to enquire about %s\n", campName);
        String enqString = scanner.nextLine();
        Enquiries enquiry = new Enquiries(enqString, senderName, campName);
        enquiries.add(enquiry);
        System.out.printf("The enquiry has been sent to the staff and committee members.\n");
    }

    public void viewEnquiries(Users user) {
        if (user instanceof Staff || user instanceof CampCommitteeMember) {
            System.out.println("All Submitted Enquiries:");
            for (int i = 0; i < enquiries.size(); i++) {
                Enquiries enquiry = enquiries.get(i);
                if (enquiry.getSubmitStatus()) {
                    System.out.printf("=== EnquiryID %d ===\n", i);
                    System.out.println("Camp Name: " + enquiry.getCampName());
                    System.out.println("Sender: " + enquiry.getSender());
                    System.out.println("------------------------------------------------------");
                    System.out.println(enquiry.getEnquiry());
                    System.out.println(); // Add a line break for better readability
                }

            }
        } else if (user instanceof CampCommitteeMember) {
            CampCommitteeMember member = (CampCommitteeMember) user;
            System.out.println("All Submitted Enquiries:");
            for (int i = 0; i < enquiries.size(); i++) {
                Enquiries enquiry = enquiries.get(i);
                if (enquiry.getSubmitStatus() && enquiry.getCampName() == member.getCampName()) {
                    System.out.printf("=== EnquiryID %d ===\n", i);
                    System.out.println("Camp Name: " + enquiry.getCampName());
                    System.out.println("Sender: " + enquiry.getSender());
                    System.out.println("------------------------------------------------------");
                    System.out.println(enquiry.getEnquiry());
                    System.out.println(); // Add a line break for better readability
                }

            }
        } else { // Student trying to access their enquires
            System.out.println("All unprocessed Enquiries:");
            for (int i = 0; i < enquiries.size(); i++) {
                Enquiries enquiry = enquiries.get(i);
                if (!enquiry.getSubmitStatus() && enquiry.getSender() == user.getID()) {
                    System.out.printf("=== EnquiryID %d ===\n", i);
                    System.out.println("Camp Name: " + enquiry.getCampName());
                    System.out.println("------------------------------------------------------");
                    System.out.println(enquiry.getEnquiry());
                    System.out.println(); // Add a line break for better readability
                }

            }
        }
    }

    public void modifyEnquiry(Users user) {
        System.out.println("Key in the enquire id number: ");
        int EnquiryID = scanner.nextInt();
        Enquiries enquiry = enquiries.get(EnquiryID);
        if (!enquiry.getSubmitStatus() && enquiry.getSender() == user.getID()) {
            System.out.println("Camp Name: " + enquiry.getCampName());
            System.out.println("Current Enquiry:" + enquiry.getEnquiry());
            System.out.println("------------------------------------------------------");
            System.out.println("What is your editted enquiry?");
            String enqString = scanner.nextLine();
            enquiry.modifyEnquiry(enqString);

            // Replace the enquiry here!
            enquiries.set(EnquiryID, enquiry);
        } else {
            System.out.printf("You do not have permission to edit this enquiry.\n");
        }
    }

    public void deleteEnquiry(Users user) throws Exception {
    }

}
