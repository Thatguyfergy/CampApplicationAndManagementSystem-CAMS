package camps;

import java.util.ArrayList;

import camdate.CAMDate;

public class CampInfo {
    // private int campID;
    private String campName;
    private ArrayList<CAMDate> dates;
    private CAMDate registrationClosingDate;
    private String campAvailability;
    private String location;
    private int totalSlots;
    private int committeeMembersSlots;
    private String campDescription;
    private String staffInCharge;

    // The `public CampInfo()` constructor is the default constructor for the
    // `CampInfo` class. It
    // initializes all the fields of the class to their default values. In this
    // case, it sets the
    // `campName`, `campAvailability`, `location`, `campDescription`, and
    // `staffInCharge` fields to
    // empty strings, and the `dates` field to an empty `ArrayList` of `CAMDate`
    // objects. It also sets
    // the `totalSlots` and `committeeMembersSlots` fields to 0.
    public CampInfo() {
        // this.campID = 0;
        this.campName = "";
        this.dates = new ArrayList<CAMDate>();
        this.campAvailability = "";
        this.location = "";
        this.totalSlots = 0;
        this.committeeMembersSlots = 0;
        this.campDescription = "";
        this.staffInCharge = "";
    }

    // The `public CampInfo(String campName, CAMDate registrationClosingDate, String
    // campAvailability,
    // String location, int totalSlots, int committeeMembersSlots, String
    // campDescription, String
    // staffInCharge)` constructor is a parameterized constructor for the `CampInfo`
    // class. It takes in
    // values for all the fields of the class and initializes them accordingly.
    public CampInfo(String campName, CAMDate registrationClosingDate, String campAvailability,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {

        // this.campID = campID;
        this.campName = campName;
        this.dates = new ArrayList<CAMDate>();
        this.registrationClosingDate = registrationClosingDate;
        this.campAvailability = campAvailability;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeMembersSlots = committeeMembersSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }

    /**
     * The function sorts a list of dates in ascending order using the insertion
     * sort algorithm.
     */
    public void sortDates() {
        for (int i = 1; i < dates.size(); i++) {
            CAMDate key = dates.get(i);
            int j = i - 1;
            while (j >= 0 && dates.get(j).compareTo(key) > 0) {
                dates.set(j + 1, dates.get(j));
                j--;
            }
            dates.set(j + 1, key);
        }
    }

    /**
     * The function adds a CAMDate object to a list of dates.
     * 
     * @param date The parameter "date" is of type CAMDate, which is likely a custom
     *             class representing
     *             a date.
     */
    public void addDate(CAMDate date) {
        dates.add(date);
    }

    /**
     * The function adds a range of dates to a list, starting from a given start
     * date and ending at a
     * given end date.
     * 
     * @param startDate The start date of the range of dates to be added.
     * @param endDate   The endDate parameter is of type CAMDate, which represents a
     *                  date in the CAMDate
     *                  class.
     */
    public void addDate(CAMDate startDate, CAMDate endDate) {
        CAMDate temp = startDate;
        if (startDate.compareTo(endDate) <= 0) {
            while (temp.compareTo(endDate) <= 0) {
                dates.add(temp);
                temp = new CAMDate(temp.getDay(), temp.getMonth(), temp.getYear());
                temp.nextDay();
            }
        } else {
            System.out.println("Error: Start date cannot be after end date");
        }
    }

    /**
     * The function removes a specific date from a list of dates.
     * 
     * @param date The parameter "date" is a string representing a date that needs
     *             to be removed from
     *             the "dates" list.
     */
    public void removeDate(String date) {
        for (CAMDate d : dates) {
            if (d.toString().equals(date)) {
                dates.remove(d);
                break;
            }
        }
    }

    /**
     * The function checks if a given date exists in a list of CAMDate objects.
     * 
     * @param date The "date" parameter is a string representing a date.
     * @return The method is returning a boolean value. It returns true if the given
     *         date exists in the
     *         list of dates, and false otherwise.
     */
    public boolean dateExists(String date) {
        for (CAMDate d : dates) {
            if (d.toString().equals(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function returns the name of a camp.
     * 
     * @return The method is returning the value of the variable "campName".
     */
    public String getCampName() {
        return campName;
    }

    /**
     * The function returns an ArrayList of CAMDate objects.
     * 
     * @return An ArrayList of CAMDate objects.
     */
    public ArrayList<CAMDate> getDates() {
        return dates;
    }

    /**
     * The function returns the registration closing date as a CAMDate object.
     * 
     * @return The method is returning a CAMDate object.
     */
    public CAMDate getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    /**
     * The function returns the availability of a camp.
     * 
     * @return The method is returning the value of the variable "campAvailability".
     */
    public String getCampAvailability() {
        return campAvailability;
    }

    /**
     * The getLocation() function returns the location.
     * 
     * @return The method is returning the value of the variable "location".
     */
    public String getLocation() {
        return location;
    }

    /**
     * The function returns the total number of slots.
     * 
     * @return The method is returning the value of the variable "totalSlots".
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * The function returns the number of slots available for committee members.
     * 
     * @return The method is returning the value of the variable
     *         committeeMembersSlots.
     */
    public int getCommitteeMembersSlots() {
        return committeeMembersSlots;
    }

    /**
     * The function returns the camp description as a string.
     * 
     * @return The method is returning the value of the variable "campDescription".
     */
    public String getCampDescription() {
        return campDescription;
    }

    /**
     * The function returns the staff member in charge.
     * 
     * @return The method is returning the value of the variable "staffInCharge".
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * The function `getFormatedDates` formats a list of dates into a string
     * representation with
     * consecutive dates grouped together.
     * 
     * @return The method is returning a string that contains formatted dates.
     */
    public String getFormatedDates() {
        String formatedDates = "";
        CAMDate starDate = dates.get(0).clone();
        CAMDate currentDate = starDate.clone();
        CAMDate endDate = starDate.clone();
        for (int i = 1; i < dates.size(); i++) {
            currentDate.nextDay();
            if (currentDate.equals(dates.get(i))) {
                endDate = currentDate.clone();
            } else {
                if (starDate.equals(endDate)) {
                    formatedDates += starDate.toString() + ", ";
                } else {
                    formatedDates += starDate.toString() + " - " + endDate.toString() + ", ";
                }
                starDate = dates.get(i).clone();
                currentDate = starDate.clone();
                endDate = starDate.clone();
            }
        }
        String temp1 = starDate.toString() + " - " + endDate.toString();
        String temp2 = starDate.toString();
        if (!formatedDates.contains(temp1) && !starDate.equals(endDate)) {
            formatedDates += temp1;
        } else if (!formatedDates.contains(temp2)) {
            formatedDates += temp2;
        }
        return formatedDates;
    }

    /**
     * The function returns a string containing information about a camp, including
     * its name, dates,
     * registration closing date, visibility, location, total slots, committee
     * members slots,
     * description, and staff in charge.
     * 
     * @return The method is returning a string that contains information about a
     *         camp. The information
     *         includes the camp name, camp dates, registration closing date, camp
     *         visibility, location, total
     *         slots, committee members slots, camp description, and staff in
     *         charge.
     */
    public String getCampInfo() {
        return "Camp Name: " + campName + "\n" + "Camp Dates: " + dates + "\n" + "Registration Closing Date: "
                + registrationClosingDate + "\n" + "Camp Visibility: " + campAvailability + "\n" + "Location: "
                + location
                + "\n" + "Total Slots: " + totalSlots + "\n" + "Committee Members Slots: " + committeeMembersSlots
                + "\n" + "Camp Description: " + campDescription + "\n" + "Staff In Charge: " + staffInCharge + "\n";
    }

    /**
     * The toString() function returns the camp information as a string.
     * 
     * @return The method is returning the result of the getCampInfo() method.
     */
    @Override
    public String toString() {
        return getCampInfo();
    }

    /**
     * The function sets the value of the campName variable.
     * 
     * @param campName The parameter "campName" is a String that represents the name
     *                 of a camp.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * The function sets the registration closing date for a specific event.
     * 
     * @param registrationClosingDate The registration closing date is a CAMDate
     *                                object, which
     *                                represents the date when registration for
     *                                something closes.
     */
    public void setRegistrationClosingDate(CAMDate registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    /**
     * The function sets the availability of a camp.
     * 
     * @param campAvailability The parameter "campAvailability" is a String that
     *                         represents the
     *                         availability of a camp.
     */
    public void setCampAvailability(String campAvailability) {
        this.campAvailability = campAvailability;
    }

    /**
     * The function sets the location of an object.
     * 
     * @param location The "location" parameter is a String that represents the new
     *                 location to be set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The function sets the value of the totalSlots variable.
     * 
     * @param TotalSlots The parameter TotalSlots is an integer that represents the
     *                   total number of
     *                   slots.
     */
    public void setTotalSlots(int TotalSlots) {
        this.totalSlots = TotalSlots;
    }

    /**
     * The function sets the number of slots available for committee members.
     * 
     * @param committeeMembersSlots The parameter "committeeMembersSlots" is an
     *                              integer that represents
     *                              the number of slots available for committee
     *                              members.
     */
    public void setCommitteeMembersSlots(int committeeMembersSlots) {
        this.committeeMembersSlots = committeeMembersSlots;
    }

    /**
     * The function sets the camp description for a Java object.
     * 
     * @param campDescription The campDescription parameter is a String that
     *                        represents the description
     *                        of a camp.
     */
    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    /**
     * The function sets the staff in charge for a particular task or
     * responsibility.
     * 
     * @param staffInCharge The staff member who is in charge of a particular task
     *                      or responsibility.
     */
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    // Set all camp info except commitee members and camp dates in a single method
    /**
     * The function sets the information for a camp, including its name,
     * registration closing date,
     * availability, location, total slots, committee members slots, description,
     * and staff in charge.
     * 
     * @param campName                The name of the camp.
     * @param registrationClosingDate The registrationClosingDate parameter is of
     *                                type CAMDate, which
     *                                is a custom class that represents a date for
     *                                the closing of registration for the camp.
     * @param campAvailability        The availability of the camp, such as "open"
     *                                or "closed".
     * @param location                The location parameter is a String that
     *                                represents the location of the camp. It
     *                                could be the name of a city, a specific
     *                                address, or any other information that
     *                                identifies the
     *                                physical location of the camp.
     * @param totalSlots              The total number of slots available for the
     *                                camp.
     * @param committeeMembersSlots   The parameter "committeeMembersSlots"
     *                                represents the number of
     *                                slots available specifically for committee
     *                                members at the camp.
     * @param campDescription         A description of the camp, providing
     *                                information about the activities,
     *                                goals, and purpose of the camp.
     * @param staffInCharge           The staffInCharge parameter is a String that
     *                                represents the name or
     *                                identifier of the staff member who is in
     *                                charge of the camp.
     */
    public void setCampInfo(String campName, CAMDate registrationClosingDate, String campAvailability,
            String location, int totalSlots, int committeeMembersSlots, String campDescription, String staffInCharge) {
        this.campName = campName;
        this.registrationClosingDate = registrationClosingDate;
        this.campAvailability = campAvailability;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeMembersSlots = committeeMembersSlots;
        this.campDescription = campDescription;
        this.staffInCharge = staffInCharge;
    }

    /**
     * The function displays information about a camp, including its name, dates,
     * registration closing
     * date, availability, location, total slots, committee members slots,
     * description, and staff in
     * charge.
     */
    public void displayCampInfo() {
        System.out.println("======================================");
        System.out.println("Information for Camp " + campName);
        System.out.println("======================================");
        System.out.print("Dates:\t\t\t");
        for (int i = 0; i < dates.size(); i++) {
            System.out.print(dates.get(i));
            if (i != dates.size() - 1)
                System.out.print(", ");
        }
        System.out.println("\nClose Date:\t\t" + registrationClosingDate);
        System.out.println("Avail:\t\t\t" + campAvailability);
        System.out.println("Location:\t\t" + location);
        System.out.println("Total:\t\t\t" + totalSlots);
        System.out.println("Com-Mem:\t\t" + committeeMembersSlots);
        System.out.println("Description:\t\t" + campDescription);
        System.out.println("Staff-in-charge:\t" + staffInCharge);
        System.out.println("======================================");
        System.out.println();
    }

    // Deprecated

    // public int getCampID() {
    // return campID;
    // }

    // public void setCampID(int campID) {
    // this.campID = campID;
    // }
}
