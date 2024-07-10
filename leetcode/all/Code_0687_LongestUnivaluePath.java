package leetcode.all;
/*
    687 最长同值路径
    给定一个二叉树的root, 返回最长的路径的长度, 这个路径中的每个节点具有相同值. 这条路径可以经过也可以不经过根节点。
    两个节点之间的路径长度由它们之间的边数表示
 */
public class Code_0687_LongestUnivaluePath {
    public static class TreeNode {
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

    public static int longestUnivaluePath(TreeNode root) {
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

        TreeNode l = x.left;
        TreeNode r = x.right;
        Info lInfo = process(l);
        Info rInfo = process(r);
        int len = 1;
        if (l != null && l.val == x.val) {
            len = lInfo.len + 1;
        }
        if (r != null && r.val == x.val) {
            len = Math.max(len, rInfo.len + 1);
        }

        int max = Math.max(Math.max(lInfo.max, rInfo.max), len);
        if (l != null && r != null && l.val == x.val && r.val == x.val) {
            max = Math.max(lInfo.len + rInfo.len + 1, max);
        }

        return new Info(len, max);
    }

    public static int longestUnivaluePath1(TreeNode root) {
        int[] res = new int[1]; // 使用数组来存储结果，数组是可变的，可以传递引用
        dfs(root, res);
        return res[0];
    }

    public static int dfs(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }

        int left = dfs(root.left, res), right = dfs(root.right, res);
        int left1 = 0, right1 = 0;
        if (root.left != null && root.left.val == root.val) {
            left1 = left + 1;
        }

        if (root.right != null && root.right.val == root.val) {
            right1 = right + 1;
        }
        res[0] = Math.max(res[0], left1 + right1);
        return Math.max(left1, right1);
    }
}
