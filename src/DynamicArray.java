import java.util.Arrays;

public class DynamicArray {
    private int[] data;
    private int size;
    private long accesses;
    private long movements;
    private long comparisons;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        accesses = 0;
        movements = 0;
        comparisons = 0;
    }

    public void add(int x) {
        ensureCapacity();
        data[size] = x;
        size++;
        accesses++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movements++;
            accesses += 2;
        }
        data[index] = x;
        size++;
        accesses++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movements++;
            accesses += 2;
        }
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        accesses++;
        return data[index];
    }

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            comparisons++;
            accesses++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
        }
    }

    public long getAccesses() {
        return accesses;
    }

    public void resetAccesses() {
        accesses = 0;
    }

    public long getMovements() {
        return movements;
    }

    public void resetMovements() {
        movements = 0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetComparisons() {
        comparisons = 0;
    }
}