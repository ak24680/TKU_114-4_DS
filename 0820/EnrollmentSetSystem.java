import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Enrollment {
    private final String studentId;
    private final String courseCode;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return "Enrollment{" + studentId + " -> " + courseCode + "}";
    }
}

public class EnrollmentSetSystem {
    public static void main(String[] args) {
        Set<Enrollment> set = new HashSet<>();

        System.out.println("1. 同一人加入不同課程：");
        boolean r1 = set.add(new Enrollment("S01", "CS101"));
        boolean r2 = set.add(new Enrollment("S01", "CS102"));
        System.out.println("S01 加選 CS101: " + r1);
        System.out.println("S01 加選 CS102: " + r2);

        System.out.println("\n2. 同一人重複加入同一課程：");
        boolean r3 = set.add(new Enrollment("S01", "CS101"));
        System.out.println("S01 重複加選 CS101: " + r3);

        System.out.println("\n3. 使用新物件測試 contains() 與 remove()：");
        Enrollment testObj = new Enrollment("S01", "CS101");
        System.out.println("contains(" + testObj + "): " + set.contains(testObj));

        boolean r4 = set.remove(testObj);
        System.out.println("remove(" + testObj + "): " + r4);
        System.out.println("移除後 contains(): " + set.contains(testObj));
    }
}