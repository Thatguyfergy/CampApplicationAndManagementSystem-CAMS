package camps;

import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;
import users.Student;
import users.Users;

/**
 * The CampFilter class is used to filter a list of camps based on certain
 * criteria such as dates or faculties.
 * 
 * @author Tan Ying Hao
 * @version 1.0
 */
public class CampFilter {

    private ArrayList<String> availableDates;
    private ArrayList<String> availableFaculties;
    private ArrayList<String> availableLocations;
    private ArrayList<String> availableSIC;
    private ArrayList<Camp> filteredCamps;
    private ArrayList<Camp> unfilteredCamps;
    private Scanner sc;

    /**
     * The `CampFilter()` is a constructor method for the `CampFilter` class.
     * It initialises 'availableDates', 'availableFaculties', 'availableLocations',
     * 'availableSIC', 'filteredCamps', 'unfilteredCamps', and 'sc' variables.
     */
    public CampFilter() {
        availableDates = new ArrayList<String>();
        availableFaculties = new ArrayList<String>();
        availableLocations = new ArrayList<String>();
        availableSIC = new ArrayList<String>();
        filteredCamps = new ArrayList<Camp>();
        unfilteredCamps = new ArrayList<Camp>();
        sc = new Scanner(System.in);
    }

    /**
     * The method 'addAvailableDate()' adds a date to 'availableDates' ArrayList
     * if it is not already in the list
     * 
     * @param date The parameter "date" is a String that represents a date
     *             to be added to 'availableDates' ArrayList
     * @return The method returns a boolean value.
     *         It returns true if the date is successfully added to the list,
     *         and false if the date is already in the list.
     */
    public boolean addAvailableDate(String date) {
        if (!availableDates.contains(date)) {
            return availableDates.add(date);
        }
        return false; // Return false if the date is already in the list
    }

    /**
     * The method 'addAvailableFaculty()' adds a faculty to 'availableFaculties'
     * ArrayList
     * if it is not already in the list
     * 
     * @param faculty The parameter "faculty" is a String that represents
     *                the faculty that to be added to 'availableFaculties' ArrayList
     * @return The method returns a boolean value.
     *         It returns true if the faculty is successfully added to the list,
     *         and false if the faculty is already in the list.
     */
    public boolean addAvailableFaculty(String faculty) {
        if (!availableFaculties.contains(faculty)) {
            return availableFaculties.add(faculty);
        }
        return false; // Return false if the faculty is already in the list
    }

    /**
     * The method 'addAvailableLocation()' adds a location to 'availableLocations'
     * ArrayList
     * if it is not already in the list
     * 
     * @param location The location parameter is a String that represents the
     *                 location to be added to 'availableLocations' ArrayList
     * @return The method returnds a boolean value.
     *         It returns true if the location was successfully added to the list,
     *         and false if the location is already in the list.
     */
    public boolean addAvailableLocation(String location) {
        if (!availableLocations.contains(location)) {
            return availableLocations.add(location);
        }
        return false; // Return false if the location is already in the list
    }

    /**
     * The method 'addAvailableSIC()' adds a staff in charge to 'availableSIC'
     * ArrayList
     * if it is not already in the list
     * 
     * @param sic The parameter "sic" is a String representing a Staff In Charge
     *            to be added to 'availableSIC' ArrayList
     * @return The method returns a boolean value.
     *         It returns true if sic was successfully added to the list,
     *         and false if sic is already in the list.
     */
    public boolean addAvailableSIC(String sic) {
        if (!availableSIC.contains(sic)) {
            return availableSIC.add(sic);
        }
        return false; // Return false if the sic is already in the list
    }

    /**
     * The method 'filter(Users user)' filters a list of camps based on user's input
     * for the type of filter,
     * such as date, faculty, location, and staff in charge.
     * if the user enters an invalid choice, no filter will be applied.
     * 
     * @param user The "user" parameter is an instance of the "Users" class.
     * @return The method returns the ArrayList<Camp> of filtered camps.
     */
    public ArrayList<Camp> filter(Users user) {
        System.out.println("Filter by: ");
        System.out.println("1. Date");
        System.out.println("2. Faculty");
        System.out.println("3. Location");
        System.out.println("4. Staff in charge");
        System.out.println("5. No filter");
        System.out.printf("Enter choice: ");

        // Read the user input
        String choice = sc.nextLine();

        // Clear the lines printed by this function

        switch (choice) {
            case "1":
                filterByDate();
                break;
            case "2":
                filterByFaculty(user);
                break;
            case "3":
                filterByLocation();
                break;
            case "4":
                filterBySIC();
                break;
            case "5":
                return unfilteredCamps;
            default:
                System.out.println("Invalid choice! - Applying no filter");
                return unfilteredCamps;
        }
        return filteredCamps;
    }

    /**
     * The method 'filterByDate()' filters a list of camps by a user-selected date.
     * The user is prompted to enter a date in the format dd/mm/yyyy
     * if user enters an invalid date, user will be prompted to enter date again.
     * using the given date, the method adds all camps that have the given date
     * into the 'filteredCamps' ArrayList.
     */
    private void filterByDate() {
        String date;
        while (true) {
            System.out.println("Enter date to filter by (dd/mm/yyyy): ");
            date = sc.nextLine();
            if (date.matches("\\d{2}/\\d{2}/\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid date format. Please enter date in the format dd/mm/yyyy");
            }
        }
        for (Camp camp : unfilteredCamps) {
            for (CAMDate d : camp.getDates()) {
                if (d.toString().equals(date)) {
                    filteredCamps.add(camp);
                }
            }
        }
    }

    /**
     * The method 'filterByFaculty(Users user)' filters a list of camps by a
     * user-selected faculty.
     * If user is a Student, the method will display the student's faculty and NTU
     * as options.
     * If user is a Staff, the method will display all available faculties as
     * options.
     * The user is prompted to enter a faculty to filter by.
     * The method adds all camps that have the given faculty into the
     * 'filteredCamps' ArrayList.
     * 
     * @param user The user parameter is an instance of the Users class, which is
     *             the parent class for both the Student and Staff classes.
     */
    private void filterByFaculty(Users user) {
        String faculty;
        System.out.println("Enter faculty to filter by: ");

        if (user instanceof Student) {
            System.out.println("1. " + ((Student) user).getFacultyInfo());
            System.out.println("2. NTU");
            while (true) {
                String choice = sc.nextLine();
                if (choice.equals("1")) {
                    faculty = ((Student) user).getFacultyInfo();
                    break;
                } else if (choice.equals("2")) {
                    faculty = "NTU";
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2");
                }
            }
            for (Camp camp : unfilteredCamps) {
                if (camp.getCampAvailability().equals(faculty)) {
                    filteredCamps.add(camp);
                }
            }
        } else {
            for (int i = 0; i < availableFaculties.size(); i++) {
                System.out.println(i + 1 + ". " + availableFaculties.get(i));
            }
            while (true) {
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice > 0 && choice <= availableFaculties.size()) {
                    faculty = availableFaculties.get(choice - 1);
                    break;
                } else {
                    System.out.println(
                            "Invalid choice. Please enter a number between 1 and " + availableFaculties.size());
                }
            }
            for (Camp camp : unfilteredCamps) {
                if (camp.getCampAvailability().equals(faculty)) {
                    filteredCamps.add(camp);
                }
            }
        }

    }

    /**
     * The method 'filterByLocation()' filters a list of camps by a user-selected
     * location.
     * The user is prompted to enter a location to filter by.
     * The method adds all camps that have the given location into the
     * 'filteredCamps' ArrayList.
     */
    private void filterByLocation() {
        String location;
        System.out.println("Enter location to filter by: ");
        for (int i = 0; i < availableLocations.size(); i++) {
            System.out.println(i + 1 + ". " + availableLocations.get(i));
        }
        while (true) {
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice > 0 && choice <= availableLocations.size()) {
                location = availableLocations.get(choice - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + availableLocations.size());
            }
        }

        for (Camp camp : unfilteredCamps) {
            if (camp.getLocation().equals(location)) {
                filteredCamps.add(camp);
            }
        }
    }

    /**
     * The method 'filterBySIC()' filters a list of camps by a user-selected staff
     * in charge.
     * The user is prompted to enter a staff in charge to filter by.
     * The method adds all camps that have the given staff in charge into the
     * 'filteredCamps' ArrayList.
     */
    private void filterBySIC() {

        String sic;
        System.out.println("Enter staff in charge to filter by: ");
        for (int i = 0; i < availableSIC.size(); i++) {
            System.out.println(i + 1 + ". " + availableSIC.get(i));
        }
        while (true) {
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice > 0 && choice <= availableSIC.size()) {
                sic = availableSIC.get(choice - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + availableSIC.size());
            }
        }

        for (Camp camp : unfilteredCamps) {
            if (camp.getStaffInCharge().equals(sic)) {
                filteredCamps.add(camp);
            }
        }
    }

    /**
     * The method 'setUnfilterCamps' sets the 'unfilteredCamps' ArrayList to the
     * given ArrayList of Camp objects.
     * 
     * @param camps The "camps" parameter is an ArrayList of objects of type Camp.
     */
    public void setUnfilteredCamps(ArrayList<Camp> camps) {
        unfilteredCamps = camps;
    }

    /**
     * The method 'clearFilteredCamps()' clears the 'filteredCamps' ArrayList.
     */
    public void clearFilteredCamps() {
        filteredCamps.clear();
    }
}
