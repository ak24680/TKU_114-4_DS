import java.util.Arrays;

public class ArrayMinHeap {
    private int[] heap;
    private int size;

    public ArrayMinHeap(int initialCapacity) {
        this.heap = new int[initialCapacity];
        this.size = 0;
    }

    public void add(int val) {
        if (size == heap.length) {
            resize();
        }
        heap[size] = val;
        size++;
        siftUp(size - 1);
    }

    public Integer peek() {
        if (size == 0) return null;
        return heap[0];
    }

    public Integer remove() {
        if (size == 0) return null;
        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
        return min;
    }

    public int[] snapshot() {
        return Arrays.copyOf(heap, size);
    }

    public int size() {
        return size;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap[index] < heap[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (index * 2 + 1 < size) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            int smallest = left;

            if (right < size && heap[right] < heap[left]) {
                smallest = right;
            }

            if (heap[index] > heap[smallest]) {
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

    private void resize() {
        int newCap = heap.length * 2;
        heap = Arrays.copyOf(heap, newCap);
        System.out.println("[System] Heap 容量不足，自動擴充為: " + newCap);
    }

    public static void main(String[] args) {
        ArrayMinHeap minHeap = new ArrayMinHeap(4); // 初始容量設小以測試自動擴充

        int[] testData = {45, 12, 85, 32, 89, 39, 69, 44, 42, 1, 68, 53, 2, 8, 9, 100, 77, 23, 6, 11};
        System.out.println("--- 依序加入 20 筆資料 ---");
        for (int num : testData) {
            minHeap.add(num);
        }

        System.out.println("\n目前 Heap Snapshot: " + Arrays.toString(minHeap.snapshot()));
        System.out.println("最小值 (peek): " + minHeap.peek());

        System.out.println("\n--- 依序取出 (Heap Sort 效果) ---");
        while (minHeap.size() > 0) {
            System.out.print(minHeap.remove() + " ");
        }
        System.out.println();
    }
}