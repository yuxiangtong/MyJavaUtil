package jdk.java.nio.charset;

import java.nio.charset.Charset;
import org.junit.Test;


public class CharsetTest {

    /**
     * 获取当前虚拟机的默认字符集 Charset.defaultCharset() 或
     * System.getProperty("file.encoding")
     * Charset.defaultCharset()底层代码也是通过调用 System.getProperty("file.encoding") 实现
     * <p>
     * 1.只有通过传递 jvm 参数的方式来默认编码(jvm启动时生效,不可更改) == 后续变更无效 <br/>
     * 2.此系统属性正确叫法：来自于java.nio.charset.Charset.defaultCharset()注释 <br/>
     * 英文：the default charset of this Java virtual machine <br/>
     * 中文：此 Java 虚拟机的默认 charset <br/>
     * 3.jvm参数指定方式为：java -Dfile.encoding=xxx
     * 4.如果是在eclipse中运行main方法,设置参见文中图2。
     * </p>
     * 
     * @author yuxiangtong
     */
    @Test
    public void defaultCharsetTest() {
        String encoding = System.getProperty("file.encoding");

        System.out.println("file.encoding :" + encoding); // UTF-8
        System.out.println(
                "此 Java 虚拟机的默认 charset :" + Charset.defaultCharset().name()); // UTF-8

        /* 尝试重置 file.encoding */
        encoding = System.setProperty("file.encoding", "GBK");

        /* 重置失败,依然是默认的UTF-8 */
        System.out.println(encoding); // UTF-8

        /* 尝试获取测试属性 */
        System.out.println(System.getProperty("testkey")); // null
        System.setProperty("testkey", "张三");
        System.out.println(System.getProperty("testkey")); // 张三

        System.out.println("张三".getBytes().length); // 6
    }

}
