package utils;

import java.util.InputMismatchException;
import java.util.Scanner;

public class inputInt {

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
