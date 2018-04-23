//package org.web3j.spring.forkjoin;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.ForkJoinTask;
//import java.util.concurrent.RecursiveTask;
///**
// * fork
// * 对一个大数组进行并行求和的RecursiveTask
// *
// * 大任务可以拆成小任务，小任务还可以继续拆成更小的任务，最后把任务的结果汇总合并，得到最终结果，这种模型就是Fork/Join模型。
// Java7引入了Fork/Join框架，我们通过RecursiveTask这个类就可以方便地实现Fork/Join模式。
// * Created by wenbronk on 2017/7/13.
// */
//class ForkJoinTest extends RecursiveTask<Long> {
//
//    static final int THRESHOLD = 100;
//    long[] array;
//    int start;
//    int end;
//
//    ForkJoinTest(long[] array, int start, int end) {
//        this.start = start;
//        this.end = end;
//        this.array = array;
//    }
//    @Override
//    protected Long compute() {
//
//        if (end - start < THRESHOLD) {
//            long sum = 0;
//            for (int i = start; i < end; i++) {
//                sum += array[i];
//            }
//            try {
//                Thread.sleep(100);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            println String.format('compute %d %d = %d', start, end, sum);
//        }
//
//        // 对于大任务, 分多线程执行
//        int middle = (end + start) / 2;
//        println String.format('split %d %d => %d %d, %d %d', start, end, start, middle, middle, end);
//
//        def subtask1 = new ForkJoinTest(this.array, start, middle);
//        def subtask2 = new ForkJoinTest(this.array, middle, end);
//        invokeAll(subtask1, subtask2);
//
//        Long subresult1 = subtask1.join();
//        Long subresult2 = subtask2.join();
//
//        Long result = subresult1 + subresult2;
//
//        System.out.println("result = " + subresult1 + " + " + subresult2 + " ==> " + result);
//        return result;
//    }
//
//    public static void main(String[] args) throws Exception {
//        // 创建随机数组成的数组:
//        long[] array = new long[400];
////        fillRandom(array);
//        // fork/join task:
//        ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
//        ForkJoinTask<Long> task = new ForkJoinTest(array, 0, array.length);
//        long startTime = System.currentTimeMillis();
//        Long result = fjp.invoke(task);
//        long endTime = System.currentTimeMillis();
//        System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");
//    }
//}