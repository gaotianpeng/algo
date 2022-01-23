package tree;
import java.util.Stack;

public class Code04_UnRecursiveTraverseBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void preTraverse(Node head) {
        System.out.println("pre order:");
        if (head != null) {
            Stack<Node> stack = new Stack<Node>();
            stack.add(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.println(head.value + " ");
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

    public static void inTraverse(Node cur) {
        System.out.println("in order:");
        if (cur != null) {
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.println(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    public static void posTraverse(Node head) {
        System.out.println("pos order:");
        if (head != null) {
            Stack<Node> s1 = new Stack<>();
            Stack<Node> s2 = new Stack<>();
            s1.push(head);
            while (!s1.isEmpty()) {
                head = s1.pop();    // 头、右、左
                s2.push(head);
                if (head.left != null) {
                    s1.push(head.left);
                }
                if (head.right != null) {
                    s1.push(head.right);
                }
            }
            // 左、右、头
            while (!s2.isEmpty()) {
                System.out.print(s2.pop().value + " ");
            }
        }
        System.out.println();
    }

    public static void posTraverse1(Node head) {
        System.out.println("pos order1:");
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            Node cur = null;
            while (!stack.isEmpty()) {
                cur = stack.peek();
                if (cur.left != null && head != cur.left && head != cur.right) {
                    stack.push(cur.left);
                } else if (cur.right != null && head != cur.right) {
                    stack.push(cur.right);
                } else {
                    System.out.print(stack.pop().value + " ");
                    head = cur;
                }
            }
        }
        System.out.println();
    }
}
