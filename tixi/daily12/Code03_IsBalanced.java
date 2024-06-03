package tixi.daily12;

public class Code03_IsBalanced {
    public static class Node {
        public Node left;
        public Node right;
        public int value;

        public Node(int val) {
            value = val;
        }
    }

    public static boolean isBalanced(Node root) {
        if (root == null) {
            return true;
        }
        
        return process(root).isBalanced;
    }

    private static class Info {
        public int height;
        public boolean isBalanced;

        public Info(int h, boolean balance) {
            height = h;
            isBalanced = balance;
        }
    }

    private static Info process(Node root) {
        if (root == null) {
            return new Info(0, true);
        }

        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isBalanced = true;
        
        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBalanced = false;
        }

        return new Info(height, isBalanced);
    }

    /*
        for test
     */
    public static boolean test(Node root) {
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(root, ans);
        return ans[0];
    }

    private static int process1(Node head, boolean[] ans) {
        if(!ans[0] || head == null) {
            return -1;
        }

        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static Node generateRandomBT(int maxLevel, int maxVal) {
        return generate(0, maxLevel, maxVal);
    }

    private static Node generate(int curLevel, int maxLevel, int maxVal) {
        if (curLevel > maxLevel || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomValue(maxVal));
        node.left = generate(curLevel + 1, maxLevel, maxVal);
        node.right = generate( curLevel + 1, maxLevel, maxVal);
        return node;
    }

    private static int randomValue(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 1000000;
        int maxLevel = 20;
        int maxVal = 30;

        for (int i = 0; i < testTimes; i++) {
            Node node = generateRandomBT(maxLevel, maxVal);
            if (isBalanced(node) != test(node)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
