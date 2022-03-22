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

    public static class Info {
        public boolean is_full;
        public boolean is_cbt;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            is_full = full;
            is_cbt = cbt;
            height = h;
        }
    }

    public static boolean isCBT(Node root) {
        if (root == null) {
            return true;
        }
        return process(root).is_cbt;
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(true, true, 0);
        }

        Info left_info = process(node.left);
        Info right_info = process(node.right);
        int height = Math.max(left_info.height, right_info.height) + 1;
        boolean is_full = left_info.is_full && right_info.is_full
                    && left_info.height == right_info.height;

        boolean is_cbt = false;
        if (is_full) {
            is_cbt = true;
        } else {
            if (left_info.is_cbt && right_info.is_cbt) {
                if (left_info.is_full && right_info.is_cbt
                        && left_info.height == right_info.height) {
                    is_cbt = true;
                }

                if (left_info.is_cbt && right_info.is_full
                        && left_info.height == right_info.height + 1) {
                    is_cbt = true;
                }

                if (left_info.is_full && right_info.is_full
                        && left_info.height == right_info.height + 1) {
                    is_cbt = true;
                }
            }
        }

        return new Info(is_full, is_cbt, height);
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
        int test_times = 1000000;
        int max_level = 20;
        int max_val = 30;

        for (int i = 0; i < test_times; i++) {
            Node node = generateRandomBT(max_level, max_val);
            if (isCBT(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
