package threadpool;

/**
 * Created by huchengjie on 2014/11/12.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class FactorialCalculator implements Callable<Integer> {
    private Integer number;

    public FactorialCalculator(Integer number) {
        this.number = number;
    }

    @Override
    public Integer call() throws Exception {
        int result = 1;

        if(number == 0 || number == 1) {
            result = 1;
        } else {
            for(int i = 2; i <= number; i++) {
                result *= i;
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }
        System.out.printf("%s: %d\n", Thread.currentThread().getName(), result);
        return result;
    }

    public static void main(String[] args) {
        //创建固定长度为2的线程池
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.
                newFixedThreadPool(2);
        // 声明保存返回结果的列表，注意类型为Future<Integer>
        List<Future<Integer>> resultList = new ArrayList<>();
        Random random = new Random();

        // For循环中的submit方法在提交线程执行后会有一个返回类型为Future<Integer>的结果。将结果保存在列表中。
        for(int i = 0; i < 10; i++) {
            Integer number = random.nextInt(10);
            FactorialCalculator calculator = new FactorialCalculator(number);
            Future<Integer> result = executor.submit(calculator);
            resultList.add(result);
        }

        System.out.printf("Main: Results\n");
        for(int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            Integer number = null;
            try {
                // 结果需要在线程执行完后才能get到，所以get执行时会使得线程等待，需要捕捉异常
                number = result.get();
            } catch(InterruptedException e) {
                e.printStackTrace();
            } catch(ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("Main: Task %d: %d\n", i, number);
        }

        // 关闭线程池
        executor.shutdown();
    }
}