import java.util.PriorityQueue;

class Patient implements Comparable<Patient> {
    String name;
    int urgency; // 1: 最緊急, 數字越小越優先
    long arrivalTime;
    String id;

    public Patient(String name, int urgency, long arrivalTime, String id) {
        this.name = name;
        this.urgency = urgency;
        this.arrivalTime = arrivalTime;
        this.id = id;
    }

    @Override
    public int compareTo(Patient other) {
        if (this.urgency != other.urgency) {
            return Integer.compare(this.urgency, other.urgency);
        }
        if (this.arrivalTime != other.arrivalTime) {
            return Long.compare(this.arrivalTime, other.arrivalTime);
        }
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("[%s (ID: %s, 危急: %d)]", name, id, urgency);
    }
}

public class EmergencyTriageQueue {
    private PriorityQueue<Patient> queue = new PriorityQueue<>();

    public void register(Patient patient) {
        queue.add(patient);
        System.out.println("報到成功: " + patient);
    }

    public Patient peek() {
        return queue.peek();
    }

    public Patient callNext() {
        return queue.poll();
    }

    public int size() {
        return queue.size();
    }

    public static void main(String[] args) {
        EmergencyTriageQueue triage = new EmergencyTriageQueue();
        long now = System.currentTimeMillis();

        triage.register(new Patient("張三", 3, now, "P003"));
        triage.register(new Patient("李四", 1, now + 10, "P001"));
        triage.register(new Patient("王五", 1, now + 5, "P002"));
        triage.register(new Patient("趙六", 2, now + 20, "P004"));

        System.out.println("\n目前等待人數: " + triage.size());
        System.out.println("下一位叫號預覽: " + triage.peek());

        System.out.println("\n--- 開始叫號 ---");
        while (triage.size() > 0) {
            System.out.println("叫號: " + triage.callNext());
        }

        System.out.println("\n--- 測試空佇列處理 ---");
        System.out.println("叫號 (空佇列): " + triage.callNext());
        System.out.println("查看下一位 (空佇列): " + triage.peek());
    }
}