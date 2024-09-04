import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PerformanceCheck {

    private static final int NUM_THREADS = 2;
    private static final int NUM_ITERATIONS = 10000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Comparing ArrayList and CopyOnWriteArrayList");

        // Measure time taken by ArrayList (Non-Concurrent Collection)
        List<Integer> arrayList = new ArrayList<>();
        long arrayListTime = measureTime(arrayList);
        System.out.println("Time taken by ArrayList: " + arrayListTime + " ms");

        // Measure time taken by CopyOnWriteArrayList (Concurrent Collection)
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        long copyOnWriteArrayListTime = measureTime(copyOnWriteArrayList);
        System.out.println("Time taken by CopyOnWriteArrayList: " + copyOnWriteArrayListTime + " ms");
    }

    // Method to measure the time taken to perform concurrent operations on a list
    private static long measureTime(List<Integer> list) throws InterruptedException {
        // Create a fixed thread pool with NUM_THREADS threads
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        // Record the start time
        long startTime = System.currentTimeMillis();

        // Submit tasks to the executor
        for (int i = 0; i < NUM_THREADS; i++) {
            executor.execute(() -> {
                // Each thread adds NUM_ITERATIONS elements to the list
                for (int j = 0; j < NUM_ITERATIONS; j++) {
                    list.add(j);
                }
            });
        }

        // Shutdown the executor and wait for all tasks to complete
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        // Record the end time
        long endTime = System.currentTimeMillis();

        // Return the elapsed time
        return endTime - startTime;
    }
}