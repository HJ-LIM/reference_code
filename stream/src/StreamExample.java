import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

   public static void main(String[] args) {
      Example example = new Example();
//      example.doConverter();
//      example.doFind();
//      example.doReduce();
      example.doFlotMap();
   }
}

class Example{

   //List<V> to Map<K, V>
   public void doConverter(){
      List<Person> personList = new ArrayList<>();
      personList.add(new Person("짱구", 23, "010-1234-1234"));
      personList.add(new Person("유리", 24, "010-2341-2341"));
      personList.add(new Person("철수", 23, "010-3412-3412"));
      personList.add(new Person("맹구", 25, null));

      //Function.identity는 t -> t, 항상 입력된 인자(자신)를 반환합니다.
      Map<String, Person> collect = personList.stream()
          .collect(Collectors.toMap(Person::getName, Function.identity()));
      System.out.println(collect);


      //한편 Collectors.toMap 메서드를 수행할 때 하나의 키에 매핑되는
      // 값이 2개 이상인 경우 IllegalStateException 예외가 발생합니다.
      // 이럴 때는 BinaryOperator를 사용하여 아래와 같이 저장할 값을 선택할 수 있습니다.
      Map<Integer, Person> personMap = personList.stream()
          .collect(Collectors.toMap(
              o -> o.getAge(),
              Function.identity(),
              (oldValue, newValue) -> newValue));

      System.out.println(personMap);

      // List 형태로 담는다.
      Map<Integer, List<Person>> collect1 = personList.stream()
          .collect(Collectors.groupingBy(Person::getAge));
      System.out.println(collect1);

      // null 제외하기
      Stream<String> stream = Stream.of("철수", "훈이", null, "유리", null);
      List<String> collect2 = stream.filter(Objects::nonNull).collect(Collectors.toList());
      System.out.println(collect2);
   }

   public void doFind(){
      List<Person> personList = new ArrayList<>();
      personList.add(new Person("짱구", 23, "010-1234-1234"));
      personList.add(new Person("유리", 24, "010-2341-2341"));
      personList.add(new Person("맹구", 23, "010-3412-3412"));

      Person person = personList.stream()
          .filter(p -> p.getAge() == 23)
          .findFirst().get();

      System.out.println(person.getName());
      
      //단, 일반 스트림에서는 동일한 요소(짱구)가 결과로 나오지만 병렬 스트림에서는 매 실해마다 다를 수 있습니다. 
      // 순서에 상관없이 조건에 충족한 요소를 찾고 싶을 때 findAny 메서드가 효과적일 수 있습니다.
      Person person1 = personList.parallelStream()
          .filter(p -> p.getAge() == 23)
          .findAny().get();

      // 짱구 또는 맹구
      System.out.println(person1);
   }

   public void doSort(){
      List<Person> personList = new ArrayList<>();
      personList.add(new Person("짱구", 25, "010-1234-1234"));
      personList.add(new Person("유리", 24, "010-2341-2341"));
      personList.add(new Person("맹구", 23, "010-3412-3412"));
      personList.add(new Person("훈이", 26, "010-4123-4123"));

      // 맹구, 유리, 짱구, 훈이
      personList.stream()
          .sorted(Comparator.comparing(Person::getAge))
          .forEach(p -> System.out.println(p.getName()));

      // 훈이, 짱구, 유리, 맹구
      personList.stream()
          .sorted(Comparator.comparing(Person::getAge).reversed())
          .forEach(p -> System.out.println(p.getName()));
   }

   //reduce로 결과 구하기
   // reduce 메서드로 스트림을 하나의 결과로 연산할 수 있습니다.
   // 예를 들어 아래와 같이 숫자로 구성된 리스트 내의 요소를 모두 더해 합계(sum)를 구할 수 있습니다.
   public void doReduce(){
      List<Integer> list = List.of(5, 4, 2, 1, 6, 7, 8, 3);
      // 36
      Integer result = list.stream()
          .reduce(0, (value1, value2) -> value1 + value2);

      // 박싱 비용을 줄이기 위한 IntStream처럼 기본형에 특화된 스트림으로도 처리할 수 있습니다. primitive type인 int 형태로 반환됩니다.
      // 36
      int intResult = list.stream()
          // 또는 .mapToInt(x -> x).sum();
          .mapToInt(Integer::intValue).sum();


      List<String> list1 = List.of("Java", "C++", "Python", "Ruby");

      // Python
      String result1 = list1.stream()
          .reduce("Swift", (val1, val2) ->
              val1.length() >= val2.length() ? val1 : val2);
      System.out.println(result1);

   }

   //2차원 배열과 같은 요소를 flatmap 메서드를 사용하여 중첩 구조를 제거하고 단일 컬렉션으로 만들 수 있습니다.
   public void doFlotMap(){
      String[][] names = new String[][]{
          {"짱구", "철수"}, {"훈이", "맹구"}
      };

// 리스트로
      List<String> list = Arrays.stream(names)
          .flatMap(Stream::of)
          .collect(Collectors.toList());

// 1차원 배열로
      String[] flattedNames = Arrays.stream(names)
          .flatMap(Stream::of).toArray(String[]::new);
   }
}

class Person {
   private String name;
   private int age;
   private String phoneNumber;

   public Person(String name, int age, String phoneNumber) {
      this.name = name;
      this.age = age;
      this.phoneNumber = phoneNumber;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
}
