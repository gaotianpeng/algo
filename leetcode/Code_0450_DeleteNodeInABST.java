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

    public TreeNode deleteNode1(TreeNode root, int key) {
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
        // 未找要要删除的节点, 返回root
        if (cur == null) {
            return root;
        }

        // cur 来到要删除的节点(cur.val = key)
        // 1) cur 左树为空，右树也为空
        if (cur.left == null && cur.right == null) {
            cur = null;
        // 2) cur 右树为空
        } else if (cur.right == null) {
            cur = cur.left;
        // 3) cur 左树为空
        } else if (cur.left == null) {
            cur = cur.right;
        // 4) cur 左树、右树均不为空，用右树最小节点代替cur
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

    public TreeNode deleteNode2(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        TreeNode cur = root;
        TreeNode cur_parent = null;
        while (cur != null && cur.val != key) {
            cur_parent = cur;
            if (cur.val > key) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        // 未找到要删除的节点，直接返回root
        if (cur == null) {
            return root;
        }

        if (cur.left == null && cur.right == null) {
            cur = null;
        } else if (cur.left == null && cur.right != null) {
            cur = cur.right;
        } else if (cur.left != null && cur.right == null) {
            cur = cur.left;
        } else {
            TreeNode left_most_right = cur.left;
            TreeNode left_most_right_parent = cur;
            while (left_most_right.right != null) {
                left_most_right_parent = left_most_right;
                left_most_right = left_most_right.right;
            }

            if (left_most_right_parent.val == cur.val) {
                left_most_right_parent.left = left_most_right.left;
            } else {
                left_most_right_parent.right = left_most_right.left;
            }
            left_most_right.left = cur.left;
            left_most_right.right = cur.right;
            cur = left_most_right;
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
