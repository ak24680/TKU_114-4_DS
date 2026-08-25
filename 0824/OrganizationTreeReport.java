import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class OrgNode {
    String name;
    OrgNode left;
    OrgNode right;

    OrgNode(String name) {
        this.name = name;
    }
}

public class OrganizationTreeReport {

    public static String findParent(OrgNode root, String targetName) {
        if (root == null || root.name.equals(targetName)) return null;
        return findParentHelper(root, targetName);
    }

    private static String findParentHelper(OrgNode current, String targetName) {
        if (current == null) return null;
        if ((current.left != null && current.left.name.equals(targetName)) ||
            (current.right != null && current.right.name.equals(targetName))) {
            return current.name;
        }
        String leftSearch = findParentHelper(current.left, targetName);
        if (leftSearch != null) return leftSearch;
        return findParentHelper(current.right, targetName);
    }

    public static int findDepth(OrgNode root, String targetName) {
        return findDepthHelper(root, targetName, 0);
    }

    private static int findDepthHelper(OrgNode node, String targetName, int depth) {
        if (node == null) return -1;
        if (node.name.equals(targetName)) return depth;
        int left = findDepthHelper(node.left, targetName, depth + 1);
        if (left != -1) return left;
        return findDepthHelper(node.right, targetName, depth + 1);
    }

    public static List<String> pathFromRoot(OrgNode root, String targetName) {
        List<String> path = new ArrayList<>();
        findPathHelper(root, targetName, path);
        return path;
    }

    private static boolean findPathHelper(OrgNode node, String targetName, List<String> path) {
        if (node == null) return false;
        path.add(node.name);
        if (node.name.equals(targetName)) return true;
        if (findPathHelper(node.left, targetName, path) || findPathHelper(node.right, targetName, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("組織樹為空");
            return;
        }
        Queue<OrgNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int level = 0;
        System.out.println("--- 依層級列印組織架構 ---");
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < levelSize; i++) {
                OrgNode curr = queue.poll();
                System.out.print(curr.name + " ");
                if (curr.left != null) queue.offer(curr.left);
                if (curr.right != null) queue.offer(curr.right);
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        OrgNode root = new OrgNode("CEO");
        root.left = new OrgNode("VP1");
        root.right = new OrgNode("VP2");
        root.left.left = new OrgNode("DevMgr");
        root.left.right = new OrgNode("HRMgr");

        System.out.println("DevMgr 的 Parent: " + findParent(root, "DevMgr"));
        System.out.println("HRMgr 的 Depth: " + findDepth(root, "HRMgr"));
        System.out.println("Root 到 DevMgr 的 Path: " + pathFromRoot(root, "DevMgr"));

        System.out.println("\n--- 測試不存在的單位 ---");
        System.out.println("Unknown 的 Parent: " + findParent(root, "Unknown"));
        System.out.println("Unknown 的 Depth: " + findDepth(root, "Unknown"));
        System.out.println("Unknown 的 Path: " + pathFromRoot(root, "Unknown"));

        System.out.println();
        printByLevel(root);
    }
}