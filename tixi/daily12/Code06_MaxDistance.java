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
        return process(head).maxDistance;
    }

    private static class Info {
        public int maxDistance;
        public int height;

        public Info(int m, int h) {
            maxDistance = m;
            height = h;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.height + rightInfo.height + 1;
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

    private static void fillParentMap(Node head, HashMap<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }

    public static int distance(HashMap<Node, Node> parentMap, Node node1, Node node2) {
        HashSet<Node> node1Set = new HashSet<>();
        Node cur = node1;
        node1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            node1Set.add(cur);
        }

        cur = node2;
        while (!node1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        Node losestAncestor = cur;
        cur = node1;
        int distance1 = 1;
        while (cur != losestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }

        cur = node2;
        int distance2 = 1;
        while (cur != losestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }

        return distance1 + distance2 - 1;
    }

    /*
        for test
     */
    public static Node generateRandomBST(int maxLevel, int maxVal) {
        return generate(1, maxLevel, maxVal);
    }

    public static Node generate(int level, int maxLevel, int maxVal) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxVal));
        head.left = generate(level + 1, maxLevel, maxVal);
        head.right = generate(level + 1, maxLevel, maxVal);
        return head;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int maxLevel = 8;
        int maxVal = 100;
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxVal);
            if (maxDistance(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
