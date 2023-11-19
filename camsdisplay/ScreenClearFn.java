package camsdisplay;

import java.util.Scanner;

// The code is declaring a public interface named `ScreenClearFn`.
public interface ScreenClearFn {
    Scanner sc = new Scanner(System.in);

    /**
     * The function clears the console screen and waits for the user to press Enter
     * before returning to the menu.
     */
    default void ScreenClearFn() {
        System.out.printf("\nPress Enter to return to menu.");
        sc.nextLine();
        // String temp = sc.nextLine();
        System.out.print("\033[H\033[2J");
    }
}
