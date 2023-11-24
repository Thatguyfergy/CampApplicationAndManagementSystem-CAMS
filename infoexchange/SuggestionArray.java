package infoexchange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import users.*;
import camps.*;
import utils.InputInt;

/**
 * SuggestionArray class is used to store all suggestions in program
 * Only 1 instance of SuggestionArray is created when program starts, 
 * by loading suggestions stored in external file
 * 
 * @author Tan Jun Kiat
 * @version 1.0
 */
public class SuggestionArray {
    /**
     * stores Suggestion objects in an ArrayList
     */
    private static ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
    /**
     * stores the static external file name that suggestions are stored in 
     */
    private static String suggestionFile;
    /**
     * stores the scanner object used for getting user input
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Constructor for loading array of suggestions into program from stored external data
     * Invokes special constructor for suggestion object used only when 
     * loading data from external file
     * 
     * @param suggestionFile    string containing name of file that suggestions are stored in
     * @param users             usersDatabase object that contains student references 
     *                          to pass into Suggestion constructor
     * @param campArray         campArray object that contains camp references
     *                          to pass into Suggestion constructor
     */
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

    /**
     * This method creates a suggestion object and adds it to the array of suggestions
     * in this suggestionArray
     * Updates external file whenever a new suggestion is created.
     * 
     * @param s     a string representing a suggestion
     * @param std   student that gave the suggestion
     * @param cmp   camp that the suggestion is for
     */
    public void createSuggestion(String s, Student std, Camp cmp) {
        suggestions.add(new Suggestion(s, std, cmp));
        updateFile();
    }

    /**
     * Overloaded method for a staff to view suggestions details for camp
     * that they are in charge of.
     * 
     * @param staff     staff that wants to view suggestions
     * @return          true if there are suggestions for the staff, 
     *                  false if no such suggestion exists
     */
    public boolean viewSuggestions(Staff staff) {
        // Staff sees all suggestions he/she is incharge of only
        boolean x = false;
        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCampName())) && (suggestion.isSubmitted())) {
                x = true;
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
        if (!x)
            System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else
            System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");

        return x;
    }

    /**
     * Overloaded method for student to view suggestions that they have made.
     * This function only displays suggestions which have not been processed yet.
     * 
     * @param std   student that wants to view suggestions
     * @return      true if there are unprocessed suggestions made by the student,
     *              false if no such suggestion exists
     */
    public boolean viewSuggestions(Student std) {
        // Student sees all suggestions created by them and not processed
        boolean x = false;
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (!suggestion.isProcessed())) {
                printSuggestion(suggestion);
                x = true;
            }
        }
        if (!x)
            System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else
            System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");

        return x;
    }

    /**
     * This method displays all processed suggestions created by a student.
     * 
     * @param std student that wants to view processed suggestions
     */
    public void viewProcessedSuggestions(Student std) {
        boolean x = false;
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.isProcessed())) {
                printSuggestion(suggestion);
                x = true;
            }
        }
        if (!x)
            System.out.println("═════════════════════════No Suggestions Found!══════════════════════════\n");
        else
            System.out.println("═══════════════════════════End of Suggestions═══════════════════════════\n");
    }

    /**
     * This method checks if the suggestion that a student wants to edit exists, 
     * and checks if editing is allowed.
     * Calls the setSuggestion method of the suggestion object if allowed.
     * Prints the suggestion if the suggestion exists.
     * Updates suggestion file if suggestion was edited.
     * 
     * @param std student who wants to edit the suggestion
     * @param i   unique ID of the suggestion to be edited
     * @param s   string containing suggestion to be edited to
     */
    public void editSuggestion(Student std, int i, String s) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.getID() == i)) {
                if (suggestion.setSuggestion(s)) {
                    System.out.println("Suggestion successfully edited!");
                    updateFile();
                } else
                    System.out.println("Suggestion cannot be edited after being processed!");

                printSuggestion(suggestion);
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
        return;
    }

    /**
     * This method checks if the suggestion that a student wants to submit exists, 
     * and attempts to submit the suggestion.
     * Prints the suggestion and the points of the student if suggestion exists.
     * Updates the suggestion file if submission is successful.
     * 
     * @param std student who wants to submit the suggestion
     * @param i   unique ID of the suggestion to be submitted
     */
    public void submitSuggestion(Student std, int i) {
        for (Suggestion suggestion : suggestions) {
            if ((suggestion.getStudentID().equals(std.getID())) && (suggestion.getID() == i)) {
                if (suggestion.submit()) {
                    System.out.println("Suggestion successfully submitted!");
                    updateFile();
                } else
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

    /**
     * This method prints all suggestions corresponding to a staff,
     * and provides interface for a staff to process suggestions.
     * Updates suggestion file if processing is successful.
     * Prints suggestion after it has been processed.
     * 
     * @param staff staff that wants to process suggestions
     */
    public void processSuggestion(Staff staff) {
        if (!viewSuggestions(staff)) {
            System.out.println("No Suggestions to process!");
            return;
        }
        System.out.println("Which Suggestion do you want to process? ");
        int id = InputInt.nextInt(sc);

        for (Suggestion suggestion : suggestions) {
            if ((staff.checkStaffInCharge(suggestion.getCampName())) && (suggestion.getID() == id)
                    && (suggestion.isSubmitted())) {
                if (suggestion.isProcessed()) {
                    System.out.printf(
                            "Suggestion %06d has already been %s\n", id,
                            (suggestion.isAccepted() ? "Accepted" : "Rejected"));
                } else {
                    int a;
                    do {
                        System.out.println("Do you want to accept (1) or reject (0) this suggestion? ");
                        a = InputInt.nextInt(sc);
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

    /**
     * This method checks if a suggestion exists 
     * and whether a student is allowed to edit it.
     * Suggestion cannot be edited if it is processed.
     * 
     * @param std   student trying to edit the suggestion
     * @param i     unique ID of the suggestion that the student wants to edit
     * @return      1 if can edit, 0 if cannot edit, 
     *              -1 if the suggestion does not exist
     */
    public int suggestionCanEdit(Student std, int i) {
        for (Suggestion s : suggestions) {
            if ((s.getStudentID().equals(std.getID())) && (s.getID() == i)) {
                if (!s.isProcessed()) {
                    return 1;
                } else
                    return 0;
            }
        }
        return -1;
    }

    /**
     * This method checks if a suggestion exists and whether it can be deleted.
     * Deletes a suggestion if it is valid.
     * Updates suggestion file if suggestion is deleted.
     * Prints the corresponding message based on success of deletion.
     * 
     * @param std student that wants to delete suggestion
     * @param i   unique ID of the suggestion to be deleted
     */
    public void deleteSuggestion(Student std, int i) {
        for (int j = 0; j < suggestions.size(); j++) {
            if ((suggestions.get(j).getStudentID().equals(std.getID())) && (suggestions.get(j).getID() == i)) {
                if (suggestions.get(j).isProcessed()) {
                    System.out.println("Suggestion cannot be deleted as it has been processed");
                    printSuggestion(suggestions.get(j));
                } else {
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

    /**
     * This method is called when a camp is deleted.
     * All suggestions for the camp will be deleted.
     * Updates suggestion file after deletion.
     * 
     * @param camp camp that is being deleted
     */
    public void deleteCamp(Camp camp) {
        for (int i = 0; i < suggestions.size(); i++) {
            if (suggestions.get(i).getCampName().equals(camp.getCampName())) {
                suggestions.remove(i);
            }
        }
        updateFile();
    }

    /**
     * Private static method to print details of a single suggestion
     * 
     * @param suggestion suggestion to print
     */
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

    /**
     * Private method called when there are changes to suggestions in the system, 
     * Updates external suggestion storage file.
     */
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
