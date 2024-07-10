package leetcode.all;
/*
    2 两数相加
        给你两个非空 的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
        请你将两个数相加，并以相同形式返回一个表示和的链表。
        你可以假设除了数字 0 之外，这两个数都不会以 0开头

    提示：
        每个链表中的节点数在范围 [1, 100] 内
        0 <= Node.val <= 9
        题目数据保证列表表示的数字不含前导零

    示例
        输入：l1 = [2,4,3], l2 = [5,6,4]
        输出：[7,0,8]
        解释：342 + 465 = 807.

        输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
        输出：[8,9,9,9,0,0,0,1]
 */
public class Code_0002_AddTwoNumbers {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode cur1 = l1;
        ListNode cur2 = l2;
        ListNode prev = null;
        ListNode node = null;
        int val1 = 0;
        int val2 = 0;
        int cur_sum = 0;
        int carry = 0;

        while (cur1 != null || cur2 != null) {
            val1 = cur1 != null ? cur1.val : 0;
            val2 = cur2 != null ? cur2.val : 0;
            cur_sum = val1 + val2 + carry;
            carry = cur_sum / 10;
            prev = node;
            node = new ListNode(cur_sum%10);
            node.next = prev;

            cur1 = cur1 != null ? cur1.next : null;
            cur2 = cur2 != null ? cur2.next : null;
        }

        if (carry == 1) {
            prev = node;
            node = new ListNode(carry);
            node.next = prev;
        }
        return reverseList(node);
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }

        return prev;
    }

    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }

        if (l2 == null) {
            return l1;
        }

        ListNode head = null;
        ListNode tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int val1 = l1 == null ? 0 : l1.val;
            int val2 = l2 == null ? 0 : l2.val;
            int sum = carry + val1 + val2;

            if (head == null) {
                head = new ListNode(sum % 10);
                tail = head;
            } else {
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }

            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(1);
        }

        return head;
    }


}
