package users;

public abstract class Users implements Comparable<Users> {
    private String FirstName;
    private String userID;
    private String password = "password";
    private String facultyInfo;

    public Users(String FirstName, String userID, String facultyInfo) {
        this.FirstName = FirstName;
        this.userID = userID;
        this.facultyInfo = facultyInfo;
    }

    protected void setPassword(String newPassword) {
        this.password = newPassword;
    }

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
        return password == this.password;
    }

    public String getFacultyInfo() {
        return facultyInfo;
    }
}
