package leetcode;

import java.util.HashMap;
import java.util.HashSet;

/*
    236. 二叉树的最近公共祖先
        给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
 */
public class Code_0236_LowestCommonAncestorOfaBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        HashMap<TreeNode, TreeNode> parent_map = fillParentMap(root);
        TreeNode low_ancestor = null;
        TreeNode cur = p;
        HashSet<TreeNode> set = new HashSet<>();
        set.add(cur);
        while (parent_map.get(cur) != null) {
            cur = parent_map.get(cur);
            set.add(cur);
        }

        cur = q;
        while (!set.contains(cur)) {
            cur = parent_map.get(cur);
        }

        return cur;
    }

    private static HashMap<TreeNode, TreeNode> fillParentMap(TreeNode head) {
        HashMap<TreeNode, TreeNode> ans = new HashMap<>();
        ans.put(head, null);
        fillParent(head, ans);
        return ans;
    }

    private static void fillParent(TreeNode head, HashMap<TreeNode, TreeNode> ans) {
        if (head.left != null) {
            ans.put(head.left, head);
            fillParent(head.left, ans);
        }

        if (head.right != null) {
            ans.put(head.right, head);
            fillParent(head.right, ans);
        }
    }

    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        return process(root, p, q).ans;
    }

    private static Info process(TreeNode head, TreeNode p, TreeNode q) {
        if (head == null) {
            return new Info(false, false, null);
        }

        Info left_info = process(head.left, p, q);
        Info right_info = process(head.right, p, q);
        boolean find_p = head == p || left_info.find_p || right_info.find_p;
        boolean find_q = head == q || left_info.find_q || right_info.find_q;
        TreeNode ans = null;
        if (left_info.ans != null) {
            ans = left_info.ans;
        } else if (right_info.ans != null) {
            ans = right_info.ans;
        } else {
            if (find_p && find_q) {
                ans = head;
            }
        }

        return new Info(find_p, find_q, ans);
    }

    private static class Info {
        boolean find_p;
        boolean find_q;
        TreeNode ans;

        public Info(boolean find_p, boolean find_q, TreeNode ans) {
            this.find_p = find_p;
            this.find_q = find_q;
            this.ans = ans;
        }
    }
}
