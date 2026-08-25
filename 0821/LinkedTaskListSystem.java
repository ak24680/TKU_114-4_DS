class Task {
    private final String id;
    private final String description;

    public Task(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + description;
    }
}

class TaskNode {
    Task task;
    TaskNode next;

    TaskNode(Task task) {
        this.task = task;
        this.next = null;
    }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    public boolean addFirst(Task task) {
        if (task == null || findById(task.getId()) != null) return false;
        TaskNode newNode = new TaskNode(task);
        newNode.next = head;
        head = newNode;
        size++;
        return true;
    }

    public boolean addLast(Task task) {
        if (task == null || findById(task.getId()) != null) return false;
        TaskNode newNode = new TaskNode(task);
        if (head == null) {
            head = newNode;
        } else {
            TaskNode curr = head;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = newNode;
        }
        size++;
        return true;
    }

    public Task findById(String id) {
        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.getId().equals(id)) {
                return curr.task;
            }
            curr = curr.next;
        }
        return null;
    }

    public boolean removeById(String id) {
        if (head == null) return false;

        if (head.task.getId().equals(id)) {
            head = head.next;
            size--;
            return true;
        }

        TaskNode curr = head;
        while (curr.next != null && !curr.next.task.getId().equals(id)) {
            curr = curr.next;
        }

        if (curr.next != null) {
            curr.next = curr.next.next;
            size--;
            return true;
        }
        return false;
    }

    public boolean insertAfter(String existingId, Task task) {
        if (task == null || findById(task.getId()) != null) return false;
        TaskNode curr = head;
        while (curr != null) {
            if (curr.task.getId().equals(existingId)) {
                TaskNode newNode = new TaskNode(task);
                newNode.next = curr.next;
                curr.next = newNode;
                size++;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printAll() {
        System.out.print("List (size=" + size + "): ");
        TaskNode curr = head;
        while (curr != null) {
            System.out.print(curr.task + (curr.next != null ? " -> " : ""));
            curr = curr.next;
        }
        System.out.println(head == null ? "EMPTY" : "");
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();

        System.out.println("1. 測試空 List 刪除與搜尋：");
        System.out.println("刪除結果: " + list.removeById("T01"));
        System.out.println("搜尋結果: " + list.findById("T01"));
        list.printAll();

        System.out.println("\n2. 新增節點與重複 id 防護：");
        list.addFirst(new Task("T02", "Task 2"));
        list.addFirst(new Task("T01", "Task 1"));
        list.addLast(new Task("T03", "Task 3"));
        System.out.println("重複新增 T01: " + list.addLast(new Task("T01", "Task 1 Duplicate")));
        list.printAll();

        System.out.println("\n3. 測試 insertAfter：");
        list.insertAfter("T02", new Task("T02-1", "Task 2.1"));
        list.printAll();

        System.out.println("\n4. 測試刪除 Middle (T02-1)：");
        list.removeById("T02-1");
        list.printAll();

        System.out.println("\n5. 測試刪除 Head (T01)：");
        list.removeById("T01");
        list.printAll();

        System.out.println("\n6. 測試刪除 Tail (T03)：");
        list.removeById("T03");
        list.printAll();
    }
}