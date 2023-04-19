package leetcode;

public class Code_0450_DeleteNodeInABST {
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

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        // 找到 == key 的节点，保存在 cur 中
        TreeNode cur = root, cur_parent = null;
        while (cur != null && cur.val != key) {
            cur_parent = cur;
            if (cur.val > key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }

        if (cur == null) {
            return root;
        }

        if (cur.left == null && cur.right == null) {
            cur = null;
        } else if (cur.right == null) {
            cur = cur.left;
        } else if (cur.left == null) {
            cur = cur.right;
        } else {
            TreeNode right_left_most = cur.right, right_left_most_p = cur;
            while (right_left_most.left != null) {
                right_left_most_p = right_left_most;
                right_left_most = right_left_most.left;
            }
            if (right_left_most_p.val == cur.val) {
                right_left_most_p.right = right_left_most.right;
            } else {
                right_left_most_p.left = right_left_most.right;
            }
            right_left_most.right = cur.right;
            right_left_most.left = cur.left;
            cur = right_left_most;
        }

        if (cur_parent == null) {
            return cur;
        } else {
            if (cur_parent.left != null && cur_parent.left.val == key) {
                cur_parent.left = cur;
            } else {
                cur_parent.right = cur;
            }
            return root;
        }
    }
}
