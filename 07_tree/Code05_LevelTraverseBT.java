package tree;

import java.util.LinkedList;
import java.util.Queue;

public class Code05_LevelTraverseBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void levelTraverse(Node head) {
        if (head == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.value + " ");
            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }
}
