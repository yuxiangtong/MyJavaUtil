package jdk.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * 测试[创建链接-到-关闭链接]耗时
 * 
 * @author yuxiangtong
 *
 */
public class CreateConnectionTest {

    private static String driveClass = "com.mysql.jdbc.Driver";

    private static String url =
            "jdbc:mysql://192.168.1.204:3306/engine_v4_thread?useUnicode=true&amp;characterEncoding=utf-8";

    private static String username = "root";

    private static String password = "admin";


    public static void main(String[] args) {
        long time = 0;
        for (int i = 0; i < 1000; i++) {
            long buff = getConnectionTime();
            time = time + buff;
            System.out.println((i + 1) + " : " + "(" + buff + "/" + time + ")");
        }
        System.out.println("平均耗时:" + (time / 1000) + "ms.");
    }


    public static long getConnectionTime() {
        long startTime = System.currentTimeMillis();
        Connection connection = null;
        try {
            Class.forName(driveClass);
            connection = DriverManager.getConnection(url, username, password);

            // System.out.println("此 Connection 对象的自动提交模式的当前状态:" +
            // connection.getAutoCommit());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        long time = System.currentTimeMillis() - startTime;
        return time;
    }

}
