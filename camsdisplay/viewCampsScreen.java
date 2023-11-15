package camsdisplay;

import users.Users;

// The code is declaring a Java interface named `viewCampsScreen`. An interface in Java is a collection
// of abstract methods that can be implemented by classes. In this case, the `viewCampsScreen`
// interface is defining two abstract methods: `viewCampsScreen` and `viewCampDetailsScreen`. These
// methods do not have any implementation details and are meant to be implemented by classes that
// implement this interface.
public interface viewCampsScreen {
    /**
     * This function is used to display the camps screen for a specific user.
     * 
     * @param user The user object represents the user who wants to view the camps
     *             screen.
     */
    public void viewCampsScreen(Users user);

    /**
     * The function "viewCampDetailsScreen" takes a user object as a parameter and
     * is used to display the
     * details of a camp.
     * 
     * @param user The user object represents the user who wants to view the camp
     *             details screen.
     */
    public void viewCampDetailsScreen(Users user);
}
