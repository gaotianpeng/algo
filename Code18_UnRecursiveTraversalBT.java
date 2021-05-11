import java.util.Stack;

public class Code18_UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preTraversal(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null) {
                    stack.push(head.right);
                }
                if (head.left != null) {
                    stack.push(head.left);
                }
            }
        }
        System.out.println();
    }

    public static void inTraversal(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            while (!stack.empty() || head != null) {
                if (head != null) {
                    stack.push(head);
                    head = head.left;
                } else {
                    head = stack.pop();
                    System.out.print(head.value + " ");
                    head = head.right;
                }
            }
        }
        System.out.println();
    }

    public static void pos1Traversal(Node head) {
        if (head != null) {
            Stack<Node> s1 = new Stack<Node>();
            Stack<Node> s2 = new Stack<Node>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void pos2Traversal(Node h) {
        if (h != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.push(h);
            Node cur = null;
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && h != cur.left && h != cur.right) {
                    stack.push(cur.left);
                } else if (cur.right != null && h != cur.right) {
                    stack.push(cur.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    h = cur;
                }
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        System.out.println("pre traversal---------------------");
        preTraversal(head);
        System.out.println("in  traversal---------------------");
        inTraversal(head);
        System.out.println("pos traversal---------------------");
        pos1Traversal(head);
        pos2Traversal(head);
}
