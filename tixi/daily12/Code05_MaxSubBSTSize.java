package tixi.daily12;

import java.util.ArrayList;
import java.util.List;

public class Code05_MaxSubBSTSize {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).max_bst_sub_size;
    }

    private static class Info {
        public int max_bst_sub_size;
        public int all_size;
        public int max;
        public int min;

        public Info(int m, int a, int ma, int mi) {
            max_bst_sub_size = m;
            all_size = a;
            max = ma;
            min = mi;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return null;
        }

        int max_sub_bst_size = 0;
        int all_size = 1;
        int max = node.value;
        int min = node.value;

        Info left_info = process(node.left);
        Info right_info = process(node.right);
        if (left_info != null) {
            max = Math.max(max, left_info.max);
            min = Math.min(min, left_info.min);
            all_size += left_info.all_size;
        }
        if (right_info != null) {
            max = Math.max(max, right_info.max);
            min = Math.min(min, right_info.min);
            all_size += right_info.all_size;
        }

        int p1 = -1;
        if (left_info != null) {
            p1 = left_info.max_bst_sub_size;
        }
        int p2 = -1;
        if (right_info != null) {
            p2 = right_info.max_bst_sub_size;
        }

        int p3 = -1;
        boolean left_bst = left_info == null ? true : (left_info.max_bst_sub_size == left_info.all_size);
        boolean right_bst = right_info == null ? true : (right_info.max_bst_sub_size == right_info.all_size);
        if (left_bst && right_bst) {
            boolean left_max_less_node = left_info == null ? true : (left_info.max < node.value);
            boolean right_max_more_node = right_info == null ? true : (node.value < right_info.min);
            if (left_max_less_node && right_max_more_node) {
                int left_size = left_info == null ? 0 : left_info.all_size;
                int right_size = right_info == null ? 0 : right_info.all_size;
                p3 = left_size + right_size + 1;
            }
        }

        max_sub_bst_size = Math.max(Math.max(p1, p2), p3);
        return new Info(max_sub_bst_size, all_size, max, min);
    }

    public static int test(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(test(head.left), test(head.right));
    }

    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }

        List<Node> list = new ArrayList<>();
        in(head, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).value <= list.get(i - 1).value) {
                return 0;
            }
        }
        return list.size();
    }

    private static void in(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        in(head.left, list);
        list.add(head);
        in(head.right, list);
    }

    /*
        for test
     */
    public static Node generateRandomBST(int max_level, int max_val) {
        return generate(1, max_level, max_val);
    }

    public static Node generate(int level, int max_level, int max_val) {
        if (level > max_level || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * max_val));
        head.left = generate(level + 1, max_level, max_val);
        head.right = generate(level + 1, max_level, max_val);
        return head;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_level = 10;
        int max_val = 100;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomBST(max_level, max_val);
            if (maxSubBSTSize1(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
