import java.util.*;

/**
 * This is a skeleton program. You'll
 * need to modify it, either by adding code in main
 * or in methods called from main.
 * @author Scotty Shaw
 *
 */

public class HangmanStats {
	public static void main(String[] args) {
		HangmanFileLoader loader = new HangmanFileLoader();
		loader.readFile("lowerwords.txt");

		int lettersInWord = 4;
		int sumUniqueWords = 0;
		boolean added = true;
		double numTests = 1000.0;
		double average = 0;
		HashSet<String> set = new HashSet<String>();

		for (int j = 4; j <= 20; j++) {
			for (int k = 0; k < 1000; k++) {
				set.add(loader.getRandomWord(j));
			}
			System.out.printf("number of " + j + " letter words = %d\n", set.size());
			set = new HashSet<String>();
		}

		System.out.println();

		for (int i = 0; i < numTests; i++) {
			HashSet<String> set2 = new HashSet<String>();
			for (int k = 0; k < 1000; k++) {
				added = set2.add(loader.getRandomWord(lettersInWord));
				if (added == false) {
					break;
				}
				sumUniqueWords++;
			}
		}

		average = (double) (sumUniqueWords) / numTests;
		System.out.printf("Average number of unique " + lettersInWord + " letter words added to set before first duplicate = %f\n", average);

		//		FOR REFERENCE
		//		This line is equivalent to:
		//			System.out.println("number of 4 letter words = " + set.size());
		//		printf allows printing something in a formatted manner
		//		%d always denote printing an integer
		//		\n moves to the next line, like println
	}
}
