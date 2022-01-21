package tree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Code08_TreeMaxWidth {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxTreeWidth1(Node head) {
        if (head == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        HashMap<Node, Integer> level_map = new HashMap<>();
        level_map.put(head, 1);
        int cur_level = 1;
        int cur_level_nodes = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int cur_node_level = level_map.get(cur);
            if (cur.left != null) {
                level_map.put(cur.left, cur_node_level + 1);
                queue.add(cur.left);
            }

            if (cur.right != null) {
                level_map.put(cur.right, cur_node_level + 1);
                queue.add(cur.right);
            }
            if (cur_node_level == cur_level) {
                cur_level_nodes++;
            } else {
                max = Math.max(max, cur_level_nodes);
                cur_level++;
                cur_level_nodes = 1;
            }
        }

        return Math.max(max, cur_level_nodes);
    }

    public static int maxTreeWidth2(Node head) {
        if (head == null) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur_end = head;
        Node next_end = null;
        int max = 0;
        int cur_level_nodes = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                next_end = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                next_end = cur.right;
            }
            cur_level_nodes++;
            if (cur == cur_end) {
                max = Math.max(max, cur_level_nodes);
                cur_level_nodes = 0;
                cur_end = next_end;
            }
        }

        return max;
    }

    public static Node generateRandomBST(int max_level, int max_value) {
        return generate(1, max_level, max_value);
    }

    public static Node generate(int level, int max_level, int max_value) {
        if (level > max_level || Math.random() < 0.5) {
            return null;
        }

        Node cur = new Node((int)(Math.random()*(max_value + 1)));
        cur.left = generate(level + 1, max_level, max_value);
        cur.right = generate(level + 1, max_level, max_value);
        return cur;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val= 100;
        int max_level = 10;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomBST(max_level, max_val);
            if (maxTreeWidth1(head) != maxTreeWidth2(head)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
