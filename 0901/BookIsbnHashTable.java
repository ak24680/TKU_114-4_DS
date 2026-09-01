public class BookIsbnHashTable {

    private static class Entry {
        String isbn;
        String title;
        Entry next;

        Entry(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    private Entry[] buckets;
    private int capacity;
    private int size;

    public BookIsbnHashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    private int getIndex(String isbn) {
        return Math.abs(isbn.hashCode()) % capacity;
    }

    public void put(String isbn, String title) {
        int index = getIndex(isbn);
        Entry curr = buckets[index];

        while (curr != null) {
            if (curr.isbn.equals(isbn)) {
                curr.title = title; // 更新書名
                return;
            }
            curr = curr.next;
        }

        Entry newEntry = new Entry(isbn, title);
        newEntry.next = buckets[index];
        buckets[index] = newEntry;
        size++;
    }

    public String get(String isbn) {
        int index = getIndex(isbn);
        Entry curr = buckets[index];
        while (curr != null) {
            if (curr.isbn.equals(isbn)) return curr.title;
            curr = curr.next;
        }
        return null;
    }

    public boolean remove(String isbn) {
        int index = getIndex(isbn);
        Entry curr = buckets[index];
        Entry prev = null;

        while (curr != null) {
            if (curr.isbn.equals(isbn)) {
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

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    public void bucketReport() {
        System.out.println("=== Hash Table Bucket Report ===");
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket " + i + ": ");
            Entry curr = buckets[i];
            if (curr == null) {
                System.out.println("[Empty]");
            } else {
                while (curr != null) {
                    System.out.print("[" + curr.isbn + " : " + curr.title + "] -> ");
                    curr = curr.next;
                }
                System.out.println("null");
            }
        }
        System.out.println("Size: " + size() + ", Load Factor: " + String.format("%.2f", getLoadFactor()));
    }

    public static void main(String[] args) {
        BookIsbnHashTable table = new BookIsbnHashTable(5);

        table.put("978-3-16-148410-0", "Java Programming");
        table.put("978-0-13-468599-1", "Data Structures");
        table.put("978-0-262-03384-8", "Algorithms");

        table.bucketReport();

        System.out.println("\n--- 搜尋與更新測試 ---");
        System.out.println("搜尋 '978-0-13-468599-1': " + table.get("978-0-13-468599-1"));
        table.put("978-0-13-468599-1", "Data Structures 2nd Ed.");
        System.out.println("更新後搜尋: " + table.get("978-0-13-468599-1"));

        System.out.println("\n--- 刪除測試 ---");
        table.remove("978-3-16-148410-0");
        table.bucketReport();
    }
}