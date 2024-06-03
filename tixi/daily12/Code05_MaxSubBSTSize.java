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
        return process(head).maxBstSubSize;
    }

    private static class Info {
        public int maxBstSubSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int m, int a, int ma, int mi) {
            maxBstSubSize = m;
            allSize = a;
            max = ma;
            min = mi;
        }
    }

    private static Info process(Node node) {
        if (node == null) {
            return null;
        }

        int maxSubBstSize = 0;
        int allSize = 1;
        int max = node.value;
        int min = node.value;

        Info leftInfo = process(node.left);
        Info rightInfo = process(node.right);
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }

        int p1 = -1;
        if (leftInfo != null) {
            p1 = leftInfo.maxBstSubSize;
        }
        int p2 = -1;
        if (rightInfo != null) {
            p2 = rightInfo.maxBstSubSize;
        }

        int p3 = -1;
        boolean leftBst = leftInfo == null ? true : (leftInfo.maxBstSubSize == leftInfo.allSize);
        boolean rightBst = rightInfo == null ? true : (rightInfo.maxBstSubSize == rightInfo.allSize);
        if (leftBst && rightBst) {
            boolean leftMaxLessNode = leftInfo == null ? true : (leftInfo.max < node.value);
            boolean rightMaxMoreNode = rightInfo == null ? true : (node.value < rightInfo.min);
            if (leftMaxLessNode && rightMaxMoreNode) {
                int leftSize = leftInfo == null ? 0 : leftInfo.allSize;
                int rightSize = rightInfo == null ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }

        maxSubBstSize = Math.max(Math.max(p1, p2), p3);
        return new Info(maxSubBstSize, allSize, max, min);
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
    public static Node generateRandomBST(int mavLevel, int maxVal) {
        return generate(1, mavLevel, maxVal);
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
            if (maxSubBSTSize1(head) != test(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
