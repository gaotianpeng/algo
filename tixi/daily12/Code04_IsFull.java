package tixi.daily12;
/*
    给定一棵二叉树的头节点head，返回这颗二叉树是不是满二叉树
 */
public class Code04_IsFull {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    /*
        方法1
            收集整棵树的高度h，和节点数n
            只有满二叉树满足 : 2 ^ h - 1 == n
     */
    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }

        Info1 all = process1(head);
        return all.nodes == (1 << all.height) - 1;
    }

    private static class Info1 {
        public int nodes;
        public int height;

        public Info1(int n, int h) {
            nodes = n;
            height = h;
        }
    }

    private static Info1 process1(Node root) {
        if (root == null) {
            return new Info1(0, 0);
        }

        Info1 leftInfo = process1(root.left);
        Info1 rightInfo = process1(root.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;

        return new Info1(nodes, height);
    }



    /*
        方法2
            收集子树是否是满二叉树
            收集子树的高度
            左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
     */
    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }

        return process2(head).isFull;
    }

    private static class Info2 {
        public int height;
        public boolean isFull;

        public Info2(int h, boolean f) {
            height = h;
            isFull = f;
        }
    }

    private static Info2 process2(Node head) {
        if (head == null) {
            return new Info2(0, true);
        }

        Info2 leftInfo = process2(head.left);
        Info2 rightInfo = process2(head.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(height, isFull);
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
            if (isFull1(head) != isFull2(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
