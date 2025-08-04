package helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Sorter {
    /**
     * Reads a text file containing words and their indices, sorts them using merge
     * sort,
     * and writes the sorted words to a new file.
     * The output file will be named <baseName>.txt_sorted.txt.
     *
     * @param baseName the base name of the book (without extension)
     * @return the time taken to complete the operation in milliseconds
     */
    public static long run(String baseName) {
        String inputPath = "src/books/" + baseName + ".txt_words.txt";
        String outputPath = "src/books/" + baseName + ".txt_sorted.txt";

        long startTime = System.currentTimeMillis();

        try {
            // Read words and their original indices from the input file
            Scanner in = new Scanner(new File(inputPath));
            int wordCount = in.nextInt();

            String[] words = new String[wordCount];
            int[] indices = new int[wordCount];

            for (int i = 0; i < wordCount; i++) {
                words[i] = in.next();
                indices[i] = in.nextInt();
            }
            in.close();

            // Sort the words alphabetically using merge sort
            mergeSort(words, indices, 0, wordCount - 1);

            // Write the sorted words and their original indices to the output file
            PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
            writer.println(wordCount);
            for (int i = 0; i < wordCount; i++) {
                writer.println(words[i] + " " + indices[i]);
            }
            writer.close();

            long endTime = System.currentTimeMillis();
            System.out.println("Merge sort completed in " + (endTime - startTime) + " ms\n");
            System.out.println(outputPath + " created.");

            return endTime - startTime;

        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
            return -1;
        }
    }

    // Recursive merge sort for strings with parallel index tracking
    private static void mergeSort(String[] words, int[] indices, int left, int right) {
        if (left >= right)
            return;

        int mid = (left + right) / 2;
        mergeSort(words, indices, left, mid);
        mergeSort(words, indices, mid + 1, right);
        merge(words, indices, left, mid, right);
    }

    // Merge two sorted halves into a combined sorted section
    private static void merge(String[] words, int[] indices, int left, int mid, int right) {
        int size = right - left + 1;
        String[] tempWords = new String[size];
        int[] tempIndices = new int[size];

        int i = left, j = mid + 1, k = 0;

        // Copy any remaining items from the left half
        while (i <= mid && j <= right) {
            if (words[i].compareTo(words[j]) <= 0) {
                tempWords[k] = words[i];
                tempIndices[k] = indices[i];
                i++;
            } else {
                tempWords[k] = words[j];
                tempIndices[k] = indices[j];
                j++;
            }
            k++;
        }

        // Copy any remaining items from the right half
        while (i <= mid) {
            tempWords[k] = words[i];
            tempIndices[k] = indices[i];
            i++;
            k++;
        }

        while (j <= right) {
            tempWords[k] = words[j];
            tempIndices[k] = indices[j];
            j++;
            k++;
        }

        // Write the sorted section back into the original arrays
        for (int m = 0; m < size; m++) {
            words[left + m] = tempWords[m];
            indices[left + m] = tempIndices[m];
        }
    }
}