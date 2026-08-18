class CourseGrade {
    private String studentId;
    private String name;
    private double regularScore;
    private double midtermScore;
    private double finalScore;
    private double attendanceScore;

    public CourseGrade(String studentId, String name, double regular, double midterm, double finalTerm, double attendance) {
        this.studentId = studentId;
        this.name = name;
        this.regularScore = clamp(regular);
        this.midtermScore = clamp(midterm);
        this.finalScore = clamp(finalTerm);
        this.attendanceScore = clamp(attendance);
    }

    private double clamp(double score) {
        if (score < 0) return 0;
        if (score > 100) return 100;
        return score;
    }

    public double calculateFinalScore() {
        return regularScore * 0.50 + midtermScore * 0.20 + finalScore * 0.20 + attendanceScore * 0.10;
    }

    public String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | 總分: %.2f | 等級: %s", studentId, name, calculateFinalScore(), getLevel());
    }

    public String getName() { return name; }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = new CourseGrade[] {
            new CourseGrade("S01", "張三", 90, 85, 88, 100),
            new CourseGrade("S02", "李四", 50, 40, 55, 60),
            new CourseGrade("S03", "王五", 70, 75, 80, 90),
            new CourseGrade("S04", "趙六", 95, 92, 98, 90),
            new CourseGrade("S05", "錢七", 40, 30, 20, 50)
        };

        double totalSum = 0;
        CourseGrade highest = grades[0];

        System.out.println("=== 所有學生成績列表 ===");
        for (CourseGrade g : grades) {
            System.out.println(g);
            double finalScore = g.calculateFinalScore();
            totalSum += finalScore;
            if (finalScore > highest.calculateFinalScore()) {
                highest = g;
            }
        }

        System.out.printf("\n全班平均分數: %.2f\n", (totalSum / grades.length));
        System.out.println("最高分學生: " + highest.getName() + " (" + String.format("%.2f", highest.calculateFinalScore()) + "分)");

        System.out.println("\n不及格名單 (F):");
        for (CourseGrade g : grades) {
            if (g.getLevel().equals("F")) {
                System.out.println("- " + g.getName() + " (" + String.format("%.2f", g.calculateFinalScore()) + "分)");
            }
        }
    }
}