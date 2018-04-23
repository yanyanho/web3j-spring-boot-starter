package org.web3j.spring.forkjoin;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * forkjoin的简单易用
 * Created by wenbronk on 2017/7/26.
 */
public class CountTask extends RecursiveTask<Integer> {
    private volatile static int count = 0;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public static final int threadhold = 2;

    @Override
    protected Integer compute() {
        int sum = 0;
        System.out.println("开启了一条线程单独干: " + count++);
        // 如果任务足够小, 就直接执行
        boolean canCompute = (end - start) <= threadhold;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            //任务大于阈值, 分裂为2个任务
            int middle = (start + end) / 2;
            CountTask countTask1 = new CountTask(start, middle);
            CountTask countTask2 = new CountTask(middle + 1, end);

            // 开启线程
//            countTask1.fork();
//            countTask2.fork();
            invokeAll(countTask1, countTask2);

            Integer join1 = countTask1.join();
            Integer join2 = countTask2.join();

            // 结果合并
            sum = join1 + join2;
        }
        return sum;
    }


    // 测试
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CountTask countTask = new CountTask(1, 100);
        ForkJoinTask<Integer> result = forkJoinPool.submit(countTask);
        System.out.println(result.get());
    }
}