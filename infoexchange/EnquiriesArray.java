package infoexchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import users.*;
import utils.inputInt;

public class EnquiriesArray {
    private static ArrayList<Enquiries> enquiries = new ArrayList<Enquiries>();
    private static ArrayList<EnqReplies> replies = new ArrayList<EnqReplies>();
    private Scanner scanner = new Scanner(System.in);
    private static String enquiriesFile;
    private static String repliesFile;

    // Constructor will load Arrays from the csv files
    public EnquiriesArray(String enquiriesFile, String repliesFile) {
        EnquiriesArray.enquiriesFile = enquiriesFile;
        EnquiriesArray.repliesFile = repliesFile;

        // Load the Enquiries
        BufferedReader csvReader;
        try {
            csvReader = new BufferedReader(new FileReader(enquiriesFile));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: enquiryID | enquiryString | sender | campName
                Enquiries enquiry = new Enquiries(data[1], data[2], data[3]);
                enquiry.setEnquiryID(data[0]);
                enquiries.add(enquiry);
            }
            csvReader.close();

            // Load the Replies
            csvReader = new BufferedReader(new FileReader(repliesFile));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: enquiryID | replyID | replyString | recipient | replyCreator
                EnqReplies reply = new EnqReplies(data[2], data[0], data[3],
                        data[1], data[4]);
                replies.add(reply);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // The enquiryID will only be generated after submission.
    // If you require the ID, please use getEnquiryID() only AFTER SUBMISSION.
    public void submitEnquiry(Enquiries enquiry) {
        if (enquiry.getSubmitted()) {
            System.out.println("Enquiry already submitted!");
            return;
        }
        enquiry.setEnquiryID(generateID(enquiries));
        enquiries.add(enquiry);
        enquiry.setSubmittedTrue();
        try {
            updateFile(enquiries);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String generateID(ArrayList<?> Array) {
        String uniqueString;

        try {
            if (Array.get(0) instanceof Enquiries) {
                do {
                    uniqueString = UUID.randomUUID().toString();
                } while (findEnquiryIndex(uniqueString) != -1);
            } else {
                do {
                    uniqueString = UUID.randomUUID().toString();
                } while (findRepliesIndex(uniqueString) != -1);
            }
        } catch (IndexOutOfBoundsException e) {
            uniqueString = UUID.randomUUID().toString();
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

    public void viewEnquiries(Users user) {
        // Need a feature for Camp Committee members to view
        if (user instanceof Staff) {

            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ All Submitted Enquiries:                                        ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            System.out.println("");
            Staff userStaff = (Staff) user;
            int localCounter = 0;
            for (int i = 0; i < enquiries.size(); i++) {
                Enquiries enquiry = enquiries.get(i);
                if (userStaff.checkStaffInCharge(enquiry.getCampName())) {
                    System.out.printf("╔════════════════════════ EnquiryID %-2d ═══════════════════════════╗\n", i);
                    System.out.println("> Camp Name: " + enquiry.getCampName());
                    System.out.println("> Sender: " + enquiry.getSender());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println(enquiry.getEnquiry());
                    System.out.println("═══════════════════════════════════════════════════════════════════");
                    System.out.println(); // Add a line break for better readability
                    localCounter++;
                }
            }
            if (localCounter == 0) {
                System.out.println("There are no enquiries for you!");
            }
        } else if (user instanceof Student && ((Student) user).IsCampComm()) { // Only CampComitteeMember can view
            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ All Submitted Enquiries:                                        ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            System.out.println("");
            int localCounter = 0;
            Student userStudent = (Student) user;
            for (int i = 0; i < enquiries.size(); i++) {
                Enquiries enquiry = enquiries.get(i);
                if (userStudent.IsCampComm()
                        && userStudent.getCampCommitteeRole().getCampName().equals(enquiry.getCampName())) {
                    System.out.printf("╔════════════════════════ EnquiryID %-2d ═══════════════════════════╗\n", i);
                    System.out.println("> Camp Name: " + enquiry.getCampName());
                    System.out.println("> Sender: " + enquiry.getSender());
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println(enquiry.getEnquiry());
                    System.out.println("═══════════════════════════════════════════════════════════════════");
                    System.out.println(); // Add a line break for better readability
                    localCounter++;
                }
            }
            if (localCounter == 0) {
                System.out.println("There are no enquiries for you!");
            }
        } else {
            System.out.println("How did you even access this page lol");
        }
    }

    // This is deprecated as it's not required in the Assignment Rubrics
    // public void deleteEnquiry(Users user) throws Exception {
    // // Need a feature for Camp Committee members to delete
    // System.out.printf("EnquiryID: \n");
    // int idx = scanner.nextInt();

    // if (user instanceof Staff) {
    // enquiries.remove(idx);
    // } else {
    // throw new Exception("You shall not pass");
    // }
    // updateFile(enquiries);
    // }

    private Boolean checkExistEnquiries(Users user) {
        if (user instanceof Student) {
            Student userStudent = (Student) user;
            for (Enquiries enquiry : enquiries) {
                if (userStudent.getCampCommitteeRole().getCampName().equals(enquiry.getCampName())) {
                    return true;
                }
            }
        } else if (user instanceof Staff) {
            Staff userStaff = (Staff) user;
            for (Enquiries enquiry : enquiries) {
                if (userStaff.checkStaffInCharge(enquiry.getCampName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void replyEnquiry(Users user) {
        // Student
        if (user instanceof Student) {
            Student userStudent = (Student) user;

            // Early Return - If the student is not a Camp Comm
            if (!userStudent.IsCampComm())
                return;
            viewEnquiries(user);

            if (!checkExistEnquiries(user)) {
                System.out.println("You have no enquiries to reply to!");
                return;
            }
            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enquiry Replies                                                 ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            Enquiries enquiry;
            while (true) {
                try {
                    System.out.printf("EnquiryID: \n");
                    int idx = inputInt.nextInt(scanner);
                    scanner.nextLine();
                    enquiry = enquiries.get(idx);
                    break;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enquiry cannot be found, make sure you selected the correct enquiry.");
                }
            }

            if (!userStudent.getCampCommitteeRole().getCampName().equals(enquiry.getCampName())) {
                System.out.println("Enquiry cannot be found, make sure you selected the correct enquiry.");
                return;
            } else {
                System.out.println("╒═════════════════════════════════════════════════════════════════╕");
                System.out.println("> Camp Name: " + enquiry.getCampName());
                System.out.println("> Sender: " + enquiry.getSender());
                System.out.println("> Enquiry: " + enquiry.getEnquiry());
                System.out.println("-------------------------------------------------------------------");
            }
            System.out.printf("What is your reply?\n");
            String replyString = scanner.nextLine();

            EnqReplies reply = new EnqReplies(replyString, enquiry.getEnquiryID(), enquiry.getSender(),
                    generateID(replies), user.getID());
            replies.add(reply);

            userStudent.getCampCommitteeRole().addOnePoint(); // student gets point for enq reply

            try {
                updateFile(replies);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        // Staff accessing the enquiries
        else if (user instanceof Staff) {
            Staff userStaff = (Staff) user;
            viewEnquiries(user);

            if (!checkExistEnquiries(user)) {
                System.out.println("You have no enquiries to reply to!");
                return;
            }

            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Enquiry Replies                                                 ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");

            Enquiries enquiry;
            while (true) {
                try {
                    System.out.printf("EnquiryID: \n");
                    int idx = inputInt.nextInt(scanner);
                    scanner.nextLine();
                    enquiry = enquiries.get(idx);
                    break;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Enquiry cannot be found, make sure you selected the correct enquiry.");
                }
            }

            if (!userStaff.checkStaffInCharge(enquiry.getCampName())) {
                System.out.println("Enquiry cannot be found, make sure you selected the correct enquiry.");
                return;
            } else {
                System.out.println("╒═════════════════════════════════════════════════════════════════╕");
                System.out.println("> Camp Name: " + enquiry.getCampName());
                System.out.println("> Sender: " + enquiry.getSender());
                System.out.println("> Enquiry: " + enquiry.getEnquiry());
                System.out.println("-------------------------------------------------------------------");
            }
            System.out.printf("What is your reply?\n");
            String replyString = scanner.nextLine();

            EnqReplies reply = new EnqReplies(replyString, enquiry.getEnquiryID(), enquiry.getSender(),
                    generateID(replies), user.getID());
            replies.add(reply);
            try {
                updateFile(replies);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        updateFile(replies);
    }

    public void viewReplies(Users user) {
        System.out.println("╔═════════════════════════════════════════════════════════════════╗");
        System.out.println("║ All Submitted Enquiries & Replies:                              ║");
        System.out.println("╚═════════════════════════════════════════════════════════════════╝");
        System.out.println("");
        int enquiryCounter = 0;
        for (int i = 0; i < enquiries.size(); i++) {
            Enquiries enquiry = enquiries.get(i);
            if (enquiry.getSender().equals(user.getID())) {
                enquiryCounter++;
                System.out.println("╒═════════════════════════════════════════════════════════════════╕");
                System.out.println("> Camp Name: " + enquiry.getCampName());
                System.out.println("> Sender: " + enquiry.getSender());
                System.out.println("> Your enquiry: " + enquiry.getEnquiry());
                System.out.println("-------------------------------------------------------------------");
                // System.out.println("Replies by Staff/Camp Committee Member");
                // System.out.println();
                int replyCounter = 0;
                for (int j = 0; j < replies.size(); j++) {
                    EnqReplies reply = replies.get(j);
                    if (reply.getEnquiryID().equals(enquiry.getEnquiryID())) {
                        replyCounter++;
                        System.out.println("Replied by: " + reply.getReplyCreator());
                        System.out.println();
                        System.out.println(reply.getReplyString());
                        System.out.println();
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    }
                }
                if (replyCounter == 0)
                    System.out.println("There are no replies for this enquiry yet!");
            }
        }
        if (enquiryCounter == 0) {
            System.out.println("You have not submitted any enquiries!");
        }
    }

    // @Override
    private void updateFile(ArrayList<?> Array) {
        try {
            // Enquiries
            if (Array.get(0) instanceof Enquiries) {
                FileWriter csvWriter = new FileWriter(enquiriesFile);
                for (Enquiries enquiry : enquiries) {
                    csvWriter.append(enquiry.getEnquiryID());
                    csvWriter.append(",");
                    csvWriter.append(enquiry.getEnquiry());
                    csvWriter.append(",");
                    csvWriter.append(enquiry.getSender());
                    csvWriter.append(",");
                    csvWriter.append(enquiry.getCampName());
                    csvWriter.append("\n");
                }

                csvWriter.flush();
                csvWriter.close();
            }
            // Replies
            else {
                FileWriter csvWriter = new FileWriter(repliesFile);
                for (EnqReplies reply : replies) {
                    csvWriter.append(reply.getEnquiryID());
                    csvWriter.append(",");
                    csvWriter.append(reply.getReplyID());
                    csvWriter.append(",");
                    csvWriter.append(reply.getReplyString());
                    csvWriter.append(",");
                    csvWriter.append(reply.getRecipient());
                    csvWriter.append(",");
                    csvWriter.append(reply.getReplyCreator());
                    csvWriter.append("\n");
                }

                csvWriter.flush();
                csvWriter.close();
            }
        } catch (IndexOutOfBoundsException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public void deleteCamp(String campName) {
        for (int i = 0; i < enquiries.size();) {
            Enquiries enquiry = enquiries.get(i);
            if (enquiry.getCampName().equals(campName)) {
                // Remove any related replies first
                for (int j = 0; j < replies.size();) {
                    if (replies.get(j).getEnquiryID().equals(enquiry.getEnquiryID())) {
                        replies.remove(j);
                    } else { // Cannot auto j++ in for loop as the size of the replies array shrinks
                        j++;
                    }
                }
                // Remove enquiry now
                enquiries.remove(i);
            } else {
                i++; // Cannot auto i++ in for loop as the size of the Enquiry array shrinks
            }
        }

        updateFile(enquiries);
        updateFile(replies);
    }

    public void editCamp(String oldName, String newName) {
        for (int i = 0; i < enquiries.size(); i++) {
            Enquiries enquiry = enquiries.get(i);
            if (enquiry.getCampName().equals(oldName)) {
                enquiry.changeCampName(newName);
            }
        }
        updateFile(enquiries);
    }

    public ArrayList<Enquiries> getEnquiries() {
        return enquiries;
    }

    public ArrayList<EnqReplies> getReplies() {
        return replies;
    }
}
