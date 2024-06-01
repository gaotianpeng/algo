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
        public static TreeNode encode(Node root) {
            if (root == null) {
                return null;
            }

            TreeNode head = new TreeNode(root.val);
            head.left = en(root.children);
            return head;
        }

        private static TreeNode en(List<Node> children) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
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
        public static Node decode(TreeNode root) {
            if (root == null) {
                return null;
            }

            return new Node(root.val, de(root.left));
        }

        private static List<Node> de(TreeNode root) {
            List<Node> ans = new ArrayList<>();
            while (root != null) {
                Node node = new Node(root.val, de(root.left));
                ans.add(node);
                root = root.right;
            }
            return ans;
        }
    }

    // Generates a random n-ary tree
    public static Node generateRandomNTree(int maxVal, int maxLevel, int maxChildren) {
        return generateNTree(1, maxLevel, maxVal, maxChildren);
    }

    private static Node generateNTree(int level, int maxLevel, int maxVal, int maxChildren) {
        if (level > maxLevel || Math.random() > 0.5) {
            return null;
        }

        Node node = new Node(randomVal(maxVal));
        int numChildren = (int) (Math.random() * (maxChildren + 1));
        node.children = new ArrayList<>();
        for (int i = 0; i < numChildren; i++) {
            Node child = generateNTree(level + 1, maxLevel, maxVal, maxChildren);
            if (child != null) {
                node.children.add(child);
            }
        }

        return node;
    }

    private static int randomVal(int maxVal) {
        return (int) (Math.random() * (maxVal + 1));
    }

    // Compares two n-ary trees for equality
    public static boolean compareNTrees(Node n1, Node n2) {
        if (n1 == null && n2 == null) {
            return true;
        }
        if (n1 == null || n2 == null) {
            return false;
        }
        if (n1.val != n2.val) {
            return false;
        }
        if ((n1.children == null && n2.children != null) || (n1.children != null && n2.children == null)) {
            return false;
        }
        if (n1.children != null && n2.children != null) {
            if (n1.children.size() != n2.children.size()) {
                return false;
            }
            for (int i = 0; i < n1.children.size(); i++) {
                if (!compareNTrees(n1.children.get(i), n2.children.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxLevel = 10;
        int maxVal = 100;
        int maxChildren = 5;

        Codec codec = new Codec();

        for (int i = 0; i < testTimes; ++i) {
            Node tree = generateRandomNTree(maxVal, maxLevel, maxChildren);
            TreeNode encodedTree = codec.encode(tree);
            Node decodedTree = codec.decode(encodedTree);
            if (!compareNTrees(tree, decodedTree)) {
                success = false;
                System.out.println("Failed at test case: " + i);
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
