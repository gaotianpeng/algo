package tixi.daily10;
/*
    给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，
    如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
    【要求】
        如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 */
public class Code01_FindFirstIntersectionNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    public static Node getIntersectionNode(Node head1, Node head2) {
        return null;
    }

    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null)  {
            return null;
        }

        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        fast = head;
        while (slow != head) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {

        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }

}
