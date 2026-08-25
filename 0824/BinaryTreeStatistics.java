class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

public class BinaryTreeStatistics {

    public static int size(TreeNode root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }

    public static int sum(TreeNode root) {
        if (root == null) return 0;
        return root.val + sum(root.left) + sum(root.right);
    }

    public static Integer maximum(TreeNode root) {
        if (root == null) return null;
        Integer leftMax = maximum(root.left);
        Integer rightMax = maximum(root.right);
        
        int max = root.val;
        if (leftMax != null && leftMax > max) max = leftMax;
        if (rightMax != null && rightMax > max) max = rightMax;
        return max;
    }

    public static int leafCount(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static boolean contains(TreeNode root, int target) {
        if (root == null) return false;
        if (root.val == target) return true;
        return contains(root.left, target) || contains(root.right, target);
    }

    public static void main(String[] args) {
        System.out.println("--- 測試 Empty Tree ---");
        TreeNode emptyRoot = null;
        System.out.println("Size: " + size(emptyRoot));
        System.out.println("Maximum: " + maximum(emptyRoot));

        System.out.println("\n--- 測試 一般二元樹 ---");
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(15);
        root.left.left = new TreeNode(-2);
        root.left.right = new TreeNode(8);

        System.out.println("Size: " + size(root));
        System.out.println("Sum: " + sum(root));
        System.out.println("Maximum: " + maximum(root));
        System.out.println("Leaf Count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("Contains 8: " + contains(root, 8));
        System.out.println("Contains 99: " + contains(root, 99));
    }
}