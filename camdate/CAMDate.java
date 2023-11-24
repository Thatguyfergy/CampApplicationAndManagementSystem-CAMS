package camdate;

/**
 * The CAMDate class is a Java class that implements the Comparable interface.
 * Main class used for various dates as it contain the checkers for them.
 * 
 * @author Tan Ying Hao
 * @version 1.0
 */
public class CAMDate implements Comparable<CAMDate> {
    private int day;
    private int month;
    private int year;

    /**
     * The `public CAMDate()` constructor is a default constructor that initializes
     * the 'day', 'month', and 'year' variables to 0.
     */
    public CAMDate() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    /**
     * The `public CAMDate(int day, int month, int year)` constructor is used to
     * create a new
     * `CAMDate` object with the same day, month, and year values as the `date`
     * parameter. If the date given is non-existent, the constructor throws an
     * illegal argument exception.
     * 
     * @param day   The 'day' parameter represents the day of the date.
     * @param month The 'month' parameter represents the month of the date.
     * @param year  The 'year' parameter represents the year of the date.
     */
    public CAMDate(int day, int month, int year) throws IllegalArgumentException {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    /**
     * The `CAMDate(CAMDate date)` constructor is used to create a new `CAMDate`
     * object with a string representation of the date in the format "dd/mm/yyyy".
     * If date given is non-existent, the constructor throws an illegal argument
     * 
     * @param date The 'date' parameter represents a string representation of the
     *             date in the format "dd/mm/yyyy".
     */
    public CAMDate(String date) {
        String[] dateArray = date.split("/");
        int newDay = Integer.parseInt(dateArray[0]);
        int newMonth = Integer.parseInt(dateArray[1]);
        int newYear = Integer.parseInt(dateArray[2]);
        if (isValidDate(newDay, newMonth, newYear)) {
            this.day = newDay;
            this.month = newMonth;
            this.year = newYear;
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    /**
     * The method 'getDay' returns the value of the day variable.
     * 
     * @return The method returns an int value
     *         representing the day of the month.
     */
    public int getDay() {
        return day;
    }

    /**
     * The method 'getMonth' returns the value of the month variable.
     * 
     * @return The method returns an int value
     *         representing the month of the year.
     */
    public int getMonth() {
        return month;
    }

    /**
     * The method 'getYear' returns the value of the year variable.
     * 
     * @return The method returns an int value
     *         representing the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * The method sets the value of the "day" variable.
     * If the date is invalid, the method throws an illegal argument exception.
     * 
     * @param day The parameter "day" is an integer that represents the day of the
     *            month.
     */
    public void setDay(int day) {
        if (isValidDate(day, this.month, this.year)) // Check if the date is valid before setting the day value
            this.day = day;
        else
            throw new IllegalArgumentException("Invalid date");
    }

    /**
     * The method sets the value of the 'month' variable.
     * If the date is invalid, the method throws an illegal argument exception.
     * 
     * @param month The parameter "month" is an integer representing the month of
     *              the year.
     */
    public void setMonth(int month) {
        if (month > 0 && month < 13 && isValidDate(this.day, month, this.year)) // Check if the date is valid before
                                                                                // setting the month
            this.month = month;
        else
            throw new IllegalArgumentException("Invalid date");
    }

    /**
     * The method sets the value of the "year" variable.
     * If the date is invalid, the method throws an illegal argument exception.
     * 
     * @param year The parameter "year" is an integer that represents the year value
     *             to be set.
     */
    public void setYear(int year) {
        if (isValidDate(this.day, this.month, year)) // Check if the date is valid before setting the year value
            this.year = year;
        else
            throw new IllegalArgumentException("Invalid date");
    }

    /**
     * The method 'toString' returns a formatted string representation of the day,
     * month, and year in the format 'dd/mm/yyyy'.
     * 
     * @return The method is returning a formatted string representation of a date
     *         in the format "dd/mm/yyyy".
     */
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    // compareTo method
    // return 1 if this is later than other return -1 if this is earlier than other
    // return 0 if this is the same as other
    /**
     * The method 'compareTo' compares two CAMDate objects based on their year,
     * month, and day
     * values and returns
     * 1 if the first object is greater, -1 if the second object is greater, and 0
     * if they are equal.
     * 
     * @param other The "other" parameter is an object of the CAMDate class that we
     *              are comparing the current object to.
     * @return The method is returning an integer value.
     *         If `this` date is greater than `other` date, it returns 1.
     *         If `this` date is less than `other` date it returns -1.
     *         If both dates are equal, it returns 0.
     */
    public int compareTo(CAMDate other) {
        if (this.year > other.year) {
            return 1;
        } else if (this.year < other.year) {
            return -1;
        } else {
            if (this.month > other.month) {
                return 1;
            } else if (this.month < other.month) {
                return -1;
            } else {
                if (this.day > other.day) {
                    return 1;
                } else if (this.day < other.day) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    // nextDay method
    /**
     * The method 'nextDay()' updates the day, month, and year values of the
     * current CAMDate object to the next day,
     * taking into account different month lengths and leap years.
     */
    public void nextDay() {
        if (this.day == 31 && this.month == 12) {
            this.day = 1;
            this.month = 1;
            this.year++;
        } else if (this.day == 31 && (this.month == 1 || this.month == 3 || this.month == 5 || this.month == 7
                || this.month == 8 || this.month == 10)) {
            this.day = 1;
            this.month++;
        } else if (this.day == 30 && (this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11)) {
            this.day = 1;
            this.month++;
        } else if (this.day == 28 && this.month == 2 && this.year % 4 != 0) {
            this.day = 1;
            this.month++;
        } else if (this.day == 29 && this.month == 2 && this.year % 4 == 0) {
            this.day = 1;
            this.month++;
        } else {
            this.day++;
        }
    }

    /**
     * The method 'clone()' returns a new instance of the CAMDate class with the
     * same 'day', 'month', and 'year' values.
     * 
     * @return The method returns CAMDate object
     *         with the same 'day', 'month', and 'year' values as this.
     */
    public CAMDate clone() {
        return new CAMDate(this.day, this.month, this.year);
    }

    /**
     * The 'equals()' method checks if the day, month, and year values of the
     * other CAMDate object are equal to the day, month, and year values of the
     * current object.
     * 
     * @param other The "other" parameter is an object of type CAMDate that is being
     *              compared to the current object.
     * @return The method returns a boolean value.
     *         If the day, month, and year values of the two objects are equal, it
     *         returns true.
     *         else it returns false.
     */
    public boolean equals(CAMDate other) {
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    /**
     * The 'getNextDay()' method returns a new CAMDate object with the day, month,
     * and year values
     * of the next day after the date in the current object.
     * 
     * @return The method return a CAMDate object with the day, month, and year
     *         values
     *         of the next day after the date in the current object.
     */
    public CAMDate getNextDay() {
        CAMDate nextDay = this.clone();
        nextDay.nextDay();
        return nextDay;
    }

    /**
     * The method 'isValidDate' checks if the day, month, and year values form a
     * valid date.
     * 
     * @param day   The 'day' parameter represents the day of the date.
     * @param month The 'month' parameter represents the month of the date.
     * @param year  The 'year' parameter represents the year of the date.
     * @return The method returns a boolean value.
     *         If the day, month, and year values form a valid date, it returns
     *         true.
     *         else it returns false.
     */
    private boolean isValidDate(int day, int month, int year) {
        if (year < 1 || month < 1 || month > 12 || day < 1) {
            return false;
        }

        int maxDays = 31; // Initialize maxDays to the maximum days in a month
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDays = 30;
        } else if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                maxDays = 29;
            } else {
                maxDays = 28;
            }
        }

        return day <= maxDays;
    }
}
