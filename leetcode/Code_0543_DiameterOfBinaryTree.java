package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/*
    543 二叉树的直径
        给定一棵二叉树，你需要计算它的直径长度。
        一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
        两结点之间的路径长度是以它们之间边的数目表示

        示例
                  1
                 / \
                2   3
               / \
              4   5
          返回 3, 它的长度是路径 [4,2,1,3] 或者 [5,2,1,3]
 */
public class Code_0543_DiameterOfBinaryTree {
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

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).max_distance;
    }

    private static Info process(TreeNode root) {
        if (root == null) {
            return new Info(0, 0);
        }

        Info left_info = process(root.left);
        Info right_info = process(root.right);
        int height = Math.max(left_info.height, right_info.height) + 1;
        int max_distance = Math.max(left_info.max_distance, right_info.max_distance);
        max_distance = Math.max(max_distance, left_info.height + right_info.height);

        return new Info(height, max_distance);
    }

    private static class Info {
        public int height;
        public int max_distance;

        public Info(int h, int m) {
            height = h;
            max_distance = m;
        }
    }



    public int diameterOfBinaryTree1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        List<TreeNode> pre_list = getPreList(root);
        HashMap<TreeNode, TreeNode> parent_map = getParentMap(root);
        int max = 0;
        for (int i = 0; i < pre_list.size(); i++) {
            for (int j = i; j < pre_list.size(); j++) {
                max = Math.max(max, distance(parent_map, pre_list.get(i), pre_list.get(j)));
            }
        }

        return max;
    }

    private static List<TreeNode> getPreList(TreeNode head) {
        List<TreeNode> ans = new LinkedList<>();
        getPre(head, ans);
        return ans;
    }

    private static void getPre(TreeNode head, List<TreeNode> ans) {
        if (head == null) {
            return;
        }

        ans.add(head);
        getPre(head.left, ans);
        getPre(head.right, ans);
    }

    private static HashMap<TreeNode, TreeNode> getParentMap(TreeNode head) {
        HashMap<TreeNode, TreeNode> ans = new HashMap<>();
        ans.put(head, null);
        getParent(head, ans);
        return ans;
    }

    private static void getParent(TreeNode head, HashMap<TreeNode, TreeNode> map) {
        if (head.left != null) {
            map.put(head.left, head);
            getParent(head.left, map);
        }

        if (head.right != null) {
            map.put(head.right, head);
            getParent(head.right, map);
        }
    }

    private int distance(HashMap<TreeNode, TreeNode> parent_map, TreeNode node1, TreeNode node2) {
        HashSet<TreeNode> set = new HashSet<>();
        TreeNode cur = node1;
        set.add(cur);
        while (parent_map.get(cur) != null) {
            cur = parent_map.get(cur);
            set.add(cur);
        }

        cur = node2;
        while (!set.contains(cur)) {
            cur = parent_map.get(cur);
        }

        TreeNode lowest_ancestor = cur;
        int distance1 = 0;
        cur = node1;
        while (cur != lowest_ancestor) {
            ++distance1;
            cur = parent_map.get(cur);
        }

        int distance2 = 0;
        cur = node2;
        while (cur != lowest_ancestor) {
            ++distance2;
            cur = parent_map.get(cur);
        }

        return distance1 + distance2;
    }
}
