package helpers;

public class Timings {
    /**
     * Runs the indexing and sorting operations for multiple books and prints the
     * average time taken.
     *
     * @param baseNames an array of base names of the books (without extension)
     */

    public static void runTimingForBooks(String[] baseNames) {
        long totalIndexTime = 0;
        long totalSortTime = 0;

        for (String baseName : baseNames) {
            System.out.println("Timing: " + baseName);

            long timeIndex = WordIndexer.run(baseName);
            long timeSort = Sorter.run(baseName);

            totalIndexTime += timeIndex;
            totalSortTime += timeSort;
        }

        System.out.println("\nAverages");
        System.out.println("WordIndexer average: " + (totalIndexTime / baseNames.length) + " ms.");
        System.out.println("Sorter average: " + (totalSortTime / baseNames.length) + " ms.\n");
    }
}
