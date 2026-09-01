import java.util.*;

public class WebsiteLinkGraph {
    private Map<String, List<String>> adjList = new HashMap<>();

    public void addPage(String page) {
        adjList.putIfAbsent(page, new ArrayList<>());
    }

    public void addLink(String from, String to) {
        addPage(from);
        addPage(to);
        adjList.get(from).add(to);
    }

    public List<String> getOutgoingLinks(String page) {
        return adjList.getOrDefault(page, new ArrayList<>());
    }

    public int getIncomingCount(String targetPage) {
        int count = 0;
        for (String page : adjList.keySet()) {
            for (String link : adjList.get(page)) {
                if (link.equals(targetPage)) {
                    count++;
                }
            }
        }
        return count;
    }

    public void reportPageTypes() {
        Set<String> allPages = adjList.keySet();
        List<String> noIncoming = new ArrayList<>();
        List<String> noOutgoing = new ArrayList<>();

        for (String page : allPages) {
            if (getIncomingCount(page) == 0) {
                noIncoming.add(page);
            }
            if (getOutgoingLinks(page).isEmpty()) {
                noOutgoing.add(page);
            }
        }

        System.out.println("=== 網站連結圖結構分析 ===");
        System.out.println("無 Incoming 連結頁面 (無外連進來): " + noIncoming);
        System.out.println("無 Outgoing 連結頁面 (死胡同頁面): " + noOutgoing);
    }

    public static void main(String[] args) {
        WebsiteLinkGraph site = new WebsiteLinkGraph();

        site.addLink("Home", "About");
        site.addLink("Home", "Products");
        site.addLink("Products", "Detail");
        site.addLink("About", "Home");

        System.out.println("Home 的外連頁面 (Outgoing): " + site.getOutgoingLinks("Home"));
        System.out.println("Home 的連入次數 (Incoming): " + site.getIncomingCount("Home"));
        
        System.out.println();
        site.reportPageTypes();
    }
}