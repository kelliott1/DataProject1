package helpers;

import java.io.File;
import java.util.Scanner;

public class QuerySearch {
    /**
     * Searches for a word in the index and prints its occurrences with context.
     *
     * @param baseName the base name of the book (without extension)
     */
    private static String[] words;
    private static String[] indexLines;
    private static String[] originalText;

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
                words[i] = line.substring(0, space);
                indexLines[i] = line.substring(space + 1);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
        }
    }

    private static void loadOriginal(String baseName) {
        String path = "src/books/" + baseName + ".txt_words.txt";

        try {
            Scanner reader = new Scanner(new File(path));
            int count = reader.nextInt();

            originalText = new String[count];

            for (int i = 0; i < count; i++) {
                if (reader.hasNext()) {
                    originalText[i] = reader.next();
                    reader.nextInt(); // skip index
                }
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
        }
    }

    private static int binarySearch(String[] list, String target) {
        int start = 0;
        int end = list.length - 1;

        while (start <= end) {
            int middle = (start + end) / 2;
            int compare = list[middle].compareTo(target);

            if (compare == 0) {
                return middle;
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
        loadIndex(baseName);
        loadOriginal(baseName);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a word or 'exit' to stop: ");
            String word = scanner.nextLine().trim().toLowerCase();

            if (word.equals("exit")) {
                break;
            }

            int result = binarySearch(words, word);

            if (result == -1) {
                System.out.println("Can't find " + word + " in the text");
            } else {
                System.out.println(word + ": " + indexLines[result]);

                String[] parts = indexLines[result].split(" ");

                for (String part : parts) {
                    int index = Integer.parseInt(part);
                    System.out.print(index + ": ");

                    for (int i = index - 2; i <= index + 2; i++) {
                        if (i >= 0 && i < originalText.length) {
                            System.out.print(originalText[i] + " ");
                        }
                    }

                    System.out.println();
                }
            }
        }

        scanner.close();
    }
}