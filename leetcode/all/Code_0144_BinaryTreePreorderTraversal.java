package leetcode.all;

import java.util.ArrayList;
import java.util.List;
/*
    leetcode 144 二叉树前序遍历
        https://leetcode.cn/problems/binary-tree-preorder-traversal/
 */
public class Code_0144_BinaryTreePreorderTraversal {
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

    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        TreeNode cur = root;
        TreeNode most_right = null;
        while (cur != null) {
            most_right = cur.left;
            if (most_right != null) {
                while (most_right.right != null && most_right.right != cur) {
                    most_right = most_right.right;
                }
                if (most_right.right == null) {
                    ans.add(cur.val);
                    most_right.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    most_right.right = null;
                }
            } else {
                ans.add(cur.val);
            }

            cur = cur.right;
        }

        return ans;
    }
}
