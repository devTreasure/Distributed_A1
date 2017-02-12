package cs455.overlay.util;
import java.util.Random;
import java.util.stream.IntStream;

public class Hello {

	public static void main(String[] args) {
		IntStream ints = new Random().ints(15, 0, 10);
		int[] array = ints.toArray();
		System.out.println(array);
		for (int i : array) {
			System.out.println(i);
		}
	}
}
