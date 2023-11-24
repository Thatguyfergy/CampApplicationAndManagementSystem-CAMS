package camsdisplay;

import users.Users;

/**
 * The code is declaring a Java interface named `EnquiriesScreen`. The
 * interface defines the abstract methods `viewEnquiriesScreen`,
 * `replyEnquiriesScreen`, and `generateEnquiriesReportScreen`.
 * 
 * @author Enric Tan
 * @version 1.0
 */
public interface EnquiriesScreen {
    /**
     * The function "viewEnquiriesScreen" takes a parameter of type "Users" and does
     * not return anything
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who is accessing the display function
     */
    public void viewEnquiriesScreen(Users user);

    /**
     * The function "replyEnquiriesScreen" takes a parameter of type "Users" and
     * does not return anything
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who is accessing the display function
     */
    public void replyEnquiriesScreen(Users user);

    /**
     * The function "generateEnquiriesReportScreen" takes a parameter of type
     * "Users" and does not return anything
     * 
     * @param user The user parameter is an object of the Users class.It represents
     *             the user who is accessing the display function
     */
    public void generateEnquiriesReportScreen(Users user);
}
