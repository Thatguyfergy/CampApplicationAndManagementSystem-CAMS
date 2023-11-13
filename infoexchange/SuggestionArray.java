package infoexchange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        updateFile();
    }

    // Method Overloading
    public boolean viewSuggestions(Staff staff) {
        // Staff sees all suggestions he/she is incharge of only
        boolean x=false;
        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCampName())) && (suggestion.isSubmitted())) {
                x=true;
                System.out.printf("Suggestion %06d by %s - Camp : %s", suggestion.getID(),
                        suggestion.getStudentID(), suggestion.getCampName());
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
        if (!x) System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else    System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");

        return x;
    }

    // Method Overloading
    public boolean viewSuggestions(Student std) {
        // Student sees all suggestions created by them and not processed
        boolean x=false;
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (!suggestion.isProcessed())) {
                printSuggestion(suggestion);
                x=true;
            }
        }
        if (!x)
            System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else 
            System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");

        return x;
    }

    public void viewProcessedSuggestions(Student std) {
        boolean x=false;
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.isProcessed())) {
                printSuggestion(suggestion);
                x=true;
            }
        }
        if (!x)
            System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else 
            System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");
    }

    public void editSuggestion(Student std, int i, String s) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.getID() == i)) {
                if (suggestion.setSuggestion(s)) {
                    System.out.println("Suggestion successfully edited!");
                    updateFile();
                }
                else
                    System.out.println("Suggestion cannot be edited after being processed!");

                printSuggestion(suggestion);
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
        return;
    }

    public void submitSuggestion(Student std, int i) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.getID() == i)) {
                if (suggestion.submit()) {
                    System.out.println("Suggestion successfully submitted!");
                    updateFile();
                }
                else
                    System.out.println("Suggestion already submtitted!");

                printSuggestion(suggestion);

                System.out.println("Your Points: " + std.getCampCommitteeRole().getPoints());
                System.out.println();
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
        return;
    }

    public void processSuggestion(Staff staff) {
        if (!viewSuggestions(staff)) {
            System.out.println("No Suggestions to process!");
            return;
        }
        System.out.println("Which Suggestion do you want to process? ");
        int id = inputInt.nextInt(sc);

        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCampName())) && (suggestion.getID() == id)
                    && (suggestion.isSubmitted())) {
                if (suggestion.isProcessed()) {
                    System.out.printf(
                            "Suggestion %06d has already been %s\n", id, (suggestion.isAccepted() ? "Accepted" : "Rejected"));
                } else {
                    int a;
                    do {
                        System.out.println("Do you want to accept (1) or reject (0) this suggestion? ");
                        a = inputInt.nextInt(sc);
                    } while (a != 0 && a != 1);
                    boolean accept = (a == 1) ? true : false;

                    suggestion.process(accept);
                    updateFile();
                    System.out.printf(
                            "Suggestion %06d has been %s\n", id, (suggestion.isAccepted() ? "Accepted" : "Rejected"));
                }
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
        return;
    }

    public int suggestionCanEdit(Student std, int i) {
        for (Suggestion s : suggestions) {
            if ((s.getStudentID().equals(std.getID())) && (s.getID() == i)) {
                if (!s.isProcessed()) {
                    return 1;
                }
                else return 0;
            }
        }
        return -1;
    }

    public void deleteSuggestion(Student std, int i) {
        for (int j=0; j<suggestions.size(); j++) {
            if ((suggestions.get(j).getStudentID().equals(std.getID())) && (suggestions.get(j).getID() == i)) {
                if (suggestions.get(j).isProcessed()) {
                    System.out.println("Suggestion cannot be deleted as it has been processed");
                    printSuggestion(suggestions.get(j));
                }
                else {
                    suggestions.remove(j);
                    System.out.printf("Suggestion %06d has been deleted\n", i);
                    updateFile();
                }
                return;
            }
        }
        System.out.printf("Suggestion %06d does not exist!", i);
        return;
    }

    public void deleteCamp(Camp camp) {
        for (int i=0; i<suggestions.size(); i++) {
            if (suggestions.get(i).getCampName().equals(camp.getCampName())) {
                suggestions.remove(i);
            }
        }
        updateFile();
    }

    private static void printSuggestion(Suggestion suggestion) {
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

    private void updateFile() {
        try (FileWriter csvWriter = new FileWriter(suggestionFile)) {
            for (Suggestion suggestion : suggestions) {
                csvWriter.append(suggestion.getSuggestion());
                csvWriter.append(",");
                csvWriter.append(suggestion.getStudentID());
                csvWriter.append(",");
                csvWriter.append(suggestion.getCampName());
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
