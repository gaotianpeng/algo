package tixi.daily13;

import java.util.ArrayList;
import java.util.List;

public class Code02_MaxSubBSTHead {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }


    public static Node maxSubBSTHead(Node head) {
        if (head == null) {
            return null;
        }

        return process(head).max_bst_head;
    }

    private static class Info {
        public Node max_bst_head;
        public int max_bst_size;
        public int max;
        public int min;

        public Info(Node node, int bst_size, int ma, int mi) {
            max_bst_head = node;
            max_bst_size = bst_size;
            max = ma;
            min = mi;
        }
    }

    private static Info process(Node head) {
        if (head == null) {
            return null;
        }

        Info left_info = process(head.left);
        Info right_info = process(head.right);
        Node max_bst_head = null;
        int max = head.value;
        int min = head.value;
        int max_bst_size = 0;
        if (left_info != null) {
            max = Math.max(max, left_info.max);
            min = Math.min(min, left_info.min);
            max_bst_head = left_info.max_bst_head;
            max_bst_size = left_info.max_bst_size;
        }

        if (right_info != null) {
            max = Math.max(max, right_info.max);
            min = Math.min(min, right_info.min);
            if (right_info.max_bst_size > max_bst_size) {
                max_bst_head = right_info.max_bst_head;
                max_bst_size = right_info.max_bst_size;
            }
        }

        if ((left_info == null ? true : (left_info.max_bst_head == head.left && left_info.max < head.value))
            && (right_info == null ? true : (right_info.max_bst_head == head.right && right_info.min > head.value))) {
            max_bst_head = head;
            max_bst_size = (left_info == null ? 0 : left_info.max_bst_size)
                        + (right_info == null ? 0 : right_info.max_bst_size) + 1;
        }

        return new Info(max_bst_head, max_bst_size, max, min);
    }

    public static Node test(Node head) {
        if (head == null) {
            return null;
        }

        if (getBSTSize(head) != 0) {
            return head;
        }

        Node ans_left = test(head.left);
        Node ans_right = test(head.right);

        return getBSTSize(ans_left) >= getBSTSize(ans_right) ? ans_left : ans_right;
    }

    private static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }

        List<Node> list = new ArrayList<>();
        inorder(head, list);
        for (int i = 1; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i).value <= list.get(i-1).value) {
                    return 0;
                }
            }
        }

        return list.size();
    }

    private static void inorder(Node head, List<Node> ans) {
        if (head == null) {
            return;
        }
        inorder(head.left, ans);
        ans.add(head);
        inorder(head.right, ans);
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
            if (maxSubBSTHead(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
