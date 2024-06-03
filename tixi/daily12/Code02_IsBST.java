package tixi.daily12;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code02_IsBST {
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int val) {
            value = val;
        }
    }


    public static boolean isBST(Node root) {
        if (root == null) {
            return true;
        }

        return process(root).isBST;
    }

    private static class Info {
        public int max;
        public int min;
        public boolean isBST;

        public Info(int max, int min, boolean isBST) {
            this.max = max;
            this.min = min;
            this.isBST = isBST;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return null;
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int max = node.value;
        int min = node.value;
        boolean isBST = true;
    
        if (leftInfo != null) {
            max = Math.max(leftInfo.max, max);
            min = Math.min(leftInfo.min, min);
        }

        if (rightInfo != null) {
            max = Math.max(rightInfo.max, max);
            min = Math.min(rightInfo.min, min);
        }

        if (leftInfo != null && (!leftInfo.isBST || node.value <= leftInfo.max)) {
            isBST = false;
        }

        if (rightInfo != null && (!rightInfo.isBST || node.value >= rightInfo.min)) {
            isBST = false;
        }
        

        return new Info(max, min, isBST);
    }

    /*
        for test
     */
    public static boolean test(Node root) {
        if (root == null) {
            return true;
        }

        List<Node> inList = new LinkedList<Node>();
        inorder(root, inList);
        for (int i = 1; i < inList.size(); i++) {
            if (inList.get(i).value <= inList.get(i-1).value) {
                return false;
            }
        }
        return true;
    }

    public static void inorder(Node root, List<Node> ans) {
        if (root == null) {
            return;
        }

        inorder(root.left, ans);
        ans.add(root);
        inorder(root.right, ans);
    }

    public static Node generateRandomBT(int maxLevel, int maxVal) {
        return generate(0, maxLevel, maxVal);
    }

    private static Node generate(int curLevel, int maxLevel, int maxVal) {
        if (curLevel > maxLevel || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomValue(maxVal));
        node.left = generate(curLevel + 1, maxLevel, maxVal);
        node.right = generate( curLevel + 1, maxLevel, maxVal);
        return node;
    }

    private static int randomValue(int maxVal) {
        return (int)(Math.random() * (maxVal + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxLevel = 20;
        int maxVal = 30;

        for (int i = 0; i < testTimes; i++) {
            Node node = generateRandomBT(maxLevel, maxVal);
            if (isBST(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
