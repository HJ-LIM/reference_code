import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamCalculator {
   public static void main(String[] args) {
      StreamPractice streamPractice = new StreamPractice();
//      streamPractice.doCalculator();
//      streamPractice.doFilter();
//      streamPractice.doMap();
//      streamPractice.doMapToInt();
//      streamPractice.doFlatMap();
//      streamPractice.doDistinct();
//      streamPractice.doSort();
//      streamPractice.doPeek();
//      streamPractice.doLimit();
      streamPractice.doSkip();
   }
}

class StreamPractice{

   //중간연산
   public void doCalculator(){
      List<String> list = List.of("a", "ab", "abc", "abcd");

      List<String> result = list.stream()
          .filter(x -> {   //중간 연산 1
             System.out.println(x + " in filter method");
             return x.length() >= 1;
          }).map(s -> {    //중간 연산 2
             System.out.println(s + " in map method");
             return s.toUpperCase();
          }).limit(4)      //중간 연산 3
          .collect(Collectors.toList());     // 나중에 살펴볼 단말 연산.

      System.out.println(result);
   }

   //filter 메서드로 필터링
   //filter 메서드로 스트림 내 요소들을 조건에 맞게 필터링할 수 있습니다.
   //메서드의 인자인 Predicate<T> 인터페이스는 test 라는 추상 메서드를 정의하는데, 이는 제네릭 형식의 객체를 인수로 받아 boolean 값을 반환합니다.
   public void doFilter(){
      List<String> list = List.of("Lim", "Hyung", "Joon");

      List<String> collect = list.stream().filter(s -> s.length() > 3).collect(Collectors.toList());
      System.out.println(collect);

      collect = list.stream().filter(new Predicate<String>() {
         @Override
         public boolean test(String s) {
            return s.length() >3;
         }
      }).collect(Collectors.toList());

      System.out.println(collect);
   }


   //Map 메서드로 특정 형태로 변환.
   // map 메서드를 사용하여 스트림 내 요소를 원하는 특정 형태로 변환할 수 있습니다.
   public void doMap(){
      List<String> list = List.of("lim", "hyung", "joon");

      list.stream().map(s -> s.toUpperCase()).forEach(System.out::println);

      list.stream().map(new Function<String, String>() {
         @Override
         public String apply(String s) {
            return s.toUpperCase();
         }
      }).forEach(System.out::println);
   }

   //기본 타입에 특화된 스트림으로 변환
   public void doMapToInt(){
      List<String> list = List.of("0", "1");

      list.stream()
            .mapToInt(value -> Integer.parseInt(value))
          .forEach(System.out::println);

      list.stream()
            .mapToInt(new ToIntFunction<String>() {
               @Override
               public int applyAsInt(String value) {
                  return Integer.parseInt(value);
               }
            })
          .forEach(System.out::println);
   }

   //flatmap 메서드로 단일 스트림 변환
   //중첩된 구조를 한 단계 없애고 단일 원소 스트림으로 만들어줍니다.
   public void doFlatMap(){
      List<String> list1 = List.of("mad", "play");
      List<String> list2 = List.of("lim", "hyung" , "joon");
      List<List<String>> combinedList = List.of(list1, list2);

      List<String> collect = combinedList.stream()
          .flatMap(list -> list.stream())
          .collect(Collectors.toList());

      //[mad, play, lim, hyung, joon]
      System.out.println(collect);
   }

   //distinct 메서드로 중복 제거.
   public void doDistinct(){
      // int[] -> List<Integer>
//      List<Integer> collect = Arrays.stream(new int[]{1, 2, 2, 3, 3}).boxed()
//          .collect(Collectors.toList());
//      System.out.println(collect);

      IntStream stream = Arrays.stream(new int[]{1, 2, 2, 3, 3});

      List<Integer> collect = stream.distinct().boxed().collect(Collectors.toList());
      System.out.println(collect);
   }

   //sorted 메서드로 정렬하기
   public void doSort(){
      List.of(1,2,3).stream().sorted().forEach(System.out::print);
      System.out.println();
      List.of(1,2,3).stream().sorted(Comparator.reverseOrder()).forEach(System.out::print);


      // 다만 IntStream, DoubleStream, LongStream과 같은 기본형 특화 스트림의 경우
      // sorted 메서드에 인자를 넘길 수 없습니다. 따라서 boxed 메서드를 이용해 객체 스트림으로 변환 후 사용해야 합니다.
      // 2, 1, 0
      IntStream.range(0, 3)
          .boxed() // boxing
          .sorted(Comparator.reverseOrder());
   }

   // peek 메서드로 각각의 요소에 연산 수행하기.
   // 스트림 내의 각각의 요소를 대상으로 특정 연산 수행.
   // 원본 스트림에서 요소를 소모하지 않기 때문에 중간 연산 사이의 결과를 확인할 때 유용.
   //
   public void doPeek(){
      List<Integer> otherList = new ArrayList<>();

      List.of(1,2,3).stream()
          .limit(2)
          .peek(i -> {
             // 실제로는 사용하면 안된다.
             otherList.add(i);
          })
          .forEach(System.out::println);

      System.out.println(otherList);
      // 단말 연산인 forEach가 없으면 otherList는 비어있다.
   }

   // limit 메서드로 개수 제한하기
   // limit 메서드를 사용하면 스트림 내의 요소 개수를 제한할 수 있습니다.
   public void doLimit(){
      List<String> collect = List.of("a", "b", "c")
          .stream()
          .limit(2)
          .collect(Collectors.toList());
      System.out.println(collect);
   }

   //skip 메서드로 특정 요소 생략하기
   // 스트림 내의 첫번째 요소부터 인자로 전달된 개수 만큼의 요소를 제외.
   // 나머지 요소로 구성된 새로운 스트림을 리턴.
   public void doSkip(){
      List<String> collect = Arrays.stream(new String[]{"a", "b", "c"}).skip(1)
          .collect(Collectors.toList());

      System.out.println(collect);
   }

   // boxed 메서드로 객체 스트림으로 변환하기
   public void doBoxed(){
      IntStream intStream = IntStream.range(0, 3);

      // 객체 타입의 일반 스트림으로 변환
      Stream<Integer> boxedStream = intStream.boxed();
   }
}


