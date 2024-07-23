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

    private static class Info {
        public boolean findP;
        public boolean findQ;
        public Node ans; // p, q 最先交汇点

        public Info(boolean findP, boolean findQ, Node ans) {
            this.findP = findP;
            this.findQ = findQ;
            this.ans = ans;
        }
    }

    /*
     *  1) p, q 无一个在X上
     *  2）p, q 只一个在X上
     *  3）p, q 都在X为头的树上
     *      a) 左右各一个
     *      b) p, q 都在左
     *      c) p, q 都在右
     *      d) x 是 p或q
     */
    private static Info process(Node X, Node p, Node q) {
        if (X == null) {
            return new Info(false, false, null);
        }


        Info leftInfo = process(X.left, p, q);
        Info rightInfo = process(X.right, p, q);

        boolean findP = (X == p) || leftInfo.findP || rightInfo.findP;
        boolean findQ = (X == q) || leftInfo.findQ || rightInfo.findQ;
        Node ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (findP && findQ) {
                ans = X;
            }
        }
        
        return new Info(findP, findQ, ans);
    }


    /*
        for test
     */
    public static Node test(Node head, Node p, Node q) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> parentMap = getParentMap(head);
        HashSet<Node> set = new HashSet<>();
        Node cur = p;
        set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            set.add(cur);
        }

        cur = q;
        while (!set.contains(cur)) {
            cur = parentMap.get(cur);
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

    private static Node generateRandomBT(int maxLevel, int maxVal) {
        return generate(1, maxLevel, maxVal);
    }

    private static Node generate(int curLevel, int maxLevel, int maxVal) {
        if (curLevel > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node ans = new Node(randomVal(maxVal));
        ans.left = generate(curLevel + 1, maxLevel, maxVal);
        ans.right = generate(curLevel + 1, maxLevel, maxVal);

        return ans;
    }

    private static Node pickOneNode(Node head) {
        if (head == null) {
            return null;
        }

        List<Node> preList = gerPreList(head);
        int index = (int)(Math.random() * preList.size());
        return preList.get(index);
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

    private static int randomVal(int maxVal) {
        return (int)(Math.random() * (maxVal + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLevel = 20;
        int maxVal = 30;
        int testTimes = 100000;

        for (int i = 0; i < testTimes; i++) {
            Node root = generateRandomBT(maxLevel, maxVal);
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
