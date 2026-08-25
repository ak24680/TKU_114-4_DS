import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

class Patient {
    private final String id;
    private final String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}

public class ClinicQueueSystem {
    private final Queue<Patient> waitingQueue = new ArrayDeque<>();
    private final List<Patient> completedList = new ArrayList<>();

    public void register(String id, String name) {
        Patient p = new Patient(id, name);
        waitingQueue.offer(p);
        System.out.println("掛號成功: " + p);
    }

    public boolean cancel(String id) {
        Iterator<Patient> it = waitingQueue.iterator();
        while (it.hasNext()) {
            Patient p = it.next();
            if (p.getId().equals(id)) {
                it.remove();
                System.out.println("取消掛號成功: " + p);
                return true;
            }
        }
        System.out.println("取消失敗：找不到病歷號 " + id);
        return false;
    }

    public Patient callNext() {
        Patient p = waitingQueue.poll();
        if (p != null) {
            completedList.add(p);
            System.out.println("叫號看診: " + p);
        } else {
            System.out.println("目前無等待看診的病人");
        }
        return p;
    }

    public Patient peekNext() {
        Patient p = waitingQueue.peek();
        System.out.println("下一位看診病人: " + (p != null ? p : "無"));
        return p;
    }

    public void printCompleted() {
        System.out.println("當日已完成看診清單: " + completedList);
    }

    public static void main(String[] args) {
        ClinicQueueSystem clinic = new ClinicQueueSystem();

        clinic.register("P01", "Alice");
        clinic.register("P02", "Bob");
        clinic.register("P03", "Charlie");

        clinic.peekNext();
        clinic.cancel("P02");

        clinic.callNext();
        clinic.callNext();
        clinic.callNext(); // 空 queue

        clinic.printCompleted();
    }
}