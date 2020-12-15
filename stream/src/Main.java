import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Main {
   public static void main(String[] args) {


//      List<String> strings = new ArrayList<String>();
//      strings.add("blue");
//      strings.add("red");
//      System.out.println(strings);

//      List<String> list = List.of("mad", "play");
//      System.out.println(list);

//      String[] arr = {"blue", "red"};
//      Stream<String> specificStream = Arrays.stream(arr, 0, 1);
//      // mad
//      specificStream.forEach(System.out::println);

//      List<String> list = List.of("blue", "red", "...");
//      Stream<String> stringStream = list.parallelStream();
//
//      stringStream.forEach(System.out::println);

      /*
       * 스트림과 병렬 처리
       * 순차 처리와 병렬 처리 비교.
       * */
//      List<String> list = Arrays.asList("John", "Simon", "Andy", "Andrew", "Bill");
//
//      // 순차 처리
//      Stream<String> stream = list.stream();
//      stream.forEach(Main::print);
//
//      System.out.println();
//
//      // 병렬 스트림
//      Stream<String> parallelStream = list.parallelStream();
//      parallelStream.forEach(Main::print);

      /*
      * 기본 타입에 특화된 스트림 생성.
      * */
      // 0 / 1 / 2 / 3 / 4 / 5 / 6 / 7 / 8 /
//      IntStream.range(0, 9).forEach(v -> System.out.print(v + " / "));
//      System.out.println();
//
//      // 0 / 1 / 2 / 3 / 4 / 5 / 6 / 7 / 8 / 9 /
//      IntStream.rangeClosed(0, 9).forEach(v -> System.out.print(v + " / "));
//      System.out.println();
//
//      // 0 / 1 / 2 / 3 / 4 / 5 / 6 / 7 / 8 /
//      LongStream.range(0, 9).forEach(v -> System.out.print(v + " / "));
//      System.out.println();
//
//      // 0.0 / 9.0 /
//      DoubleStream.of(0, 9).forEach(v -> System.out.print(v + " / "));
//      System.out.println();
//
//      // 랜덤으로 스트림 생성.
//      new Random().ints().limit(3).forEach(v -> System.out.print(v + " / "));
//      System.out.println();
//
//      new Random().doubles(3).forEach(v -> System.out.print(v + " / "));
//      System.out.println();

      // BufferedReader의 lines() 로 생성.
      // try-catch-resources
//      try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
//         Stream<String> stream = br.lines();
//         // do something
//      } catch (Exception e) {
//         // exception handling
//      }

      // Pattern 으로 스트림 생성.
//      Pattern.compile(",").splitAsStream("mad,play").forEach(System.out::println);

      // Stream.iterate() 생성.
      // limit 로 제한.
//      Stream.iterate(1 , x -> x + 1).limit(10).forEach(System.out::println);

      // Stream.generate()로 생성.
      // generate 메서드는 각 값을 연속적으로 생산하지 않으면, 인자가 없고 리턴값만 있는 Supplier<T>를 인수로 받음.
      // 무한 스트림으로 인해 limit 로 제한.
//      Stream<Integer> limit = Stream.generate(() -> 1).limit(10);
//      limit.forEach(v -> System.out.print(v + " , "));
//      System.out.println();
//
//      Stream<Double> limit1 = Stream.generate(Math::random).limit(10);
//      limit1.forEach(v -> System.out.print(v + " , "));
//      System.out.println();

      //스트림 연결
      List<String> list1 = List.of("blue", "red");
      List<String> list2 = List.of("yellow", "black");
      Stream<String> concat = Stream.concat(list1.stream(), list2.stream());
      concat.forEach(v -> System.out.print(v + " , "));
      System.out.println();

   }

   public static void print(String str) {
      System.out.println(str + " : " + Thread.currentThread().getName());
   }

}
