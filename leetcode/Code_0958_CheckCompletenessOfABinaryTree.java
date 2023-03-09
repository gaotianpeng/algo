package leetcode;

public class Code_0958_CheckCompletenessOfABinaryTree {
    /*
        https://leetcode.cn/problems/check-completeness-of-a-binary-tree/
        958. 二叉树的完全性检验
            给定一个二叉树的 root ，确定它是否是一个 完全二叉树
     */
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

    public static boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }

        return process(root).is_cbt;
    }

    private static class Info {
        public boolean is_full;
        public boolean is_cbt;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            is_full = full;
            is_cbt = cbt;
            height = h;
        }
    }

    private static Info process(TreeNode root) {
        if (root == null) {
            return new Info(true, true, 0);
        }

        Info left_info = process(root.left);
        Info right_info = process(root.right);

        boolean is_full = left_info.is_full && right_info.is_full && left_info.height == right_info.height;
        boolean is_cbt = false;
        int height = Math.max(left_info.height, right_info.height) + 1;
        if (left_info.is_full && right_info.is_full && left_info.height == right_info.height + 1) {
            is_cbt = true;
        } else if (left_info.is_cbt && right_info.is_full && left_info.height == right_info.height + 1) {
            is_cbt = true;
        } else if (left_info.is_full && right_info.is_cbt && left_info.height == right_info.height) {
            is_cbt = true;
        } else if (left_info.is_full && right_info.is_full && left_info.height == right_info.height) {
            is_cbt = true;
        }

        return new Info(is_full, is_cbt, height);
    }
}
