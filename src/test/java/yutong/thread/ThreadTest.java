package yutong.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ThreadTest {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<Long>> resultList = new ArrayList<Future<Long>>();

        try {
            Queue<List<String>> patientQueueDown =
                    new ConcurrentLinkedQueue<List<String>>();
            for (int i = 0; i < 1000; i++) {
                List<String> ids = new ArrayList<String>();
                ids.add((i + 1) + "");
                patientQueueDown.offer(ids);
            }

            for (int i = 0; i < patientQueueDown.size(); i++) {
                List<String> ids = patientQueueDown.poll();
                Future<Long> future =
                        executorService.submit(new PatientOperationThread(ids));
                // 将任务执行结果存储到List中
                resultList.add(future);
            }

            while (true) {
                try {
                    Thread.sleep(1 * 1000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

                /* 移除已完成的线程 */
                Iterator<Future<Long>> iterator = resultList.iterator();
                while (iterator.hasNext()) {
                    Future<Long> future = iterator.next();
                    if (future.isDone()) {
                        iterator.remove();
                    }
                }

                /* 计算空闲线程数量 */
                int nobusyCount = 5 - resultList.size();
                for (int i = 0; i < nobusyCount; i++) {
                    if (patientQueueDown.size() > 0) {
                        List<String> ids = patientQueueDown.poll();
                        Future<Long> future = executorService
                                .submit(new PatientOperationThread(ids));
                        // 将任务执行结果存储到List中
                        resultList.add(future);
                    }
                    else {
                        // 生成10页数据放在待消耗队列中

                    }
                }

                /* 若全部被消费完,则进行跳出循环 */
                if (patientQueueDown.size() == 0) {
                    break;
                }
            }

            /* 阻塞剩余消费线程,直到全部分析完成 */
            for (int i = 0; i < resultList.size(); i++) {
                Future<Long> future = resultList.get(i);
                future.get();
            }
            System.out.println(executorService.isTerminated());

            System.out.println("全部处理完成1");
            executorService.shutdown();
            try {
                Thread.sleep(5 * 1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(executorService.isTerminated());
            System.out.println("全部处理完成2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
