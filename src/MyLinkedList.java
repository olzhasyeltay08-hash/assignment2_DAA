public class MyLinkedList {

    private class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;
    private long accesses;
    private long comparisons;

    public MyLinkedList() {
        head = null;
        size = 0;
        accesses = 0;
        comparisons = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
            size++;
            return;
        }
        Node current = head;
        accesses++;
        while (current.next != null) {
            accesses++;
            current = current.next;
        }
        current.next = newNode;
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            size++;
            return;
        }
        Node current = head;
        accesses++;
        for (int i = 0; i < index - 1; i++) {
            accesses++;
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        accesses++;
        for (int i = 0; i < index - 1; i++) {
            accesses++;
            current = current.next;
        }
        current.next = current.next.next;
        size--;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        Node current = head;
        accesses++;
        for (int i = 0; i < index; i++) {
            current = current.next;
            accesses++;
        }
        return current.data;
    }

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            comparisons++;
            if (current.data == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public long getAccesses() {
        return accesses;
    }

    public void resetAccesses() {
        accesses = 0;
    }

    public long getComparisons() {
        return comparisons;
    }

    public void resetComparisons() {
        comparisons = 0;
    }
}