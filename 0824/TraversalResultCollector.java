import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

class StringNode {
    String val;
    StringNode left;
    StringNode right;

    StringNode(String val) {
        this.val = val;
    }
}

public class TraversalResultCollector {

    public static List<String> preorder(StringNode root) {
        List<String> result = new ArrayList<>();
        preorderHelper(root, result);
        return result;
    }

    private static void preorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        result.add(node.val);
        preorderHelper(node.left, result);
        preorderHelper(node.right, result);
    }

    public static List<String> inorder(StringNode root) {
        List<String> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private static void inorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.val);
        inorderHelper(node.right, result);
    }

    public static List<String> postorder(StringNode root) {
        List<String> result = new ArrayList<>();
        postorderHelper(root, result);
        return result;
    }

    private static void postorderHelper(StringNode node, List<String> result) {
        if (node == null) return;
        postorderHelper(node.left, result);
        postorderHelper(node.right, result);
        result.add(node.val);
    }

    public static List<String> levelOrder(StringNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) return result;
        Queue<StringNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            StringNode curr = queue.poll();
            result.add(curr.val);
            if (curr.left != null) queue.offer(curr.left);
            if (curr.right != null) queue.offer(curr.right);
        }
        return result;
    }

    private static void testTree(String label, StringNode root) {
        System.out.println("=== " + label + " ===");
        System.out.println("Preorder:   " + preorder(root));
        System.out.println("Inorder:    " + inorder(root));
        System.out.println("Postorder:  " + postorder(root));
        System.out.println("LevelOrder: " + levelOrder(root));
        System.out.println();
    }

    public static void main(String[] args) {
        testTree("1. Empty Tree", null);
        testTree("2. Single Node Tree", new StringNode("A"));

        StringNode leftSkewed = new StringNode("A");
        leftSkewed.left = new StringNode("B");
        leftSkewed.left.left = new StringNode("C");
        testTree("3. Left Skewed Tree", leftSkewed);

        StringNode complete = new StringNode("A");
        complete.left = new StringNode("B");
        complete.right = new StringNode("C");
        complete.left.left = new StringNode("D");
        complete.left.right = new StringNode("E");
        complete.right.left = new StringNode("F");
        complete.right.right = new StringNode("G");
        testTree("4. Complete Tree", complete);
    }
}