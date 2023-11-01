package InfoExchange;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import users.*;

public class EnquiriesArray {
    private static ArrayList<Enquiries> enquiries = new ArrayList<Enquiries>();
    private static ArrayList<EnqReplies> replies = new ArrayList<EnqReplies>();
    private Scanner scanner = new Scanner(System.in);

    public void submitEnquiry(Enquiries enquiry) {
        enquiry.setEnquiryID(generateID("enquiries"));
        enquiries.add(enquiry);
    }

    private String generateID(String Array) {
        String uniqueString;
        if (Array == "enquiries") {
            do {
                uniqueString = UUID.randomUUID().toString();
            } while (findEnquiryIndex(uniqueString) != -1);
        } else {
            do {
                uniqueString = UUID.randomUUID().toString();
            } while (findRepliesIndex(uniqueString) != -1);
        }
        return uniqueString;
    }

    private int findEnquiryIndex(String EnquiryID) {
        for (int i = 0; i < enquiries.size(); i++) {
            if (enquiries.get(i).getEnquiryID() == EnquiryID) {
                return i;
            }
        }
        return -1;
    }

    private int findRepliesIndex(String replyID) {
        for (int i = 0; i < enquiries.size(); i++) {
            if (replies.get(i).getReplyID() == replyID) {
                return i;
            }
        }
        return -1;
    }

    public void viewEnquiries(Users user) throws Exception {
        // Need a feature for Camp Committee members to view
        if (user instanceof Staff) {
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
        } else { // Student trying to access their enquires
            throw new Exception("You shall not pass");
        }
    }

    // This is deprecated as it's not required in the Assignment Rubrics
    public void deleteEnquiry(Users user) throws Exception {
        // Need a feature for Camp Committee members to delete
        System.out.printf("EnquiryID: \n");
        int idx = scanner.nextInt();

        if (user instanceof Staff) {
            enquiries.remove(idx);
        } else {
            throw new Exception("You shall not pass");
        }
    }

    public void replyEnquiry(Users user) throws Exception {
        System.out.printf("EnquiryID: \n");
        int idx = scanner.nextInt();
        Enquiries enquiry = enquiries.get(idx);

        System.out.printf("What is your reply?\n");
        String replyString = scanner.nextLine();

        EnqReplies reply = new EnqReplies(replyString, enquiry.getEnquiryID(), enquiry.getSender(),
                generateID("replies"));
        replies.add(reply);
    }

}
