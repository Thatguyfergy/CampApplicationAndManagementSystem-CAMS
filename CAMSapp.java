import camps.*;
import users.*;

import java.util.ArrayList;


public class CAMSapp {
    public static void main(String[] args) {
        System.out.println("NiHao");
        ArrayList<String> admins = new ArrayList<String>();
        String hi = "ni hao";
        Staff gay = new Staff("hi", "hi");

        if (gay instanceof Comparable){
            System.out.println("Chin yi is gay");
        }
    }
}
