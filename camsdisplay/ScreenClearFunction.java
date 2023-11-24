package camsdisplay;

import java.util.Scanner;

/**
 * This interface is implemented by classes which uses the screen clear function
 * 
 * @author Tan Ying Hao
 * @version 1.0
 */
public interface ScreenClearFunction {
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
