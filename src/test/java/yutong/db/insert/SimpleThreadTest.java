package yutong.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
import com.yutong.util.db.ConnectionUtils;
import com.yutong.util.db.bean.DBBean;
import com.yutong.util.db.enums.EnumDBType;


public class SimpleThreadTest {

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
            String tableName = "T_PATIENT";
            DBBean dbBean = new DBBean(EnumDBType.MYSQL_5.getDriveClass(),
                    "jdbc:mysql://192.168.1.204:3306/engine_v4_thread?useUnicode=true&amp;characterEncoding=utf-8",
                    "root", "admin");
            connection = ConnectionUtils.getConnection(dbBean);

            statement = connection.createStatement();
            statement.execute("truncate table " + tableName);
            System.out.println("清空表 " + tableName + " 完成.");

            connection.setAutoCommit(false);
            String sql = "INSERT INTO t_patient(ID,NAME)VALUES(?,?)";
            preparedStatement = connection.prepareStatement(sql);
            long startTime = System.currentTimeMillis();
            for (int i = 0; i < 100 * 10000; i++) {
                long startTime2 = System.currentTimeMillis();
                int id = i + 1;
                String name = "姓名" + id;
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                int updatecount = preparedStatement.executeUpdate();
                if (updatecount > 0) {
                    // System.out.println("插入成功！");
                }
                if (id % 1000 == 0) {
                    connection.commit();
                    long endTime2 = System.currentTimeMillis();
                    System.out.println("插入成功 "
                            + id + ",耗时:" + (endTime2 - startTime2) + "ms");
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("共计耗时:" + ((endTime - startTime) / 1000) + "s");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(connection);
            DbUtils.closeQuietly(preparedStatement);
        }

    }

}
