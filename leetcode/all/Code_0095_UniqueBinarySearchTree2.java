package leetcode.all;

import java.util.LinkedList;
import java.util.List;

public class Code_0095_UniqueBinarySearchTree2 {
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

    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<TreeNode>();
        }

        return process(1, n);
    }

    private static List<TreeNode> process(int start, int end) {
        List<TreeNode> ans = new LinkedList<>();
        if (start > end) {
            ans.add(null);
            return ans;
        }
        // 枚举可能的根节点
        for (int i = start; i <= end; ++i) {
            List<TreeNode> left_trees = process(start, i - 1);
            List<TreeNode> right_trees = process(i+1, end);
            for (TreeNode left: left_trees) {
                for (TreeNode right: right_trees) {
                    TreeNode tree_root = new TreeNode(i);
                    tree_root.left = left;
                    tree_root.right = right;
                    ans.add(tree_root);
                }
            }
        }

        return ans;
    }
}
