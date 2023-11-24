package camsdisplay;

import users.Users;

/**
 * The code is declaring a Java interface named `ViewCampsScreen`. The interface
 * defines the abstract methods `viewCampsScreen` and `viewCampDetailsScreen`.
 * 
 * @author Enric Tan
 * @version 1.0
 */
public interface ViewCampsScreen {
    /**
     * The function "viewCampsScreen" takes a parameter of type "Users" and does not
     * return anything
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who is accessing the display function
     */
    public void viewCampsScreen(Users user);

    /**
     * The function "viewCampDetailsScreen" takes a parameter of type "Users" and
     * does not return anything
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who is accessing the display function
     */
    public void viewCampDetailsScreen(Users user);
}
