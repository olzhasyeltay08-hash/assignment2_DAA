import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {
    private static PrintWriter csvWriter;

    public static void main(String[] args) {
        try {
            File resultsDir = new File("results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            csvWriter = new PrintWriter(new FileWriter("results/results.csv"));
            csvWriter.println("workload,variant,structure,n,time_ms,steps,moves,comparisons");

            int[] sizes = {100, 1000, 10000, 100000};
            for (int n : sizes) {
                randomAccess(n);
                search(n);
                insertionRemoval(n);
                heapWorkload(n);
            }

            csvWriter.close();
            System.out.println("Нәтижелер results/results.csv файлына сәтті сақталды!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void randomAccess(int n) {
        Random random = new Random(42);
        int[] values = new int[n];
        int[] indices = new int[10000];
        for (int i = 0; i < n; i++) values[i] = random.nextInt();
        for (int i = 0; i < 10000; i++) indices[i] = random.nextInt(n);

        DynamicArray dynamicArray = new DynamicArray();
        MyLinkedList linkedList = new MyLinkedList();
        for (int i = 0; i < n; i++) dynamicArray.add(values[i]);
        for (int i = n - 1; i >= 0; i--) linkedList.add(0, values[i]);

        long arrayTotalTime = 0, listTotalTime = 0;
        long arrayAccesses = 0, listAccesses = 0;

        for (int run = 0; run < 5; run++) {
            dynamicArray.resetAccesses();
            long start = System.nanoTime();
            for (int index : indices) dynamicArray.get(index);
            long end = System.nanoTime();
            if (run > 0) arrayTotalTime += (end - start);
            arrayAccesses = dynamicArray.getAccesses();

            linkedList.resetAccesses();
            start = System.nanoTime();
            for (int index : indices) linkedList.get(index);
            end = System.nanoTime();
            if (run > 0) listTotalTime += (end - start);
            listAccesses = linkedList.getAccesses();
        }

        double arrayTimeMs = (arrayTotalTime / 4.0) / 1_000_000.0;
        double listTimeMs = (listTotalTime / 4.0) / 1_000_000.0;

        csvWriter.printf("W1,-,DynamicArray,%d,%.4f,%d,0,0%n", n, arrayTimeMs, arrayAccesses);
        csvWriter.printf("W1,-,MyLinkedList,%d,%.4f,%d,0,0%n", n, listTimeMs, listAccesses);
    }

    public static void search(int n) {
        Random random = new Random(42);
        int[] values = new int[n];
        int[] searchValues = new int[1000];
        for (int i = 0; i < n; i++) values[i] = random.nextInt();
        for (int i = 0; i < 1000; i++) searchValues[i] = random.nextInt();

        DynamicArray dynamicArray = createArray(values);
        MyLinkedList linkedList = createList(values);

        long arrayTotalTime = 0, listTotalTime = 0;
        long arrayComparisons = 0, listComparisons = 0;

        for (int run = 0; run < 5; run++) {
            dynamicArray.resetComparisons();
            long start = System.nanoTime();
            for (int value : searchValues) dynamicArray.contains(value);
            long end = System.nanoTime();
            if (run > 0) arrayTotalTime += (end - start);
            arrayComparisons = dynamicArray.getComparisons();

            linkedList.resetComparisons();
            start = System.nanoTime();
            for (int value : searchValues) linkedList.contains(value);
            end = System.nanoTime();
            if (run > 0) listTotalTime += (end - start);
            listComparisons = linkedList.getComparisons();
        }

        double arrayTimeMs = (arrayTotalTime / 4.0) / 1_000_000.0;
        double listTimeMs = (listTotalTime / 4.0) / 1_000_000.0;

        csvWriter.printf("W2,-,DynamicArray,%d,%.4f,0,0,%d%n", n, arrayTimeMs, arrayComparisons);
        csvWriter.printf("W2,-,MyLinkedList,%d,%.4f,0,0,%d%n", n, listTimeMs, listComparisons);
    }

    public static void insertionRemoval(int n) {
        Random random = new Random(42);
        int[] values = new int[n];
        int[] insertValues = new int[1000];
        for (int i = 0; i < n; i++) values[i] = random.nextInt();
        for (int i = 0; i < 1000; i++) insertValues[i] = random.nextInt();

        long arrHeadTime = 0, listHeadTime = 0, arrHeadMoves = 0, listHeadAccesses = 0;
        for (int run = 0; run < 5; run++) {
            DynamicArray arr = createArray(values);
            MyLinkedList list = createList(values);

            arr.resetMovements();
            long start = System.nanoTime();
            for (int v : insertValues) arr.add(0, v);
            long end = System.nanoTime();
            if (run > 0) arrHeadTime += (end - start);
            arrHeadMoves = arr.getMovements();

            list.resetAccesses();
            start = System.nanoTime();
            for (int v : insertValues) list.add(0, v);
            end = System.nanoTime();
            if (run > 0) listHeadTime += (end - start);
            listHeadAccesses = list.getAccesses();
        }

        csvWriter.printf("W3,head,DynamicArray,%d,%.4f,0,%d,0%n", n, (arrHeadTime / 4.0) / 1_000_000.0, arrHeadMoves);
        csvWriter.printf("W3,head,MyLinkedList,%d,%.4f,%d,0,0%n", n, (listHeadTime / 4.0) / 1_000_000.0, listHeadAccesses);

        int middle = n / 2;
        long arrMidTime = 0, listMidTime = 0, arrMidMoves = 0, listMidAccesses = 0;
        for (int run = 0; run < 5; run++) {
            DynamicArray arr = createArray(values);
            MyLinkedList list = createList(values);

            arr.resetMovements();
            long start = System.nanoTime();
            for (int v : insertValues) arr.add(middle, v);
            long end = System.nanoTime();
            if (run > 0) arrMidTime += (end - start);
            arrMidMoves = arr.getMovements();

            list.resetAccesses();
            start = System.nanoTime();
            for (int v : insertValues) list.add(middle, v);
            end = System.nanoTime();
            if (run > 0) listMidTime += (end - start);
            listMidAccesses = list.getAccesses();
        }

        csvWriter.printf("W3,middle,DynamicArray,%d,%.4f,0,%d,0%n", n, (arrMidTime / 4.0) / 1_000_000.0, arrMidMoves);
        csvWriter.printf("W3,middle,MyLinkedList,%d,%.4f,%d,0,0%n", n, (listMidTime / 4.0) / 1_000_000.0, listMidAccesses);
    }

    public static void heapWorkload(int n) {
        Random random = new Random(42);
        int[] values = new int[n];
        for (int i = 0; i < n; i++) values[i] = random.nextInt();

        long totalTime = 0, comparisons = 0;
        for (int run = 0; run < 5; run++) {
            MinHeap heap = new MinHeap();
            heap.resetComparisons();
            long start = System.nanoTime();
            for (int v : values) heap.insert(v);
            for (int i = 0; i < n; i++) heap.extractMin();
            long end = System.nanoTime();
            if (run > 0) totalTime += (end - start);
            comparisons = heap.getComparisons();
        }

        csvWriter.printf("W4,-,MinHeap,%d,%.4f,0,0,%d%n", n, (totalTime / 4.0) / 1_000_000.0, comparisons);
    }

    private static DynamicArray createArray(int[] values) {
        DynamicArray arr = new DynamicArray();
        for (int v : values) arr.add(v);
        return arr;
    }

    private static MyLinkedList createList(int[] values) {
        MyLinkedList list = new MyLinkedList();
        for (int i = values.length - 1; i >= 0; i--) list.add(0, values[i]);
        return list;
    }
}