package camdate;

public class CAMDate {
    private int day;
    private int month;
    private int year;

    // Default constructor - Set all to 0
    public CAMDate() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    // Constructor with all fields
    public CAMDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public CAMDate(String date) {
        String[] dateArray = date.split("/");
        this.day = Integer.parseInt(dateArray[0]);
        this.month = Integer.parseInt(dateArray[1]);
        this.year = Integer.parseInt(dateArray[2]);
    }

    // Getter methods for all individual fields
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Setter methods for all individual fields
    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        if (month > 0 && month < 13) {
            this.month = month;
        }
    }

    public void setYear(int year) {
        this.year = year;
    }

    // toString method
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    // compareTo method
    // return 1 if this is later than other return -1 if this is earlier than other
    // return 0 if this is the same as other
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
}
