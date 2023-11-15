package camdate;

public class CAMDate {
    private int day;
    private int month;
    private int year;

    // The `public CAMDate()` constructor is a default constructor that initializes
    // the `day`, `month`,
    // and `year` fields of the `CAMDate` class to 0. This means that if an object
    // of the `CAMDate`
    // class is created using this constructor, the date will be set to 0/0/0, which
    // is an invalid
    // date.
    public CAMDate() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    // The `public CAMDate(int day, int month, int year)` constructor is used to
    // create a new `CAMDate`
    // object with the specified day, month, and year values.
    public CAMDate(int day, int month, int year) throws IllegalArgumentException {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
    }

    // The `public CAMDate(String date)` constructor is used to create a new
    // `CAMDate` object with the
    // specified date string.
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
     * The function returns the value of the variable "day".
     * 
     * @return The method is returning the value of the variable "day".
     */
    public int getDay() {
        return day;
    }

    /**
     * The function returns the value of the month.
     * 
     * @return The method is returning the value of the variable "month".
     */
    public int getMonth() {
        return month;
    }

    /**
     * The function returns the value of the year variable.
     * 
     * @return The method is returning the value of the variable "year".
     */
    public int getYear() {
        return year;
    }

    /**
     * The function sets the value of the "day" variable.
     * 
     * @param day The parameter "day" is an integer that represents the day of the
     *            month.
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * The function sets the value of the month variable if the input is a valid
     * month number.
     * 
     * @param month The parameter "month" is an integer representing the month of
     *              the year.
     */
    public void setMonth(int month) {
        if (month > 0 && month < 13) {
            this.month = month;
        }
    }

    /**
     * The function sets the value of the "year" variable.
     * 
     * @param year The parameter "year" is an integer that represents the year value
     *             to be set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * The toString() function returns a formatted string representation of the day,
     * month, and year.
     * 
     * @return The method is returning a formatted string representation of a date
     *         in the format
     *         "dd/mm/yyyy".
     */
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    // compareTo method
    // return 1 if this is later than other return -1 if this is earlier than other
    // return 0 if this is the same as other
    /**
     * The function compares two CAMDate objects based on their year, month, and day
     * values and returns
     * 1 if the first object is greater, -1 if the second object is greater, and 0
     * if they are equal.
     * 
     * @param other The "other" parameter is an object of the CAMDate class that we
     *              are comparing the
     *              current object to.
     * @return The `compareTo` method is returning an integer value. If `this` date
     *         is greater than
     *         `other` date, it returns 1. If `this` date is less than `other` date,
     *         it returns -1. If both
     *         dates are equal, it returns 0.
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
     * The function "nextDay" increments the day, month, and year values to
     * represent the next day,
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
     * The function returns a new instance of the CAMDate class with the same day,
     * month, and year
     * values as the current instance.
     * 
     * @return A new instance of the CAMDate class is being returned.
     */
    public CAMDate clone() {
        return new CAMDate(this.day, this.month, this.year);
    }

    /**
     * The equals() function checks if the day, month, and year of two CAMDate
     * objects are equal.
     * 
     * @param other The "other" parameter is an object of type CAMDate that is being
     *              compared to the
     *              current object.
     * @return The method is returning a boolean value.
     */
    public boolean equals(CAMDate other) {
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    /**
     * The getNextDay() function returns a new CAMDate object representing the next
     * day after the
     * current date.
     * 
     * @return The method is returning a CAMDate object, which represents the next
     *         day after the
     *         current date.
     */
    public CAMDate getNextDay() {
        CAMDate nextDay = this.clone();
        nextDay.nextDay();
        return nextDay;
    }

    /**
     * The function checks if a given date is valid by checking if the day, month,
     * and year values are
     * within valid ranges and if the day value is within the maximum number of days
     * for the given
     * month and year.
     * 
     * @param day   The day of the month. It should be an integer value between 1
     *              and 31.
     * @param month The month parameter represents the month of the date. It should
     *              be an integer value
     *              between 1 and 12, where 1 represents January and 12 represents
     *              December.
     * @param year  The year parameter represents the year of the date. It should be
     *              a positive integer
     *              value.
     * @return The method is returning a boolean value. It returns true if the given
     *         day, month, and
     *         year form a valid date, and false otherwise.
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
