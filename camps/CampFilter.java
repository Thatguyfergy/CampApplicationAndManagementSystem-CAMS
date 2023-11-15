package camps;

import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;
import users.Student;
import users.Users;

public class CampFilter {

    private ArrayList<String> availableDates;
    private ArrayList<String> availableFaculties;
    private ArrayList<String> availableLocations;
    private ArrayList<String> availableSIC;
    private ArrayList<Camp> filteredCamps;
    private ArrayList<Camp> unfilteredCamps;
    private Scanner sc;

    // The `public CampFilter()` is a constructor method for the `CampFilter` class.
    // It initializes the
    // instance variables `availableDates`, `availableFaculties`,
    // `availableLocations`, `availableSIC`,
    // `filteredCamps`, and `unfilteredCamps` as empty ArrayLists. It also
    // initializes the `sc`
    // variable as a new instance of the `Scanner` class, which reads input from the
    // standard input
    // stream.
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
     * The function adds a date to a list of available dates if it is not already
     * present.
     * 
     * @param date The parameter "date" is a String representing a date that you
     *             want to add to the
     *             list of available dates.
     * @return The method is returning a boolean value. It returns true if the date
     *         is successfully
     *         added to the list of available dates, and false if the date is
     *         already in the list.
     */
    public boolean addAvailableDate(String date) {
        if (!availableDates.contains(date)) {
            return availableDates.add(date);
        }
        return false; // Return false if the date is already in the list
    }

    /**
     * The function adds a faculty to a list of available faculties if it is not
     * already in the list.
     * 
     * @param faculty The parameter "faculty" is a String representing the name of a
     *                faculty that you
     *                want to add to the list of available faculties.
     * @return The method is returning a boolean value. It returns true if the
     *         faculty is successfully
     *         added to the list of available faculties, and false if the faculty is
     *         already in the list.
     */
    public boolean addAvailableFaculty(String faculty) {
        if (!availableFaculties.contains(faculty)) {
            return availableFaculties.add(faculty);
        }
        return false; // Return false if the faculty is already in the list
    }

    /**
     * The function adds a location to a list of available locations if it is not
     * already in the list.
     * 
     * @param location The location parameter is a String that represents the
     *                 location to be added to
     *                 the list of available locations.
     * @return The method is returning a boolean value. It returns true if the
     *         location was
     *         successfully added to the availableLocations list, and false if the
     *         location is already in the
     *         list.
     */
    public boolean addAvailableLocation(String location) {
        if (!availableLocations.contains(location)) {
            return availableLocations.add(location);
        }
        return false; // Return false if the location is already in the list
    }

    /**
     * The function addAvailableSIC checks if a given Staff In Charge is already in
     * the availableSIC list and adds
     * it if it is not.
     * 
     * @param sic The parameter "sic" is a String representing a Staff In Charge
     * @return The method is returning a boolean value. It returns true if the given
     *         sic is not already
     *         in the availableSIC list and it is successfully added to the list. It
     *         returns false if the given
     *         sic is already in the list and cannot be added again.
     */
    public boolean addAvailableSIC(String sic) {
        if (!availableSIC.contains(sic)) {
            return availableSIC.add(sic);
        }
        return false; // Return false if the sic is already in the list
    }

    /**
     * The function filters a list of camps based on user input for various criteria
     * such as date,
     * faculty, location, and staff in charge.
     * 
     * @param user The "user" parameter is an instance of the "Users" class.
     * @return The method is returning an ArrayList of Camp objects.
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
     * The function filterByDate() prompts the user to enter a date in the format
     * dd/mm/yyyy, validates
     * the input, and filters a list of camps based on the entered date.
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
     * The function filters a list of camps based on the faculty of a user.
     * 
     * @param user The user parameter is an instance of the Users class, which is
     *             the parent class for
     *             both the Student and other user types.
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
     * The function filters a list of camps by a user-selected location.
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
     * The function filters a list of camps by a specified staff in charge (SIC).
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
     * The function sets the value of the unfilteredCamps variable to the provided
     * ArrayList of Camp
     * objects.
     * 
     * @param camps The "camps" parameter is an ArrayList of objects of type Camp.
     */
    public void setUnfilteredCamps(ArrayList<Camp> camps) {
        unfilteredCamps = camps;
    }

    /**
     * The function clears the contents of the "filteredCamps" list.
     */
    public void clearFilteredCamps() {
        filteredCamps.clear();
    }
}
