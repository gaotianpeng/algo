package tixi.daily12;

import java.util.LinkedList;
import java.util.List;

public class Code03_IsBalanced {
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int val) {
            value = val;
        }
    }

    public static class Info {
        public int max;
        public int min;
        public boolean is_bst;

        public Info(boolean bst, int max, int min) {
            is_bst = bst;
            this.max = max;
            this.min = min;
        }
    }

    public static boolean isBalance(Node root) {
        if (root == null) {
            return true;
        }

        return process(root).is_bst;
    }

    public static Info process(Node node) {
        if (node == null) {
            return null;
        }

        Info left_info = process(node.left);
        Info right_info = process(node.right);

        int max = node.value;
        if (left_info != null) {
            max = Math.max(max, left_info.max);
        }
        if (right_info != null) {
            max = Math.max(max, right_info.max);
        }

        int min = node.value;
        if (left_info != null) {
            min = Math.min(min, left_info.min);
        }
        if (right_info != null) {
            min = Math.min(min, right_info.min);
        }

        boolean is_bst = true;
        if (left_info != null && !left_info.is_bst) {
            is_bst = false;
        }
        if (right_info != null && !right_info.is_bst) {
            is_bst = false;
        }
        if (left_info != null && left_info.max >= node.value) {
            is_bst = false;
        }
        if (right_info != null && right_info.min <= node.value) {
            is_bst = false;
        }

        return new Info(is_bst, max, min);
    }

    /*
        for test
     */
    public static boolean test(Node root) {
        if (root == null) {
            return true;
        }

        List<Node> in_list = new LinkedList<Node>();
        inorder(root, in_list);
        for (int i = 1; i < in_list.size(); i++) {
            if (in_list.get(i).value <= in_list.get(i-1).value) {
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
        int test_times = 100000;
        int max_level = 20;
        int max_val = 30;

        for (int i = 0; i < test_times; i++) {
            Node node = generateRandomBT(max_level, max_val);
            if (isBalance(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
