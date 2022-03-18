package tixi.daily11;

/*
    431. 将N叉树编码为二叉树
 */

import java.util.ArrayList;
import java.util.List;

public class Code03_EncodeNaryTreeToBinaryTree {
    // 提交时不要提交这个类
    public static class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // 提交时不要提交这个类
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static class Codec {
        // Encodes an n-ary tree to a binary tree.
        public TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }

            TreeNode tree_root = new TreeNode(root.val);
            tree_root.left = en(root.children);
            return tree_root;
        }

        private static TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child: children) {
                TreeNode node = new TreeNode(child.val);
                if (head == null) {
                    head = node;
                } else {
                    cur.right = node;
                }
                cur = node;
                cur.left = en(child.children);
            }
            return head;
        }

        // Decodes your binary tree to an n-ary tree.
        public Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }

            return new Node(root.val, de(root.left));
        }

        private static List<Node> de(TreeNode root) {
            List<Node> children = new ArrayList<>();
            while (root != null) {
                Node node = new Node(root.val, de(root.left));
                children.add(node);
                root = root.right;
            }
            return children;
        }
    }
}
