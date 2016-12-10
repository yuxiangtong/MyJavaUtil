package jdk.java.lang;

import java.util.Enumeration;
import java.util.Properties;


public class SystemTest {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement().toString();
            System.out.println(key + ":" + properties.getProperty(key));
        }
    }

}
