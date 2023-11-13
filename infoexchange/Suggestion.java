package infoexchange;

import users.*;
import camps.*;

public class Suggestion implements Comparable<Suggestion> {
    private String suggestion;
    private Student student;
    private Camp camp;
    private boolean submitted = false;
    private boolean processed = false;
    private boolean accepted = false;
    private int suggestionID;
    private static int counter=1;

    public Suggestion(String s, Student std, Camp cmp) {
        suggestion = s;
        student = std;
        camp = cmp;
        suggestionID = counter++;
    }

    public Suggestion(String s, Student std, Camp cmp, String sub, String pro, String acc, String id, String count) {
        // for use when loading from DB
        suggestion = s;
        student = std;
        camp = cmp;
        submitted = (sub.equals("1")) ? true : false;
        processed = (pro.equals("1")) ? true : false;
        accepted = (acc.equals("1")) ? true : false;
        suggestionID = Integer.parseInt(id);
        counter = Integer.parseInt(count);
    }

    public String getSuggestion() {
        return suggestion;
    }

    public boolean setSuggestion(String s) {
        if (processed)
            return false;

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
            submitted = true;
            student.getCampCommitteeRole().addOnePoint();
            return true;
        } else
            return false;
    }

    public boolean isProcessed() {
        return processed;
    }

    public boolean process(boolean accept) {
        if ((submitted) && (!processed)) {
            processed = true;
            accepted = accept;
            if (accept)
                student.getCampCommitteeRole().addOnePoint();
        }
        return submitted;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getStudentID() {
        return student.getID();
    }

    public String getCampName() {
        return camp.getCampName();
    }

    public int getCount() {
        return counter;
    }

    @Override
    public int compareTo(Suggestion o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
