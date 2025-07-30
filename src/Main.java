import java.util.Scanner;
import helpers.Timings;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] baseNames = new String[3];

        // get 3 books
        for (int i = 0; i < 3; i++) {
            System.out.print("Type the name of book " + (i + 1) + " (no .txt): ");
            baseNames[i] = scanner.nextLine().trim();
        }

        // Run the timings
        Timings.runTimingForBooks(baseNames);

        // Only use first book for the remaining steps
        String firstBook = baseNames[0];
        System.out.println("\nNow we are working with " + firstBook + ".\n");
        helpers.IndexBuilder.main(new String[] { firstBook });
        helpers.ContextSearch.main(new String[] { firstBook });

        scanner.close();
    }
}
