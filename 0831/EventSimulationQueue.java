import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Event implements Comparable<Event> {
    int id;
    long timestamp;
    String type;
    int sequence;

    public Event(int id, long timestamp, String type, int sequence) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.sequence = sequence;
    }

    @Override
    public int compareTo(Event other) {
        if (this.timestamp != other.timestamp) {
            return Long.compare(this.timestamp, other.timestamp);
        }
        return Integer.compare(this.sequence, other.sequence);
    }

    @Override
    public String toString() {
        return String.format("Event[ID=%d, Time=%d, Type='%s', Seq=%d]", id, timestamp, type, sequence);
    }
}

public class EventSimulationQueue {
    private PriorityQueue<Event> queue = new PriorityQueue<>();

    public void addEvent(Event event) {
        queue.add(event);
    }

    public boolean cancelEvent(int eventId) {
        return queue.removeIf(e -> e.id == eventId);
    }

    public List<String> runSimulation() {
        List<String> log = new ArrayList<>();
        while (!queue.isEmpty()) {
            Event curr = queue.poll();
            String record = "執行 -> " + curr;
            log.add(record);
            System.out.println(record);
        }
        return log;
    }

    public static void main(String[] args) {
        EventSimulationQueue sim = new EventSimulationQueue();

        sim.addEvent(new Event(101, 1000L, "START", 1));
        sim.addEvent(new Event(102, 2000L, "CHECK", 1));
        sim.addEvent(new Event(103, 2000L, "LOG", 2));
        sim.addEvent(new Event(104, 1500L, "CANCEL_TARGET", 1));
        sim.addEvent(new Event(105, 3000L, "END", 1));

        System.out.println("取消 Event ID 104: " + sim.cancelEvent(104));

        System.out.println("\n--- 開始執行模擬 ---");
        sim.runSimulation();
    }
}