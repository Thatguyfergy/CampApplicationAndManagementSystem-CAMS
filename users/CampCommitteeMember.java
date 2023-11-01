package users;

import camps.Camp;

public class CampCommitteeMember extends Student {
    private String campName;

    public CampCommitteeMember(String userID, String facultyInfo) {
        super(userID, facultyInfo);
    }

    public String getCampName() {
        return campName;
    }
}
