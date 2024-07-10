package leetcode.all;

import java.util.Stack;

/*
    234 回文链表
        给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。
        如果是，返回 true ；否则，返回 false
 */
public class Code_0234_PalindromeLinkedList {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) {
          this.val = val; this.next = next;
        }
    }
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        cur = head;
        while (!stack.isEmpty()) {
            if (cur.val != stack.pop().val) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    public boolean isPalindrome1(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode cur = head;
        ListNode right = head.next;
        while (cur.next != null && cur.next.next != null) {
            cur = cur.next.next;
            right = right.next;
        }

        Stack<ListNode> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }

        cur = head;
        while (!stack.isEmpty()) {
            if (cur.val != stack.pop().val) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    public boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode mid = head;
        ListNode cur = head;
        while (cur.next != null && cur.next.next != null) {
            mid = mid.next;
            cur = cur.next.next;
        }

        ListNode right_first = mid.next;
        mid.next = null;
        ListNode pre = null;
        ListNode next = null;
        while (right_first != null) {
            next = right_first.next;
            right_first.next = pre;
            pre = right_first;
            right_first = next;
        }

        cur = head;
        boolean ans = true;
        ListNode reverse_head = pre;
        right_first = pre;
        while (cur != null && right_first != null) {
            if (cur.val != right_first.val) {
                ans = false;
                break;
            }
            cur = cur.next;
            right_first = right_first.next;
        }

        pre = null;
        next = null;
        while (reverse_head != null) {
            next = reverse_head.next;
            reverse_head.next = pre;
            pre = reverse_head;
            reverse_head = next;
        }
        mid.next = pre;
        return ans;
    }
}
