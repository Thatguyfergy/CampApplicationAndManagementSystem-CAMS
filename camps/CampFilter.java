package camps;

import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;

public class CampFilter {

    private ArrayList<String> availableDates;
    private ArrayList<String> availableFaculties;
    private ArrayList<String> availableLocations;
    private ArrayList<String> availableSIC;
    private ArrayList<Camp> filteredCamps;
    private ArrayList<Camp> unfilteredCamps;
    private Scanner sc = new Scanner(System.in);

    public CampFilter() {
        availableDates = new ArrayList<String>();
        availableFaculties = new ArrayList<String>();
        availableLocations = new ArrayList<String>();
        availableSIC = new ArrayList<String>();
        filteredCamps = new ArrayList<Camp>();
        unfilteredCamps = new ArrayList<Camp>();
    }

    public boolean addAvailableDate(String date) {
        if (!availableDates.contains(date)) {
            return availableDates.add(date);
        }
        return false; // Return false if the date is already in the list
    }

    public boolean addAvailableFaculty(String faculty) {
        if (!availableFaculties.contains(faculty)) {
            return availableFaculties.add(faculty);
        }
        return false; // Return false if the faculty is already in the list
    }

    public boolean addAvailableLocation(String location) {
        if (!availableLocations.contains(location)) {
            return availableLocations.add(location);
        }
        return false; // Return false if the location is already in the list
    }

    public boolean addAvailableSIC(String sic) {
        if (!availableSIC.contains(sic)) {
            return availableSIC.add(sic);
        }
        return false; // Return false if the sic is already in the list
    }

    public ArrayList<Camp> filter() {
        System.out.println("Filter by: ");
        System.out.println("1. Date");
        System.out.println("2. Faculty");
        System.out.println("3. Location");
        System.out.println("4. Staff in charge");
        System.out.println("5. No filter");
        System.out.printf("Enter choice: ");
        String choice = sc.nextLine();
        switch (choice) {
            case "1":
                filterByDate();
                break;
            case "2":
                filterByFaculty();
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

    private void filterByFaculty() {
        String faculty;
        System.out.println("Enter faculty to filter by: ");
        for (int i = 0; i < availableFaculties.size(); i++) {
            System.out.println(i + 1 + ". " + availableFaculties.get(i));
        }
        while (true) {
            int choice = sc.nextInt();
            if (choice > 0 && choice <= availableFaculties.size()) {
                faculty = availableFaculties.get(choice - 1);
                break;
            } else {
                System.out.println("Invalid choice. Please enter a number between 1 and " + availableFaculties.size());
            }
        }
        for (Camp camp : unfilteredCamps) {
            if (camp.getCampAvailability().equals(faculty)) {
                filteredCamps.add(camp);
            }
        }
    }

    private void filterByLocation() {
        String location;
        System.out.println("Enter location to filter by: ");
        for (int i = 0; i < availableLocations.size(); i++) {
            System.out.println(i + 1 + ". " + availableLocations.get(i));
        }
        while (true) {
            int choice = sc.nextInt();
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

    private void filterBySIC() {

        String sic;
        System.out.println("Enter staff in charge to filter by: ");
        for (int i = 0; i < availableSIC.size(); i++) {
            System.out.println(i + 1 + ". " + availableSIC.get(i));
        }
        while (true) {
            int choice = sc.nextInt();
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

    public void setUnfilteredCamps(ArrayList<Camp> camps) {
        unfilteredCamps = camps;
    }

    public void clearFilteredCamps() {
        filteredCamps.clear();
    }
}
