package users;

/**
 * The Users class is an abstract class that implements the Comparable
 * interface. Base user class for Staff and Student.
 * 
 * @author Kok Chin Yi
 * @version 1.0
 */
public abstract class Users implements Comparable<Users> {
    private String FirstName;
    private String userID;
    private String password = "password";
    private String facultyInfo;
    public Object users;

    public Users(String FirstName, String userID, String facultyInfo) {
        this.FirstName = FirstName;
        this.userID = userID;
        this.facultyInfo = facultyInfo;
    }

    /**
     * The function sets a new password for an object.
     * 
     * @param newPassword The new password that will be set for the object.
     */
    protected void setPassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * The function returns the password as a string.
     * 
     * @return The password is being returned.
     */
    protected String getPassword() {
        return this.password;
    }

    /**
     * The function returns the userID.
     * 
     * @return The method is returning the value of the variable "userID".
     */
    public String getID() {
        return userID;
    }

    /**
     * The function returns the value of the FirstName variable.
     * 
     * @return The method is returning the value of the variable "FirstName".
     */
    public String getFirstName() {
        return FirstName;
    }

    /**
     * The function checks if a given password matches the stored password.
     * 
     * @param password The password parameter is a String that represents the
     *                 password to be checked.
     * @return The method is returning a Boolean value, which indicates whether the
     *         provided password
     *         matches the stored password.
     */
    protected Boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    /**
     * The function returns the faculty information as a string.
     * 
     * @return The method is returning a String value.
     */
    public String getFacultyInfo() {
        return facultyInfo;
    }

    /**
     * The compareTo function compares the IDs of two Users objects.
     * 
     * @param other The "other" parameter is an object of the "Users" class.
     * @return The method is returning the result of comparing the IDs of two Users
     *         objects.
     */
    public int compareTo(Users other) {
        return this.getID().compareTo(other.getID());
    }

    /**
     * The function compares two Users objects based on the specified sorting
     * criteria.
     * 
     * @param other  The "other" parameter is an instance of the Users class that we
     *               are comparing the
     *               current instance to.
     * @param sortBy A string indicating the field by which the users should be
     *               sorted. It can be
     *               "UserID", "FirstName", or "FacultyInfo".
     * @return The method is returning an integer value.
     */
    public int compareTo(Users other, String sortBy) {
        if (sortBy.equals("UserID")) {
            return this.getID().compareTo(other.getID());
        } else if (sortBy.equals("FirstName")) {
            return this.getFirstName().compareTo(other.getFirstName());
        } else if (sortBy.equals("FacultyInfo")) {
            return this.getFacultyInfo().compareTo(other.getFacultyInfo());
        } else
            return 0;
    }
}
