package camps;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The CampSorter class is used for sorting a list of camps.
 * Sort by various params such as campName or campAvailability.
 * 
 * @author Tan Ying Hao
 * @author Ferguson Chiew
 * @version 1.0
 */
public class CampSorter {
    private static String sortBy;

    /**
     * The method 'insertionSort' sorts the ArrayList of Camp objects based on the
     * "sortBy" parameter.
     * 
     * @param camps  An ArrayList of Camp objects that needs to be sorted.
     * @param sortBy The "sortBy" parameter is a String that specifies the attribute
     *               by which the "camps" ArrayList should be sorted.
     */
    private static void insertionSort(ArrayList<Camp> camps, String sortBy) {
        for (int i = 1; i < camps.size(); i++) {
            Camp key = camps.get(i);
            int j = i - 1;
            while (j >= 0 && camps.get(j).compareTo(key, sortBy) > 0) {
                camps.set(j + 1, camps.get(j));
                j--;
            }
            camps.set(j + 1, key);
        }
    }

    /**
     * The method 'sortCamps' sorts the ArrayList of Camp objects based on the
     * user's choice which sets the 'sortby' parameter of insertionSort method.
     * The user is prompted to enter a choice for the attribute by which the
     * ArrayList
     * should be sorted.
     * The attributes includes: Camp Name, Registration Closing Date,
     * Camp Availability, Location, Total Slots, Committee Members Slots,
     * Staff in Charge.
     * If the user enters an invalid choice, the ArrayList will be sorted by the
     * default attribute, which is Camp Name.
     * 
     * @param camps An ArrayList of Camp objects.
     */
    public static void sortCamps(ArrayList<Camp> camps) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sort by: ");
        System.out.println("1. Camp Name (Default))");
        System.out.println("2. Registration Closing Date");
        System.out.println("3. Camp Availability");
        System.out.println("4. Location");
        System.out.println("5. Total Slots");
        System.out.println("6. Committee Members Slots");
        System.out.println("7. Staff in Charge");
        System.out.printf("Enter choice: ");
        String choice = sc.nextLine();

        insertionSort(camps, "campName");
        switch (choice) {
            case "1":
                sortBy = "campName";
                break;
            case "2":
                sortBy = "registrationClosingDate";
                break;
            case "3":
                sortBy = "campAvailability";
                break;
            case "4":
                sortBy = "location";
                break;
            case "5":
                sortBy = "totalSlots";
                break;
            case "6":
                sortBy = "committeeMembersSlots";
                break;
            case "7":
                sortBy = "staffInCharge";
                break;
            default:
                System.out.println("Invalid choice! - Applying no sort");
                return;
        }

        insertionSort(camps, sortBy);
    }

}
