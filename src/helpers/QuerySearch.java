package helpers;

import java.io.File;
import java.util.Scanner;

public class QuerySearch {
    /**
     * Searches for a word in the index and prints its occurrences with context.
     *
     * @param baseName the base name of the book (without extension)
     */

    // Loaded from the index file
    private static String[] words;
    private static String[] indexLines;
    // Loaded from the original word list
    private static String[] originalText;

    // Reads the index file and splits it into words and their index lines
    private static void loadIndex(String baseName) {
        String inputPath = "src/books/" + baseName + ".txt_index.txt";

        try {
            Scanner reader = new Scanner(new File(inputPath));
            int count = reader.nextInt();
            reader.nextLine();

            words = new String[count];
            indexLines = new String[count];

            for (int i = 0; i < count; i++) {
                String line = reader.nextLine();
                int space = line.indexOf(" ");
                words[i] = line.substring(0, space); // Word itself
                indexLines[i] = line.substring(space + 1); // List of positions
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
        }
    }

    // Reads the word list file and stores the words in order
    private static void loadOriginal(String baseName) {
        String path = "src/books/" + baseName + ".txt_words.txt";

        try {
            Scanner reader = new Scanner(new File(path));
            int count = reader.nextInt();

            originalText = new String[count];

            for (int i = 0; i < count; i++) {
                if (reader.hasNext()) {
                    originalText[i] = reader.next(); // Get word
                    reader.nextInt(); // skip index
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
        }
    }

    // Binary search for a word in the sorted index
    private static int binarySearch(String[] list, String target) {
        int start = 0;
        int end = list.length - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int compare = list[middle].compareTo(target);

            if (compare == 0) {
                return middle; // Found it
            } else if (compare < 0) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {

        String baseName = args[0];
        loadIndex(baseName); // Load word-to-index mapping
        loadOriginal(baseName); // Load full text in word order

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a word or 'exit' to stop: ");
            String word = scanner.nextLine().trim().toLowerCase();

            if (word.equals("exit")) {
                break;
            }

            int result = binarySearch(words, word); // Search the index

            if (result == -1) {
                System.out.println("Can't find " + word + " in the text");
            } else {
                // Show index info for the word
                System.out.println(word + ": " + indexLines[result]);

                // For each index the word appeared at, show surrounding words
                String[] parts = indexLines[result].split(" ");

                for (String part : parts) {
                    int index = Integer.parseInt(part);
                    System.out.print(index + ": ");

                    for (int i = index - 2; i <= index + 2; i++) {
                        if (i >= 0 && i < originalText.length) {
                            System.out.print(originalText[i] + " ");
                        }
                    }

                    System.out.println(); // Line break after each context block
                }
            }
        }

        scanner.close();
    }
}