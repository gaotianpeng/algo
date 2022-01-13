package list;
/*
    给定两个可能有环也可能无环的单链表，头节点head1和head2
    请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
    【要求】
        如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)
 */
public class Code17_FindFirstIntersectNode {
    public static class Node {
        public int value;
        public Node next;

        public Node(int val) {
            value = val;
            next = null;
        }
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }

        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }

        return null;
    }

    /*
        找到链表第一个入环节点，如果无环，返回null
     */
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        Node slow = head.next;
        Node fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    /*
        如果两个链表都无环, 返回第一个相交节点，如果不相交, 返回null
     */
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }

        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }

        if (cur1 != cur2) {
            return null;
        }

        cur1 = n > 0 ? head1: head2;
        cur2 = cur1 == head1 ? head2 : head1;
        n = Math.abs(n);
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /*
        两个有环链表，返回第一个相交节点，如果不相交返回null
     */
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }

            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {
                    return loop1;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    /*
        for test
     */
    public static class DoubleNodes {
        public Node head1;
        public Node head2;

        public DoubleNodes(Node head1, Node head2) {
            this.head1 = head1;
            this.head2 = head2;
        }
    }

    public static DoubleNodes generateTwoRandomLinkedList(int max_val, int max_len) {
        double p = Math.random();
        if (p < 0.25) {
            return genTwoNoLoopNoIntersectionList(max_val, max_len);
        } else if (p < 0.5) {
            return genTwoNoLoopIntersectionList(max_val, max_len);
        } else if (p < 0.75) {
            return genTwoLoopNoIntersectionList(max_val, max_len);
        } else {
            return genTwoLoopIntersectionList(max_val, max_len);
        }
    }

    public static DoubleNodes genTwoNoLoopNoIntersectionList(int max_val, int max_len) {
        return new DoubleNodes(genRandomLinkedList(max_val, max_len),
                                genRandomLinkedList(max_val, max_len));
    }

    public static DoubleNodes genTwoNoLoopIntersectionList(int max_val, int max_len) {
        Node head1 = genRandomLinkedList(max_val, max_len);
        Node head2 = genRandomLinkedList(max_val, max_len);
        int list1_len = getLinkedListLength(head1);
        int list2_len = getLinkedListLength(head2);

        int pos = (int)(Math.random()*(Math.min(list1_len, list2_len))) + 1;
        Node cur1 = head1;
        Node cur2 = head2;
        int i = 0;
        while (cur1 != null) {
            if (i == pos - 1) {
                cur2.next = cur1;
                break;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
            i++;
        }

        return new DoubleNodes(head1, head2);
    }

    public static DoubleNodes genTwoLoopNoIntersectionList(int max_val, int max_len) {
        return new DoubleNodes(null, null);
    }

    public static DoubleNodes genTwoLoopIntersectionList(int max_val, int max_len) {
        return new DoubleNodes(null, null);
    }

    public static Node genRandomLinkedList(int max_val, int max_len) {
        int arr_len = (int)(Math.random()*(max_len + 1));
        if (arr_len == 0) {
            return null;
        }

        Node head = new Node((int)(Math.random()*(1+max_val)));
        Node prev = head;
        for (int i = 1; i < arr_len; i++) {
            Node node = new Node((int)(Math.random()*(1+max_val)));
            prev.next = node;
            prev = node;
        }

        return head;
    }

    public static int getLinkedListLength(Node head) {
        Node cur = head;
        int ret = 0;
        while (cur != null) {
            ++ret;
            cur = cur.next;
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 30;
        int max_len = 25;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            DoubleNodes nodes = generateTwoRandomLinkedList(max_val, max_len);
            getIntersectNode(nodes.head1, nodes.head2);
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
