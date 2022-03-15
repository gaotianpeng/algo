package leetcode;
/*
    687 最长同值路径
    给定一个二叉树的root, 返回最长的路径的长度, 这个路径中的每个节点具有相同值. 这条路径可以经过也可以不经过根节点。
    两个节点之间的路径长度由它们之间的边数表示
 */
public class Code_0687_LongestUnivaluePath {
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

    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return process(root).max - 1;
    }

    public static class Info {
        public int len; // 路径必须从X结点出发且只能往下走的情况下, 路径的最大距离
        public int max; // 路径不要求从X出发的情况下, 整棵树的合法路径最大距离

        public Info(int l, int m) {
            len = l;
            max = m;
        }
    }

    private static Info process(TreeNode x) {
        if (x == null) {
            return new Info(0, 0);
        }

        TreeNode left = x.left;
        TreeNode right = x.right;
        Info left_info = process(left);
        Info right_info = process(right);
        int len = 1;
        if (left != null && left.val == x.val) {
            len = left_info.len + 1;
        }
        if (right != null && right.val == x.val) {
            len = Math.max(len, right_info.len + 1);
        }
        int max = Math.max(Math.max(left_info.max, right_info.max), len);
        if (left != null && right != null && left.val == x.val && right.val == x.val) {
            max = Math.max(max, left_info.len + right_info.len + 1);
        }
        return new Info(len, max);
    }
}
