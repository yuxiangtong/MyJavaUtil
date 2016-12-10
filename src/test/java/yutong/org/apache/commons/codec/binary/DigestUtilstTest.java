package yutong.org.apache.commons.codec.binary;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;


public class DigestUtilstTest {

    @Test
    public void main() {
        System.out.println(DigestUtils.md5Hex("这是一只猫"));
        try {
            //09c8143b3d3de32f62301479d26f8a3
            System.out.println(DigestUtils.md5Hex(this.getClass().getResourceAsStream("DigestUtilstTest.class")));
        }
        catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}
