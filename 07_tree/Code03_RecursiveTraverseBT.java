package tree;

public class Code03_RecursiveTraverseBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preTraverse(Node head) {
        if (head == null) {
            return;
        }
        System.out.println(head.value + " ");
        preTraverse(head.left);
        preTraverse(head.right);
    }

    public static void inTraverse(Node head) {
        if (head == null) {
            return;
        }
        inTraverse(head.left);
        System.out.println(head.value + " ");
        inTraverse(head.right);
    }

    public static void posTraverse(Node head) {
        if (head == null) {
            return;
        }
        
        posTraverse(head.left);
        posTraverse(head.right);
        System.out.println(head.value + " ");
    }
}
