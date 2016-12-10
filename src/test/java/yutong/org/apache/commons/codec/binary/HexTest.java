package yutong.org.apache.commons.codec.binary;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Hex;


public class HexTest {

    public static void main(String[] args) {
        try {
            System.out.println(Charset.defaultCharset()); // UTF-8
            System.out.println("bird".getBytes().length);
            System.out.println("鸟".getBytes().length);
            System.out.println(String.valueOf(Hex.encodeHex("bird".getBytes())));
            System.out.println(String.valueOf(Hex.encodeHex("Z".getBytes())));
            System.out.println(Hex.encodeHex("鸟".getBytes()));
            String string = "It is a 猫";
            String hex = new String(Hex.encodeHex(string.getBytes()));
            System.out.println(hex);

            System.out.println(new String(Hex.decodeHex(hex.toCharArray())));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
