package users;

import java.util.ArrayList;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;
import infoexchange.Enquiries;
import infoexchange.EnquiriesArray;

public class Student extends Users {
    private boolean IsCampComm = false;
    private CampCommitteeRole CommRole;
    private ArrayList<CampAttendeeRole> Attendee;
    private ArrayList<String> RegCamps;
    private ArrayList<CAMDate> BusyDates;
    private ArrayList<Enquiries> PendingEnquiries = new ArrayList<Enquiries>();

    // public Student(String FirstName, String userID, String facultyInfo) {
    // super(FirstName, userID, facultyInfo);
    // }

    public Student(String FirstName, String userID, String facultyInfo, String commCamp, String regCamps,
            String busyDates, CampArray campArray) {
        super(FirstName, userID, facultyInfo);
        Attendee = new ArrayList<CampAttendeeRole>();
        BusyDates = new ArrayList<CAMDate>();
        RegCamps = new ArrayList<String>();

        if (!commCamp.equals("")) {
            IsCampComm = true;
            String[] cc = commCamp.split(";");
            CommRole = new CampCommitteeRole(campArray.getCamp(cc[0]), Integer.parseInt(cc[1]));
        }
        if (!regCamps.equals("")) {
            for (String camp : regCamps.split(";")) {
                RegCamps.add(camp);
            }
        }
        if (!busyDates.equals("")) {
            for (String date : busyDates.split(";")) {
                BusyDates.add(new CAMDate(date));
            }
        }

        for (int i = 0; i < RegCamps.size(); i++) {
            CampAttendeeRole attendeeRole = new CampAttendeeRole(RegCamps.get(i), this);
            Attendee.add(attendeeRole);
        }
    }

    public boolean IsCampComm() {
        return IsCampComm;
    }

    public ArrayList<CampAttendeeRole> getAttendeeArray() {
        return this.Attendee;
    }

    public CampAttendeeRole getAttendee(int index) {
        return Attendee.get(index);
    }

    public void viewAvailAndRegCamps(CampArray currentCamps) {
        currentCamps.viewCamps(this);
    }

    public void registerCampAttendee(Camp camp, CampArray campArray) {
        // check if camp registered before
        for (int i = 0; i < RegCamps.size(); i++) {
            if (camp.getCampName().equals(RegCamps.get(i))) {
                System.out.println("Camp has already been registered before!");
                return;
            }
        }

        // check if camp date clashes
        ArrayList<CAMDate> regDates = camp.getDates();
        for (int i = 0; i < regDates.size(); i++) {
            for (int j = 0; j < BusyDates.size(); j++) {
                if (regDates.get(i).compareTo(BusyDates.get(j)) == 0) {
                    System.out.println("Camp dates clashes with registered camps!");
                    return;
                }
            }
        }

        // check if student is allowed to register for this camp, i.e. campVisibility
        if (camp.getCampInfo().getCampAvailability() == "off") {
            System.out.println("This camp has been closed for registration!");
            return;
        }

        RegCamps.add(camp.getCampName());
        CampAttendeeRole attendeeRole = new CampAttendeeRole(camp.getCampName(), this);
        Attendee.add(attendeeRole);
        campArray.registerAttendee(camp.getCampName(), this.getID());
        for (int i = 0; i < regDates.size(); i++) {
            BusyDates.add(regDates.get(i));
        }
        System.out.println("Successfully registered as Camp Attendee!");
    }

    public void registerCampCommittee(Camp camp, CampArray campArray) {
        // check if camp registered before
        for (int i = 0; i < RegCamps.size(); i++) {
            if (camp.getCampName().equals(RegCamps.get(i))) {
                System.out.println("Camp has already been registered before!");
                return;
            }
        }

        // check if camp date clashes
        ArrayList<CAMDate> regDates = camp.getDates();
        for (int i = 0; i < regDates.size(); i++) {
            for (int j = 0; j < BusyDates.size(); j++) {
                if (regDates.get(i).compareTo(BusyDates.get(j)) == 0) {
                    System.out.println("Camp dates clashes with registered camps!");
                    return;
                }
            }
        }

        // check if student is allowed to register for this camp, i.e. campVisibility
        if (camp.getCampInfo().getCampAvailability() == "off") {
            System.out.println("This camp has been closed for registration!");
            return;
        }

        // Camp Committee check
        if (IsCampComm) {
            System.out.println(
                    "You are already a Camp Committee of another camp!");
            return;
        }

        CommRole = new CampCommitteeRole(camp, 0);
        IsCampComm = true;
        RegCamps.add(camp.getCampName());
        campArray.registerCampCom(camp.getCampName(), this.getID());
        for (int i = 0; i < regDates.size(); i++) {
            BusyDates.add(regDates.get(i));
        }
        System.out.println("Successfully registered as Camp Committee!");
    }

    public void withdrawFromCamp(Camp camp, CampArray campArray) {
        if (IsCampComm) {
            if (camp.getCampName().equals(CommRole.getCampName())) {
                System.out.println("Not allowed to withdraw from Camp as a Camp Committee Member!");
                return;
            }
        }
        campArray.withdrawAttendee(camp.getCampName(), this.getID());
        RegCamps.remove(camp.getCampName());
        CampAttendeeRole remAttendee = new CampAttendeeRole(null, null);
        for (int i = 0; i < Attendee.size(); i++) {
            if (Attendee.get(i).getCampAttending() == camp.getCampName()) {
                remAttendee = Attendee.get(i);
                break;
            }
        }
        Attendee.remove(remAttendee);
        for (int i = 0; i < camp.getDates().size(); i++) {
            BusyDates.remove(camp.getDates().get(i));
        }
        System.out.println("Successfully withdrew from " + camp.getCampName());

    }

    public void createEnquiry(String enqString, String campName) {
        Enquiries newEnquiry = new Enquiries(enqString, this.getID(), campName);
        PendingEnquiries.add(newEnquiry);
        System.out.println("Enquiry created successfully!");
    }

    public void viewEnquiries() {
        for (int i = 0; i < PendingEnquiries.size(); i++) {
            String submittedString = "NOT SUBMITTED";
            if (PendingEnquiries.get(i).getSubmitted())
                submittedString = "SUBMITTED";
            System.out.print((i + 1) + ": ");
            System.out
                    .printf("[" + PendingEnquiries.get(i).getCampName() + "] " + PendingEnquiries.get(i).getEnquiry());
            System.out.println("    [" + submittedString + "]");
        }
    }

    public void editEnquiry(String enqString, int index) {
        PendingEnquiries.get(index).modifyEnquiry(enqString);
        System.out.println("Enquiry edited successfully!");
    }

    public void submitEnquiry(EnquiriesArray enqArray, int index) {
        enqArray.submitEnquiry(PendingEnquiries.get(index));
        System.out.println("Enquiry submitted successfully!");
    }

    public ArrayList<CAMDate> getBusyDates() {
        return BusyDates;
    }

    public void viewEnquiriesReplies(EnquiriesArray enqArray) {
        enqArray.viewReplies(this);
    }

    // public void listEnquiries(){
    // for (int i=0;i<Attendee.size();i++){
    // System.out.println("Enquiry under camp(index): "+
    // Attendee.get(i).getCampAttending()+" "+"("+(i+1)+")");
    // Attendee.get(i).viewEnquiries();
    // }
    // }

    public CampCommitteeRole getCampCommitteeRole() {
        return CommRole;
    }

    public static int compareCommPoints(Student a, Student b) {
        // if equal return 0
        // if a better than b return 1, b better return -1
        if (a.getCampCommitteeRole().getPoints() == b.getCampCommitteeRole().getPoints())
            return 0;
        return (a.getCampCommitteeRole().getPoints() > b.getCampCommitteeRole().getPoints()) ? 1 : -1;
    }

    // Camp is deleted. If the student is a campCom, then he will be removed
    // Will also be removed from reg camp is necessary
    public void deleteCamp(String campName, ArrayList<CAMDate> deleteDates) {
        for (int i = 0; i < Attendee.size(); i++) {
            if (Attendee.get(i).getCampAttending().equals(campName)) {
                Attendee.remove(i);

                // Remove busy dates
                for (CAMDate date : deleteDates) {
                    BusyDates.remove(date);
                }
                return;
            }
        }
        // Remove Camp Comm
        if (this.IsCampComm && this.getCampCommitteeRole().getCampName().equals(campName)) {
            IsCampComm = false;
            CommRole = null;
            RegCamps.remove(campName);
            for (CAMDate date : deleteDates) {
                BusyDates.remove(date);
            }
        }

    }

    public void editCamp(String oldName, String newName) {
        for (CampAttendeeRole attend : Attendee) {
            if (attend.getCampAttending().equals(oldName)) {
                attend.setCampAttending(newName);
                return;
            }
        }
    }

}