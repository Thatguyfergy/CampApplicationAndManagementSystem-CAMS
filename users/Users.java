package users;

public abstract class Users implements Comparable<Users>{
    private String userID;
    private String password = "password";
    private String facultyInfo;

    public Users(String userID, String facultyInfo) {
        this.userID = userID;
        this.facultyInfo = facultyInfo;
    }

    protected void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getID() {
        return userID;
    }

    protected Boolean checkPassword(String password) {
        return password == this.password;
    }

    public String getFacultyInfo() {
        return facultyInfo;
    }
}
