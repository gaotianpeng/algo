package leetcode.all;

import java.util.List;

/*
    Leetcode
        431. Encode N-ary Tree to Binary Tree
 */
public class Code_0431_EncodeNaryTreeToBinaryTree {
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Codec {
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }

            TreeNode head = new TreeNode(root.val);
            head.left = en(root.children);
            return head;
        }

        private TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child: children) {
                TreeNode tree_node = new TreeNode(child.val);
                if (head == null) {
                    head = tree_node;
                } else {
                    cur.right = tree_node;
                }
                cur = tree_node;
                cur.left = en(child.children);
            }
            return head;
        }

        public Node decode(TreeNode root) {
            return null;
        }

        public List<Node> de(TreeNode root) {
            return null;
        }
    }
}
