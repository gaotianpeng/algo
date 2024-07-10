package leetcode.all;

public class Code_0101_SymmetricTree {
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

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isMirror(root, root);
    }

    public static boolean isMirror(TreeNode head1, TreeNode head2) {
        if (head1 == null ^ head2 == null) {
            return false;
        }

        if (head1 == null && head2 == null) {
            return true;
        }

        return head1.val == head2.val && isMirror(head1.left, head2.right)
                    && isMirror(head1.right, head2.left);
    }
}
