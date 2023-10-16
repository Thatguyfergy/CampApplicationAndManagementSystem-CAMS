package cams;

public abstract class Users {
    private String userID;
    private String password = "password";
    private String facultyInfo;

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
