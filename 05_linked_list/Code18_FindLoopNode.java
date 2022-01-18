package list;

import java.util.HashSet;

/*
    链表可能有环，也可能无环，也可能是一个全环形
    1 如果非全环形返回入环点
    2 如果是全环形，返回头节点即可
    3 如果无环返回null
 */
public class Code18_FindLoopNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    public static Node findLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }



        return null;
    }
    /*
        for test
     */
    public static Node test(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        HashSet<Node> set = new HashSet<Node>();
        Node cur = head;
        Node loop_node = null;
        while (cur != null) {
            if (set.contains(cur)) {
                loop_node = cur;
                break;
            }
            set.add(cur);
            cur = cur.next;
        }

        return loop_node;
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 20;
        int max_len = 20;
        int test_times = 1000000;

        System.out.println("test end");
    }
}
