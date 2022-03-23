package tixi.daily12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/*
    给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
    返回整棵二叉树的最大距离
 */
public class Code06_MaxDistance {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static int maxDistance(Node head) {
        return process(head).max_distance;
    }

    private static class Info {
        public int max_distance;
        public int height;

        public Info(int m, int h) {
            max_distance = m;
            height = h;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info left_info = process(node.left);
        Info right_info = process(node.right);
        int height = Math.max(left_info.height, right_info.height) + 1;
        int p1 = left_info.max_distance;
        int p2 = right_info.max_distance;
        int p3 = left_info.height + right_info.height + 1;
        int max_distance = Math.max(Math.max(p1, p2), p3);
        return new Info(max_distance, height);
    }

    public static int test(Node head) {
        if (head == null) {
            return 0;
        }

        List<Node> list = getPreList(head);
        HashMap<Node, Node> parent_map = getParentMap(head);
        int max = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                max = Math.max(max, distance(parent_map, list.get(i), list.get(j)));
            }
        }

        return max;
    }

    private static List<Node> getPreList(Node head) {
        List<Node> ans = new ArrayList<>();
        fillPreList(head, ans);
        return ans;
    }

    private static void fillPreList(Node head, List<Node> ans) {
        if (head == null) {
            return;
        }

        ans.add(head);
        fillPreList(head.left, ans);
        fillPreList(head.right, ans);
    }

    private static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    private static void fillParentMap(Node head, HashMap<Node, Node> parent_map) {
        if (head.left != null) {
            parent_map.put(head.left, head);
            fillParentMap(head.left, parent_map);
        }
        if (head.right != null) {
            parent_map.put(head.right, head);
            fillParentMap(head.right, parent_map);
        }
    }

    public static int distance(HashMap<Node, Node> parent_map, Node node1, Node node2) {
        HashSet<Node> node1_set = new HashSet<>();
        Node cur = node1;
        node1_set.add(cur);
        while (parent_map.get(cur) != null) {
            cur = parent_map.get(cur);
            node1_set.add(cur);
        }

        cur = node2;
        while (!node1_set.contains(cur)) {
            cur = parent_map.get(cur);
        }
        Node lowest_ancestor = cur;
        cur = node1;
        int distance1 = 1;
        while (cur != lowest_ancestor) {
            cur = parent_map.get(cur);
            distance1++;
        }

        cur = node2;
        int distance2 = 1;
        while (cur != lowest_ancestor) {
            cur = parent_map.get(cur);
            distance2++;
        }

        return distance1 + distance2 - 1;
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
        int max_level = 8;
        int max_val = 100;
        int test_times = 100000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            Node head = generateRandomBST(max_level, max_val);
            if (maxDistance(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
