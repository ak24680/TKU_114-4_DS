import java.util.ArrayList;
import java.util.List;

class FolderNode {
    String name;
    int ownSize;
    FolderNode left;
    FolderNode right;

    FolderNode(String name, int ownSize) {
        this.name = name;
        this.ownSize = ownSize;
    }
}

public class FolderSizeTree {

    public static int calculateTotalSize(FolderNode root) {
        if (root == null) return 0;
        int leftSize = calculateTotalSize(root.left);
        int rightSize = calculateTotalSize(root.right);
        return root.ownSize + leftSize + rightSize;
    }

    public static void findMaxSubtree(FolderNode root, FolderNode[] maxFolder, int[] maxSize) {
        if (root == null) return;
        int currentTotal = calculateTotalSize(root);
        if (currentTotal > maxSize[0]) {
            maxSize[0] = currentTotal;
            maxFolder[0] = root;
        }
        findMaxSubtree(root.left, maxFolder, maxSize);
        findMaxSubtree(root.right, maxFolder, maxSize);
    }

    public static List<String> getLeafFolders(FolderNode root) {
        List<String> leaves = new ArrayList<>();
        findLeafFoldersHelper(root, leaves);
        return leaves;
    }

    private static void findLeafFoldersHelper(FolderNode node, List<String> leaves) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            leaves.add(node.name + " (" + node.ownSize + " KB)");
            return;
        }
        findLeafFoldersHelper(node.left, leaves);
        findLeafFoldersHelper(node.right, leaves);
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("Root", 10);
        root.left = new FolderNode("Docs", 20);
        root.right = new FolderNode("Media", 50);
        root.left.left = new FolderNode("Work", 5);
        root.left.right = new FolderNode("Personal", 15);

        System.out.println("總目錄大小: " + calculateTotalSize(root) + " KB");

        FolderNode[] maxFolder = new FolderNode[1];
        int[] maxSize = new int[]{-1};
        findMaxSubtree(root, maxFolder, maxSize);
        System.out.println("最大 Subtree 目錄: " + maxFolder[0].name + " (包含子目錄總大小: " + maxSize[0] + " KB)");

        System.out.println("Leaf Folders: " + getLeafFolders(root));
    }
}