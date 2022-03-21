package leetcode;

/*
    958. 二叉树的完全性检验
        给定一个二叉树的root，确定它是否是一个完全二叉树。
        在一个完全二叉树中，除了最后一个关卡外，所有关卡都是完全被填满的，
        并且最后一个关卡中的所有节点都是尽可能靠左的。它可以包含1到2^h节点之间的最后一级 h

 */
public class Code_0958_CheckCompletenessOfABinaryTree {
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

    public static class Info {
        public boolean is_full;
        public boolean is_cbt;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            is_full = full;
            is_cbt = cbt;
            height = h;
        }
    }

    public boolean isCompleteTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).is_cbt;
    }

    public static Info process(TreeNode node) {
        if (node == null) {
            return new Info(true, true, 0);
        }

        Info left_info = process(node.left);
        Info right_info = process(node.right);

        int height = Math.max(left_info.height, right_info.height) + 1;
        boolean is_full = left_info.is_full && right_info.is_full
                && left_info.height == right_info.height;

        boolean is_cbt = false;
        if (is_full) {
            is_cbt = true;
        } else {
            if (left_info.is_cbt && right_info.is_cbt) {
                if (left_info.is_cbt && right_info.is_full
                    && left_info.height == right_info.height + 1) {
                    is_cbt = true;
                }

                if (left_info.is_full && right_info.is_full
                    && left_info.height == right_info.height + 1) {
                    is_cbt = true;
                }

                if (left_info.is_full && right_info.is_cbt
                    && left_info.height == right_info.height) {
                    is_cbt = true;
                }
            }
        }

        return new Info(is_full, is_cbt, height);
    }
}
