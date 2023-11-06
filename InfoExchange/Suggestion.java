package InfoExchange;

import users.*;
import camps.*;

public class Suggestion {
    private String suggestion;
    private Student student;
    private Camp camp;
    private boolean submitted = false;
    private boolean processed = false;
    private boolean accepted = false;
    private int suggestionID;
    private static int counter;

    public Suggestion(String s, Student std, Camp cmp) {
        suggestion = s;
        student = std;
        camp = cmp;
        suggestionID = counter++;
    }

    // set counter when start of program
    public void setIDCounter(int i) {
        counter=i;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public boolean setSuggestion(String s) {
        if (processed) return false;
        
        suggestion = s;
        return true;
    }

    public int getID() {
        return suggestionID;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public boolean submit() {
        if (!submitted) {
            submitted=true;
            return true;
        }
        else return false;
    }

    public boolean canModify() {
        return !processed;
    }

    public boolean process(boolean accept) {
        if ((submitted) && (!processed)) {
            processed=true;
            accepted = accept;
            // add points?
        }
        return submitted;
    }

    public boolean accepted() {
        return accepted;
    }
}
