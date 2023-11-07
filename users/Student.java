package users;

import java.util.ArrayList;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;

public class Student extends Users {
    private boolean IsCampComm = false;
    private CampCommitteeRole CommRole;
    private ArrayList<CampAttendeeRole> Attendee; 
    private ArrayList<String> RegCamps;
    private ArrayList<CAMDate> BusyDates;

    // public Student(String FirstName, String userID, String facultyInfo) {
    //     super(FirstName, userID, facultyInfo);
    // }

    public Student(String FirstName, String userID, String facultyInfo, String commCamp, String regCamps, String busyDates, CampArray campArray) {
        super(FirstName, userID, facultyInfo);
        Attendee = new ArrayList<CampAttendeeRole>();
        BusyDates = new ArrayList<CAMDate>();
        RegCamps = new ArrayList<String>();

        if (commCamp != null) {
            IsCampComm = true;
            CommRole = new CampCommitteeRole(campArray.getCamp(commCamp));
        }
        for (String camp : regCamps.split(";")) {
            RegCamps.add(camp);
        }
        for (String date : busyDates.split(";")) {
            BusyDates.add(new CAMDate(date));
        }

        for (int i=0;i<RegCamps.size();i++){
            CampAttendeeRole attendeeRole = new CampAttendeeRole(RegCamps.get(i), this);
            Attendee.add(attendeeRole);
        }
    }

    public boolean IsCampComm(){
        return IsCampComm;
    }

    public ArrayList<CampAttendeeRole> getAttendeeArray(){
        return this.Attendee;
    }

    public CampAttendeeRole getAttendee(int index){
        return Attendee.get(index);
    }

    public void viewAvailAndRegCamps(CampArray currentCamps) {
        currentCamps.viewCamps(this);
    }

    public void registerCamp(Camp camp, boolean campCommittee) {
        // check if camp registered before
        for (int i=0;i<RegCamps.size();i++){
            if (camp.getCampName().equals(RegCamps.get(i))){
                System.out.println("Camp has already been registered before!");
                return;
            }
        }

        // check if camp date clashes
        ArrayList<CAMDate> regDates = camp.getDates();
        for (int i=0;i<regDates.size();i++){
            for (int j=0;j<BusyDates.size();j++){
                if(regDates.get(i).compareTo(BusyDates.get(j))==0){
                    System.out.println("Camp dates clashes with registered camps!");
                    return;
                }
            }
        }

        // Camp Committee check
        if (campCommittee) {
            if (IsCampComm){
                System.out.println("You are already a Camp Committee of another camp! Withdraw Camp Committee role and register again!");
                return;
            }
            CommRole = new CampCommitteeRole(camp);
            IsCampComm = campCommittee;
        }
        else{
            CampAttendeeRole attendeeRole = new CampAttendeeRole(camp.getCampName(),this);
            Attendee.add(attendeeRole);
        }

        // check if student is allowed to register for this camp, i.e. campVisibility
        if (camp.getCampInfo().getCampVisibility() == "off"){
            System.out.println("This camp has been closed for registration!");
            return;
        }

        RegCamps.add(camp.getCampName());
        camp.registerStudent(super.getID(), super.getFirstName(), campCommittee, camp.getCampName());
        for (int i=0;i<regDates.size();i++){
            BusyDates.add(regDates.get(i));
        }
    }

    public void withdrawFromCamp(Camp camp) {
        if (IsCampComm){
            if (camp.getCampName().equals(CommRole.getCampName())){
                System.out.println("Not allowed to withdraw from Camp as a Camp Committee Member!");
            }
        }
        else{
            camp.withdrawFromCamp(getID());
            RegCamps.remove(camp.getCampName());
            CampAttendeeRole remAttendee = new CampAttendeeRole(null, null);
            for (int i=0;i<Attendee.size();i++){
                if (Attendee.get(i).getCampAttending()==camp.getCampName()){
                    remAttendee = Attendee.get(i);
                    break;
                }
            }
            Attendee.remove(remAttendee);
            for (int i=0;i<camp.getDates().size();i++){
                BusyDates.remove(camp.getDates().get(i));
            }  
        }
    }

    public void listEnquiries(){
        for (int i=0;i<Attendee.size();i++){
            System.out.println("Enquiry under camp(index): "+ Attendee.get(i).getCampAttending()+" "+"("+(i+1)+")");
            Attendee.get(i).viewEnquiries();
        }
    }

    public CampCommitteeRole getCampCommitteeRole() {
        return CommRole;
    }

    public int compareTo(Users other) {
        return this.getID().compareTo(other.getID());
    }
    public int compareTo(Student other, String sortBy) {
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