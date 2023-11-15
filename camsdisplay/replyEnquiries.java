package camsdisplay;

import users.Users;

// The code is declaring a Java interface named `replyEnquiries`. An interface in Java is a collection
// of abstract methods that can be implemented by classes. In this case, the `replyEnquiries` interface
// is defining three abstract methods: `viewEnquiriesScreen`, `replyEnquiriesScreen`, and
// `generateEnquiriesReportScreen`. These methods do not have any implementation details and are meant
// to be implemented by classes that implement this interface.
public interface replyEnquiries {
    /**
     * The function "viewEnquiriesScreen" takes a user as a parameter and displays
     * the enquiries screen.
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who wants to
     *             view the enquiries screen.
     */
    public void viewEnquiriesScreen(Users user);

    /**
     * The function "replyEnquiriesScreen" takes a user object as a parameter and is
     * used to display and
     * reply to user enquiries.
     * 
     * @param user The user parameter is an object of the Users class. It represents
     *             the user who is making
     *             the enquiry.
     */
    public void replyEnquiriesScreen(Users user);

    /**
     * The function generates a report screen for enquiries for a specific user.
     * 
     * @param user The user parameter is an object of the Users class.
     */
    public void generateEnquiriesReportScreen(Users user);
}
