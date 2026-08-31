import java.util.*;

class Product {
    String id;
    int sales;

    public Product(String id, int sales) {
        this.id = id;
        this.sales = sales;
    }

    @Override
    public String toString() {
        return String.format("%s(銷量:%d)", id, sales);
    }
}

public class TopSellingProducts {

    public static List<Product> getTopK(List<Product> inputList, int k) {
        // 1. 先合併重複商品 ID 的銷量
        Map<String, Integer> salesMap = new HashMap<>();
        for (Product p : inputList) {
            salesMap.put(p.id, salesMap.getOrDefault(p.id, 0) + p.sales);
        }

        // 2. Min-Heap 保存前 K 個商品
        // PriorityQueue 的比較邏輯：銷售量小者優先出隊；銷量相同時 ID 字典序大者優先出隊
        PriorityQueue<Product> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.sales != b.sales) {
                return Integer.compare(a.sales, b.sales);
            }
            return b.id.compareTo(a.id);
        });

        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            Product p = new Product(entry.getKey(), entry.getValue());
            minHeap.add(p);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        // 3. 取出結果並由高到低排序
        List<Product> result = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll());
        }
        result.sort((a, b) -> {
            if (a.sales != b.sales) {
                return Integer.compare(b.sales, a.sales);
            }
            return a.id.compareTo(b.id);
        });

        return result;
    }

    public static void main(String[] args) {
        List<Product> rawData = Arrays.asList(
            new Product("P_B", 100),
            new Product("P_A", 150),
            new Product("P_C", 200),
            new Product("P_B", 100), // 合併後 P_B 銷量為 200
            new Product("P_D", 50),
            new Product("P_E", 200)  // 與 P_B, P_C 銷量相同
        );

        int k = 3;
        List<Product> topK = getTopK(rawData, k);

        System.out.println("--- Top " + k + " 熱門商品 ---");
        for (int i = 0; i < topK.size(); i++) {
            System.out.println("Rank " + (i + 1) + ": " + topK.get(i));
        }
    }
}