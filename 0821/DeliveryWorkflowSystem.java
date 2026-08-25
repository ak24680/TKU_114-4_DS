import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

class DeliveryItem {
    private final String id;
    private final String address;

    public DeliveryItem(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + address;
    }
}

public class DeliveryWorkflowSystem {
    private final Map<String, DeliveryItem> map = new HashMap<>();
    private final Queue<DeliveryItem> waitingQueue = new ArrayDeque<>();
    private final Deque<DeliveryItem> completedStack = new ArrayDeque<>();

    public boolean addOrder(String id, String address) {
        if (map.containsKey(id)) {
            System.out.println("新增失敗：重複的配送編號 " + id);
            return false;
        }
        DeliveryItem item = new DeliveryItem(id, address);
        map.put(id, item);
        waitingQueue.offer(item);
        System.out.println("新增包裹: " + item);
        return true;
    }

    public void processNext() {
        DeliveryItem item = waitingQueue.poll();
        if (item != null) {
            completedStack.push(item);
            System.out.println("完成配送: " + item);
        } else {
            System.out.println("無等待配送之包裹");
        }
    }

    public void undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("無法復原：沒有已完成的配送紀錄");
            return;
        }
        DeliveryItem item = completedStack.pop();
        ((ArrayDeque<DeliveryItem>) waitingQueue).addFirst(item);
        System.out.println("復原最後一次完成之包裹，重新放入待處理前段: " + item);
    }

    public void search(String id) {
        DeliveryItem item = map.get(id);
        System.out.println("查詢結果 " + id + ": " + (item != null ? item : "不存在"));
    }

    public void printStats() {
        System.out.println("--- 統計數據 ---");
        System.out.println("總訂單數: " + map.size());
        System.out.println("等待配送數: " + waitingQueue.size());
        System.out.println("已完成配送數: " + completedStack.size());
        System.out.println("----------------");
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem delivery = new DeliveryWorkflowSystem();

        delivery.addOrder("D101", "Taipei");
        delivery.addOrder("D102", "New Taipei");
        delivery.addOrder("D101", "Kaohsiung"); // 重複 id

        delivery.processNext();
        delivery.printStats();

        delivery.undoLastCompletion();
        delivery.printStats();

        delivery.search("D102");
    }
}