package users;

/**
 * The CampAttendeeRole class represents the role of an attendee at a camp.
 * It is used to store the relevant camp related attributes and methods.
 * More functionality can be added in the future for a student who is an attendee.
 * 
 * @author Enric Tan
 * @version 1.0
 */
public class CampAttendeeRole {

    private String CampAttending;
    private Student StudentAttending;

    /**
     * The code below is a constructor for the
     * `CampAttendeeRole` class. It takes two parameters: `camp` and `student`.
     * 
     * @param camp
     * @param student
     */
    public CampAttendeeRole(String camp, Student student) {
        CampAttending = camp;
        StudentAttending = student;
    }

    /**
     * The function returns the value of the CampAttending variable.
     * 
     * @return The value of the variable "CampAttending".
     */
    public String getCampAttending() {
        return CampAttending;
    }

    /**
     * The function modifies the value of the CampAttending variable.
     * 
     * @param newName The modified value that will be assigned to the CampAttending
     *                variable.
     */
    public void setCampAttending(String newName) {
        CampAttending = newName;

    }

}