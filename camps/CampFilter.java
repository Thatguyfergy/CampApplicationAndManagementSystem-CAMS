package camps;

import java.util.ArrayList;
import java.util.Scanner;

import camdate.CAMDate;

public class CampFilter {

    private ArrayList<String> availableDates;
    private ArrayList<String> availableFaculties;
    private ArrayList<String> availableLocations;
    private ArrayList<String> availableSIC;
    private CampArray filteredCamps;
    private CampArray camps;
    private Scanner sc;

    public CampFilter(CampArray camps) {
        availableDates = new ArrayList<String>();
        availableFaculties = new ArrayList<String>();
        availableLocations = new ArrayList<String>();
        availableSIC = new ArrayList<String>();
        filteredCamps = new CampArray();
        this.camps = camps;
        Scanner sc = new Scanner(System.in);
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

    private CampArray filterByDate() {
        if (availableDates.isEmpty()) {
            return filteredCamps;
        }
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
        for (Camp camp : camps.getCamps()) {
            if (availableDates.contains(date)) {
                filteredCamps.addCamp(camp);
            }
        }
        return filteredCamps;
    }

    private CampArray filterByFaculty() {
        if (availableFaculties.isEmpty()) {
            return filteredCamps;
        }
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

        for (Camp camp : camps.getCamps()) {
            if (camp.getCampAvailability().equals(faculty)) {
                filteredCamps.addCamp(camp);
            }
        }
        return filteredCamps;
    }
}
