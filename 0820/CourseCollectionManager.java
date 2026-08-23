import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class StudentCourse {
    private final String studentId;
    private final String tag;
    private int score;

    public StudentCourse(String studentId, String tag, int score) {
        this.studentId = studentId;
        this.tag = (tag == null || tag.isBlank()) ? "UNTAGGED" : tag;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTag() {
        return tag;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGradeLevel() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return studentId.equals(that.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }

    @Override
    public String toString() {
        return "StudentCourse{id='" + studentId + "', tag='" + tag + "', score=" + score + "}";
    }
}

public class CourseCollectionManager {
    private final List<StudentCourse> list = new ArrayList<>();
    private final Set<StudentCourse> set = new HashSet<>();
    private final Map<String, StudentCourse> map = new HashMap<>();

    public boolean addStudent(String id, String tag, int score) {
        StudentCourse sc = new StudentCourse(id, tag, score);
        if (set.contains(sc)) {
            return false;
        }
        list.add(sc);
        set.add(sc);
        map.put(id, sc);
        return true;
    }

    public boolean updateScore(String studentId, int score) {
        StudentCourse sc = map.get(studentId);
        if (sc != null) {
            sc.setScore(score);
            return true;
        }
        return false;
    }

    public List<StudentCourse> findByTag(String tag) {
        String targetTag = (tag == null || tag.isBlank()) ? "UNTAGGED" : tag;
        List<StudentCourse> result = new ArrayList<>();
        for (StudentCourse sc : list) {
            if (sc.getTag().equalsIgnoreCase(targetTag)) {
                result.add(sc);
            }
        }
        return result;
    }

    public Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new HashMap<>();
        dist.put("A", 0);
        dist.put("B", 0);
        dist.put("C", 0);
        dist.put("D", 0);
        dist.put("F", 0);

        for (StudentCourse sc : list) {
            String lvl = sc.getGradeLevel();
            dist.put(lvl, dist.get(lvl) + 1);
        }
        return dist;
    }

    public List<StudentCourse> top(int count) {
        List<StudentCourse> sorted = new ArrayList<>(list);
        sorted.sort(Comparator.comparingInt(StudentCourse::getScore).reversed());
        if (count >= sorted.size()) {
            return sorted;
        }
        return sorted.subList(0, Math.max(0, count));
    }

    public void removeBelow(int minimum) {
        Iterator<StudentCourse> it = list.iterator();
        while (it.hasNext()) {
            StudentCourse sc = it.next();
            if (sc.getScore() < minimum) {
                it.remove();
                set.remove(sc);
                map.remove(sc.getStudentId());
            }
        }
    }

    public void printStatus() {
        System.out.println("Current State (List Size: " + list.size() + ", Set Size: " + set.size() + ", Map Size: " + map.size() + "):");
        for (StudentCourse sc : list) {
            System.out.println("  " + sc);
        }
    }

    public static void main(String[] args) {
        CourseCollectionManager manager = new CourseCollectionManager();

        System.out.println("--- 1. 新增 6 筆報名資料（包含重複學號與空白 tag） ---");
        manager.addStudent("S01", "CS", 85);
        manager.addStudent("S02", "EE", 85); 
        manager.addStudent("S03", "  ", 55); 
        manager.addStudent("S04", "CS", 92);
        manager.addStudent("S05", "EE", 48);
        manager.addStudent("S06", "MATH", 72);
        System.out.println("加入重複學號 S01 結果: " + manager.addStudent("S01", "CS", 90));
        manager.printStatus();

        System.out.println("\n--- 2. 更新分數 ---");
        manager.updateScore("S03", 62);

        System.out.println("\n--- 3. 依 Tag 搜尋 (UNTAGGED) ---");
        System.out.println(manager.findByTag(""));

        System.out.println("\n--- 4. 成績分布統計 ---");
        System.out.println(manager.scoreDistribution());

        System.out.println("\n--- 5. 排名前 3 名 ---");
        System.out.println(manager.top(3));

        System.out.println("\n--- 6. 移除分數低於 60 分的學生並維持三者同步 ---");
        manager.removeBelow(60);
        manager.printStatus();
    }
}