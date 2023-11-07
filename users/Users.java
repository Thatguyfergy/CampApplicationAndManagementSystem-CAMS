package users;

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

    protected void setPassword(String newPassword) {
        this.password = newPassword;
    }

    // Don't make it public or else it will violate OOP principle
    protected String getPassword() {
        return this.password;
    }

    public String getID() {
        return userID;
    }

    public String getFirstName() {
        return FirstName;
    }

    protected Boolean checkPassword(String password) {
        return password.equals(this.password);
    }

    public String getFacultyInfo() {
        return facultyInfo;
    }

    public int compareTo(Users other) {
        return this.getID().compareTo(other.getID());
    }
    public int compareTo(Users other, String sortBy) {
        if (sortBy.equals("UserID")){
            return this.getID().compareTo(other.getID());
        }
        else if (sortBy.equals("FirstName")){
            return this.getFirstName().compareTo(other.getFirstName());
        }
        else if (sortBy.equals("FacultyInfo")){
            return this.getFacultyInfo().compareTo(other.getFacultyInfo());
        }
        else return 0;
    }
}
