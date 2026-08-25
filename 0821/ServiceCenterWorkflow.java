import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private final String id;
    private final String serviceType;

    public ServiceTicket(String id, String serviceType) {
        this.id = id;
        this.serviceType = serviceType;
    }

    public String getId() {
        return id;
    }

    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        return "Ticket[" + id + ", " + serviceType + "]";
    }
}

public class ServiceCenterWorkflow {
    private final Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private final Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private final Deque<ServiceTicket> completedStack = new ArrayDeque<>();
    private final Set<String> existingIds = new HashSet<>();

    public boolean createTicket(String id, String serviceType) {
        if (existingIds.contains(id)) {
            System.out.println("建立失敗：Ticket ID " + id + " 已存在");
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, serviceType);
        ticketMap.put(id, ticket);
        existingIds.add(id);
        waitingQueue.offer(ticket);
        System.out.println("成功取號: " + ticket);
        return true;
    }

    public ServiceTicket processNext() {
        ServiceTicket ticket = waitingQueue.poll();
        if (ticket != null) {
            completedStack.push(ticket);
            System.out.println("正在處理: " + ticket);
        } else {
            System.out.println("叫號失敗：等待佇列為空");
        }
        return ticket;
    }

    public boolean cancelWaiting(String id) {
        ServiceTicket target = ticketMap.get(id);
        if (target != null && waitingQueue.contains(target)) {
            waitingQueue.remove(target);
            System.out.println("成功取消等待中的號碼: " + target);
            return true;
        }
        System.out.println("取消失敗：號碼 " + id + " 不在等待佇列中或不存在");
        return false;
    }

    public boolean undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗：無已完成之服務紀錄");
            return false;
        }
        ServiceTicket ticket = completedStack.pop();
        waitingQueue.addFirst(ticket);
        System.out.println("Undo 成功：將 " + ticket + " 放回等待佇列前端");
        return true;
    }

    public ServiceTicket findById(String id) {
        return ticketMap.get(id);
    }

    public void printSummary() {
        System.out.println("\n========== 服務中心當前狀態 ==========");
        System.out.println("等待中佇列: " + waitingQueue);
        System.out.println("已完成歷程: " + completedStack);
        System.out.println("================--------------------");
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("1. 測試重複 ID 與正常取號：");
        center.createTicket("K01", "諮詢");
        center.createTicket("K02", "繳費");
        center.createTicket("K01", "維修"); // 重複 ID

        System.out.println("\n2. 叫號處理：");
        center.processNext(); // 完成 K01
        center.createTicket("K03", "查詢");

        System.out.println("\n3. 取消等待中 ID 與取消已處理/不存在 ID：");
        center.cancelWaiting("K03"); // 成功
        center.cancelWaiting("K01"); // 失敗 (已處理)
        center.cancelWaiting("K99"); // 失敗 (不存在)

        System.out.println("\n4. 測試連續兩次 Undo：");
        center.processNext(); // 完成 K02
        center.printSummary();

        center.undoLastCompletion(); // Undo K02
        center.undoLastCompletion(); // Undo K01
        center.undoLastCompletion(); // 失敗 (無紀錄可 Undo)

        System.out.println("\n5. 測試空 Queue 叫號：");
        ServiceCenterWorkflow emptyCenter = new ServiceCenterWorkflow();
        emptyCenter.processNext();

        center.printSummary();
    }
}