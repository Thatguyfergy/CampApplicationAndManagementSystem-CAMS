package InfoExchange;

public class Suggestion {
    private String suggestion;
    private boolean submitted = false;
    private boolean processed = false;
    private int suggestionID;
    private static int counter=1;

    public Suggestion(String s) {
        suggestion = s;
        suggestionID = counter++;
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

    public boolean process() {
        if (submitted) processed=true;
        return submitted;
    }
}
