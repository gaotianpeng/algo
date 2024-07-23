package leetcode.all;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
    leetcode 94 二叉树的中序列遍历
        给定一个二叉树的根节点root，返回它的中序遍历
    https://leetcode.cn/problems/binary-tree-inorder-traversal/
 */
public class Code_0094_BinaryTreeInorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        process(root, ans);
        return ans;
    }

    public static void process(TreeNode root, List<Integer> ans) {
        if (root == null) {
            return;
        }
        process(root.left, ans);
        ans.add(root.val);
        process(root.right, ans);
    }

    public static  List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root != null) {
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.isEmpty() || root != null) {
                if (root != null) {
                    stack.add(root);
                    root = root.left;
                } else {
                    root = stack.pop();
                    ans.add(root.val);
                    root = root.right;
                }
            }
        }
        return ans;
    }

    public static List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }

        TreeNode cur = root;
        TreeNode most = null;
        while (cur != null) {
            most = cur.left;
            if (most != null) {
                while (most.right != null && most.right != cur) {
                    most = most.right;
                }

                if (most.right == null) {
                    most.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    most.right = null;
                }
            }
            ans.add(cur.val);
            cur = cur.right;
        }

        return ans;
    }
}