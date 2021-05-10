public class Code17_RecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preTraversal(Node head) {
        if (head == null) {
            return;
        }

        System.out.print(head.value + " ");
        preTraversal(head.left);
        preTraversal(head.right);
    }

    public static void inTraversal(Node head) {
        if (head == null) {
            return;
        }

        inTraversal(head.left);
        System.out.print(head.value + " ");
        inTraversal(head.right);
    }

    public static void posTraversal(Node head) {
        if (head == null) {
            return;
        }

        posTraversal(head.left);
        posTraversal(head.right);
        System.out.print(head.value + " ");
    }

    public static void templateFunc(Node head) {
        if (head == null) {
            return;
        }

        // 1 先序: print(head.value)
        templateFunc(head.left);
        // 2 中序: print(head.value)
        templateFunc(head.right);
        // 3 后序: print(head.value)
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println("pre traversal --------------------");
        preTraversal(head);
        System.out.println();

        System.out.println("in  traversal --------------------");
        inTraversal(head);
        System.out.println();

        System.out.println("pos traversal --------------------");
        posTraversal(head);
        System.out.println();
    }
}
