package helpers;

import java.io.File;
import java.util.Scanner;

public class ContextSearch {
    /**
     * Searches for a word in the index and prints its occurrences with context.
     *
     * @param baseName the base name of the book (without extension)
     */

    // Binary search through a sorted list of words
    public static int binarySearch(String[] list, String target) {
        int low = 0;
        int high = list.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = list[mid].compareTo(target);

            if (cmp == 0)
                return mid;
            else if (cmp < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }

        return -1; // loop ended without finding the word
    }

    public static void main(String[] args) {

        String baseName = args[0];
        String indexPath = "src/books/" + baseName + ".txt_index.txt";
        String wordsPath = "src/books/" + baseName + ".txt_words.txt";

        String[] searchWords; // List of indexed words
        String[] indexStrings; // Index line (positions) for each word
        String[] originalWords; // Full word list in order

        try {
            // Load the index file
            Scanner indexReader = new Scanner(new File(indexPath));
            int uniqueWordCount = indexReader.nextInt();
            indexReader.nextLine();

            searchWords = new String[uniqueWordCount];
            indexStrings = new String[uniqueWordCount];

            for (int i = 0; i < uniqueWordCount; i++) {
                String line = indexReader.nextLine();
                int space = line.indexOf(" ");
                searchWords[i] = line.substring(0, space); // word
                indexStrings[i] = line.substring(space + 1); // index list
            }
            indexReader.close();

            // Load the original word file
            Scanner wordReader = new Scanner(new File(wordsPath));
            int totalWords = wordReader.nextInt();
            originalWords = new String[totalWords];

            for (int i = 0; i < totalWords; i++) {
                if (wordReader.hasNext()) {
                    originalWords[i] = wordReader.next(); // grab word
                    wordReader.nextInt(); // skip index
                }
            }
            wordReader.close();

        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
            return;
        }

        // prompt for words to search
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a word (or 'exit' to quit): ");
            String query = scanner.nextLine().trim().toLowerCase();

            if (query.equals("exit")) {
                System.out.println("We done.");
                break;
            }

            // Search for the word in the index
            int result = binarySearch(searchWords, query);
            if (result == -1) {
                System.out.println("Can't find " + query + " in the text");
                continue;
            }

            // For each loc the word appears, print its pos & two words before/after
            String[] positions = indexStrings[result].split(" ");
            for (int j = 0; j < positions.length; j++) {
                String pos = positions[j];
                int idx = Integer.parseInt(pos);
                System.out.print(idx + ": ");

                for (int i = idx - 2; i <= idx + 2; i++) {
                    if (i >= 0 && i < originalWords.length) {
                        System.out.print(originalWords[i] + " ");
                    }
                }
                System.out.println();
            }

        }

        scanner.close();
    }
}