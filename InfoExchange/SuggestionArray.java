package InfoExchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import users.*;
import utils.inputInt;

public class SuggestionArray {
    private static ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();
    private static String suggestionFile;

    public SuggestionArray(String suggestionFile) {
        SuggestionArray.suggestionFile = suggestionFile;

    }

    public void createSuggestion(Suggestion suggestion) {
        suggestions.add(suggestion);
    }

    // For Staff to view thr ALL the suggestions that he/she is incharge of
    // Make sure to filter out those that the staff r not incharge of
    public void viewSuggestions(Staff staff) {
        //

    }

    // The Staff will accept the suggestion
    // Make sure the student score will be incremented
    public void acceptSuggestion(Staff staff, int SuggestionIndex) {

    }

    public void updateFile(ArrayList<?> Array) {

    }
}
