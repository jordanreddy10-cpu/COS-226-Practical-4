public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] threadCounts = {2, 4, 8, 16};
        int operationsPerThread = 1000;

        System.out.println("Threads | Coarse-Grained Time | Fine-Grained Time");
        
        for (int numberOfThreads : threadCounts) {

            CoarseList coarseList = new CoarseList();
            long coarseTime = runCoarseTest(coarseList, numberOfThreads, operationsPerThread);
            
            FineList fineList = new FineList();
            long fineTime = runFineTest(fineList, numberOfThreads, operationsPerThread);
            
            System.out.printf("%-7d | %-19s | %s%n", numberOfThreads, coarseTime + " ms", fineTime + " ms");
        }
    }

    private static long runCoarseTest(CoarseList list, int numberOfThreads, int operationsPerThread) throws InterruptedException {
        Thread[] threads = new Thread[numberOfThreads];
        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadID = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    int value = (threadID * 1000) + (j % 1000);
                    if (j % 3 == 0) {
                        list.add(value);
                    } else if (j % 3 == 1) {
                        list.contains(value);
                    } else {
                        list.remove(value);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }

    private static long runFineTest(FineList list, int numberOfThreads, int operationsPerThread) throws InterruptedException {
        Thread[] threads = new Thread[numberOfThreads];
        long startTime = System.nanoTime();

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadID = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    int value = (threadID * 1000) + (j % 1000);
                    if (j % 3 == 0) {
                        list.add(value);
                    } else if (j % 3 == 1) {
                        list.contains(value);
                    } else {
                        list.remove(value);
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000;
    }
}