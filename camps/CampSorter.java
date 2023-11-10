package camps;

import java.util.ArrayList;
import java.util.Scanner;

public class CampSorter {
    private static String sortBy;

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

    public static void sortCamps(ArrayList<Camp> camps) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sort by: ");
        System.out.println("1. Camp Name");
        System.out.println("2. Registration Closing Date");
        System.out.println("3. Camp Availability");
        System.out.println("4. Location");
        System.out.println("5. Total Slots");
        System.out.println("6. Committee Members Slots");
        System.out.println("7. Staff in Charge");
        System.out.println("8. No sort");
        System.out.printf("Enter choice: ");
        String choice = sc.nextLine();
        // Clear the lines printed by this function
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
            case "8":
                return;
            default:
                System.out.println("Invalid choice! - Applying no sort");
                return;
        }

        // Clear the entire line
        insertionSort(camps, sortBy);

    }
}
