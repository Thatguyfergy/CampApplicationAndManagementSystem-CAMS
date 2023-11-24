package camsdisplay;

import users.Users;

/**
 * The CampsRegisterable interface is implemented by classes which can register for camps
 * 
 * @author Enric Tan
 * @version 1.0
 */
public interface CampsRegisterable {
    /**
     * The function "registerCampsScreen" takes a parameter of type "Users" and does
     * not return anything.
     * 
     * @param user The user object represents the user who is accessing the display
     *             function
     */
    public void registerCampsScreen(Users user);
}
