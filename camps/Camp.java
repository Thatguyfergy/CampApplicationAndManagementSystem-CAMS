package camps;

public class Camp {
    CampInfo campInfo;

    public Camp() {
        // Create campInfo
        CampInfo campInfo = new CampInfo();
    }

    public String getCampName() {
        return campInfo.getCampName();
    }
}