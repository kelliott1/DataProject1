package helpers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class IndexBuilder {
    /**
     * Reads a sorted text file, groups duplicate words, and writes them to a new
     * index file.
     * The output file will be named <baseName>.txt_index.txt.
     *
     * @param baseName the base name of the book (without extension)
     */

    public static void main(String[] args) {

        String baseName = args[0];
        String inputPath = "src/books/" + baseName + ".txt_sorted.txt";
        String outputPath = "src/books/" + baseName + ".txt_index.txt";

        try {
            Scanner in = new Scanner(new File(inputPath));
            PrintWriter out = new PrintWriter(new FileWriter(outputPath));

            int totalWords = in.nextInt();
            in.nextLine(); // skip to next line

            String previousWord = "";
            int uniqueCount = 0;
            StringBuilder line = new StringBuilder();

            for (int i = 0; i < totalWords; i++) {
                String word = in.next();
                int index = in.nextInt();

                if (!word.equals(previousWord)) {
                    if (!previousWord.equals("")) {
                        out.println(line.toString());
                    }
                    line = new StringBuilder(word + " " + index);
                    previousWord = word;
                    uniqueCount++;
                } else {
                    line.append(" ").append(index);
                }
            }

            out.println(line.toString());

            in.close();
            out.close();

            // Add unique count at the top
            Scanner reader = new Scanner(new File(outputPath));
            StringBuilder full = new StringBuilder();
            full.append(uniqueCount).append("\n");

            while (reader.hasNextLine()) {
                full.append(reader.nextLine()).append("\n");
            }
            reader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(outputPath));
            writer.print(full.toString());
            writer.close();

            // System.out.println("Index created. Duplicate words are grouped together.");

        } catch (Exception e) {
            System.out.println("Fix your error: " + e.getMessage());
        }
    }
}