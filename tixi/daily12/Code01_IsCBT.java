package tixi.daily12;

import java.util.LinkedList;
import java.util.Queue;

/*
    判断二叉树是否是完全二叉树
 */
public class Code01_IsCBT {
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int val) {
            value = val;
        }
    }


    public static boolean isCBT(Node root) {
        if (root == null) {
            return true;
        }

        return process(root).isCBT;
    }

    private static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return new Info(true, true, 0);
        }

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        boolean isCBT = false;

        if (isFull) {
            isCBT = true;
        } else {
            if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                isCBT = true;
            }

            if (leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
                isCBT = true;
            }

            if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height + 1) {
                isCBT = true;
            }
        }

        return new Info(isFull, isCBT, height);
    }
    

    /*
        for test
     */
    public static boolean test(Node root) {
        if (root == null) {
            return true;
        }

        Queue<Node> queue = new LinkedList<>();
        Node left = null;
        Node right = null;
        boolean leaf = false;
        queue.add(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            left = cur.left;
            right = cur.right;

            if((leaf && (left != null || right != null))
                    || (left == null && right != null)
            ) {
                return false;
            }

            if (left != null) {
                queue.add(left);
            }

            if (right != null) {
                queue.add(right);
            }

            if (left == null || right == null) {
                leaf = true;
            }
        }

        return true;
    }

    public static Node generateRandomBT(int max_level, int max_val) {
        return generate(0, max_level, max_val);
    }

    private static Node generate(int cur_level, int max_level, int max_val) {
        if (cur_level > max_level || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomValue(max_val));
        node.left = generate(cur_level + 1, max_level, max_val);
        node.right = generate( cur_level + 1, max_level, max_val);
        return node;
    }

    private static int randomValue(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 1000000;
        int maxLevel = 20;
        int maxVal = 30;

        for (int i = 0; i < testTimes; i++) {
            Node node = generateRandomBT(maxLevel, maxVal);
            if (isCBT(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
