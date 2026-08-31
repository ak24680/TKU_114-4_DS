public class IntegerStringHashTable {

    private static class Entry {
        int key;
        String value;
        Entry next;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry[] buckets;
    private int capacity;
    private int size;

    public IntegerStringHashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    private int getBucketIndex(int key) {
        return Math.abs(key) % capacity;
    }

    public void put(int key, String value) {
        int index = getBucketIndex(key);
        Entry curr = buckets[index];

        while (curr != null) {
            if (curr.key == key) {
                curr.value = value; // key 已存在，覆蓋 value，size 不增加
                return;
            }
            curr = curr.next;
        }

        Entry newEntry = new Entry(key, value);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public String get(int key) {
        int index = getBucketIndex(key);
        Entry curr = buckets[index];
        while (curr != null) {
            if (curr.key == key) return curr.value;
            curr = curr.next;
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public boolean remove(int key) {
        int index = getBucketIndex(key);
        Entry curr = buckets[index];
        Entry prev = null;

        while (curr != null) {
            if (curr.key == key) {
                if (prev == null) {
                    buckets[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void bucketReport() {
        System.out.println("=== Bucket Status Report ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Entry curr = buckets[i];
            if (curr == null) {
                System.out.println("[Empty]");
            } else {
                while (curr != null) {
                    System.out.print("[" + curr.key + " -> " + curr.value + "] -> ");
                    curr = curr.next;
                }
                System.out.println("null");
            }
        }
        System.out.println("Total Size: " + size);
    }

    public static void main(String[] args) {
        IntegerStringHashTable table = new IntegerStringHashTable(5);

        table.put(1, "Alice");
        table.put(6, "Bob");     // Hash collision with 1 (6 % 5 = 1)
        table.put(11, "Charlie");// Hash collision with 1 (11 % 5 = 1)
        table.put(2, "David");

        table.bucketReport();

        System.out.println("\n--- 覆蓋測試 ---");
        table.put(6, "Bobby"); // 更新 key 6
        System.out.println("Get 6: " + table.get(6));
        System.out.println("Size (應該維持 4): " + table.size());

        System.out.println("\n--- 移除測試 ---");
        table.remove(6);
        System.out.println("Contains 6: " + table.containsKey(6));
        table.bucketReport();
    }
}