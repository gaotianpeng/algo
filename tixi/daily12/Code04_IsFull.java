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
        return (1 << all.height) - 1 == all.nodes;
    }

    public static class Info1 {
        public int height;
        public int nodes;

        public Info1(int h, int n) {
            height = h;
            nodes = n;
        }
    }

    public static Info1 process1(Node head) {
        if (head == null) {
            return new Info1(0, 0);
        }
        Info1 leftInfo = process1(head.left);
        Info1 rightInfo = process1(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info1(height, nodes);
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

    public static class Info2 {
        public boolean isFull;
        public int height;

        public Info2(boolean f, int h) {
            isFull = f;
            height = h;
        }
    }

    public static Info2 process2(Node h) {
        if (h == null) {
            return new Info2(true, 0);
        }
        Info2 leftInfo = process2(h.left);
        Info2 rightInfo = process2(h.right);
        boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info2(isFull, height);
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
            if (isFull1(head) != isFull2(head)) {
                success = false;
                break;
            }
        }
        System.out.println(success ?  "success" : "failed");
        System.out.println("test end");
    }
}
