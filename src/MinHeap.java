import java.util.Arrays;

public class MinHeap {
    private int[] heap;
    private int size;
    private long comparisons;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        comparisons = 0;
    }

    public void insert(int x) {
        ensureCapacity();
        heap[size] = x;
        size++;
        heapifyUp(size - 1);
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            comparisons++;
            if (heap[index] < heap[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                comparisons++;
                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                comparisons++;
                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
    }

    public int size() {
        return size;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetComparisons() {
        comparisons = 0;
    }
}