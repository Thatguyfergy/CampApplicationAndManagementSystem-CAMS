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
    private String sortBy;

    public CampArray(String campsFile){
        sortBy = "campName";
        CampArray.campsFile = campsFile;
        try (BufferedReader csvReader = new BufferedReader(new FileReader(campsFile))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: Name | Dates | Closing Date | Visibility |
                // Location | Total Slots | Committee Members |
                // Committee Mem slots | Description | Staff-In-Charge | Attendees
                Camp camp = new Camp(data[0], new CAMDate(data[2]), data[3], data[4],
                        Integer.parseInt(data[5]), Integer.parseInt(data[7]), data[8], data[9]);
                for (String date : data[1].split(";")) {
                    camp.addDate(new CAMDate(date));
                }
                camps.add(camp);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sortCamps(String sortBy) {
        this.sortBy = sortBy;
        insertionSort(camps, sortBy);
    }

    private void insertionSort(ArrayList<Camp> camps, String sortBy) {
        for (int i = 1; i < camps.size(); i++) {
            Camp key = camps.get(i);
            int j = i - 1;
            while (j >= 0 && camps.get(j).compareTo(key, sortBy) > 0) {
                camps.set(j + 1, camps.get(j));
                j--;
            }
            camps.set(j + 1, key);
        }

        updateFile(camps);
    }

    // update file with new campArray
    private void updateFile(ArrayList<Camp> camps) {
        try (FileWriter csvWriter = new FileWriter(campsFile)) {
            for (Camp camp : camps) {
                csvWriter.append(camp.getCampName());
                csvWriter.append(",");
                for (CAMDate date : camp.getDates()) {
                    csvWriter.append(date.toString());
                    csvWriter.append(";");
                }
                csvWriter.append(",");
                csvWriter.append(camp.getRegistrationClosingDate().toString());
                csvWriter.append(",");
                csvWriter.append(camp.getCampVisibility());
                csvWriter.append(",");
                csvWriter.append(camp.getLocation());
                csvWriter.append(",");
                csvWriter.append(String.valueOf(camp.getTotalSlots()));
                csvWriter.append(",");
                for (String committeeMember : camp.getCommitteeMembers()) {
                    csvWriter.append(committeeMember);
                    csvWriter.append(";");
                }
                csvWriter.append(",");
                csvWriter.append(String.valueOf(camp.getCommitteeMembersSlots()));
                csvWriter.append(",");
                csvWriter.append(camp.getCampDescription());
                csvWriter.append(",");
                csvWriter.append(camp.getStaffInCharge());
                csvWriter.append(",");
                for (String attendee : camp.getAttendees()) {
                    csvWriter.append(attendee);
                    csvWriter.append(";");
                }
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCamp(String staffinCharge) throws Exception {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();

        System.out.println("Registration closing date (dd/mm/yyyy):");
        CAMDate registrationClosingDate = new CAMDate(scanner.nextLine());

        System.out.println("Camp start date (dd/mm/yyyy):");
        CAMDate startDate = new CAMDate(scanner.nextLine());

        System.out.println("Camp end date (dd/mm/yyyy):");
        CAMDate endDate = new CAMDate(scanner.nextLine());

        System.out.println("Camp Visibility (Y/N):");
        String campVisibility = scanner.nextLine().toUpperCase();

        System.out.println("Location:");
        String location = scanner.nextLine().toUpperCase();

        System.out.println("Total Slots:");
        int totalSlots = scanner.nextInt();
        scanner.nextLine(); // Flush

        System.out.println("Committee Memebers Slots (Max 10):");
        int committeeMembersSlots = scanner.nextInt();
        committeeMembersSlots = committeeMembersSlots > 10 ? 10 : committeeMembersSlots;
        scanner.nextLine(); // Flush;

        System.out.println("Camp Description:");
        String campDescription = scanner.nextLine();

        Camp newCamp = new Camp(campName, registrationClosingDate, campVisibility, location, totalSlots,
                committeeMembersSlots, campDescription, staffinCharge);
        newCamp.addDate(startDate, endDate);
        camps.add(newCamp);
        updateFile(camps);
    }

    public void editCamp(String campName) {
        Camp targetCamp = null;

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName().equals(campName)) {
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

    public void deleteCamp(String campName) throws Exception {

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName().equals(campName)) {
                camps.remove(i);
                System.out.println("Camp deleted successfully");
            }
        }
        updateFile(camps);
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
                System.out.println(camp.toString()); // Return Camps in string format
                System.out.println(); // Add a line break for better readability
                //displayRegisteredStudents(camp);
                //System.out.println(); // Add a line break for better readability
            }

            // Display camps created by the staff if they are the staffInCharge
            System.out.println("\nYour Created Camps:");
            for (Camp camp : camps) {
                if (staffUser.getID().equals(camp.getStaffInCharge())) {
                    System.out.println(camp.toString());
                    System.out.println(); // Add a line break for better readability
                    //displayRegisteredStudents(camp);
                    //System.out.println(); // Add a line break for better readability
                }
            }
            System.out.println("=====================================");
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
            boolean hasRegisteredCamps = false; // Check if student has registered for camps
            for (Camp camp : camps) {
                if (camp.getAttendees().contains(studentUser.getID())) {
                    System.out.println("Camp Name: " + camp.getCampName());
                    System.out.println("Camp Dates: " + camp.getDates());
                    System.out.println("Location: " + camp.getLocation());
                    System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                    System.out.println("Your Role: Attendee");
                    System.out.println(); // Add a line break for better readability
                    hasRegisteredCamps = true;
                }
                if (camp.getCommitteeMembers().contains(studentUser.getID())) {
                    System.out.println("Camp Name: " + camp.getCampName());
                    System.out.println("Camp Dates: " + camp.getDates());
                    System.out.println("Location: " + camp.getLocation());
                    System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                    System.out.println("Your Role: Committee Member");
                    displayRegisteredStudents(camp);
                    System.out.println(); // Add a line break for better readability
                    hasRegisteredCamps = true;
                }
            }
            if (!hasRegisteredCamps) {
                System.out.println("None.");
            }
        }
    }

    // this method should only be used by staff or camp committee members
    // TODO add in logic to only show details of the camp to camp committee members if they are camp committee members of that camp
    public void viewCampDetails(String campName) {
        // view the details of the camp
        System.out.println("Camp Details");
        System.out.println("=====================================");
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                System.out.println("Camp Name: " + camp.getCampName());
                System.out.println("Camp Dates: " + camp.getDates());
                System.out.println("Registration Closing Date: " + camp.getRegistrationClosingDate());
                System.out.println("Location: " + camp.getLocation());
                System.out.println("Total Slots: " + camp.getTotalSlots());
                System.out.println("Remaining Slots for Camp Committee: " + getRemainingCommitteeSlots(camp));
                System.out.println("Remaining Slots for Attendees: " + getRemainingAttendeeSlots(camp));
                System.out.println("Camp Description: " + camp.getCampDescription());
                System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                System.out.println("Camp Visibility: " + camp.getCampVisibility());
                System.out.println("Committee Members: " + camp.getCommitteeMembers());
                System.out.println("Attendees: " + camp.getAttendees());
                System.out.println(); // Add a line break for better readability
            }
        }
    }

    // Helper method to calculate remaining slots for attendees
    private int getRemainingAttendeeSlots(Camp camp) {
        int totalSlots = camp.getTotalSlots();
        int occupiedAttendeeSlots = camp.getAttendees().size();
        int occupiedCommitteeSlots = camp.getCommitteeMembers().size();
        int totalOccupiedSlots = occupiedAttendeeSlots + occupiedCommitteeSlots;

        return totalSlots - totalOccupiedSlots;
    }

    // Helper method to calculate remaining slots for committee members
    private int getRemainingCommitteeSlots(Camp camp) {
        int committeeMembersSlots = camp.getCommitteeMembersSlots();
        int occupiedCommitteeSlots = camp.getCommitteeMembers().size();

        return committeeMembersSlots - occupiedCommitteeSlots;
    }

    // Helper method to display the list of registered students for a camp
    private void displayRegisteredStudents(Camp camp) {
        System.out.println("Registered Students:");

        // Display attendees
        System.out.println("Attendees:");
        for (String attendee : camp.getAttendees()) {
            if (attendee != null)
                System.out.println("- " + attendee);
        }

        // Display committee members
        System.out.println("Committee Members:");
        for (String committeeMember : camp.getCommitteeMembers()) {
            if (committeeMember != null)
                System.out.println("- " + committeeMember);
        }

        System.out.println(); // Add a line break for better readability
    }

}