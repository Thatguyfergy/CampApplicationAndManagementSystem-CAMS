package camsdisplay;

import java.util.Scanner;

public interface ScreenClearFn {
    Scanner sc = new Scanner(System.in);

    default void ScreenClearFn() {
        System.out.printf("\nPress Enter to return to menu.");
        sc.nextLine();
        // String temp = sc.nextLine();
        System.out.print("\033[H\033[2J");
    }
}
