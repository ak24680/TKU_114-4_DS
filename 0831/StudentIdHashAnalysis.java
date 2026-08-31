import java.util.ArrayList;
import java.util.List;

public class StudentIdHashAnalysis {

    public static void analyze(String[] studentIds, int bucketCount) {
        List<List<String>> buckets = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (String id : studentIds) {
            int hash = Math.abs(id.hashCode());
            int index = hash % bucketCount;
            buckets.get(index).add(id);
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int nonEmptyBuckets = 0;

        for (int i = 0; i < bucketCount; i++) {
            int chainLen = buckets.get(i).size();
            if (chainLen > 0) {
                nonEmptyBuckets++;
                totalCollisions += (chainLen - 1);
            }
            if (chainLen > maxChain) {
                maxChain = chainLen;
            }
        }

        double avgChain = nonEmptyBuckets == 0 ? 0.0 : (double) studentIds.length / nonEmptyBuckets;

        System.out.println("Bucket 數量: " + bucketCount);
        System.out.println("總資料筆數: " + studentIds.length);
        System.out.println("總 Collision 次數: " + totalCollisions);
        System.out.println("最長 Chain 長度: " + maxChain);
        System.out.printf("非空 Bucket 平均 Chain 長度: %.2f\n", avgChain);
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        // 模擬 15 筆學號資料
        String[] studentIds = {
            "410410001", "410410002", "410410003", "410410004", "410410005",
            "410410006", "410410007", "410410008", "410410009", "410410010",
            "410410011", "410410012", "410410013", "410410014", "410410015"
        };

        System.out.println("=== 學號 Hash Collision 比較分析 ===\n");
        analyze(studentIds, 5);  // 較小的 Bucket count (容易碰撞)
        analyze(studentIds, 17); // 較大的質數 Bucket count (碰撞率低)
    }
}