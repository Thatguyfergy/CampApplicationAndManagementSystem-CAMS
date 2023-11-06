package test;

import users.Student;
import camps.*;
import camdate.*;

public class CampCommTest {
    public static void main(String[] args) {
        Student s = new Student("JK", "JTAN", "SPMS");

        
        CAMDate date = new CAMDate(10,12,2031);
        Camp c = new Camp("Supremes",date,"SPMS","SPMS",100,20,"sup","JKTAN");
        s.registerCamp(c, true);
    }
    
}
