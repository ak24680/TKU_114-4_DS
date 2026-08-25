import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionChoiceReport {
    public static void main(String[] args) {
        System.out.println("=== 需求 1: 保留搜尋紀錄且允許重複 ===");
        System.out.println("選擇 Interface: List, Implementation: ArrayList");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java");
        searchHistory.add("Data Structure");
        searchHistory.add("Java");
        System.out.println("操作結果: " + searchHistory);

        System.out.println("\n=== 需求 2: 保存不重複會員編號 ===");
        System.out.println("選擇 Interface: Set, Implementation: HashSet");
        Set<String> memberIds = new HashSet<>();
        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M001");
        System.out.println("操作結果: " + memberIds);

        System.out.println("\n=== 需求 3: 以學號查詢成績 ===");
        System.out.println("選擇 Interface: Map, Implementation: HashMap");
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("S01", 95);
        scoreMap.put("S02", 88);
        System.out.println("操作結果 (S01 的成績): " + scoreMap.get("S01"));

        System.out.println("\n=== 需求 4: 依到達順序處理列印工作 ===");
        System.out.println("選擇 Interface: Queue, Implementation: ArrayDeque");
        Queue<String> printQueue = new ArrayDeque<>();
        printQueue.offer("Doc1.pdf");
        printQueue.offer("Doc2.pdf");
        System.out.println("操作結果 (處理列印): " + printQueue.poll());

        System.out.println("\n=== 需求 5: 復原最近操作 ===");
        System.out.println("選擇 Interface: Deque (As Stack), Implementation: ArrayDeque");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("Type A");
        undoStack.push("Type B");
        System.out.println("操作結果 (復原最近): " + undoStack.pop());
    }
}