import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

class TestNode {
    String val;
    TestNode left;
    TestNode right;

    TestNode(String val) {
        this.val = val;
    }
}

public class TraversalTestReport {

    public static List<String> preorder(TestNode root) {
        List<String> res = new ArrayList<>();
        preorderH(root, res);
        return res;
    }
    private static void preorderH(TestNode n, List<String> res) {
        if (n == null) return;
        res.add(n.val);
        preorderH(n.left, res);
        preorderH(n.right, res);
    }

    public static List<String> inorder(TestNode root) {
        List<String> res = new ArrayList<>();
        inorderH(root, res);
        return res;
    }
    private static void inorderH(TestNode n, List<String> res) {
        if (n == null) return;
        inorderH(n.left, res);
        res.add(n.val);
        inorderH(n.right, res);
    }

    public static List<String> postorder(TestNode root) {
        List<String> res = new ArrayList<>();
        postorderH(root, res);
        return res;
    }
    private static void postorderH(TestNode n, List<String> res) {
        if (n == null) return;
        postorderH(n.left, res);
        postorderH(n.right, res);
        res.add(n.val);
    }

    public static List<String> levelOrder(TestNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TestNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            TestNode curr = q.poll();
            res.add(curr.val);
            if (curr.left != null) q.offer(curr.left);
            if (curr.right != null) q.offer(curr.right);
        }
        return res;
    }

    private static void runTest(String testName, TestNode root, 
                                List<String> expPre, List<String> expIn, 
                                List<String> expPost, List<String> expLevel) {
        System.out.println("==================================================");
        System.out.println("測試案例: " + testName);
        
        List<String> actPre = preorder(root);
        List<String> actIn = inorder(root);
        List<String> actPost = postorder(root);
        List<String> actLevel = levelOrder(root);

        boolean passPre = actPre.equals(expPre);
        boolean passIn = actIn.equals(expIn);
        boolean passPost = actPost.equals(expPost);
        boolean passLevel = actLevel.equals(expLevel);

        System.out.println("Preorder   -> 預期: " + expPre + " | 實際: " + actPre + " | 相符: " + passPre);
        System.out.println("Inorder    -> 預期: " + expIn + " | 實際: " + actIn + " | 相符: " + passIn);
        System.out.println("Postorder  -> 預期: " + expPost + " | 實際: " + actPost + " | 相符: " + passPost);
        System.out.println("LevelOrder -> 預期: " + expLevel + " | 實際: " + actLevel + " | 相符: " + passLevel);
    }

    public static void main(String[] args) {
        // 1. Empty Tree
        runTest("1. Empty Tree", null, 
                Arrays.asList(), Arrays.asList(), Arrays.asList(), Arrays.asList());

        // 2. Single-Node Tree
        runTest("2. Single-Node Tree", new TestNode("A"), 
                Arrays.asList("A"), Arrays.asList("A"), Arrays.asList("A"), Arrays.asList("A"));

        // 3. Only-Left Tree
        TestNode onlyLeft = new TestNode("A");
        onlyLeft.left = new TestNode("B");
        runTest("3. Only-Left Tree", onlyLeft, 
                Arrays.asList("A", "B"), Arrays.asList("B", "A"), Arrays.asList("B", "A"), Arrays.asList("A", "B"));

        // 4. Only-Right Tree
        TestNode onlyRight = new TestNode("A");
        onlyRight.right = new TestNode("C");
        runTest("4. Only-Right Tree", onlyRight, 
                Arrays.asList("A", "C"), Arrays.asList("A", "C"), Arrays.asList("C", "A"), Arrays.asList("A", "C"));

        // 5. Complete Tree
        TestNode complete = new TestNode("A");
        complete.left = new TestNode("B");
        complete.right = new TestNode("C");
        runTest("5. Complete Tree", complete, 
                Arrays.asList("A", "B", "C"), Arrays.asList("B", "A", "C"), Arrays.asList("B", "C", "A"), Arrays.asList("A", "B", "C"));

        // 6. Irregular Tree
        TestNode irregular = new TestNode("A");
        irregular.left = new TestNode("B");
        irregular.left.right = new TestNode("C");
        runTest("6. Irregular Tree", irregular, 
                Arrays.asList("A", "B", "C"), Arrays.asList("B", "C", "A"), Arrays.asList("C", "B", "A"), Arrays.asList("A", "B", "C"));
    }
}