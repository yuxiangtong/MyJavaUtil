package jdk.java.lang;

import java.util.ArrayList;
import java.util.List;


public class OutOfMemoryErrorTest {

    public static void main(String[] args) {
        try {
            List<String> list = new ArrayList<String>();
            while (true) {
                list.add("1");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
