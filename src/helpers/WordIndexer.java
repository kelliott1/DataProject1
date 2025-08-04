package helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class WordIndexer {
    /**
     * Reads a text file, counts the words, and writes them to a new file with their
     * indices.
     * The output file will be named <baseName>.txt_words.txt.
     *
     * @param baseName the base name of the book (without extension)
     * @return the time taken to complete the operation in milliseconds
     */
    public static long run(String baseName) {
        String inputPath = "src/books/" + baseName + ".txt";
        String outputPath = "src/books/" + baseName + ".txt_words.txt";

        long startTime = System.currentTimeMillis();

        try {
            // First pass: count how many words are in the file
            Scanner counter = new Scanner(new File(inputPath));
            int totalWords = 0;

            while (counter.hasNext()) {
                counter.next();
                totalWords++;
            }
            counter.close();

            // Second pass: read all words into an array
            Scanner reader = new Scanner(new File(inputPath));
            String[] words = new String[totalWords];
            for (int i = 0; i < totalWords && reader.hasNext(); i++) {
                words[i] = reader.next();
            }
            reader.close();

            // Write total word count and each word with its index to the output file
            PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
            writer.println(totalWords);
            for (int i = 0; i < totalWords; i++) {
                writer.println(words[i] + " " + i);
            }
            writer.close();

            long endTime = System.currentTimeMillis();
            // System.out.println(outputPath + " created.");
            System.out.println("Indexing completed in " + (endTime - startTime) + " ms");

            return endTime - startTime;

        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
            return -1;
        }
    }
}