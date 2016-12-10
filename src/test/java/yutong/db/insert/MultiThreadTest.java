package yutong.db.insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.dbutils.DbUtils;
import com.yutong.util.db.ConnectionUtils;
import com.yutong.util.db.bean.DBBean;
import com.yutong.util.db.enums.EnumDBType;
import yutong.db.insert.bean.Patient;
import yutong.db.insert.thread.PatientOperationThread;


/**
 * 1张表+5个线程(每个线程独立数据库链接)
 * 
 * @author yuxiangtong
 *
 */
public class MultiThreadTest {

    public static void main(String[] args) {
        int count = 20 * 10000;
        int threadCount = 5;
        int commitBatch = 1000;

        Connection connection1 = null;
        Connection connection2 = null;
        Connection connection3 = null;
        Connection connection4 = null;
        Connection connection5 = null;
        Statement statement = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        PreparedStatement preparedStatement4 = null;
        PreparedStatement preparedStatement5 = null;

        try {
            String[] tableName = {
                "T_PATIENT"
            };
            DBBean dbBean = new DBBean(EnumDBType.MYSQL_5.getDriveClass(),
                    "jdbc:mysql://192.168.1.204:3306/engine_v4_thread?useUnicode=true&amp;characterEncoding=utf-8",
                    "root", "admin");
            connection1 = ConnectionUtils.getConnection(dbBean);
            connection2 = ConnectionUtils.getConnection(dbBean);
            connection3 = ConnectionUtils.getConnection(dbBean);
            connection4 = ConnectionUtils.getConnection(dbBean);
            connection5 = ConnectionUtils.getConnection(dbBean);

            statement = connection1.createStatement();
            for (int i = 0; i < tableName.length; i++) {
                statement.execute("truncate table " + tableName[i]);
                System.out.println("清空表 " + tableName[i] + " 完成.");
            }
            System.out.println("修改前-此 Connection 对象的自动提交模式的当前状态:"
                    + connection1.getAutoCommit());
            connection1.setAutoCommit(false);
            connection2.setAutoCommit(false);
            connection3.setAutoCommit(false);
            connection4.setAutoCommit(false);
            connection5.setAutoCommit(false);
            System.out.println("修改后-此 Connection 对象的自动提交模式的当前状态:"
                    + connection1.getAutoCommit());
            String sql = "INSERT INTO t_patient(ID,NAME)VALUES(?,?)";
            preparedStatement1 = connection1.prepareStatement(sql);
            preparedStatement3 = connection3.prepareStatement(sql);
            preparedStatement5 = connection5.prepareStatement(sql);

            // String sql2 = "INSERT INTO t_patient2(ID,NAME)VALUES(?,?)";
            preparedStatement2 = connection2.prepareStatement(sql);
            preparedStatement4 = connection4.prepareStatement(sql);

            Queue<Patient> patientQueueDown =
                    new ConcurrentLinkedQueue<Patient>();
            for (int i = 1; i <= count; i++) {
                Patient patient = new Patient(i, "张三" + i);
                patientQueueDown.offer(patient);
            }

            ExecutorService executorService = Executors.newCachedThreadPool();
            List<Future<Long>> resultList = new ArrayList<Future<Long>>();

            for (int i = 0; i < threadCount; i++) {
                // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
                if ((i + 1) == 1) {
                    Future<Long> future =
                            executorService.submit(new PatientOperationThread(
                                    (i + 1) + "", patientQueueDown, connection1,
                                    preparedStatement1, commitBatch));
                    // 将任务执行结果存储到List中
                    resultList.add(future);
                }
                if ((i + 1) == 2) {
                    Future<Long> future =
                            executorService.submit(new PatientOperationThread(
                                    (i + 1) + "", patientQueueDown, connection2,
                                    preparedStatement2, commitBatch));
                    // 将任务执行结果存储到List中
                    resultList.add(future);
                }
                if ((i + 1) == 3) {
                    Future<Long> future =
                            executorService.submit(new PatientOperationThread(
                                    (i + 1) + "", patientQueueDown, connection3,
                                    preparedStatement3, commitBatch));
                    // 将任务执行结果存储到List中
                    resultList.add(future);
                }
                if ((i + 1) == 4) {
                    Future<Long> future =
                            executorService.submit(new PatientOperationThread(
                                    (i + 1) + "", patientQueueDown, connection4,
                                    preparedStatement4, commitBatch));
                    // 将任务执行结果存储到List中
                    resultList.add(future);
                }
                if ((i + 1) == 5) {
                    Future<Long> future =
                            executorService.submit(new PatientOperationThread(
                                    (i + 1) + "", patientQueueDown, connection5,
                                    preparedStatement5, commitBatch));
                    // 将任务执行结果存储到List中
                    resultList.add(future);
                }

            }
            executorService.shutdown();

            long startTime = System.currentTimeMillis();

            // 遍历任务的结果
            for (int i = 0; i < resultList.size(); i++) {
                try {
                    Future<Long> fs = resultList.get(i);
                    Long future = fs.get();
                    System.out
                            .println("线程" + (i + 1) + "分析了" + future + "个患者.");
                }
                catch (Exception e) {
                    executorService.shutdownNow();
                    e.printStackTrace();
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println(
                    "====共计耗时:" + ((endTime - startTime) / 1000) + "s");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            DbUtils.closeQuietly(connection1);
            DbUtils.closeQuietly(connection2);
            DbUtils.closeQuietly(connection3);
            DbUtils.closeQuietly(connection4);
            DbUtils.closeQuietly(connection5);
            DbUtils.closeQuietly(statement);
            DbUtils.closeQuietly(preparedStatement1);
            DbUtils.closeQuietly(preparedStatement2);
            DbUtils.closeQuietly(preparedStatement3);
            DbUtils.closeQuietly(preparedStatement4);
            DbUtils.closeQuietly(preparedStatement5);
        }
    }

}
