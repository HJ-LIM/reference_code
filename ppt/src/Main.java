import java.util.List;

public class Main {
	public static void main(String[] args) {

		List<String> list = List.of("blue", "yellow", "red");
		list.forEach(System.out::println);
		//blue
		//yellow
		//red
	}
}
