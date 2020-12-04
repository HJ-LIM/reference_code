import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.StringJoiner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class StreamTerminalOperations {
   public static void main(String[] args) {
      TerminalOperationsPractice terminalOperationsPractice = new TerminalOperationsPractice();
//      terminalOperationsPractice.doForEach();
//      terminalOperationsPractice.doReduce();
//      terminalOperationsPractice.doMinMaxCount();
//      terminalOperationsPractice.doCollect();
      terminalOperationsPractice.doMatch();

   }
}

class TerminalOperationsPractice{

   //순회
   public void doForEach(){
      List<Integer> list = List.of(3, 2, 1, 5, 7);
      list.stream().forEach(System.out::println);
      System.out.println();
      // 병렬스트림을 사용할 경우.
      // 매 실행마다 출력 결과가 동일하지 않다.
      list.parallelStream().forEach(System.out::print);
      System.out.println();
      // 매 실행마다 동일한 출력 결과
      list.parallelStream().forEachOrdered(System.out::println);
   }

   //결과 합치기.
   public void doReduce(){

      List<Integer> list = List.of(1, 2, 3);

      //먼저 인자가 하나만 있는 형태입니다.
      //인자로는 BinaryOperator를 사용하는데 이는 두 개의 같은 타입 요소를 인자로 받아
      // 동일한 타입의 결과를 반환하는 함수형 인터페이스를 사용합니다.
      Optional<Integer> reduce = list.stream().reduce((a, b) -> a + b);
      //list.stream().reduce(Integer::sum).get()

      // 두 개의 인자를 받는 형태는 항등값과 BinaryOperator를 받습니다.
      // 아래와 같이 초기값을 줄 수도 있습니다.
      Integer reduce1 = list.stream().reduce(1, Integer::sum);
      System.out.println(reduce1);

      // 세 개의 인자를 받는 형태
      // BiFunction, BinaryOperator를 받습니다.
      // 값을 누적하는 연산의 경우 병렬 연산의 결과를 결합해야 하는데, 여기서 세 번째 인자가 그 역할을 합니다.
      // 그러니까 병렬 처리를 하는 경우에 각자 다른 스레드의 결과를 합쳐줍니다.

      list = List.of(3,7,9);

      Integer result = list.parallelStream()
          .reduce(1, Integer::sum, (a, b) -> {
             System.out.println("in combiner");
             return a + b;
          });

      System.out.println(result);
      // 일반 스트림에서는 combiner가 수행되지 않으므로 결과값도 다릅니다.
      // 즉 병렬 스트림에서만 동작합니다.
      // 초기값 1에 스트림의 요소 값을 더한 값을 계산합니다.
      // (1+3=4, 1+9=10, 1+7=8) 그리고 다음 과정에서 combiner 연산에서는
      // 여러 스레드에서 나누어 연산한 값을 합칩니다. (8+10=18, 4+18=22)
   }

   //계산하기: 최솟값, 총합, 평균 등
   public void doMinMaxCount(){
      OptionalDouble min = DoubleStream.of(4.1, 3.4, -1.3, 3.9, -5.7).min();
      System.out.println(min.isPresent());

      // 5
      int max = IntStream.of(2, 4, 5, 3).max().getAsInt();

      // 결과 4
      long count = IntStream.of(2, 4, 1, 3).count();
   }

   //결과 모으기(Collect)
   public void doCollect(){
      List<Food> list = new ArrayList<>();
      list.add(new Food("burger", 520));
      list.add(new Food("chips", 230));
      list.add(new Food("coke", 143));
      list.add(new Food("soda", 143));

      // list 로 반환
      List<String> collect = list.stream()
          .map(Food::getName)
          .collect(Collectors.toList());
      System.out.println(collect);

      // name 길이 summary
      IntSummaryStatistics collect2 = list.stream()
          .collect(Collectors.summarizingInt(value -> value.getName().length()));
      System.out.println(collect2);

      // name 길이 합.
      Integer collect1 = list.stream()
          .collect(Collectors.summingInt(value -> value.getName().length()));
      System.out.println(collect1);

      // mapToInt 메서드로 칼로리(cal) 합 구하기.
      int sum = list.stream().mapToInt(Food::getCal).sum();
      System.out.println(sum);

      // 평균 구하기: averagingInt
      Double averageInt = list.stream()
          .collect(Collectors.averagingInt(Food::getCal));

      // 평균 구하기: averagingDouble
      Double averageDouble = list.stream()
          .collect(Collectors.averagingDouble(Food::getCal));

      // 구분자(delimiter)를 인자로 받아서 처리할 수 있습니다. 이어지는 문자열 사이에 위치하게 됩니다.
      // delimiter
      String delimiterJoining = list.stream()
          .map(Food::getName).collect(Collectors.joining(","));
      // burger,chips,coke,soda
      System.out.println(delimiterJoining);

      // delimiter, prefix, suffix
      String combineJoining = list.stream()
          .map(Food::getName).collect(Collectors.joining(",", "[", "]"));

      // [burger,chips,coke,soda]
      System.out.println(combineJoining);
            
      // 특정 조건으로 그룹 짓기
      Map<Integer, List<Food>> collect3 = list.stream()
          .collect(Collectors.groupingBy(Food::getCal));
      System.out.println(collect3);
      
      //참, 거짓으로 그룹 짓기
      Map<Boolean, List<Food>> collect4 = list.stream()
          .collect(Collectors.partitioningBy(o -> o.getCal() > 200));
      System.out.println(collect4);

      //Map으로 결과 모으기
      //키에 값이 2개 이상 존재하게 되는 경우 컬렉터는 IllegalStateException을 던집니다.
      Map<Integer, String> collect5 = list.stream()
          .collect(Collectors.toMap(
              o -> o.getCal(),
              o -> o.getName(),
              (oldValue , newValue) -> newValue
          ));
      System.out.println(collect5);


      //collect 후에 연산 추가하기
      // collectingAndThen 메서드를 이용하면 특정 타입의 형태로 수집(collect)한 이후에 추가 연산을 진행할 수 있습니다.
      Food food = list.stream()
          .collect(Collectors.collectingAndThen(
              Collectors.minBy(Comparator.comparing(Food::getCal)),
              (Optional<Food> o) -> o.orElse(null)
          ));

      System.out.println(food);

      //직접 Collector를 만들기
      Collector<Food , StringJoiner , String> foodNameCollector = Collector.of(
          () -> new StringJoiner(" | "),  //supplier : collector 생성.
          (a,b) -> a.add(b.getName()), // accumulator : 두 값을 가지고 계산.
          (a,b) -> a.merge(b), // combiner : 계산 결과 수집 (합치기)
          StringJoiner::toString
      );

      // 만들 컬렉터를 스트림에 적용.
      String collect6 = list.stream().collect(foodNameCollector);

      // burger | chips | coke | soda
      System.out.println(collect6);

   }

   public void doMatch() {
      List<Food> list = new ArrayList<>();
      list.add(new Food("burger", 520));
      list.add(new Food("chips", 230));
      list.add(new Food("coke", 143));
      list.add(new Food("soda", 143));

      // 하나라도 만족하는가? (anyMatch);
      boolean b = list.stream().anyMatch((food -> food.getCal() > 300));
      System.out.println(b);

      //모두 조건을 만족하는가? (allMatch)
      b = list.stream().allMatch(food -> food.getCal() > 100);
      System.out.println(b);

      //모두 조건을 만족하지 않는가? (noneMatch)
      b = list.stream().noneMatch(food -> food.getCal() < 1000);
      System.out.println(b);
   }
}

class Food {
   public Food(String name, int cal) {
      this.name = name;
      this.cal = cal;
   }

   private String name;
   private int cal;

   @Override
   public String toString() {
      return String.format("name: %s, cal: %s", name, cal);
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getCal() {
      return cal;
   }

   public void setCal(int cal) {
      this.cal = cal;
   }
}

