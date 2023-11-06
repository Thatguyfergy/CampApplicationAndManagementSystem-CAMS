package users;

import java.util.ArrayList;
import java.util.List;

import InfoExchange.Suggestion;
import camdate.CAMDate;
import camps.Camp;
import report.CampReport;

public class CampCommitteeRole {
    private Camp camp;
    private int points = 0;
    private ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

    public CampCommitteeRole(Camp camp) {
        this.camp = camp;
    }

    // getters for all camp fields only (no setters)
    public String getCampName() {
        return camp.getCampName();
    }

    // public int getCampID() {
    //     return camp.getcampID();
    // }

    public ArrayList<CAMDate> getCampDates() {
        return camp.getDates();
    }

    public CAMDate getCampRegistrationClosingDate() {
        return camp.getRegistrationClosingDate();
    }

    public String getCampVisibility() {
        return camp.getCampVisibility();
    }

    public String getCampLocation() {
        return camp.getLocation();
    }

    public int getCampTotalSlots() {
        return camp.getTotalSlots();
    }

    public int getCampCommitteeMembersSlots() {
        return camp.getCommitteeMembersSlots();
    }

    public String getCampDescription() {
        return camp.getCampDescription();
    }

    public String getCampStaffInCharge() {
        return camp.getStaffInCharge();
    }

    // Method to get the list of attendees
    public List<String> getCampAttendees() {
        return camp.getAttendees();
    }

    // Method to get the list of Committee Members
    public List<String> getCampCommitteeMembers() {
        return camp.getCommitteeMembers();
    }

    public void createSuggestion(String s) {
        suggestions.add(new Suggestion(s));
    }
    
    public void viewSugegstions() {
        for (Suggestion s : suggestions) {
            System.out.printf("%06d", s.getID());
            System.out.print(s.getSuggestion());
            System.out.print((s.isSubmitted())? "[Submitted, ":"[NotSubmitted, ");
            System.out.println((s.canModify())? "Not Processed]":"Processed, ");
            if (!s.canModify()) System.out.println(s.accepted()? "Accepted]":"Rejected]");
        }
    }

    public void editSuggestion(int i, String s) {
        for (int j=0; j<suggestions.size(); j++) {
            if (suggestions.get(j).getID()==i) {
                if (suggestions.get(j).setSuggestion(s)) {
                    System.out.printf("Suggestion %06d edited\n", suggestions.get(i).getID());
                } else {
                    System.out.printf("Suggestion %06d cannot be edited as it has been processed\n", suggestions.get(i).getID());
                }
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
    }

    public void deleteSuggestion(int j) {
        for (int i=0; i< suggestions.size(); i++) {
            if (suggestions.get(i).getID()==j) {
                if (suggestions.get(i).canModify()) {
                    suggestions.remove(i);
                    System.out.printf("Suggestion %06d deleted\n", suggestions.get(i).getID());
                    return;
                }
            }
        }
        System.out.println("Suggestion does not exist!");
    }

    public void submitSuggestion(int i) {
        for (Suggestion s: suggestions) {
            if (s.getID()==i) {
                if (s.submit()) {
                    System.out.println("Submission successful! ");
                    System.out.printf("Submitted suggestion: %06d\n", i);
                    points++;
                    // TODO: submit to somewhere?
                }
                else {
                    System.out.printf("Already submitted suggestion %06d\n", i);
                }
                return;
            }
        }
        System.out.println("Suggestion does not exist!");
    }

    public void viewEnquiries() {

    }

    public void replyEnquiry() {
        points++;
    }

    public void generateReport() {
        CampReport cr = new CampReport(camp, 1);
        cr.generateReport(); // maybe change to be comm specific?
    }

    public void addOnePoint() {
        points++;
    }

}
