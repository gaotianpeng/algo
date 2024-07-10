package leetcode.all;

public class Code_0111_MinimumDepthOfBinaryTree {
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

    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return process(root);
    }

    private static int process(TreeNode root) {
        if (root.left == null && root.right == null) {
            return 1;
        }

        int lh = Integer.MAX_VALUE;
        if (root.left != null) {
            lh = process(root.left);
        }
        int rh = Integer.MAX_VALUE;
        if (root.right != null) {
            rh = process(root.right);
        }
        return Math.min(lh, rh) + 1;
    }
}
