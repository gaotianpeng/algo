package leetcode;
/*
    124 二叉树的最大路径和
        路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。
        该路径 至少包含一个 节点，且不一定经过根节点

        路径和 是路径中各节点值的总和
        给你一个二叉树的根节点 root ，返回其 最大路径和

        输入：root = [1,2,3]
        输出：6
        解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6
 */
public class Code_0124_BinaryTreeMaximumPathSum {
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

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return process(root).max_path_sum;
    }

    public static class Info {
        public int max_path_sum;
        public int max_path_sum_from_head;

        public Info(int path, int head) {
            max_path_sum = path;
            max_path_sum_from_head = head;
        }
    }

    public static Info process(TreeNode x) {
        if (x == null) {
            return null;
        }

        Info left_info = process(x.left);
        Info right_info = process(x.right);
        int p1 = Integer.MIN_VALUE;
        if (left_info != null) {
            p1 = left_info.max_path_sum;
        }
        int p2 = Integer.MIN_VALUE;
        if (right_info != null) {
            p2 = right_info.max_path_sum;
        }

        int p3 = x.val;
        int p4 = Integer.MIN_VALUE;
        if (left_info != null) {
            p4 = x.val + left_info.max_path_sum_from_head;
        }
        int p5 = Integer.MIN_VALUE;
        if (right_info != null) {
            p5 = x.val + right_info.max_path_sum_from_head;
        }
        int p6 = Integer.MIN_VALUE;
        if (left_info != null && right_info != null) {
            p6 = x.val + left_info.max_path_sum_from_head + right_info.max_path_sum_from_head;
        }
        int max_sum = Math.max(Math.max(Math.max(p1, p2), Math.max(p3, p4)), Math.max(p5, p6));
        int max_sum_from_head = Math.max(p3, Math.max(p4, p5));

        return new Info(max_sum, max_sum_from_head);
    }
}
