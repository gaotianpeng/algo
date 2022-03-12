package leetcode;

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
}
