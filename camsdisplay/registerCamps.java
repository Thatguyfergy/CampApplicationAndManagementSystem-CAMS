package camsdisplay;

import users.Users;

// The line `public interface registerCamps` is declaring an interface named `registerCamps`. An
// interface in Java is a collection of abstract methods that can be implemented by classes. In this
// case, the `registerCamps` interface is defining a single method `registerCampsScreen` that takes a
// parameter of type `Users` and does not return anything.
public interface registerCamps {
    /**
     * The function "registerCampsScreen" takes a parameter of type "Users" and does
     * not return anything.
     * 
     * @param user The user object represents the user who wants to register for
     *             camps.
     */
    public void registerCampsScreen(Users user);
}
