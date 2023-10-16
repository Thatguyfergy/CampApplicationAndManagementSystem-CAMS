package camps;

import java.util.ArrayList;

public class CampArray {
    ArrayList<Camp> camps = new ArrayList<Camp>();

    public void createCamp(Camp newCamp) {
        camps.add(newCamp);
    }

    public void editCamp(String campName) {
        Camp targetCamp = null;

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                targetCamp = camps.get(i);
                break;
            }
        }
        if (targetCamp == null) {
            System.out.println("Camp not found");
            return;
        }

        // Editscanner stuff - Fugerson
    }

    public void deleteCamp(String campName) {

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                camps.remove(i);
            }
        }
    }

    // Fugerson help - This one quite troublesome as there are different visibility
    // requirements. Need to refer to Student and Staff.
    // For Staff - I wan to filter by staffInCharge. Help me to do this.
    public void viewCamps() {

    }
}
