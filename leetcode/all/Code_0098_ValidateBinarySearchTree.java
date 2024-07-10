package leetcode.all;

import java.util.LinkedList;
import java.util.List;

public class Code_0098_ValidateBinarySearchTree {
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

    public static boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        List<TreeNode> in_list = new LinkedList<>();
        inorder(root, in_list);
        for (int i = 1; i < in_list.size(); i++) {
            if (in_list.get(i).val < in_list.get(i-1).val) {
                return false;
            }
        }
        return true;
    }

    public static void inorder(TreeNode root, List<TreeNode> ans) {
        if (root == null) {
            return;
        }
        inorder(root.left, ans);
        ans.add(root);
        inorder(root.right, ans);
    }

    public static boolean isValidBST1(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).is_bst;
    }


    private static class Info {
        public int max;
        public int min;
        public boolean is_bst;

        public Info(boolean is_bst, int max, int min) {
            this.max = max;
            this.min = min;
            this.is_bst = is_bst;
        }
    }

    private static Info process(TreeNode root) {
        if (root == null) {
            return null;
        }

        Info left_info = process(root.left);
        Info right_info = process(root.right);

        int max = root.val;
        if (left_info != null) {
            max = Math.max(max, left_info.max);
        }
        if (right_info != null) {
            max = Math.max(max, right_info.max);
        }

        int min = root.val;
        if (left_info != null) {
            min = Math.min(min, left_info.min);
        }
        if (right_info != null) {
            min = Math.min(min, right_info.min);
        }

        boolean is_bst = true;
        if (left_info != null && !left_info.is_bst) {
            is_bst = false;
        }

        if (right_info != null && !right_info.is_bst) {
            is_bst = false;
        }

        if (left_info != null && left_info.max >= root.val) {
            is_bst = false;
        }

        if (right_info != null && right_info.min <= root.val) {
            is_bst = false;
        }

        return new Info(is_bst,max, min);
    }
}
