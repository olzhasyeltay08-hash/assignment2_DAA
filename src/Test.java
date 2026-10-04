import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        testDynamicArray();
        testMyLinkedList();
        testMinHeap();
        System.out.println("All tests passed");
    }

    public static void testDynamicArray() {
        DynamicArray array = new DynamicArray();

        try {
            array.get(0);
            throw new RuntimeException("Empty DynamicArray test failed");
        } catch (IndexOutOfBoundsException ignored) {}

        array.add(10);
        if (array.get(0) != 10) {
            throw new RuntimeException("One element test failed");
        }

        array.add(20);
        array.add(30);
        array.add(1, 15);
        if (array.get(0) != 10 || array.get(1) != 15 || array.get(3) != 30) {
            throw new RuntimeException("Multiple elements test failed");
        }

        array.add(15);
        if (!array.contains(15)) {
            throw new RuntimeException("Duplicate test failed");
        }

        array.remove(0);
        try {
            array.get(-1);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException ignored) {}

        DynamicArray large = new DynamicArray();
        for (int i = 0; i < 100000; i++) {
            large.add(i);
        }
        if (large.get(99999) != 99999) {
            throw new RuntimeException("Large input test failed");
        }

        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        DynamicArray actual = new DynamicArray();
        actual.add(1);
        actual.add(2);
        actual.add(3);
        for (int i = 0; i < 3; i++) {
            if (actual.get(i) != expected.get(i)) {
                throw new RuntimeException("ArrayList comparison failed");
            }
        }
    }

    public static void testMyLinkedList() {
        MyLinkedList list = new MyLinkedList();

        try {
            list.get(0);
            throw new RuntimeException("Empty MyLinkedList test failed");
        } catch (IndexOutOfBoundsException ignored) {}

        list.add(10);
        if (list.get(0) != 10) {
            throw new RuntimeException("One element test failed");
        }

        list.add(20);
        list.add(30);
        list.add(1, 15);
        if (list.get(0) != 10 || list.get(1) != 15 || list.get(3) != 30) {
            throw new RuntimeException("Multiple elements test failed");
        }

        list.add(15);
        if (!list.contains(15)) {
            throw new RuntimeException("Duplicate test failed");
        }

        list.remove(0);
        try {
            list.get(-1);
            throw new RuntimeException("Invalid index test failed");
        } catch (IndexOutOfBoundsException ignored) {}

        MyLinkedList large = new MyLinkedList();
        for (int i = 99999; i >= 0; i--) {
            large.add(0, i);
        }
        if (large.get(99999) != 99999) {
            throw new RuntimeException("Large input test failed");
        }

        java.util.LinkedList<Integer> expected = new java.util.LinkedList<>();
        expected.add(1);
        expected.add(2);
        expected.add(3);
        MyLinkedList actual = new MyLinkedList();
        actual.add(1);
        actual.add(2);
        actual.add(3);
        for (int i = 0; i < 3; i++) {
            if (actual.get(i) != expected.get(i)) {
                throw new RuntimeException("Java LinkedList comparison failed");
            }
        }
    }

    public static void testMinHeap() {
        MinHeap heap = new MinHeap();

        try {
            heap.peekMin();
            throw new RuntimeException("Empty heap test failed");
        } catch (IllegalStateException ignored) {}

        heap.insert(10);
        if (heap.peekMin() != 10) {
            throw new RuntimeException("One element heap test failed");
        }

        heap.insert(5);
        heap.insert(20);
        heap.insert(5);
        if (heap.peekMin() != 5) {
            throw new RuntimeException("Heap property test failed");
        }

        int previous = Integer.MIN_VALUE;
        while (true) {
            try {
                int current = heap.extractMin();
                if (current < previous) {
                    throw new RuntimeException("Heap order test failed");
                }
                previous = current;
            } catch (IllegalStateException e) {
                break;
            }
        }

        MinHeap large = new MinHeap();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int i = 100000; i >= 1; i--) {
            large.insert(i);
            expected.add(i);
            if (!Objects.equals(large.peekMin(), expected.peek())) {
                throw new RuntimeException("Heap insertion validation failed");
            }
        }
        while (!expected.isEmpty()) {
            if (!Objects.equals(large.extractMin(), expected.poll())) {
                throw new RuntimeException("Heap extraction validation failed");
            }
        }
    }
}