package tixi.daily10;
/*
    leetcode 160 相交链表
        给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，
        如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
        【要求】
            如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。
 */
public class Code01_FindFirstIntersectionNode {
    public static class ListNode {
        public int value;
        public ListNode next;

        public ListNode(int val) {
            value = val;
            next = null;
        }
    }

    public static ListNode getIntersectionNode(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        ListNode loop1 = getLoopListNode(head1);
        ListNode loop2 = getLoopListNode(head2);

        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }

        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }

        return null;
    }

    // 找到链表第一个入环节点，如果无环，返回null
    public static ListNode getLoopListNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast) {
            if (fast.next == null || fast.next.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return fast;
    }

    // 如果两个链表都无环，返回第一个相交节点，如果不想交，返回null
    public static ListNode noLoop(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return null;
        }

        int n = 0;
        ListNode cur1 = head1;
        while (cur1 != null) {
            n++;
            cur1 = cur1.next;
        }

        ListNode cur2 = head2;
        while (cur2 != null) {
            n--;
            cur2 = cur2.next;
        }

        if (cur1 != cur2) {
            return null;
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
    }

    // 两个有环链表，返回第一个相交节点，如果不相交返回null
    public static ListNode bothLoop(ListNode head1, ListNode loop1, ListNode head2, ListNode loop2) {
        ListNode cur1 = null;
        ListNode cur2 = null;
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop1) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = cur1 == head1 ? head2 : head1;
            n = Math.abs(n);
            while (n != 0) {
                cur1 = cur1.next;
                n--;
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
                    return loop2;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }
}
