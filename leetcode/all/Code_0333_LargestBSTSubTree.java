package leetcode.all;

import java.util.LinkedList;
import java.util.List;

/*
    https://leetcode.cn/problems/largest-bst-subtree/
    333 最大BST子树
    给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，并返回该子树的大小。其中，最大指的是子树节点数最多的。
    二叉搜索树（BST）中的所有节点都具备以下属性：
        左子树的值小于其父（根）节点的值。
        右子树的值大于其父（根）节点的值。
        注意：子树必须包含其所有后代
 */
public class Code_0333_LargestBSTSubTree {
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

    public int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int size = getBSTSize(root);
        if (size != 0) {
            return size;
        }

        return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
    }

    private static int getBSTSize(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<TreeNode> list = new LinkedList<>();
        in(root, list);
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).val <= list.get(i - 1).val) {
                return 0;
            }
        }
        return list.size();
    }

    private static void in(TreeNode root, List<TreeNode> ans) {
        if (root == null) {
            return;
        }

        in(root.left, ans);
        ans.add(root);
        in(root.right, ans);
    }

    public int largestBSTSubtree2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return process(root).max_sub_bst_tree_size;
    }

    private static class Info {
        public int max_sub_bst_tree_size;
        public int all_size;
        public int max;
        public int min;

        public Info(int max_sub_bst, int all_size, int max, int min) {
            max_sub_bst_tree_size = max_sub_bst;
            this.all_size = all_size;
            this.max = max;
            this.min = min;
        }
    }

    private static Info process(TreeNode root) {
        if (root == null) {
            return null;
        }

        Info left_info = process(root.left);
        Info right_info = process(root.right);

        int max_sub_bst = 0;
        int all_size = 1;
        int max = root.val;
        int min = root.val;

        if (left_info != null) {
            max = Math.max(max, left_info.max);
            min = Math.min(min, left_info.min);
            all_size += left_info.all_size;
        }
        if (right_info != null) {
            max = Math.max(max, right_info.max);
            min = Math.min(min, right_info.min);
            all_size += right_info.all_size;
        }

        int p1 = -1;
        if (left_info != null) {
            p1 = left_info.max_sub_bst_tree_size;
        }
        int p2 = -1;
        if (right_info != null) {
            p2 = right_info.max_sub_bst_tree_size;
        }

        int p3 = -1;
        boolean left_bst = left_info == null ? true : (left_info.max_sub_bst_tree_size == left_info.all_size);
        boolean right_bst = right_info == null ? true : (right_info.max_sub_bst_tree_size == right_info.all_size);
        if (left_bst && right_bst) {
            boolean left_less = left_info == null ? true : (left_info.max < root.val);
            boolean right_more = right_info == null ? true : (right_info.min > root.val);
            if (left_less && right_more) {
                int left_size = left_info == null ? 0 : left_info.all_size;
                int right_size = right_info == null ? 0 : right_info.all_size;
                p3 = left_size + 1 + right_size;
            }
        }
        max_sub_bst = Math.max(Math.max(p1, p2), p3);
        return new Info(max_sub_bst, all_size, max, min);
    }

}
