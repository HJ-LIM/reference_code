import abstrack.SimplePrint;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

public class ParallelPerformanceTest {
   public static void main(String[] args) throws ExecutionException, InterruptedException {
//      doExecute(100);
//      doExecute(1000);
//      doExecute(10000);
//      doExecute(100000);
//      doExecute(1000000);
//      doExecute(10000000);
//      doExecute(50000000);
      doExecute(100000000);
      doExecute(200000000);
      doExecute(300000000);
      doExecute(400000000);
      doExecute(500000000);
      doExecute(1000000000);

   }

   public static void doExecute(int size) throws ExecutionException, InterruptedException {
      System.out.println("> size : " + size);


      long l = System.currentTimeMillis();
      LongStream.range(0,size).forEach( integer -> {});
      System.out.println("1. 순차 : " + (System.currentTimeMillis() - l) + " ms");

      l = System.currentTimeMillis();
      LongStream.range(0,size).parallel().forEach(num -> {});
      System.out.println("2. 병렬 stream (Thread 설정 X): " + (System.currentTimeMillis() - l) + " ms");

      l = System.currentTimeMillis();
      ForkJoinPool forkJoinPool = new ForkJoinPool(5);
      forkJoinPool.submit(()-> LongStream.range(0,size).parallel().forEach(num -> {})).get();
      System.out.println("3. 병렬 stream (Thread 설정 "+forkJoinPool.getPoolSize()+"): " + (System.currentTimeMillis() - l) + " ms");

      l = System.currentTimeMillis();
      forkJoinPool = new ForkJoinPool(10);
      forkJoinPool.submit(()-> LongStream.range(0,size).parallel().forEach(num -> {})).get();
      System.out.println("4. 병렬 stream (Thread 설정 "+forkJoinPool.getPoolSize()+"): " + (System.currentTimeMillis() - l) + " ms");

      l = System.currentTimeMillis();
      forkJoinPool = new ForkJoinPool(15);
      forkJoinPool.submit(()-> LongStream.range(0,size).parallel().forEach(num -> {})).get();
      System.out.println("5. 병렬 stream (Thread 설정 "+forkJoinPool.getPoolSize()+"): " + (System.currentTimeMillis() - l) + " ms");

      System.out.println();
   }

}
