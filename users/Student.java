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

    // The above code is defining a constructor for a class called "Student". The
    // constructor takes in
    // several parameters including the student's first name, user ID, faculty
    // information, information
    // about their committee camp, regular camps, busy dates, and a CampArray
    // object.
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

    /**
     * The function returns a boolean value indicating whether the variable
     * IsCampComm is true or
     * false.
     * 
     * @return The method is returning the value of the variable "IsCampComm".
     */
    public boolean IsCampComm() {
        return IsCampComm;
    }

    /**
     * The function returns an ArrayList of CampAttendeeRole objects.
     * 
     * @return An ArrayList of CampAttendeeRole objects.
     */
    public ArrayList<CampAttendeeRole> getAttendeeArray() {
        return this.Attendee;
    }

    /**
     * The function returns the CampAttendeeRole object at the specified index in
     * the Attendee list.
     * 
     * @param index The index parameter is an integer value that represents the
     *              position of the desired
     *              attendee in the Attendee list.
     * @return The method is returning a CampAttendeeRole object.
     */
    public CampAttendeeRole getAttendee(int index) {
        return Attendee.get(index);
    }

    /**
     * The function "viewAvailAndRegCamps" calls the "viewCamps" method on the
     * "currentCamps" object,
     * passing in the current object as an argument.
     * 
     * @param currentCamps A CampArray object that contains a list of camps.
     */
    public void viewAvailAndRegCamps(CampArray currentCamps) {
        currentCamps.viewCamps(this);
    }

    /**
     * The function returns an ArrayList of Strings called RegCamps.
     * 
     * @return An ArrayList of Strings named "RegCamps" is being returned.
     */
    public ArrayList<String> getRegCampsArray() {
        return RegCamps;
    }

    /**
     * The function `registerCampAttendee` checks various conditions before
     * registering a camp attendee
     * and adds them to the list of registered camps and attendees.
     * 
     * @param camp      The "camp" parameter is an object of the Camp class, which
     *                  represents a specific
     *                  camp that a student wants to register for. It contains
     *                  information such as the camp name, dates,
     *                  and visibility status.
     * @param campArray The `campArray` parameter is an instance of the `CampArray`
     *                  class. It is used
     *                  to register the attendee for the camp by calling the
     *                  `registerAttendee` method on it.
     */
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
        if (camp.toggleVisibility() == "off") {
            System.out.println("This camp has been closed for registration!");
            return;
        }

        // Blacklist check
        if (camp.hasWithdrawn(this.getID())) {
            System.out.println("Student has been withdrawn from the camp and cannot be registered again!");
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

    /**
     * The function `registerCampCommittee` checks various conditions before
     * allowing a student to
     * register as a camp committee for a camp.
     * 
     * @param camp      The "camp" parameter is an object of the Camp class, which
     *                  represents a specific
     *                  camp that the user wants to register as a committee for.
     * @param campArray The `campArray` parameter is an instance of the `CampArray`
     *                  class. It is used
     *                  to register the camp committee for a specific camp.
     */
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

        // Blacklist check
        if (camp.hasWithdrawn(this.getID())) {
            System.out.println("Student has been withdrawn from the camp and cannot be registered again!");
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

    /**
     * The function withdraws an attendee from a camp and removes their registration
     * information.
     * 
     * @param camp      The "camp" parameter is an object of the Camp class. It
     *                  represents the specific camp
     *                  from which the attendee wants to withdraw.
     * @param campArray The `campArray` parameter is an instance of the `CampArray`
     *                  class. It is used
     *                  to perform operations related to the collection of camps.
     */
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

    /**
     * The function creates a new enquiry object with the given enquiry string, camp
     * name, and the ID
     * of the current object, and adds it to the list of pending enquiries.
     * 
     * @param enqString The enqString parameter is a string that represents the
     *                  enquiry message or
     *                  details. It contains the information provided by the user
     *                  when creating the enquiry.
     * @param campName  The campName parameter is a String that represents the name
     *                  of the camp for
     *                  which the enquiry is being created.
     */
    public void createEnquiry(String enqString, String campName) {
        Enquiries newEnquiry = new Enquiries(enqString, this.getID(), campName);
        PendingEnquiries.add(newEnquiry);
        System.out.println("Enquiry created successfully!");
    }

    /**
     * The function "viewEnquiries" prints out the details of pending enquiries,
     * including their
     * submission status, camp name, and enquiry text.
     */
    public void viewEnquiries() {
        for (int i = 0; i < PendingEnquiries.size(); i++) {
            String submittedString = "NOT SUBMITTED";
            System.out.println("================================================================================");
            System.out.print((i + 1) + ") ");
            if (PendingEnquiries.get(i).getSubmitted()) {
                submittedString = "SUBMITTED";
                System.out.print("[" + submittedString + "]     ");
            } else
                System.out.print("[" + submittedString + "] ");
            System.out.println("Camp Name - " + PendingEnquiries.get(i).getCampName());
            System.out.println("   Enquiry : " + PendingEnquiries.get(i).getEnquiry());
            System.out.println("================================================================================");
        }
    }

    /**
     * The function edits an enquiry in a list of pending enquiries by modifying the
     * enquiry string at
     * the specified index.
     * 
     * @param enqString The enqString parameter is a String that represents the new
     *                  content of the
     *                  enquiry that you want to edit.
     * @param index     The index parameter is an integer that represents the
     *                  position of the enquiry in
     *                  the PendingEnquiries list that you want to edit.
     */
    public void editEnquiry(String enqString, int index) {
        PendingEnquiries.get(index).modifyEnquiry(enqString);
        System.out.println("Enquiry edited successfully!");
    }

    /**
     * The submitEnquiry function submits an enquiry from the PendingEnquiries array
     * to the
     * EnquiriesArray.
     * 
     * @param enqArray An array of Enquiries objects.
     * @param index    The index parameter is an integer that represents the
     *                 position of the enquiry in
     *                 the PendingEnquiries list that you want to submit.
     */
    public void submitEnquiry(EnquiriesArray enqArray, int index) {
        enqArray.submitEnquiry(PendingEnquiries.get(index));
        System.out.println("Enquiry submitted successfully!");
    }

    /**
     * The function deletes an enquiry from a list of pending enquiries, but only if
     * it has not been
     * submitted.
     * 
     * @param index The index parameter represents the position of the enquiry in
     *              the PendingEnquiries
     *              list that you want to delete.
     */
    public void deleteEnquiry(int index) {
        if (PendingEnquiries.get(index).getSubmitted()) {
            System.out.println("Submitted Enquiry cannot be deleted!");
            return;
        }
        PendingEnquiries.remove(index);
        System.out.println("Enquiry removed!");
    }

    /**
     * The function returns an ArrayList of CAMDate objects representing busy dates.
     * 
     * @return An ArrayList of CAMDate objects is being returned.
     */
    public ArrayList<CAMDate> getBusyDates() {
        return BusyDates;
    }

    /**
     * The function "viewEnquiriesReplies" calls the "viewReplies" method on an
     * EnquiriesArray object,
     * passing in the current object as a parameter.
     * 
     * @param enqArray The enqArray parameter is an instance of the EnquiriesArray
     *                 class. It is used to
     *                 store and manage a collection of enquiries and their replies.
     */
    public void viewEnquiriesReplies(EnquiriesArray enqArray) {
        enqArray.viewReplies(this);
    }

    /**
     * The function returns the size of the PendingEnquiries list.
     * 
     * @return The size of the PendingEnquiries list.
     */
    public int getPendingEnquiriesSize() {
        return PendingEnquiries.size();
    }

    /**
     * The function returns the CampCommitteeRole object.
     * 
     * @return The method is returning a CampCommitteeRole object.
     */
    public CampCommitteeRole getCampCommitteeRole() {
        return CommRole;
    }

    /**
     * The function compares the points of two students' camp committee roles and
     * returns 1 if the
     * first student has more points, -1 if the second student has more points, and
     * 0 if they have the
     * same number of points.
     * 
     * @param a The first student object to compare.
     * @param b The parameter "b" is a Student object.
     * @return The method is returning an integer value. If the points of student a
     *         are equal to the
     *         points of student b, it returns 0. If the points of student a are
     *         greater than the points of
     *         student b, it returns 1. Otherwise, if the points of student a are
     *         less than the points of
     *         student b, it returns -1.
     */
    public static int compareCommPoints(Student a, Student b) {
        // if equal return 0
        // if a better than b return 1, b better return -1
        if (a.getCampCommitteeRole().getPoints() == b.getCampCommitteeRole().getPoints())
            return 0;
        return (a.getCampCommitteeRole().getPoints() > b.getCampCommitteeRole().getPoints()) ? 1 : -1;
    }

    /**
     * The function `deleteCamp` removes a camp from the list of attendees, removes
     * busy dates
     * associated with the camp, and removes the camp from the list of registered
     * camps.
     * If the student is a campCom, then he will be removed.
     * 
     * @param campName    The name of the camp that you want to delete.
     * @param deleteDates The `deleteDates` parameter is an `ArrayList` of `CAMDate`
     *                    objects. It
     *                    represents the dates that need to be deleted from the
     *                    `BusyDates` list.
     */
    public void deleteCamp(String campName, ArrayList<CAMDate> deleteDates) {
        for (int i = 0; i < Attendee.size(); i++) {
            if (Attendee.get(i).getCampAttending().equals(campName)) {
                Attendee.remove(i);

                // Remove busy dates
                for (CAMDate date : deleteDates) {
                    BusyDates.remove(date);
                }

                // Remove from RegCamps
                RegCamps.remove(campName);

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

    /**
     * The function edits the name of a camp for all attendees and registered camps.
     * 
     * @param oldName The old name of the camp that needs to be edited.
     * @param newName The new name that you want to replace the old name with.
     */
    public void editCamp(String oldName, String newName) {
        for (CampAttendeeRole attend : Attendee) {
            if (attend.getCampAttending().equals(oldName)) {
                attend.setCampAttending(newName);
                return;
            }
        }
        for (String campName : RegCamps) {
            if (campName == oldName) {
                campName = newName;
            }
        }
    }

    // Deprecated methods

    // public void listEnquiries(){
    // for (int i=0;i<Attendee.size();i++){
    // System.out.println("Enquiry under camp(index): "+
    // Attendee.get(i).getCampAttending()+" "+"("+(i+1)+")");
    // Attendee.get(i).viewEnquiries();
    // }
    // }

}