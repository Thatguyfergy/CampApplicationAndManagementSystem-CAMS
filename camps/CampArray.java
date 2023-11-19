package camps;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import camdate.CAMDate;
import infoexchange.EnquiriesArray;
import infoexchange.SuggestionArray;

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

    // The above code is a constructor for the CampArray class in Java. It takes a
    // file name as a parameter and reads the contents of the file. The file is
    // assumed to be in
    // CSV format, with each row representing a camp.
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

    /**
     * The function `updateFile` takes an ArrayList of Camp objects and writes their
     * data to a CSV file.
     * 
     * @param camps An ArrayList of Camp objects.
     */
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

    /**
     * The function creates a new camp with user-provided details and adds it to a
     * list of camps.
     * 
     * @param staffinCharge The parameter "staffinCharge" represents the staff
     *                      member who is in charge of the camp.
     * @return The method is returning a String, which is the campName.
     */
    public String createCamp(String staffinCharge) {
        System.out.println("Camp Name:");
        String campName = scanner.nextLine();
        // Check if the camp name already exists
        if (checkCampExists(campName)) {
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

        CAMDate registrationClosingDate;
        while (true) {
            System.out.println("Registration closing date (dd/mm/yyyy):");
            String date = scanner.nextLine();
            if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                try {
                    registrationClosingDate = new CAMDate(date);
                } catch (Exception IllegalArgumentException) {
                    System.out.println("Date does not exist. Please enter a valid date.");
                    continue;
                }

                LocalDate today = LocalDate.now();
                // Format the date as DD/MM/YYYY
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedDate = today.format(formatter);
                if (registrationClosingDate.compareTo(new CAMDate(formattedDate)) < 0) {
                    System.out.println("Registration closing date cannot be before today's date");
                    continue;
                }
                break;
            } else {
                System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
            }
        }

        String startDate, endDate;
        while (true) {
            System.out.println("Camp start date (dd/mm/yyyy):");
            startDate = scanner.nextLine();
            try {
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
            } catch (Exception IllegalArgumentException) {
                System.out.println("Date does not exist. Please enter a valid date.");
            }

        }

        CAMDate start = new CAMDate(startDate);
        CAMDate end = new CAMDate(endDate);

        System.out.println("Faculty the camp is available for:");
        String campVisibility = scanner.nextLine().toUpperCase();

        System.out.println("Location:");
        String location = scanner.nextLine().toUpperCase();
        if (location.equals("")) {
            System.out.println("Location cannot be empty");
            return null;
        }

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

    /**
     * The function `editCamp` allows a staff member to edit various fields of a
     * camp, such as the camp
     * name, registration closing date, camp visibility, location, total slots,
     * committee members slots, camp description, staff in charge, and camp dates.
     * 
     * @param staff          The staff object represents the staff member who is
     *                       editing the camp. It contains
     *                       information about the staff member, such as their name,
     *                       position, and the camps they are in charge of.
     * @param enquiriesArray The `enquiriesArray` parameter is an instance of the
     *                       `EnquiriesArray`
     *                       class, which is an array that stores all the enquiries
     *                       made by users.
     * @param usersDB        The `usersDB` parameter is an instance of the
     *                       `UsersDatabase` class, which is
     *                       used to manage the database of users in the system. It
     *                       likely contains methods for adding, updating, and
     *                       retrieving user information.
     */
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
                "3. Camp Availability\n" +
                "4. Location\n" +
                "5. Total Slots\n" +
                "6. Committee Members Slots\n" +
                "7. Camp Description\n" +
                "8. Add Date\n" +
                "9. Remove Date\n" +
                "10. Toggle Visibility\n" +
                "11. Exit\n" +
                "Enter your choice: ");

        String option = scanner.nextLine();
        switch (option) {
            case "1":
                System.out.println("Enter new name for the camp:");
                String newName = scanner.nextLine();
                if (checkCampExists(newName)) {
                    System.out.println(
                            "Camp with the name '" + newName + "' already exists. Please choose a different name.");
                    return;
                }
                String oldName = targetCamp.getCampInfo().getCampName();
                targetCamp.getCampInfo().setCampName(newName);

                // Staff change Camp Name
                staff.changeCampName(oldName, newName);

                // Student change Camp Name (for Attendees only as they have campName as String)
                usersDB.studentEditCamp(oldName, newName);

                // Enquiries change Camp Name
                enquiriesArray.editCamp(oldName, newName);
                break;
            case "2":
                // Add logic to edit Registration Closing Date
                while (true) {
                    try {
                        System.out.println("Enter new Registration Closing Date for the camp: ");
                        String newDate = scanner.nextLine();
                        if (newDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            if (new CAMDate(newDate).compareTo(targetCamp.getDates().get(0)) > 0) {
                                System.out.println("Registration Closing Date cannot be after camp start date");
                                return;
                            }
                            targetCamp.getCampInfo().setRegistrationClosingDate(new CAMDate(newDate));
                            break;
                        } else {
                            System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
                        }
                    } catch (Exception IllegalArgumentException) {
                        System.out.println("Date does not exist. Please enter a valid date.");
                    }
                }
                break;
            case "3":
                System.out.println("Enter Faculty for the camp:");
                String newVisibility = scanner.nextLine().toUpperCase();
                targetCamp.getCampInfo().setCampAvailability(newVisibility);
                break;
            case "4":
                // Add logic to edit Location
                System.out.println("Enter new location for the camp:");
                String newLocation = scanner.nextLine();
                if (newLocation.equals("")) {
                    System.out.println("Location cannot be empty");
                    return;
                }
                targetCamp.getCampInfo().setLocation(newLocation);
                break;
            case "5":
                System.out.println("Enter new total slots for the camp:");
                int newTotalSlots = scanner.nextInt();
                scanner.nextLine(); // Flush
                if (newTotalSlots < targetCamp.getAttendees().size()) {
                    System.out.println("Total slots cannot be less than the number of registered attendees");
                    return;
                }
                targetCamp.getCampInfo().setTotalSlots(newTotalSlots);
                break;
            case "6":
                System.out.println("Enter new committee members slots for the camp (Max 10):");
                int newCommitteeMembersSlots = scanner.nextInt();
                scanner.nextLine(); // Flush
                if (newCommitteeMembersSlots < targetCamp.getCommitteeMembers().size()) {
                    System.out.println(
                            "Committee members slots cannot be less than the number of registered committee members");
                    return;
                }
                newCommitteeMembersSlots = Math.min(newCommitteeMembersSlots, 10);
                targetCamp.getCampInfo().setCommitteeMembersSlots(newCommitteeMembersSlots);
                break;
            case "7":
                System.out.println("Enter new description for the camp:");
                String newDescription = scanner.nextLine();
                targetCamp.getCampInfo().setCampDescription(newDescription);
                break;
            case "8":
                // Add logic to add dates
                // targetCamp.getCampInfo().addDate(new CAMDate(startDate),
                // newCAMDate(endDate));
                if (targetCamp.getAttendees().size() > 0) {
                    System.out.println("You cannot add dates to a camp with registered attendees");
                    return;
                }

                while (true) {
                    System.out.println("Enter Date to add to the camp: ");
                    String dateToAdd = scanner.nextLine();
                    if (dateToAdd.matches("\\d{2}/\\d{2}/\\d{4}")) {
                        if (targetCamp.getCampInfo().dateExists(dateToAdd)) {
                            System.out.println("Date already exists");
                            return;
                        } else {
                            try {
                                if (new CAMDate(dateToAdd).compareTo(targetCamp.getRegistrationClosingDate()) < 0) {
                                    System.out.println("Date cannot be before registration closing date");
                                    return;
                                }
                            } catch (Exception IllegalArgumentException) {
                                System.out.println("Date does not exist. Please enter a valid date.");
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

            case "9":
                // Add logic to remove date
                if (targetCamp.getAttendees().size() > 0) {
                    System.out.println("You cannot remove dates from a camp with registered attendees");
                    return;
                }

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
            case "10":
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
            case "11":
                System.out.println("Exiting Edit Camp");
                return;
            default:
                System.out.println("Invalid choice");
        }

        updateFile(camps);

        System.out.println("Camp edited successfully");
    }

    /**
     * The function checks if a camp with a given name exists in a list of camps.
     * 
     * @param campName The parameter `campName` is a String that represents the name
     *                 of a camp.
     * @return The method is returning a boolean value. It returns true if a camp
     *         with the specified campName exists in the camps list, and false
     *         otherwise.
     */
    public boolean checkCampExists(String campName) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The addCamp function adds a Camp object to a list of camps.
     * 
     * @param camp The "camp" parameter is an object of type Camp.
     */
    public void addCamp(Camp camp) {
        camps.add(camp);
    }

    /**
     * The getCamps() function returns an ArrayList of Camp objects.
     * 
     * @return An ArrayList of Camp objects is being returned.
     */
    public ArrayList<Camp> getCamps() {
        return camps;
    }

    /**
     * The function `deleteCamp` allows a staff member to delete a camp from the
     * system, along with its associated data.
     * 
     * @param staff           An instance of the Staff class, which contains
     *                        information about the staff member and the camps they
     *                        are in charge of.
     * @param usersDB         The `usersDB` parameter is an instance of the
     *                        `UsersDatabase` class, which represents a database of
     *                        user information. It is used
     *                        to perform operations related to user data, such as
     *                        deleting a camp from the database.
     * @param enquiriesArray  The `enquiriesArray` parameter is an instance of the
     *                        `EnquiriesArray` class, which is an array that stores
     *                        all the enquiries related to camps.
     * @param suggestionArray The `suggestionArray` parameter is an instance of the
     *                        `SuggestionArray` class, which is an array that stores
     *                        suggestions for camps.
     */
    public void deleteCamp(Staff staff, UsersDatabase usersDB, EnquiriesArray enquiriesArray,
            SuggestionArray suggestionArray) {
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

        Camp targetCamp = getCamp(campName);
        ArrayList<CAMDate> deletedDates = targetCamp.getDates();
        suggestionArray.deleteCamp(targetCamp);
        camps.remove(targetCamp);
        usersDB.deleteCamp(campName, deletedDates);
        enquiriesArray.deleteCamp(campName);
        usersDB.updateFile();

        System.out.println("Camp deleted successfully");

        staff.getCampsInCharge().remove(campName);
        updateFile(camps);
    }

    /**
     * The function truncates a string to a specified width, adding an ellipsis if
     * necessary.
     * 
     * @param input The input parameter is a String that represents the text that
     *              needs to be truncated.
     * @param width The "width" parameter specifies the maximum width of the
     *              truncated string.
     * @return The method returns a truncated version of the input string with an
     *         ellipsis (...) added at the end if the length of the input string is
     *         greater than the specified width.
     *         If the length of the input string is less than or equal to the
     *         specified width, the method returns the input
     *         string padded with spaces to match the width.
     */
    private String truncateWithEllipsis(String input, int width) {
        if (input.length() > width) {
            return input.substring(0, width - 3) + "...";
        } else {
            return String.format("%-" + width + "s", input);
        }
    }

    /**
     * The function "viewCamps" displays different camps based on the visibility
     * requirements for staff
     * and students, filtering and sorting the camps accordingly.
     * 
     * @param user The "user" parameter is an instance of the "Users" class, which
     *             is a superclass for
     *             both "Staff" and "Student" classes. It represents the user who
     *             wants to view the camps.
     */
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
                String totalSlots = camp.getRemainingSlots() + "/" + camp.getTotalSlots();
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
                    String totalSlots = camp.getRemainingSlots() + "/" + camp.getTotalSlots();
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
                            String totalSlots = camp.getRemainingSlots() + "/" + camp.getTotalSlots();
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

    /**
     * The function "viewCampDetails" displays the details of a camp, including its
     * name, dates, registration closing date, location, slots available,
     * description, staff in charge, and the faculty the camp is available for.
     * 
     * @param campName The campName parameter is a String that represents the name
     *                 of the camp for which we want to view the details.
     * @param user     The "user" parameter is an instance of the "Users" class,
     *                 which represents a user in the system. It is used to
     *                 determine the type of user accessing the camp details.
     */
    public void viewCampDetails(String campName, Users user) {
        // view the details of the camp
        System.out
                .println("══════════════════════════════════════════════════════════════════════════════════════════");
        System.out.println("Camp Details");
        System.out
                .println("══════════════════════════════════════════════════════════════════════════════════════════");
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                System.out.println("Camp Name: \t\t\t\t" + camp.getCampName());
                System.out.println("Camp Dates: \t\t\t\t" + camp.getFormatedDates());
                System.out.println("Registration Closing Date: \t\t" + camp.getRegistrationClosingDate());
                System.out.println("Location: \t\t\t\t" + camp.getLocation());
                System.out.println("Total Slots: \t\t\t\t" + camp.getTotalSlots());
                System.out.println("Remaining Slots for Camp Committee: \t" + camp.getRemainingCommitteeSlots());
                System.out.println("Remaining Slots for Attendees: \t\t" + camp.getRemainingAttendeeSlots());
                System.out.println("Camp Description: \t\t\t" + camp.getCampDescription());
                System.out.println("Staff In Charge: \t\t\t" + camp.getStaffInCharge());
                System.out.println("Faculty the camp is available for: \t" + camp.getCampAvailability());
                if (user instanceof Staff || camp.getCommitteeMembers().contains(user.getID())) {
                    displayRegisteredStudents(camp);
                }
                System.out.println(
                        "══════════════════════════════════════════════════════════════════════════════════════════");
                return; // since only one camp can have that name, don't need to continue checking
            }
        }
    }

    /**
     * The function displays the registered students, attendees, and committee
     * members of a camp.
     * 
     * @param camp The "camp" parameter is an instance of the "Camp" class. It
     *             represents a camp object
     *             that contains information about registered students, attendees,
     *             and committee members.
     */
    private void displayRegisteredStudents(Camp camp) {
        System.out.println("Registered Students:");

        // Display attendees
        System.out.println("Attendees:");
        for (String attendee : camp.getAttendees()) {
            if (attendee != null)
                System.out.println("- " + UsersDatabase.getFirstName(attendee) + " (" + attendee +  ")");
        }

        // Display committee members
        System.out.println("Committee Members:");
        for (String committeeMember : camp.getCommitteeMembers()) {
            if (committeeMember != null)
                System.out.println("- " + UsersDatabase.getFirstName(committeeMember) + " (" + committeeMember + ")");
        }

        System.out.println(); // Add a line break for better readability
    }

    /**
     * The function "getCamp" returns a Camp object based on the given camp name, or
     * null if no matching camp is found.
     * 
     * @param campName The campName parameter is a String that represents the name
     *                 of the camp that we are searching for.
     * @return The method is returning a Camp object.
     */
    public Camp getCamp(String campName) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                return camp;
            }
        }
        return null;
    }

    /**
     * The function registers an attendee for a camp if the registration is still
     * open.
     * 
     * @param campName  The name of the camp that the attendee wants to register
     *                  for.
     * @param studentID The studentID parameter is a unique identifier for each
     *                  student. It is used to register the student for a camp.
     */
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

    /**
     * The function registers a student for a camp if the camp is still open for
     * registration.
     * 
     * @param campName  The campName parameter is a String that represents the name
     *                  of the camp that the student wants to register for.
     * @param studentID The student ID is a unique identifier for each student. It
     *                  could be a number or a string that uniquely identifies a
     *                  student.
     */
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

    /**
     * The function withdraws an attendee from a camp by finding the camp with the
     * given name and calling the withdrawAttendee method on it.
     * 
     * @param campName  The name of the camp from which the attendee needs to be
     *                  withdrawn.
     * @param studentID The studentID parameter is a String that represents the
     *                  unique identifier of the student who wants to withdraw from
     *                  a camp.
     */
    public void withdrawAttendee(String campName, String studentID) {
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                camp.withdrawAttendee(studentID);
            }
        }
        updateFile(camps);
    }

    /**
     * The function returns an ArrayList of Camp objects.
     * 
     * @return An ArrayList of Camp objects is being returned.
     */
    public ArrayList<Camp> getCampArray() {
        return camps;
    }

    /**
     * The function sets the value of the static variable "camps" in the CampArray
     * class to the provided ArrayList of Camp objects.
     * 
     * @param camps An ArrayList of objects of type Camp.
     */
    public void setCampArray(ArrayList<Camp> camps) {
        CampArray.camps = camps;
    }

    /**
     * The function checks if a student is eligible for a specific camp based on
     * their faculty information and the availability of the camp.
     * 
     * @param student  An object of the class Student, which contains information
     *                 about a student.
     * @param campName The campName parameter is a String that represents the name
     *                 of the camp.
     * @return The method is returning a boolean value.
     */
    public boolean checkEligibleCamp(Student student, String campName) {
        Camp targetCamp = getCamp(campName);
        // System.out.println(targetCamp.getCampAvailability()+"
        // "+student.getFacultyInfo());
        if (targetCamp.getCampAvailability().equals(student.getFacultyInfo())
                || targetCamp.getCampAvailability().equals("NTU"))
            return true;
        else
            return false;
    }

}