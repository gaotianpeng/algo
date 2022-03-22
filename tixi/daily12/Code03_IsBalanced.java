package tixi.daily12;

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
        public boolean is_balanced;
        public int height;

        public Info(boolean balanced, int h) {
            is_balanced = balanced;
            height = h;
        }
    }

    public static boolean isBalance(Node root) {
        if (root == null) {
            return true;
        }

        return process(root).is_balanced;
    }

    public static Info process(Node node) {
        if (node == null) {
            return new Info(true, 0);
        }

        Info left_info = process(node.left);
        Info right_info = process(node.right);
        boolean is_balanced = true;
        int height = Math.max(left_info.height, right_info.height) + 1;
        if (!left_info.is_balanced) {
            is_balanced = false;
        }
        if (!right_info.is_balanced) {
            is_balanced = false;
        }
        if (Math.abs(left_info.height - right_info.height) > 1) {
            is_balanced = false;
        }

        return new Info(is_balanced, height);
    }

    /*
        for test
     */
    public static boolean test(Node root) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(root, ans);
        return ans[0];
    }

    private static int process1(Node head, boolean[] ans) {
        if(!ans[0] || head == null) {
            return -1;
        }

        int left_height = process1(head.left, ans);
        int right_height = process1(head.right, ans);
        if (Math.abs(left_height - right_height) > 1) {
            ans[0] = false;
        }
        return Math.max(left_height, right_height) + 1;
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
            if (isBalance(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
