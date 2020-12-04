import abstrack.SimplePrint;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main{
   public static void main(String[] args) throws ExecutionException, InterruptedException {

      // before java 8
//      ExecutorService executor = Executors.newFixedThreadPool(5);
//
//      for (int i = 1; i <= 5 ; i++) {
//         int index = i;
//         executor.submit(() -> {
//            System.out.println(Thread.currentThread().getName() +
//                ", index= " + index + ", ended at " + new Date());
//         });
//      }
//
//      executor.shutdown();

      // after java 8
      List<Integer> list = List.of(1, 2, 3, 4, 5);
//      list.parallelStream().forEach(Main::print);
      list.parallelStream().forEach(index -> System.out.println("Starting " + Thread.currentThread().getName()
          + ", index=" + index + ", " + new Date()));

      ForkJoinPool forkJoinPool = new ForkJoinPool(3);
      forkJoinPool.submit(() -> list.parallelStream().forEach(SimplePrint::print)).get();
   }
}
