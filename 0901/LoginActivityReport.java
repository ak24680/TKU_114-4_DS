import java.util.*;

public class LoginActivityReport {

    public static void analyzeLogins(List<String> logRecords, int anomalyThreshold) {
        Map<String, Integer> loginCounts = new HashMap<>();
        Map<String, Set<String>> userIpMap = new HashMap<>();

        for (String record : logRecords) {
            // 格式假設：帳號,IP
            String[] parts = record.split(",");
            if (parts.length < 2) continue;
            String user = parts[0].trim();
            String ip = parts[1].trim();

            loginCounts.put(user, loginCounts.getOrDefault(user, 0) + 1);

            userIpMap.putIfAbsent(user, new HashSet<>());
            userIpMap.get(user).add(ip);
        }

        System.out.println("=== 登入紀錄分析報告 ===");
        for (String user : loginCounts.keySet()) {
            int count = loginCounts.get(user);
            int uniqueIps = userIpMap.get(user).size();
            System.out.printf("帳號: %-10s | 登入次數: %2d | 不同 IP 數量: %2d\n", user, count, uniqueIps);
        }

        System.out.println("\n--- 異常重複登入警示 (登入次數 >= " + anomalyThreshold + ") ---");
        for (Map.Entry<String, Integer> entry : loginCounts.entrySet()) {
            if (entry.getValue() >= anomalyThreshold) {
                System.out.println("[警告] 帳號: " + entry.getKey() + " 登入次數高達 " + entry.getValue() + " 次！");
            }
        }
    }

    public static void main(String[] args) {
        List<String> logs = Arrays.asList(
            "userA,192.168.1.1",
            "userB,192.168.1.2",
            "userA,192.168.1.1",
            "userA,10.0.0.1",
            "userC,192.168.1.3",
            "userA,192.168.1.1",
            "userB,192.168.1.5"
        );

        analyzeLogins(logs, 3);
    }
}