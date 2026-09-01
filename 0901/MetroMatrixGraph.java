import java.util.ArrayList;
import java.util.List;

public class MetroMatrixGraph {
    private String[] stations;
    private int[][] matrix;
    private int numStations;

    public MetroMatrixGraph(String[] stations) {
        this.stations = stations;
        this.numStations = stations.length;
        this.matrix = new int[numStations][numStations];
    }

    private int findIndex(String station) {
        for (int i = 0; i < numStations; i++) {
            if (stations[i].equals(station)) return i;
        }
        return -1;
    }

    public void addEdge(String s1, String s2) {
        int u = findIndex(s1);
        int v = findIndex(s2);
        if (u != -1 && v != -1) {
            matrix[u][v] = 1;
            matrix[v][u] = 1;
        }
    }

    public List<String> getNeighbors(String station) {
        List<String> neighbors = new ArrayList<>();
        int u = findIndex(station);
        if (u == -1) return neighbors;

        for (int v = 0; v < numStations; v++) {
            if (matrix[u][v] == 1) {
                neighbors.add(stations[v]);
            }
        }
        return neighbors;
    }

    public int getDegree(String station) {
        return getNeighbors(station).size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < numStations; i++) {
            for (int j = i; j < numStations; j++) {
                if (matrix[i][j] == 1) count++;
            }
        }
        return count;
    }

    public void matrixReport() {
        System.out.println("=== Metro Adjacency Matrix Report ===");
        System.out.print("       ");
        for (String s : stations) System.out.printf("%-6s", s);
        System.out.println();

        for (int i = 0; i < numStations; i++) {
            System.out.printf("%-6s ", stations[i]);
            for (int j = 0; j < numStations; j++) {
                System.out.printf("%-6d", matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("總路線 (Edge Count): " + getEdgeCount());
    }

    public static void main(String[] args) {
        String[] stationList = {"Main", "North", "South", "East"};
        MetroMatrixGraph metro = new MetroMatrixGraph(stationList);

        metro.addEdge("Main", "North");
        metro.addEdge("Main", "South");
        metro.addEdge("Main", "East");
        metro.addEdge("North", "East");

        metro.matrixReport();

        System.out.println("\nMain 站的鄰站: " + metro.getNeighbors("Main"));
        System.out.println("Main 站的 Degree: " + metro.getDegree("Main"));
    }
}