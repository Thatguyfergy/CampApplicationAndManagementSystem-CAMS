package camps;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

import users.Staff;
import users.Users;
import users.Student;

public class CampArray {
    private ArrayList<Camp> camps = new ArrayList<Camp>();
    private Scanner scanner = new Scanner(System.in);

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

    // Fugerson help - This one quite troublesome as there are different visibility
    // requirements. Need to refer to Student and Staff.
    // For Staff - I wan to filter by staffInCharge. Help me to do this.

    public void viewCamps(Users user) {
        // Display camps based on different visibility requirements
        // For Staff - No filters since every staff can view every camp
        // For Students - filter by committeeMembers

        if (user instanceof Staff) {
            Staff staffUser = (Staff) user;
    
            // Display all camps
            System.out.println("All Camps:");
            for (Camp camp : camps) {
                System.out.println(camp.toString());
            }
    
            // Display camps created by the staff if they are the staffInCharge
            System.out.println("\nYour Created Camps:");
            for (Camp camp : camps) {
                if (staffUser.getID().equals(camp.getStaffInCharge())) {
                    System.out.println(camp.toString());
                }
            }
        } else if (user instanceof Student) {
            Student studentUser = (Student) user;
    
            // Display only camps open to the student's user group with visibility toggled "on"
            // and display remaining slots for each camp open to the student
            System.out.println("Open Camps for Student:");
            for (Camp camp : camps) {
                if (camp.getCampVisibility().equals(studentUser.getFacultyInfo()) &&
                camp.getRegistrationClosingDate() && // Check if registration is still open
                camp.getCommitteeMembersSlots() > 0) {
                    System.out.println("Camp Name: " + camp.getCampName());
                    System.out.println("Remaining Slots: " + camp.getCommitteeMembersSlots());
                }
            }
    
            // Display camps the student has registered for and their roles
            System.out.println("\nYour Registered Camps:");
            for (Camp camp : camps) {
                for (String attendee : camp.getAttendees()) {
                    if (attendee.equals(studentUser.getID())) {
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("Your Role: Attendee");
                    }
                }
                for (String committeeMember : camp.getCommitteeMembers()) {
                    if (committeeMember.equals(studentUser.getID())) {
                        System.out.println("Camp Name: " + camp.getCampName());
                        System.out.println("Your Role: Committee Member");
                    }
                }
            }
        }

}
}