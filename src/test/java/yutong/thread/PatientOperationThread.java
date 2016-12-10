package yutong.thread;

import java.util.List;
import java.util.concurrent.Callable;


public class PatientOperationThread
    implements Callable<Long> {

    private List<String> ids;


    public PatientOperationThread(List<String> ids) {
        this.ids = ids;
    }


    @Override
    public Long call() throws Exception {
        long count = 0;
        try {

            System.out.println(
                    Thread.currentThread().getName() + ":" + ids.get(0));

            Thread.sleep(1 * 1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
