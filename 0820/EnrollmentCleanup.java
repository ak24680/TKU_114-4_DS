import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EnrollmentCleanup {
    public static void main(String[] args) {
        List<String> rawList = new ArrayList<>(Arrays.asList(
            "Alice", "", null, "Bob", "Alice", "  ", "Charlie", "Bob", "David", null
        ));

        System.out.println("清理前名單: " + rawList);

        Iterator<String> iterator = rawList.iterator();
        while (iterator.hasNext()) {
            String name = iterator.next();
            if (name == null || name.isBlank()) {
                iterator.remove();
            }
        }

        System.out.println("移除無效資料後: " + rawList);

        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new HashSet<>();

        for (String name : rawList) {
            String trimmed = name.trim();
            if (!seen.add(trimmed)) {
                duplicates.add(trimmed);
            }
        }

        System.out.println("重複出現的姓名: " + duplicates);
    }
}