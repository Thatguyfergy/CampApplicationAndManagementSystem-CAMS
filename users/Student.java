package users;

import java.util.ArrayList;

import camdate.CAMDate;
import camps.Camp;
import camps.CampArray;

public class Student extends Users {
    private boolean IsCampComm = false;
    private CampCommitteeRole CommRole;
    private ArrayList<CampAttendeeRole> Attendee; 
    private ArrayList<Camp> RegCamps;
    private ArrayList<CAMDate> BusyDates;

    public Student(String FirstName, String userID, String facultyInfo) {
        super(FirstName, userID, facultyInfo);
    }

    // Hello enric, please create an overloaded constructor so that UserDatabase can
    // use.
    // The purpose is to read the data from the csv file and create the Student objs
    // Make sure that EVERY (or most) of the inputs are String type.
    // So you might need to do convertion of String to wtv type u wan urself here
    public Student(String FirstName, String userID, String facultyInfo, String otherInformation) {
        this(FirstName, userID, facultyInfo);
    }

    public void viewAvailAndRegCamps(CampArray currentCamps) {
        currentCamps.viewCamps(this);
    }

    public void registerCamp(Camp camp, boolean campCommittee) {
        // check if camp registered before
        for (int i=0;i<RegCamps.size();i++){
            if (camp==RegCamps.get(i)){
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
            CampAttendeeRole attendeeRole = new CampAttendeeRole(camp,this);
            Attendee.add(attendeeRole);
        }

        // check if student is allowed to register for this camp, i.e. campVisibility
        if (camp.getCampInfo().getCampVisibility() == "off"){
            System.out.println("This camp has been closed for registration!");
            return;
        }

        RegCamps.add(camp);
        camp.registerStudent(super.getID(), super.getFirstName(), campCommittee, camp.getCampName());
        for (int i=0;i<regDates.size();i++){
            BusyDates.add(regDates.get(i));
        }
    }

    public void withdrawFromCamp(Camp camp) {
        // call withdraw from camp function from camp
    }

    public int compareTo(Users o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}

