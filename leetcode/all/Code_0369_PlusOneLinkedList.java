package leetcode.all;


/*
    369. Plus One Linked List
        Given a non-negative integer represented as a linked list of digits, plus one to the integer.
        The digits are stored such that the most significant digit is at the head of the list.
    Example 1:
        Input: head = [1,2,3]
        Output: [1,2,4]

    Example 2:
        Input: head = [0]
        Output: [1]

Constraints:
    The number of nodes in the linked list is in the range [1, 100].
    0 <= Node.val <= 9
    The number represented by the linked list does not contain leading zeros except for the zero itself.

 */
public class Code_0369_PlusOneLinkedList {
    public static class ListNode {
         int val;
         ListNode next;
         ListNode() {}
         ListNode(int val) { this.val = val; }
         ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static ListNode plusOne(ListNode head) {
        if (head == null) {
            return new ListNode(1);
        }

        ListNode rever = reverse(head);
        ListNode cur = rever;
        int carry = 1;
        while (cur != null) {
            int val = cur.val + carry;
            carry = val / 10;
            cur.val = val % 10;
            if (carry == 0) {
                break;
            }
            if (cur.next == null) {
                cur.next = new ListNode(0);
            }
            cur = cur.next;
        }

        return reverse(rever);
    }
    public static ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }

        return pre;
    }
}
