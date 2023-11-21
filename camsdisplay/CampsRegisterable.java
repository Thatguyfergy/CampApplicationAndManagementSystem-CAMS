package camsdisplay;

import users.Users;

// The line `public interface registerCamps` is declaring an interface named `registerCamps`. 
// The interface defines the abstract method `registerCampsScreen` that takes a
// parameter of type `Users` and does not return anything.
public interface CampsRegisterable {
    /**
     * The function "registerCampsScreen" takes a parameter of type "Users" and does
     * not return anything.
     * 
     * @param user The user object represents the user who is accessing the display function
     */
    public void registerCampsScreen(Users user);
}
