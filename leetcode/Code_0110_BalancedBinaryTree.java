package leetcode;
/*
    110 平衡二叉树
        给定一个二叉树，判断它是否是高度平衡的二叉树。
        本题中，一棵高度平衡二叉树定义为：
            一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1
 */
public class Code_0110_BalancedBinaryTree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        return process(root).is_balanced;
    }

    private static class Info {
        public int height;
        public boolean is_balanced;

        public Info(int h, boolean b) {
            height = h;
            is_balanced = b;
        }
    }

    private static Info process(TreeNode root) {
        if (root == null) {
            return new Info(0, true);
        }

        Info left_info = process(root.left);
        Info right_info = process(root.right);
        int height = Math.max(left_info.height, right_info.height) + 1;
        boolean is_balanced = true;
        if (!left_info.is_balanced) {
            is_balanced = false;
        }
        if (!right_info.is_balanced) {
            is_balanced = false;
        }
        if (Math.abs(left_info.height - right_info.height) > 1) {
            is_balanced = false;
        }

        return new Info(height, is_balanced);
    }

    public boolean isBalanced1(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean[] ans = new boolean[1];
        ans[0] = true;
        process2(root, ans);
        return ans[0];
    }

    private static int process2(TreeNode head, boolean[] ans) {
        if (head == null) {
            return -1;
        }
        if (!ans[0]) {
            return -1;
        }

        int left_height = process2(head.left, ans);
        int right_height = process2(head.right, ans);
        if (Math.abs(left_height - right_height) > 1) {
            ans[0] = false;
        }

        return Math.max(left_height, right_height) + 1;
    }
}
