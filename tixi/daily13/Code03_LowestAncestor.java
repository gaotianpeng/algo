package tixi.daily13;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
    给定一棵二叉树的头节点head，和另外两个节点a和b。
    返回a和b的最低公共祖先
 */
public class Code03_LowestAncestor {
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int val) {
            value = val;
        }
    }

    public static Node lowestAncestor(Node head, Node p, Node q) {
        if (head == null) {
            return null;
        }

        return process(head, p, q).ans;
    }

    private static Info process(Node head, Node p, Node q) {
        if (head == null) {
            return new Info(false, false, null);
        }

        Info left_info = process(head.left, p, q);
        Info right_info = process(head.right, p, q);

        boolean find_p = (head == p) || left_info.find_p || right_info.find_p;
        boolean find_q = (head == q) || left_info.find_q || right_info.find_q;
        Node ans = null;
        if (left_info.ans != null) {
            ans = left_info.ans;
        } else if (right_info.ans != null) {
            ans = right_info.ans;
        } else {
            if (find_p && find_q) {
                ans = head;
            }
        }

        return new Info(find_p, find_q, ans);
    }

    private static class Info {
        public boolean find_p;
        public boolean find_q;
        Node ans;

        public Info(boolean find_p, boolean find_q, Node ans) {
            this.find_p = find_p;
            this.find_q = find_q;
            this.ans = ans;
        }
    }

    /*
        for test
     */
    public static Node test(Node head, Node p, Node q) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> parent_map = getParentMap(head);
        HashSet<Node> set = new HashSet<>();
        Node cur = p;
        set.add(cur);
        while (parent_map.get(cur) != null) {
            cur = parent_map.get(cur);
            set.add(cur);
        }

        cur = q;
        while (!set.contains(cur)) {
            cur = parent_map.get(cur);
        }

        return cur;
    }

    private static HashMap<Node, Node> getParentMap(Node head) {
        HashMap<Node, Node> ans = new HashMap<>();
        ans.put(head, null);
        getParent(head, ans);
        return ans;
    }

    private static void getParent(Node head, HashMap<Node, Node> ans) {
        if (head.left != null) {
            ans.put(head.left, head);
            getParent(head.left, ans);
        }

        if (head.right != null) {
            ans.put(head.right, head);
            getParent(head.right, ans);
        }
    }

    private static Node generateRandomBT(int max_level, int max_val) {
        return generate(1, max_level, max_val);
    }

    private static Node generate(int cur_level, int max_level, int max_val) {
        if (cur_level > max_level || Math.random() < 0.5) {
            return null;
        }
        Node ans = new Node(randomVal(max_val));
        ans.left = generate(cur_level + 1, max_level, max_val);
        ans.right = generate(cur_level + 1, max_level, max_val);

        return ans;
    }

    private static Node pickOneNode(Node head) {
        if (head == null) {
            return null;
        }

        List<Node> pre_list = gerPreList(head);
        int index = (int)(Math.random() * pre_list.size());
        return pre_list.get(index);
    }

    private static List<Node> gerPreList(Node head) {
        List<Node> ans = new LinkedList<>();
        getPre(head, ans);
        return ans;
    }

    private static void getPre(Node head, List<Node> ans) {
        if (head == null) {
            return;
        }
        ans.add(head);
        getPre(head.left, ans);
        getPre(head.right, ans);
    }

    private static int randomVal(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int max_level = 20;
        int max_val = 30;
        int test_times = 100000;

        for (int i = 0; i < test_times; i++) {
            Node root = generateRandomBT(max_level, max_val);
            Node p = pickOneNode(root);
            Node q = pickOneNode(root);
            if (lowestAncestor(root, p, q) != test(root, p, q)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
