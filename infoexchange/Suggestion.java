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
    private static int counter = 1;

    // The `public Suggestion(String s, Student std, Camp cmp)` constructor is
    // creating a new
    // `Suggestion` object with the given parameters.
    public Suggestion(String s, Student std, Camp cmp) {
        suggestion = s;
        student = std;
        camp = cmp;
        suggestionID = counter++;
    }

    // The `public Suggestion(String s, Student std, Camp cmp, String sub, String
    // pro, String acc,
    // String id, String count)` constructor is used when loading a `Suggestion`
    // object from a
    // database. It takes in several parameters: `s` (the suggestion text), `std`
    // (the `Student` object
    // associated with the suggestion), `cmp` (the `Camp` object associated with the
    // suggestion), `sub`
    // (a string representing whether the suggestion has been submitted or not),
    // `pro` (a string
    // representing whether the suggestion has been processed or not), `acc` (a
    // string representing
    // whether the suggestion has been accepted or not), `id` (a string representing
    // the suggestion
    // ID), and `count` (a string representing the counter value).
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

    /**
     * The getSuggestion() function returns a suggestion as a string.
     * 
     * @return The method is returning a String value.
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * The function sets a suggestion if the processing is not yet done and returns
     * true, otherwise it
     * returns false.
     * 
     * @param s The parameter "s" is a String that represents the suggestion to be
     *          set.
     * @return The method is returning a boolean value. If the "processed" variable
     *         is true, the method
     *         will return false. Otherwise, it will set the "suggestion" variable
     *         to the value of the
     *         parameter "s" and return true.
     */
    public boolean setSuggestion(String s) {
        if (processed)
            return false;

        suggestion = s;
        return true;
    }

    /**
     * The function returns the suggestion ID.
     * 
     * @return The method is returning the value of the variable suggestionID.
     */
    public int getID() {
        return suggestionID;
    }

    /**
     * The function returns a boolean value indicating whether a form has been
     * submitted.
     * 
     * @return The method is returning a boolean value, which indicates whether the
     *         submission is true
     *         or false.
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * The submit function checks if a submission has already been made, and if not,
     * marks it as
     * submitted and adds one point to the student's camp committee role.
     * 
     * @return The method is returning a boolean value. If the condition
     *         `!submitted` is true, it will
     *         return `true`. Otherwise, it will return `false`.
     */
    public boolean submit() {
        if (!submitted) {
            submitted = true;
            student.getCampCommitteeRole().addOnePoint();
            return true;
        } else
            return false;
    }

    /**
     * The function returns a boolean value indicating whether the object has been
     * processed.
     * 
     * @return The method is returning a boolean value, specifically the value of
     *         the variable
     *         "processed".
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * The function processes a submission and updates the processed and accepted
     * status, and adds one
     * point to the student's camp committee role if the submission is accepted.
     * 
     * @param accept A boolean value indicating whether the submission should be
     *               accepted or not.
     * @return The method is returning the value of the variable "submitted".
     */
    public boolean process(boolean accept) {
        if ((submitted) && (!processed)) {
            processed = true;
            accepted = accept;
            if (accept)
                student.getCampCommitteeRole().addOnePoint();
        }
        return submitted;
    }

    /**
     * The function returns a boolean value indicating whether something is accepted
     * or not.
     * 
     * @return The method is returning a boolean value.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * The function returns the ID of a student.
     * 
     * @return The method is returning the ID of a student.
     */
    public String getStudentID() {
        return student.getID();
    }

    /**
     * The function returns the name of a camp.
     * 
     * @return The method is returning the camp name.
     */
    public String getCampName() {
        return camp.getCampName();
    }

    /**
     * The function returns the value of the counter variable.
     * 
     * @return The method is returning the value of the variable "counter".
     */
    public int getCount() {
        return counter;
    }

    /**
     * The function is an implementation of the compareTo method, but it throws an
     * UnsupportedOperationException.
     * 
     * @param o The parameter 'o' is an object of type 'Suggestion'.
     * @return In this code snippet, an UnsupportedOperationException is being
     *         thrown.
     */
    @Override
    public int compareTo(Suggestion o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
