package helpers;

public class Timings {
    /**
     * Runs the indexing and sorting operations for multiple books and prints the
     * average time taken.
     *
     * @param baseNames an array of base names of the books (without extension)
     */

    public static void runTimingForBooks(String[] baseNames) {
        // Track total time spent indexing and sorting all books
        long totalIndexTime = 0;
        long totalSortTime = 0;

        // Go through each book name using its index
        for (int i = 0; i < baseNames.length; i++) {
            String baseName = baseNames[i];
            System.out.println("Timing: " + baseName);

            // Run the indexer and sorter for this book
            long timeIndex = WordIndexer.run(baseName);
            long timeSort = Sorter.run(baseName);

            // Add the times to the totals
            totalIndexTime += timeIndex;
            totalSortTime += timeSort;
        }

        System.out.println("\nAverages");
        System.out.println("WordIndexer average: " + (totalIndexTime / baseNames.length) + " ms.");
        System.out.println("Sorter average: " + (totalSortTime / baseNames.length) + " ms.\n");
    }
}
