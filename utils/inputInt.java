package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class inputInt {

    /**
     * The function nextInt takes a Scanner object as input and repeatedly prompts
     * the user for an
     * integer until a valid input is provided.
     * 
     * @param scanner The scanner parameter is an instance of the Scanner class,
     *                which is used to read
     *                input from the user.
     * @return The method is returning an integer value.
     */
    public static int nextInt(Scanner scanner) {
        int value;
        while (true) {
            try {
                value = scanner.nextInt();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                System.out.println("Input again:");
                scanner.nextLine();
            }
        }
    }
}
