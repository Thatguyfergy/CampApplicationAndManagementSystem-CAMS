package camps;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import camdate.CAMDate;
import infoexchange.EnquiriesArray;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import users.Staff;
import users.Users;
import users.UsersDatabase;
import users.Student;

public class CampArray {
    private static ArrayList<Camp> camps = new ArrayList<Camp>();
    private Scanner scanner = new Scanner(System.in);
    private static String campsFile;
    private CampFilter campFilter;

    public CampArray(String campsFile) {
        campFilter = new CampFilter();
        CampArray.campsFile = campsFile;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(campsFile))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                // CSV Format: Name | Dates | Closing Date | Avail |
                // Location | Total Slots | Committee Members |
                // Committee Mem slots | Description | Staff-In-Charge | Attendees
                Camp camp = new Camp(data[0].trim(), new CAMDate(data[2].trim()), data[3].trim(), data[4].trim(),
                        Integer.parseInt(data[5].trim()), Integer.parseInt(data[7].trim()), data[8].trim(),
                        data[9].trim());
                campFilter.addAvailableFaculty(data[3].trim());
                campFilter.addAvailableLocation(data[4].trim());
                campFilter.addAvailableSIC(data[9].trim());
                for (String date : data[1].trim().split(";")) {
                    camp.addDate(new CAMDate(date));
                    campFilter.addAvailableDate(date);
                }
                camp.getCampInfo().sortDates();
                camps.add(camp);
                for (String committeeMember : data[6].trim().split(";")) {
                    if (committeeMember.equals("")) {
                        continue;
                    }
                    camp.registerStudent(committeeMember, committeeMember, true, camp.getCampName());
                }
                if (data.length > 10) {
                    for (String attendee : data[10].trim().split(";")) {
                        camp.registerStudent(attendee, attendee, false, camp.getCampName());
                    }
                }
            }
            campFilter.setUnfilteredCamps(camps);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                csvWriter.append(camp.getCampAvailability());
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
                csvWriter.append(",");
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createCamp(String staffinCharge) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        // Check if the camp name already exists
        if (campExists(campName)) {
            System.out.println("Camp with the name '" + campName + "' already exists. Please choose a different name.");
            return null; // Exit the method
        }

        if (campName.equals("")) {
            System.out.println("Camp name cannot be empty");
            return null;
        }

        if (campName.length() > 15) {
            System.out.println("Camp name cannot be more than 15 characters");
            return null;
        }

        System.out.println("Registration closing date (dd/mm/yyyy):");
        CAMDate registrationClosingDate = new CAMDate(scanner.nextLine());

        String startDate, endDate;
        while (true) {
            System.out.println("Camp start date (dd/mm/yyyy):");
            startDate = scanner.nextLine();
            if (startDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                if (registrationClosingDate.compareTo(new CAMDate(startDate)) > 0) {
                    System.out.println("Start date cannot be before registration closing date");
                    continue;
                }
                System.out.println("Camp end date (dd/mm/yyyy):");
                endDate = scanner.nextLine();
                if (endDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    CAMDate start = new CAMDate(startDate);
                    CAMDate end = new CAMDate(endDate);
                    if (start.compareTo(end) > 0) {
                        System.out.println("Start date cannot be after end date");
                    } else {
                        break;
                    }
                } else {
                    System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
                }
            } else {
                System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
            }
        }

        CAMDate start = new CAMDate(startDate);
        CAMDate end = new CAMDate(endDate);

        System.out.println("Faculty the camp is available for:");
        String campVisibility = scanner.nextLine().toUpperCase();

        System.out.println("Location:");
        String location = scanner.nextLine().toUpperCase();

        System.out.println("Total Slots:");
        int totalSlots = scanner.nextInt();
        scanner.nextLine(); // Flush

        System.out.println("Committee Members Slots (Max 10):");
        int committeeMembersSlots = scanner.nextInt();
        committeeMembersSlots = committeeMembersSlots > 10 ? 10 : committeeMembersSlots;
        scanner.nextLine(); // Flush;

        System.out.println("Camp Description:");
        String campDescription = scanner.nextLine();

        Camp newCamp = new Camp(campName, registrationClosingDate, campVisibility, location, totalSlots,
                committeeMembersSlots, campDescription, staffinCharge);
        // Set the visibility status based on user input
        while (true) {
            System.out.println("Toggle visibility for camp (on/off):");
            String manualVisibility = scanner.nextLine().toLowerCase();

            if (manualVisibility.equalsIgnoreCase("on") || manualVisibility.equalsIgnoreCase("off")) {
                newCamp.setManualVisibility(manualVisibility);
                break;
            } else {
                System.out.println("Invalid input. Please enter 'on' or 'off'.");
            }
        }

        newCamp.addDate(start, end);
        camps.add(newCamp);
        updateFile(camps);
        return campName;
    }

    // Helper method to check if a camp with the given name already exists
    private boolean campExists(String campName) {
        for (Camp camp : camps) {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                return true; // Camp with the same name already exists
            }
        }
        return false; // Camp name doesn't exist
    }

    // add in the logic to edit camp details
    public void editCamp(Staff staff, EnquiriesArray enquiriesArray, UsersDatabase usersDB) {
        ArrayList<String> campsInCharge = staff.getCampsInCharge();

        if (campsInCharge.size() == 0) {
            System.out.println("You have no camps to edit");
            System.out.println();
            return;
        }

        System.out.println("Select camp to edit:");
        for (String camp : campsInCharge) {
            System.out.println(campsInCharge.indexOf(camp) + 1 + ". " + camp);
        }
        System.out.println("0. Exit (Not a camp name)");
        String campName;

        while (true) {
            try {
                System.out.printf("Enter choice: ");
                String choice = scanner.nextLine();
                if (choice.equals("0")) {
                    return;
                }
                campName = campsInCharge.get(Integer.parseInt(choice) - 1);
                break;
            } catch (Exception e) {
                System.out.println("Invalid value. Try again");
            }
        }

        Camp targetCamp = null;

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName().equals(campName)) {
                targetCamp = camps.get(i);
                break;
            }
        }

        viewCampDetails(campName, staff);

        System.out.print("What field would you like to edit?\n" +
                "1. Camp Name\n" +
                "2. Registration Closing Date\n" +
                "3. Camp Visibility\n" +
                "4. Location\n" +
                "5. Total Slots\n" +
                "6. Committee Members Slots\n" +
                "7. Camp Description\n" +
                "8. Staff In Charge\n" +
                "9. Add Date\n" +
                "10. Remove Date\n" +
                "11. Toggle Visibility\n" +
                "12. Exit\n" +
                "Enter your choice: ");

        int option = scanner.nextInt();
        scanner.nextLine(); // Flush
        switch (option) {
            case 1:
                System.out.println("Enter new name for the camp:");
                String newName = scanner.nextLine();
                String oldName = targetCamp.getCampInfo().getCampName();
                targetCamp.getCampInfo().setCampName(newName);

                // Staff change Camp Name
                staff.changeCampName(oldName, newName);

                // Student change Camp Name (for Attendees only as they have campName as String)
                usersDB.studentEditCamp(oldName, newName);

                // Enquiries change Camp Name
                enquiriesArray.editCamp(oldName, newName);
                break;
            case 2:
                // Add logic to edit Registration Closing Date
                while (true) {
                    System.out.println("Enter new Registration Closing Date for the camp: ");
                    String newDate = scanner.nextLine();
                    if (newDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        targetCamp.getCampInfo().setRegistrationClosingDate(new CAMDate(newDate));
                        break;
                    } else {
                        System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
                    }
                }
                break;
            case 3:
                System.out.println("Enter Faculty for the camp:");
                String newVisibility = scanner.nextLine().toUpperCase();
                targetCamp.getCampInfo().setCampAvailability(newVisibility);
                break;
            case 4:
                // Add logic to edit Location
                System.out.println("Enter new location for the camp:");
                String newLocation = scanner.nextLine();
                targetCamp.getCampInfo().setLocation(newLocation);
                break;
            case 5:
                System.out.println("Enter new total slots for the camp:");
                int newTotalSlots = scanner.nextInt();
                targetCamp.getCampInfo().setTotalSlots(newTotalSlots);
                break;
            case 6:
                System.out.println("Enter new committee members slots for the camp (Max 10):");
                int newCommitteeMembersSlots = scanner.nextInt();
                newCommitteeMembersSlots = Math.min(newCommitteeMembersSlots, 10);
                targetCamp.getCampInfo().setCommitteeMembersSlots(newCommitteeMembersSlots);
                break;
            case 7:
                System.out.println("Enter new description for the camp:");
                String newDescription = scanner.nextLine();
                targetCamp.getCampInfo().setCampDescription(newDescription);
                break;
            case 8:
                System.out.println("Enter new staff in charge for the camp:");
                String newStaffInCharge = scanner.nextLine();
                targetCamp.getCampInfo().setStaffInCharge(newStaffInCharge);
                break;
            case 9:
                // Add logic to add dates
                // targetCamp.getCampInfo().addDate(new CAMDate(startDate),
                // newCAMDate(endDate));
                while (true) {
                    System.out.println("Enter Date to add to the camp: ");
                    String dateToAdd = scanner.nextLine();
                    if (dateToAdd.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        if (targetCamp.getCampInfo().dateExists(dateToAdd)) {
                            System.out.println("Date already exists");
                            return;
                        } else {
                            if (targetCamp.getRegistrationClosingDate().compareTo(new CAMDate(dateToAdd)) > 0) {
                                System.out.println("Date cannot be before registration closing date");
                                return;
                            }
                            targetCamp.getCampInfo().addDate(new CAMDate(dateToAdd));
                            targetCamp.getCampInfo().sortDates();
                            break;
                        }
                    } else {
                        System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
                    }
                }
                break;

            case 10:
                // Add logic to remove date

                while (true) {
                    System.out.println("Enter Date to remove from the camp: ");
                    String dateToRemove = scanner.nextLine();
                    if (dateToRemove.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        if (targetCamp.getCampInfo().dateExists(dateToRemove)) {
                            targetCamp.getCampInfo().removeDate(dateToRemove);
                            targetCamp.getCampInfo().sortDates();
                            break;
                        } else {
                            System.out.println("Date does not exist");
                            return;
                        }
                    } else {
                        System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
                    }

                }
                break;
            case 11:
                while (true) {
                    System.out.println("Enter new visibility for camp (on/off):");
                    String manualVisibility = scanner.nextLine().toLowerCase();

                    if (manualVisibility.equalsIgnoreCase("on") || manualVisibility.equalsIgnoreCase("off")) {
                        targetCamp.setManualVisibility(manualVisibility);
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'on' or 'off'.");
                    }
                }
                break;
            case 12:
                System.out.println("Exiting Edit Camp");
                return;
            default:
                System.out.println("Invalid choice");
        }

        updateFile(camps);

        System.out.println("Camp edited successfully");
    }

    public boolean checkCampExists(String campName) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                return true;
            }
        }
        return false;
    }

    public void addCamp(Camp camp) {
        camps.add(camp);
    }

    public ArrayList<Camp> getCamps() {
        return camps;
    }

    public void deleteCamp(Staff staff) {
        ArrayList<String> campsInCharge = staff.getCampsInCharge();

        if (campsInCharge.size() == 0) {
            System.out.println("You have no camps to delete");
            System.out.println();
            return;
        }

        System.out.println("Select camp to delete:");
        for (String camp : campsInCharge) {
            System.out.println(campsInCharge.indexOf(camp) + 1 + ". " + camp);
        }
        System.out.println("0. Exit (Not a camp name)");
        String campName;
        while (true) {
            try {
                System.out.printf("Enter choice: ");
                String choice = scanner.nextLine();
                if (choice.equals("0")) {
                    return;
                }
                campName = campsInCharge.get(Integer.parseInt(choice) - 1);
                break;
            } catch (Exception e) {
                System.out.println("Invalid value. Try again");
            }
        }

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName().equals(campName)) {
                camps.remove(i);
                System.out.println("Camp deleted successfully");
            }
        }

        staff.getCampsInCharge().remove(campName);
        updateFile(camps);
    }

    private String truncateWithEllipsis(String input, int width) {
        if (input.length() > width) {
            return input.substring(0, width - 3) + "...";
        } else {
            return String.format("%-" + width + "s", input);
        }
    }

    public void viewCamps(Users user) {
        // Display camps based on different visibility requirements
        // For Staff - No filters since every staff can view every camp
        // For Students - filter by committeeMembers

        System.out.print("\033[s"); // Save the current cursor position

        ArrayList<Camp> filteredCamps = campFilter.filter(user);
        CampSorter.sortCamps(filteredCamps);

        System.out.print("\033[u\033[J"); // Restore cursor position and clear everything below
        if (user instanceof Staff) {
            Staff staffUser = (Staff) user;
            // Display all camps
            System.out.println("\nAll Camps:");
            System.out.println(
                    "===============================================================================================================================================");
            System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-15s | %-7s | %-10s |%n",
                    "Camp Name", "Dates", "Close Date", "Avail", "Location", "Total", "Com-Mem", "Description",
                    "S-I-C", "Visibility");
            System.out.println(
                    "===============================================================================================================================================");
            for (Camp camp : filteredCamps) {
                String campName = truncateWithEllipsis(camp.getCampName(), 15);
                String dates = truncateWithEllipsis(camp.getFormatedDates(), 25);
                String closingDate = truncateWithEllipsis(camp.getRegistrationClosingDate().toString(), 10);
                String availability = truncateWithEllipsis(camp.getCampAvailability(), 6);
                String location = truncateWithEllipsis(camp.getLocation(), 10);
                String totalSlots = camp.getRemainingAttendeeSlots() + "/" + camp.getTotalSlots();
                String committeeSlots = camp.getRemainingCommitteeSlots() + "/" + camp.getCommitteeMembersSlots();
                String description = truncateWithEllipsis(camp.getCampDescription(), 15);
                String staffInCharge = truncateWithEllipsis(camp.getStaffInCharge(), 7);
                String visibility = camp.getManualVisibility(); // Fetch
                                                                // visibility
                // of the camp

                System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-15s | %-7s | %-10s |%n",
                        campName, dates, closingDate, availability, location, totalSlots, committeeSlots,
                        description, staffInCharge, visibility);
            }
            System.out.println(
                    "===============================================================================================================================================");

            // Display camps created by the staff if they are the staffInCharge
            System.out.println("\nYour Created Camps:");
            System.out.println(
                    "===============================================================================================================================================");
            System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-15s | %-7s | %-10s |%n",
                    "Camp Name", "Dates", "Close Date", "Avail", "Location", "Total", "Com-Mem", "Description",
                    "S-I-C", "Visibility");
            System.out.println(
                    "===============================================================================================================================================");
            for (Camp camp : filteredCamps) {
                if (staffUser.getID().equals(camp.getStaffInCharge())
                        || staffUser.getFirstName().equals(camp.getStaffInCharge())) {
                    String campName = truncateWithEllipsis(camp.getCampName(), 15);
                    String dates = truncateWithEllipsis(camp.getFormatedDates(), 25);
                    String closingDate = truncateWithEllipsis(camp.getRegistrationClosingDate().toString(), 10);
                    String availability = truncateWithEllipsis(camp.getCampAvailability(), 6);
                    String location = truncateWithEllipsis(camp.getLocation(), 10);
                    String totalSlots = camp.getRemainingAttendeeSlots() + "/" + camp.getTotalSlots();
                    String committeeSlots = camp.getRemainingCommitteeSlots() + "/" + camp.getCommitteeMembersSlots();
                    String description = truncateWithEllipsis(camp.getCampDescription(), 15);
                    String staffInCharge = truncateWithEllipsis(camp.getStaffInCharge(), 7);
                    String visibility = camp.getManualVisibility(); // Fetch
                    // visibility of
                    // the camp

                    System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-15s | %-7s | %-10s |%n",
                            campName, dates, closingDate, availability, location, totalSlots, committeeSlots,
                            description, staffInCharge, visibility);
                }
            }
            System.out.println(
                    "===============================================================================================================================================");
        } else if (user instanceof Student) {

            Student studentUser = (Student) user;
            // Display only camps open to the student's user group with visibility toggled
            // "on"
            // and display remaining slots for each camp open to the student
            System.out.println("Open Camps for Student:");
            System.out.println(
                    "===============================================================================================================================================");
            System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-25s | %-10s |%n",
                    "Camp Name", "Dates", "Close Date", "Avail", "Location", "Total", "Com-Mem", "Description",
                    "S-I-C");
            System.out.println(
                    "===============================================================================================================================================");
            for (Camp camp : filteredCamps) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                if (camp.getRegistrationClosingDate().compareTo(new CAMDate(formattedDate)) < 0) {
                    continue;
                }
                // Outer if - Manual visibility
                if (camp.getManualVisibility().equalsIgnoreCase("on")) {
                    // Next inner if loop- AutoVisibility
                    if (camp.toggleVisibility().equalsIgnoreCase("on")) {
                        // Next inner if loop Faculty checker -> NTU or same faculty
                        if (camp.getCampAvailability().equalsIgnoreCase("NTU")
                                || studentUser.getFacultyInfo().equalsIgnoreCase(camp.getCampAvailability())) {
                            String campName = truncateWithEllipsis(camp.getCampName(), 15);
                            String dates = truncateWithEllipsis(camp.getFormatedDates(), 25);
                            String closingDate = truncateWithEllipsis(camp.getRegistrationClosingDate().toString(), 10);
                            String availability = truncateWithEllipsis(camp.getCampAvailability(), 6);
                            String location = truncateWithEllipsis(camp.getLocation(), 10);
                            String totalSlots = camp.getRemainingAttendeeSlots() + "/" + camp.getTotalSlots();
                            String committeeSlots = camp.getRemainingCommitteeSlots() + "/"
                                    + camp.getCommitteeMembersSlots();
                            String description = truncateWithEllipsis(camp.getCampDescription(), 25);
                            String staffInCharge = truncateWithEllipsis(camp.getStaffInCharge(), 10);

                            System.out.printf("%-15s | %-25s | %-10s | %-6s | %-10s | %-8s | %-8s | %-25s | %-10s |%n",
                                    campName, dates, closingDate, availability, location, totalSlots, committeeSlots,
                                    description, staffInCharge);
                            System.out.println(
                                    "===============================================================================================================================================");
                        }

                    }
                }
            }
            // Display camps the student has registered for and their roles
            System.out.println("\nYour Registered Camps:");
            System.out.println(
                    "===============================================================================================================================================");
            System.out.printf("%-15s | %-40s | %-6s | %-10s | %-32s | %-10s | %-10s |%n",
                    "Camp Name", "Dates", "Avail", "Location", "Description", "S-I-C", "Role");
            System.out.println(
                    "===============================================================================================================================================");
            boolean hasRegisteredCamps = false; // Check if student has registered for camps
            for (Camp camp : filteredCamps) {
                if (camp.getAttendees().contains(studentUser.getID())
                        || camp.getAttendees().contains(studentUser.getFirstName())) {
                    String campName = truncateWithEllipsis(camp.getCampName(), 15);
                    String dates = truncateWithEllipsis(camp.getFormatedDates(), 40);
                    String availability = truncateWithEllipsis(camp.getCampAvailability(), 6);
                    String location = truncateWithEllipsis(camp.getLocation(), 10);
                    String description = truncateWithEllipsis(camp.getCampDescription(), 32);
                    String staffInCharge = truncateWithEllipsis(camp.getStaffInCharge(), 10);
                    System.out.printf("%-15s | %-40s | %-6s | %-10s | %-32s | %-10s | %-10s |%n",
                            campName, dates, availability, location, description, staffInCharge, "Attendee");
                    hasRegisteredCamps = true;

                    System.out.println(
                            "===============================================================================================================================================");
                }
                if (camp.getCommitteeMembers().contains(studentUser.getID())
                        || camp.getCommitteeMembers().contains(studentUser.getFirstName())) {
                    String campName = truncateWithEllipsis(camp.getCampName(), 15);
                    String dates = truncateWithEllipsis(camp.getFormatedDates(), 40);
                    String availability = truncateWithEllipsis(camp.getCampAvailability(), 6);
                    String location = truncateWithEllipsis(camp.getLocation(), 10);
                    String description = truncateWithEllipsis(camp.getCampDescription(), 32);
                    String staffInCharge = truncateWithEllipsis(camp.getStaffInCharge(), 10);
                    System.out.printf("%-15s | %-40s | %-6s | %-10s | %-32s | %-10s | %-10s |%n",
                            campName, dates, availability, location, description, staffInCharge, "Com Mem");
                    // displayRegisteredStudents(camp);
                    hasRegisteredCamps = true;
                    System.out.println(
                            "===============================================================================================================================================");
                }
            }
            if (!hasRegisteredCamps) {
                System.out.println("None.");
            }

        }
        campFilter.clearFilteredCamps();
    }

    // this method should only be used by staff or camp committee members
    // TODO add in logic to only show details of the camp to camp committee members
    // if they are camp committee members of that camp
    public void viewCampDetails(String campName, Users user) {
        // view the details of the camp
        System.out.println("Camp Details");
        System.out.println("=====================================");
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                System.out.println("Camp Name: " + camp.getCampName());
                System.out.println("Camp Dates: " + camp.getFormatedDates());
                System.out.println("Registration Closing Date: " + camp.getRegistrationClosingDate());
                System.out.println("Location: " + camp.getLocation());
                System.out.println("Total Slots: " + camp.getTotalSlots());
                System.out.println("Remaining Slots for Camp Committee: " + camp.getRemainingCommitteeSlots());
                System.out.println("Remaining Slots for Attendees: " + camp.getRemainingAttendeeSlots());
                System.out.println("Camp Description: " + camp.getCampDescription());
                System.out.println("Staff In Charge: " + camp.getStaffInCharge());
                System.out.println("Faculty the camp is available for: " + camp.getCampAvailability());
                if (user instanceof Staff || camp.getCommitteeMembers().contains(user.getID())) {
                    displayRegisteredStudents(camp);
                }
                System.out.println(); // Add a line break for better readability
            }
        }
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

    public Camp getCamp(String campName) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                return camp;
            }
        }
        return null;
    }

    public void registerAttendee(String campName, String studentID) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                // Get today's date
                LocalDate today = LocalDate.now();
                // Format the date as DD/MM/YYYY
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                if (camp.getRegistrationClosingDate().compareTo(new CAMDate(formattedDate)) < 0) {
                    System.out.println("Registration for this camp has closed.");
                    return;
                }
                camp.registerStudent(studentID, studentID, false, campName);
            }
        }
        updateFile(camps);
    }

    public void registerCampCom(String campName, String studentID) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                if (camp.getRegistrationClosingDate().compareTo(new CAMDate(formattedDate)) < 0) {
                    System.out.println("Registration for this camp has closed.");
                    return;
                }
                camp.registerStudent(studentID, studentID, true, campName);
            }
        }
        updateFile(camps);
    }

    public void withdrawAttendee(String campName, String studentID) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                camp.withdrawAttendee(studentID);
            }
        }
        updateFile(camps);
    }

    public ArrayList<Camp> getCampArray() {
        return camps;
    }

    public void setCampArray(ArrayList<Camp> camps) {
        CampArray.camps = camps;
    }

}