package camsdisplay;

import users.Users;

/**
 * This interface is implemented by classes which can view camps
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
