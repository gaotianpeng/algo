package leetcode;

import java.util.HashMap;

public class Code_0105_ConstructBinaryTreeFromPreorderAndInorderTraversal {
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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null) {
            return null;
        }

        HashMap<Integer, Integer> val_index_map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            val_index_map.put(inorder[i], i);
        }
        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length -1, val_index_map);
    }

    public TreeNode build(int[] pre, int l1, int r1, int[] in, int l2, int r2, HashMap<Integer, Integer> map) {
        if (l1 > r1) {
            return null;
        }

        TreeNode head = new TreeNode(pre[l1]);
        if (l1 == r1) {
            return head;
        }

        int head_pos = map.get(pre[l1]);
        head.left = build(pre, l1 + 1, l1 + head_pos - l2,
                    in, l2, head_pos -1, map);
        head.right = build(pre, l1 + head_pos  - l2 + 1, r1,
                    in,head_pos + 1, r2, map);
        return head;
    }
}
