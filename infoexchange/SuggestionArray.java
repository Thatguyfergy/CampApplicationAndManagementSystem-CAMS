package infoexchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import users.*;
import camps.*;
import utils.inputInt;

public class SuggestionArray {
    private static ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
    private static String suggestionFile;
    private Scanner sc = new Scanner(System.in);

    public SuggestionArray(String suggestionFile, UsersDatabase users, CampArray campArray) {
        SuggestionArray.suggestionFile = suggestionFile;

        try (BufferedReader csvReader = new BufferedReader(new FileReader(suggestionFile))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                // CSV Format: Suggestion, StudentName, CampName, submitted, processed,
                // accepted, suggestionID, counter
                suggestions.add(new Suggestion(data[0], users.getStudentbyID(data[1]), campArray.getCamp(data[2]),
                        data[3], data[4], data[5], data[6], data[7]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createSuggestion(String s, Student std, Camp cmp) {
        suggestions.add(new Suggestion(s, std, cmp));
    }

    // Method Overloading
    public void viewSuggestions(Staff staff) {
        // Staff sees all suggestions he/she is incharge of only
        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCamp().getCampName())) && (suggestion.isSubmitted())) {
                System.out.printf("Suggestion %06d by %s - Camp : %s", suggestion.getID(),
                        suggestion.getStudent().getID(), suggestion.getCamp().getCampName());
                if (suggestion.isSubmitted()) {
                    System.out.print(" [Submitted");
                    if (suggestion.isProcessed()) {
                        System.out.print(", Processed");
                        if (suggestion.isAccepted())
                            System.out.print(", Accepted]");
                        else
                            System.out.print(", Rejected]");
                    } else
                        System.out.print(", Not Processed]");
                } else
                    System.out.print(" [Not Submitted]");
                System.out.println();
                System.out.println(suggestion.getSuggestion());
                System.out.println();
            }
        }
    }

    // Method Overloading
    public void viewSuggestions(Student std) {
        // Student sees all suggestions created by them
        for (Suggestion suggestion : suggestions) {
            if (suggestion.getStudent().equals(std)) {
                System.out.printf("Suggestion %06d", suggestion.getID());
                if (suggestion.isSubmitted()) {
                    System.out.print(" [Submitted");
                    if (suggestion.isProcessed()) {
                        System.out.print(", Processed");
                        if (suggestion.isAccepted())
                            System.out.print(", Accepted]");
                        else
                            System.out.print(", Rejected]");
                    } else
                        System.out.print(", Not Processed]");
                } else
                    System.out.print(" [Not Submitted]");
                System.out.println();
                System.out.println(suggestion.getSuggestion());
                System.out.println();
            }
        }
    }

    public void editSuggestion(Student std, int i, String s) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudent().equals(std)) && (suggestion.getID() == i)) {
                if (suggestion.setSuggestion(s))
                    System.out.println("Suggestion successfully edited!");
                else
                    System.out.println("Suggestion cannot be edited after being processed!");

                System.out.printf("Suggestion %06d", suggestion.getID());
                if (suggestion.isSubmitted()) {
                    System.out.print(" [Submitted");
                    if (suggestion.isProcessed()) {
                        System.out.print(", Processed");
                        if (suggestion.isAccepted())
                            System.out.print(", Accepted]");
                        else
                            System.out.print(", Rejected]");
                    } else
                        System.out.print(", Not Processed]");
                } else
                    System.out.print(" [Not Submitted]");
                System.out.println();
                System.out.println(suggestion.getSuggestion());
                System.out.println();
                return;
            }
        }
    }

    public void submitSuggestion(Student std, int i) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudent().equals(std)) && (suggestion.getID() == i)) {
                if (suggestion.submit())
                    System.out.println("Suggestion successfully submitted!");
                else
                    System.out.println("Suggestion already submtitted!");

                System.out.printf("Suggestion %06d", suggestion.getID());
                if (suggestion.isSubmitted()) {
                    System.out.print(" [Submitted");
                    if (suggestion.isProcessed()) {
                        System.out.print(", Processed");
                        if (suggestion.isAccepted())
                            System.out.print(", Accepted]");
                        else
                            System.out.print(", Rejected]");
                    } else
                        System.out.print(", Not Processed]");
                } else
                    System.out.print(" [Not Submitted]");
                System.out.println();
                System.out.println(suggestion.getSuggestion());
                System.out.println();

                System.out.println("Your Points: " + std.getCampCommitteeRole().getPoints());
                System.out.println();
                return;
            }
        }
    }

    public void processSuggestion(Staff staff) {
        viewSuggestions(staff);
        System.out.println("Which Suggestion do you want to process? ");
        int id = inputInt.nextInt(sc);
        int a;
        do {
            System.out.println("Do you want to accept (1) or reject (0) this suggestion? ");
            a = inputInt.nextInt(sc);
        } while (a != 0 && a != 1);
        boolean accept = (a == 1) ? true : false;

        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCamp().getCampName())) && (suggestion.getID() == id)
                    && (suggestion.isSubmitted())) {
                if (suggestion.isProcessed()) {
                    System.out.println(
                            "Suggestion has already been " + (suggestion.isAccepted() ? "Accepted" : "Rejected"));
                    return;
                } else {
                    suggestion.process(accept);
                    System.out.println(
                            "Suggestion " + id + " has been " + (suggestion.isAccepted() ? "Accepted" : "Rejected"));
                    return;
                }
            }
        }
        System.out.println("Suggestion does not exist!");
        return;
    }

    public void updateFile() {
        try (FileWriter csvWriter = new FileWriter(suggestionFile)) {
            for (Suggestion suggestion : suggestions) {
                csvWriter.append(suggestion.getSuggestion());
                csvWriter.append(",");
                csvWriter.append(suggestion.getStudent().getID());
                csvWriter.append(",");
                csvWriter.append(suggestion.getCamp().getCampName());
                csvWriter.append(",");
                csvWriter.append(suggestion.isSubmitted() ? "1" : "0");
                csvWriter.append(",");
                csvWriter.append(suggestion.isProcessed() ? "1" : "0");
                csvWriter.append(",");
                csvWriter.append(suggestion.isAccepted() ? "1" : "0");
                csvWriter.append(",");
                csvWriter.append(suggestion.getID() + "");
                csvWriter.append(",");
                csvWriter.append(suggestion.getCount() + "");
                csvWriter.append("\n");
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
