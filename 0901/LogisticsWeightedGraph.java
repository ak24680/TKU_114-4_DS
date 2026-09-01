import java.util.*;

public class LogisticsWeightedGraph {

    private static class Edge {
        String target;
        double weight;

        Edge(String target, double weight) {
            this.target = target;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return target + "(成本:" + weight + ")";
        }
    }

    private Map<String, List<Edge>> adjList = new HashMap<>();

    public void addVertex(String vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    public boolean addOrUpdateEdge(String from, String to, double weight) {
        if (weight < 0) {
            System.out.println("[錯誤] 權重不可為負數: " + weight);
            return false;
        }
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            System.out.println("[錯誤] Vertex 不存在: " + from + " 或 " + to);
            return false;
        }

        List<Edge> edges = adjList.get(from);
        for (Edge e : edges) {
            if (e.target.equals(to)) {
                e.weight = weight; // 更新權重
                System.out.println("更新邊權重成功: " + from + " -> " + to + " = " + weight);
                return true;
            }
        }

        edges.add(new Edge(to, weight));
        System.out.println("新增邊成功: " + from + " -> " + to + " = " + weight);
        return true;
    }

    public boolean removeEdge(String from, String to) {
        if (!adjList.containsKey(from)) return false;
        return adjList.get(from).removeIf(e -> e.target.equals(to));
    }

    public Double getWeight(String from, String to) {
        if (!adjList.containsKey(from)) return null;
        for (Edge e : adjList.get(from)) {
            if (e.target.equals(to)) return e.weight;
        }
        return null;
    }

    public void printGraph() {
        System.out.println("=== 物流成本網路狀態 ===");
        for (String node : adjList.keySet()) {
            System.out.println(node + " -> " + adjList.get(node));
        }
    }

    public static void main(String[] args) {
        LogisticsWeightedGraph logistics = new LogisticsWeightedGraph();

        logistics.addVertex("Taipei");
        logistics.addVertex("Taichung");
        logistics.addVertex("Kaohsiung");

        System.out.println("--- 新增與更新邊 ---");
        logistics.addOrUpdateEdge("Taipei", "Taichung", 150.0);
        logistics.addOrUpdateEdge("Taichung", "Kaohsiung", 200.0);
        logistics.addOrUpdateEdge("Taipei", "Taichung", 130.0); // 更新權重

        System.out.println("\n--- 錯誤處理測試 ---");
        logistics.addOrUpdateEdge("Taipei", "Kaohsiung", -50.0); // 負權重
        logistics.addOrUpdateEdge("Taipei", "Tainan", 100.0);    // 不存在的 Vertex

        System.out.println();
        logistics.printGraph();

        System.out.println("\n查詢 Taipei -> Taichung 成本: " + logistics.getWeight("Taipei", "Taichung"));
    }
}