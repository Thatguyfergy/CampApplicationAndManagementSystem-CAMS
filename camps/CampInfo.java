package camps;

import java.util.ArrayList;

import camdate.CAMDate;

/**
 * The CampInfo class is a Java class that provides information about a camp.
 * 
 * @author Tan Ying Hao
 * @author Ferguson Chiew
 * @version 1.0
 */
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

    /**
     * The default constructor of the 'CampInfo' class. It initializes the values
     * for all the fields of the class.
     */
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

    /**
     * The constructor of the 'CampInfo' class. It initializes the values for all
     * the fields of the class with the given parameters.
     * 
     * @param campName                The String parameter "campName" represents the
     *                                name of a camp.
     * @param registrationClosingDate The CAMDate parameter
     *                                "registrationClosingDate" represents the date
     *                                when registration for a camp closes.
     * @param campAvailability        The String parameter "campAvailability"
     *                                represents
     *                                the availability of a camp.
     * @param location                The String parameter "location" represents
     *                                the location of a camp.
     * @param totalSlots              The integer parameter "totalSlots" represents
     *                                the total number of slots available for a
     *                                camp.
     * @param committeeMembersSlots   The integer parameter "committeeMembersSlots"
     *                                represents
     *                                the number of slots available for committee
     *                                members.
     * @param campDescription         The String parameter "campDescription"
     *                                represents
     *                                the description of a camp.
     * @param staffInCharge           The String parameter "staffInCharge"
     *                                represents
     *                                the staff member in charge of a camp.
     */
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
     * The method 'sortDates()' sort the ArrayList of dates in ascending order using
     * insertion sort.
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
     * The method 'addDate(CAMDate date)' adds a single date to the ArrayList of
     * dates.
     * 
     * @param date The CAMDate parameter "date" represents a date that needs to be
     *             added to the list of dates.
     */
    public void addDate(CAMDate date) {
        dates.add(date);
    }

    /**
     * The method 'addDate(CAMDate startDate, CAMDate endDate)' adds a range of
     * dates to the ArrayList of dates.
     * Starting from the start date, the method adds all the dates up to the end
     * date
     * to the ArrayList of dates.
     * 
     * @param startDate The CAMDate parameter "startDate" represents the start date
     *                  of the range of dates.
     * @param endDate   The CAMDate parameter "endDate" represents the end date of
     *                  the range of dates.
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
     * The method 'removeDate(String date)' removes a date from the ArrayList of
     * dates.
     * 
     * @param date The String parameter "date" represents a date that needs to be
     *             removed from the list of dates.
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
     * The method 'dateExists(String date)' checks if a date exists in the ArrayList
     * of dates.
     * 
     * @param date The String parameter "date" represents a date that needs to be
     *             checked if it exists in the list of dates.
     * @return The methods returns a boolean value.
     *         If the date exists in the list of dates, the method returns true.
     *         else, the method returns false.
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
     * The method 'getCampName()' returns the name of a camp.
     * 
     * @return The method returns the String that represents the name of a camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * The method 'getDates()' returns the ArrayList<Date> that represents the
     * dates of a camp.
     * 
     * @return The method returns the ArrayList<Date> stored in the variable
     *         "dates".
     */
    public ArrayList<CAMDate> getDates() {
        return dates;
    }

    /**
     * The method 'getRegistrationClosingDate()' returns the registration closing
     * date for a camp.
     * 
     * @return The method returns the CAMDate object stored in the variable
     *         "registrationClosingDate"
     */
    public CAMDate getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    /**
     * The method 'getCampAvailability()' returns the faculty availability for a
     * camp.
     * 
     * @return The method returns the String stored in the variable
     *         "campAvailability"
     */
    public String getCampAvailability() {
        return campAvailability;
    }

    /**
     * The method 'getLocation()' returns the location of a camp.
     * 
     * @return The method returns the String stored in the variable "location".
     */
    public String getLocation() {
        return location;
    }

    /**
     * The method 'getTotalSlots()' returns the total number of slots available for
     * a camp.
     * 
     * @return The method returns the integer stored in the variable "totalSlots".
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * The method 'getCommitteeMembersSlots()' returns the number of slots available
     * for committee members.
     * 
     * @return The method returns the integer stored in the variable
     *         "committeeMembersSlots".
     */
    public int getCommitteeMembersSlots() {
        return committeeMembersSlots;
    }

    /**
     * The method 'getCampDescription()' returns the description of a camp.
     * 
     * @return The method returns the String stored in the variable
     *         "campDescription".
     */
    public String getCampDescription() {
        return campDescription;
    }

    /**
     * The method 'getStaffInCharge()' returns the staff in charge of a camp.
     * 
     * @return The method returns the String stored in the variable
     *         "staffInCharge".
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * The method 'getFormatedDates()' returns a String of dates.
     * Consecutive dates are formatted as a range of dates.
     * Non-consecutive dates are formatted as individual dates.
     * 
     * @return The method returns a String of dates formatted in presentable form.
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
     * The method 'getCampInfo()' returns a String that contains information about a
     * camp.
     * 
     * @return The method returns a String that contains information about a camp.
     */
    public String getCampInfo() {
        return "Camp Name: " + campName + "\n" + "Camp Dates: " + dates + "\n" + "Registration Closing Date: "
                + registrationClosingDate + "\n" + "Camp Visibility: " + campAvailability + "\n" + "Location: "
                + location
                + "\n" + "Total Slots: " + totalSlots + "\n" + "Committee Members Slots: " + committeeMembersSlots
                + "\n" + "Camp Description: " + campDescription + "\n" + "Staff In Charge: " + staffInCharge + "\n";
    }

    /**
     * The method 'toString()' returns the result of the getCampInfo() method.
     * 
     * @return The method returns the result of the getCampInfo() method.
     */
    @Override
    public String toString() {
        return getCampInfo();
    }

    /**
     * The method 'setCampName(String campName)' sets attribute 'campName' to the
     * parameter campName.
     * 
     * @param campName The parameter "campName" is a String that represents the name
     *                 of a camp.
     */
    public void setCampName(String campName) {
        this.campName = campName;
    }

    /**
     * The method 'setDates(ArrayList<CAMDate> dates)' sets attribute 'dates' to the
     * parameter registrationClosingDate.
     * 
     * @param registrationClosingDate The parameter "registrationClosingDate" is a
     *                                CAMDate object that represents the date when
     *                                registration for a camp closes.
     */
    public void setRegistrationClosingDate(CAMDate registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }

    /**
     * The method 'setCampAvailability(String campAvailability)' sets attribute
     * 'campAvailability' to the parameter campAvailability.
     * 
     * @param campAvailability The parameter "campAvailability" is a String that
     *                         represents the
     *                         availability of a camp.
     */
    public void setCampAvailability(String campAvailability) {
        this.campAvailability = campAvailability;
    }

    /**
     * The method 'setLocation(String location)' sets attribute 'location' to the
     * parameter location.
     * 
     * @param location The parameter "location" is a String that represents the
     *                 location of a camp.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * The method 'setTotalSlots(int totalSlots)' sets attribute 'totalSlots' to the
     * parameter totalSlots.
     * 
     * @param TotalSlots The parameter "TotalSlots" is an integer that represents
     *                   the total number of slots available for a camp.
     */
    public void setTotalSlots(int TotalSlots) {
        this.totalSlots = TotalSlots;
    }

    /**
     * The method 'setCommitteeMembersSlots(int committeeMembersSlots)' sets
     * attribute 'committeeMembersSlots' to the parameter committeeMembersSlots.
     * 
     * @param committeeMembersSlots The parameter "committeeMembersSlots" is an
     *                              integer that represents the number of slots
     *                              available for committee members.
     */
    public void setCommitteeMembersSlots(int committeeMembersSlots) {
        this.committeeMembersSlots = committeeMembersSlots;
    }

    /**
     * The method 'setCampDescription(String campDescription)' sets attribute
     * 'campDescription' to the parameter campDescription.
     * 
     * @param campDescription The parameter "campDescription" is a String that
     *                        represents the description of a camp.
     */
    public void setCampDescription(String campDescription) {
        this.campDescription = campDescription;
    }

    /**
     * The method 'setStaffInCharge(String staffInCharge)' sets attribute
     * 'staffInCharge' to the parameter staffInCharge.
     * 
     * @param staffInCharge The parameter "staffInCharge" is a String that
     *                      represents the staff member in charge of a camp.
     */
    public void setStaffInCharge(String staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    // Set all camp info except camp dates in a single method
    /**
     * The method 'setCampInfo(String campName, CAMDate registrationClosingDate,
     * String campAvailability, String location, int totalSlots, int
     * committeeMembersSlots, String campDescription, String staffInCharge)'
     * sets all the attributes of the class except 'dates' to the given parameters.
     * 
     * @param campName                The campName parameter is a String that
     *                                represents the name of the camp.
     * @param registrationClosingDate The registrationClosingDate parameter is a
     *                                CAMDate object that represents the date when
     *                                registration for the camp closes.
     * @param campAvailability        The campAvailability parameter is a String
     *                                that represents the
     *                                facultiy availability of the camp.
     * @param location                The location parameter is a String that
     *                                represents the location of the camp.
     * @param totalSlots              The totalSlots parameter is an integer that
     *                                represents the total number of slots
     *                                available for the camp.
     * @param committeeMembersSlots   The committeeMembersSlots parameter is an
     *                                integer that represents the number of slots
     *                                available for committee members.
     * @param campDescription         The campDescription parameter is a String
     *                                that represents the description of the camp.
     * @param staffInCharge           The staffInCharge parameter is a String that
     *                                represents the staff member in charge of the
     *                                camp.
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
     * The method 'displayCampInfo()' displays all the information about a camp.
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

}
