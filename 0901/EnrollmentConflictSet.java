import java.util.*;

class Enrollment {
    String studentId;
    String courseId;

    public Enrollment(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }

    @Override
    public String toString() {
        return studentId + "-" + courseId;
    }
}

public class EnrollmentConflictSet {

    public static void processEnrollments(List<Enrollment> inputList) {
        Set<Enrollment> uniqueRecords = new HashSet<>();
        List<Enrollment> duplicates = new ArrayList<>();

        Map<String, Set<String>> studentCourses = new HashMap<>();
        Map<String, Integer> courseStudentCounts = new HashMap<>();

        for (Enrollment e : inputList) {
            if (!uniqueRecords.add(e)) {
                duplicates.add(e);
            } else {
                studentCourses.putIfAbsent(e.studentId, new HashSet<>());
                studentCourses.get(e.studentId).add(e.courseId);

                courseStudentCounts.put(e.courseId, courseStudentCounts.getOrDefault(e.courseId, 0) + 1);
            }
        }

        System.out.println("=== 1. 重複選課紀錄 ===");
        if (duplicates.isEmpty()) {
            System.out.println("無重複紀錄");
        } else {
            for (Enrollment dup : duplicates) {
                System.out.println("發現重複紀錄: " + dup);
            }
        }

        System.out.println("\n=== 2. 每位學生的選課清單 ===");
        for (String studentId : studentCourses.keySet()) {
            System.out.println("學生 " + studentId + ": " + studentCourses.get(studentId));
        }

        System.out.println("\n=== 3. 每門課程的修課人數 ===");
        for (Map.Entry<String, Integer> entry : courseStudentCounts.entrySet()) {
            System.out.println("課程 " + entry.getKey() + ": " + entry.getValue() + " 人");
        }
    }

    public static void main(String[] args) {
        List<Enrollment> records = Arrays.asList(
            new Enrollment("S001", "CS101"),
            new Enrollment("S002", "CS101"),
            new Enrollment("S001", "CS102"),
            new Enrollment("S001", "CS101"), // 重複
            new Enrollment("S003", "CS102")
        );

        processEnrollments(records);
    }
}