package tixi.daily10;
/*
    二叉树递归遍历
 */
public class Code02_RecursiveTraverseBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    public static void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.value);
        inOrder(node.right);
    }

    public static void postOrder(Node node) {
        if (node == null) {
            return;
        }
        
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.value);
    }
}
