package yutong.db.insert.thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Queue;
import java.util.concurrent.Callable;
import yutong.db.insert.bean.Patient;


public class PatientOperationThread
    implements Callable<Long> {

    private String id;

    private Queue<Patient> patientQueueDown; // 待处理的共享队列

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private int commitBatch = 1000;


    public PatientOperationThread(String id, Queue<Patient> patientQueueDown,
        Connection connection, PreparedStatement preparedStatement,
        int commitBatch) {
        this.id = id;
        this.patientQueueDown = patientQueueDown;
        this.connection = connection;
        this.preparedStatement = preparedStatement;
        this.commitBatch = commitBatch;
    }


    @Override
    public Long call() throws Exception {
        long count = 0;
        // 如果队列不为空
        if (patientQueueDown.size() != 0) {
            Patient patient;
            while ((patient = patientQueueDown.poll()) != null) {
                count++;
                long startTime2 = System.currentTimeMillis();
                preparedStatement.setInt(1, patient.getId());
                preparedStatement.setString(2, patient.getName());
                int updatecount = preparedStatement.executeUpdate();
                if (updatecount > 0) {
                    // System.out.println("插入成功！");
                }
                if (count % commitBatch == 0) {
                    connection.commit();
                    long endTime2 = System.currentTimeMillis();
                    System.out.println(id
                            + "." + Thread.currentThread().getName() + "插入成功("
                            + commitBatch + "/" + patientQueueDown.size() + ") "
                            + ",耗时:" + (endTime2 - startTime2) + "ms");
                }
            }
        }
        connection.commit(); // 最后剩余提交
        return count;
    }

}
