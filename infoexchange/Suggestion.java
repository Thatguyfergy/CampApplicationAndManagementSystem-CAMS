package infoexchange;

import users.*;
import camps.*;

/**
 * Suggestion class is used to create suggestions for a camp
 * Only a student who is a camp committee member can submit suggestions
 * 
 * @author Tan Jun Kiat
 * @version 1.0
 */
public class Suggestion implements Comparable<Suggestion> {
    /**
     * stores the actual suggestion text
     */
    private String suggestion;
    /**
     * stores the student object that gave this suggestion
     * this student must be a camp committee member
     */
    private Student student;
    /**
     * stores the camp object that this suggestion is for
     */
    private Camp camp;
    /**
     * whether or not this suggestion is submitted by student
     */
    private boolean submitted = false;
    /**
     * whether or not this suggestion has been processed by a staff
     * default value is false this when suggestion is not submitted yet
     */
    private boolean processed = false;
    /**
     * whether this suggestion was accepted or rejected
     * default value is false when this suggestion is not processed yet
     */
    private boolean accepted = false;
    /**
     * stores the unique ID tied to this suggestion
     */
    private int suggestionID;
    /**
     * a static counter to generate unique ID for suggestions
     */
    private static int counter = 1;

    /**
     * Constructor for suggestion object when suggestion is created when program runs
     * suggestionID is automatically assigned current value of counter
     * 
     * @param s     a string representing a "suggestion"
     * @param std   the student that gave this suggestion
     * @param cmp   the camp that the suggestion is for
     */
    public Suggestion(String s, Student std, Camp cmp) {
        suggestion = s;
        student = std;
        camp = cmp;
        suggestionID = counter++;
    }
    
    /**
     * Constructor for suggestion object when suggestion is loaded into program from stored data
     * suggestionID is read from stored data instead
     * 
     * @param s     a string representing a "suggestion"
     * @param std   the student that gave this suggestion
     * @param cmp   the camp that this suggestion is for
     * @param sub   string representing if this suggestion is submitted ("1") or not ("0")
     * @param pro   string representing if this suggestion is processed ("1") or not ("0")
     * @param acc   string representing if this suggestion is accepted ("1") or not ("0")
     * @param id    unique ID for this suggestion
     * @param count current counter value for assigning unique ID
     */
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
     * The method for returning a suggestion as a string.
     * 
     * @return suggestion text as a string
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * This method modifies a suggestion if it has not been processed.
     * 
     * @param s     new suggestion string
     * @return      true if modification is successful (if not processed yet), 
     *              false if suggestion is not allowed to be modified
     */
    public boolean setSuggestion(String s) {
        if (processed)
            return false;

        suggestion = s;
        return true;
    }

    /**
     * This method returns the suggestion ID for this suggestion.
     * 
     * @return unique suggestionID for this suggestion
     */
    public int getID() {
        return suggestionID;
    }

    /**
     * This method returns a boolean value indicating whether this suggestion
     * has been submitted.
     * 
     * @return true if this suggestion is submitted, false if not submitted
     */
    public boolean isSubmitted() {
        return submitted;
    }

    /**
     * This method submits this suggestion by setting submitted to true, 
     * if not yet submitted, 
     * and adds one point to the student's points if successfully submitted.
     * 
     * @return true if suggestion is successfully submitted, 
     *          false if suggestion is already submitted
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
     * This method returns a boolean value indicating whether this suggestion
     * has been processed.
     * 
     * @return true if this suggestion has been processed, false if not processed
     */
    public boolean isProcessed() {
        return processed;
    }

    /**
     * This method processes this suggestion by setting processed to true, 
     * if it is submitted but not yet processed, 
     * and changes accepted field based on whether this suggestion is to be accepted.
     * Adds one point to the student if this suggestion is accepted.
     * 
     * @param accept    boolean value indicating if this suggestion should be accepted
     * @return          true if the suggestion was processed or already processed, 
     *                  false if suggestion has not been submitted yet
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
     * This method returns a boolean value indicating whether this suggestion
     * has been accepted.
     * 
     * @return true if this suggestion has been accepted, false if rejected (or not yet processed)
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * This method returns the ID of the student who gave this suggestion.
     * 
     * @return string containing unique ID of student
     */
    public String getStudentID() {
        return student.getID();
    }

    /**
     * This method returns the name of the camp this suggestion is for.
     * 
     * @return string containing name of camp
     */
    public String getCampName() {
        return camp.getCampName();
    }

    /**
     * The method returns the value of the counter.
     * Only used for storing suggestion object to external file.
     * 
     * @return counter value
     */
    public int getCount() {
        return counter;
    }

    /**
     * This is an implementation of the compareTo method from the comparable interface.
     * This method is kept for future development, but not yet properly implemented.
     * 
     * @param o suggestion object to compare with this suggestion
     * @return integer representing result of comparison
     */
    @Override
    public int compareTo(Suggestion o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
