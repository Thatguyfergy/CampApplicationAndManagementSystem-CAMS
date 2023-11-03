package camps;

import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import users.Staff;
import users.Users;
import users.Student;

public class CampArray {
    private static ArrayList<Camp> camps = new ArrayList<Camp>();
    private Scanner scanner = new Scanner(System.in);
    private static String campsFile;

    public CampArray(String campsFile) throws NumberFormatException, IOException {
        CampArray.campsFile = campsFile;
        BufferedReader csvReader = new BufferedReader(new FileReader(campsFile));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            // CSV Format: ID | Name,Dates | Closing Date | Visibility |
            // Location | Total Slots | Committee Members |
            // Committee Mem slots | Description | Staff-In-Charge | Attendees
            Camp camp = new Camp(Integer.parseInt(data[0]), data[1], new CAMDate(data[3]), data[4], data[5],
                    Integer.parseInt(data[6]), Integer.parseInt(data[8]), data[9], data[10]);
            for (String gay:  data[2].split(";")) {
                camp.addDate(new CAMDate(gay));
            }
            camps.add(camp);
        }
        csvReader.close();
    }

    private void updateFile() throws Exception {
        FileWriter csvWriter = new FileWriter(campsFile);
        for (Camp camp : camps) {
            csvWriter.append(String.valueOf(camp.getCampID()));
            csvWriter.append(",");
            csvWriter.append(camp.getCampName());
            csvWriter.append(",");
            //TODO
            csvWriter.append(camp.getDates());
            csvWriter.append(",");
            csvWriter.append(camp.getCampName());
            csvWriter.append(",");
            csvWriter.append(camp.getCampName());
            csvWriter.append(",");
            csvWriter.append(camp.getCampName());
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();

    }

    public void createCamp(Camp newCamp) {
        camps.add(newCamp);
    }

    public void editCamp(String campName) {
        Camp targetCamp = null;

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                targetCamp = camps.get(i);
                break;
            }
        }
        if (targetCamp == null) {
            System.out.println("Camp not found");
            return;
        }

        // Edit Scanner
        // Example: Editing camp description
        System.out.println("Enter new description for the camp:");
        String newDescription = scanner.nextLine();
        // Set the new description for the targetCamp
        targetCamp.setCampDescription(newDescription);

        System.out.println("Camp edited successfully");
    }

    public void deleteCamp(String campName) {

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                camps.remove(i);
            }
        }
    }

    public void viewCamps() {
        // Display camps based on different visibility requirements
        // For Staff - No filters since every staff can view every camp
        // For Students - filter by committeeMembers

        // Display all camps
        System.out.println("All Camps:");
        for (Camp camp : camps) {
            System.out.println(camp.toString()); // What does this do?
            System.out.println(); // Add a line break for better readability
            displayRegisteredStudents(camp);
            System.out.println(); // Add a line break for better readability
        }
    }

    public void viewCamps(Users user) {
        // Display camps based on different visibility requirements
        // For Staff - No filters since every staff can view every camp
        // For Students - filter by committeeMembers

        if (user instanceof Staff) {
            Staff staffUser = (Staff) user;

            // Display all camps
            System.out.println("All Camps");
            System.out.println("=====================================");
            for (Camp camp : camps) {
                System.out.println(camp.toString()); // What does this do?
                System.out.println(); // Add a line break for better readability
                displayRegisteredStudents(camp);
                System.out.println(); // Add a line break for better readability
            }

            // Display camps created by the staff if they are the staffInCharge
            System.out.println("\nYour Created Camps:");
            for (Camp camp : camps) {
                if (staffUser.getID().equals(camp.getStaffInCharge())) {
                    System.out.println(camp.toString());
                    System.out.println(); // Add a line break for better readability
                    displayRegisteredStudents(camp);
                    System.out.println(); // Add a line break for better readability
                } else {
                    System.out.println("None.");
                }
            }
        } else if (user instanceof Student) {

            Student studentUser = (Student) user;
            // Display only camps open to the student's user group with visibility toggled
            // "on"
            // and display remaining slots for each camp open to the student
            System.out.println("Open Camps for Student:");
            for (Camp camp : camps) {
                if (camp.toggleVisibility().equalsIgnoreCase("on")
                        && studentUser.getFacultyInfo().equalsIgnoreCase(camp.getCampVisibility())) {
                    System.out.println("Camp Name: " + camp.getCampName());
                    System.out.println("Remaining Slots for Camp Committee: " + getRemainingCommitteeSlots(camp));
                    System.out.println("Remaining Slots for Attendees: " + getRemainingAttendeeSlots(camp));
                    System.out.println("Camp Dates: " + camp.getDates());
                    System.out.println("Registration Closing Date: " + camp.getRegistrationClosingDate());
                    System.out.println("Location: " + camp.getLocation());
                    System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                    System.out.println(); // Add a line break for better readability
                }
            }

            // Display camps the student has registered for and their roles
            System.out.println("\nYour Registered Camps:");
            for (Camp camp : camps) {
                for (String attendee : camp.getAttendees()) {
                    if (attendee.equals(studentUser.getID())) {
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("Camp Dates: " + camp.getDates());
                        System.out.println("Location: " + camp.getLocation());
                        System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                        System.out.println("Your Role: Attendee");
                        System.out.println(); // Add a line break for better readability
                    }
                }
                for (String committeeMember : camp.getCommitteeMembers()) {
                    if (committeeMember.equals(studentUser.getID())) {
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("Camp Dates: " + camp.getDates());
                        System.out.println("Location: " + camp.getLocation());
                        System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                        System.out.println("Your Role: Committee Member");
                        displayRegisteredStudents(camp);
                        System.out.println(); // Add a line break for better readability
                    }
                }
            }
        }
    }

    // Helper method to calculate remaining slots for attendees
    private int getRemainingAttendeeSlots(Camp camp) {
        int totalSlots = camp.getTotalSlots();
        int occupiedAttendeeSlots = camp.getAttendees().length;
        int occupiedCommitteeSlots = camp.getCommitteeMembers().length;
        int totalOccupiedSlots = occupiedAttendeeSlots + occupiedCommitteeSlots;

        return totalSlots - totalOccupiedSlots;
    }

    // Helper method to calculate remaining slots for committee members
    private int getRemainingCommitteeSlots(Camp camp) {
        int committeeMembersSlots = camp.getCommitteeMembersSlots();
        int occupiedCommitteeSlots = camp.getCommitteeMembers().length;

        return committeeMembersSlots - occupiedCommitteeSlots;
    }

    // Helper method to display the list of registered students for a camp
    private void displayRegisteredStudents(Camp camp) {
        System.out.println("Registered Students:");

        // Display attendees
        System.out.println("Attendees:");
        for (String attendee : camp.getAttendees()) {
            if(attendee != null)
            System.out.println("- " + attendee);
        }
        
        // Display committee members
        System.out.println("Committee Members:");
        for (String committeeMember : camp.getCommitteeMembers()) {
            if(committeeMember != null)
            System.out.println("- " + committeeMember);
        }

        System.out.println(); // Add a line break for better readability
    }
}