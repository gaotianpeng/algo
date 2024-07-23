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

        return process(head).maxBstHead;
    }

    private static class Info {
        public Node maxBstHead;
        public int maxBstSize;
        public int max;
        public int min;

        public Info(Node node, int bstSize, int ma, int mi) {
            maxBstHead = node;
            maxBstSize = bstSize;
            max = ma;
            min = mi;
        }
    }

    private static Info process(Node head) {
        if (head == null) {
            return null;
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        Node maxBstHead = null;
        int max = head.value;
        int min = head.value;
        int maxBstSize = 0;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            maxBstHead = leftInfo.maxBstHead;
            maxBstSize = leftInfo.maxBstSize;
        }

        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            if (rightInfo.maxBstSize > maxBstSize) {
                maxBstHead = rightInfo.maxBstHead;
                maxBstSize = rightInfo.maxBstSize;
            }
        }

        if ((leftInfo == null ? true : (leftInfo.maxBstHead == head.left && leftInfo.max < head.value))
            && (rightInfo == null ? true : (rightInfo.maxBstHead == head.right && rightInfo.min > head.value))) {
            maxBstHead = head;
            maxBstSize = (leftInfo == null ? 0 : leftInfo.maxBstSize)
                        + (rightInfo == null ? 0 : rightInfo.maxBstSize) + 1;
        }

        return new Info(maxBstHead, maxBstSize, max, min);
    }

    public static Node test(Node head) {
        if (head == null) {
            return null;
        }

        if (getBSTSize(head) != 0) {
            return head;
        }

        Node ansLeft = test(head.left);
        Node ansRight = test(head.right);

        return getBSTSize(ansLeft) >= getBSTSize(ansRight) ? ansLeft : ansRight;
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
        int maxLevel = 10;
        int maxVal = 100;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxVal);
            if (maxSubBSTHead(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
