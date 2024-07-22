package leetcode.all;

import java.util.ArrayList;
import java.util.HashSet;

public class Code_0141_LinkedListCycle {
    public static class ListNode {
        public int val;
        ListNode next;
        public ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return false;
        }

        HashSet<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }

        return false;
    }

    public static boolean hasCycle1(ListNode head) {
        if (head == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }

        return false;
    }

    public static ListNode generateRandomLinkedList(int maxLen, int maxValue) {
        int listLen = (int)(Math.random() * (maxLen+ 1));
        if (listLen == 0) {
            return null;
        }

        ArrayList<ListNode> list = new ArrayList<>();
        ListNode head = new ListNode((int)(Math.random()*(maxValue+1)));
        list.add(head);
        ListNode prev = head;
        while (listLen != 0) {
            ListNode node = new ListNode((int)(Math.random()*(maxValue+1)));
            list.add(node);
            prev.next = node;
            prev = node;
            listLen--;
        }

        if (Math.random() < 0.5) {
            prev.next = list.get((int)(Math.random()*list.size()));
        }

        return head;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 1000000;
        int maxLen = 12;
        int maxVal = 40;
        boolean success = true;

        for (int i = 0; i < testTimes; i++) {
            ListNode head = generateRandomLinkedList(maxLen, maxVal);
            if (hasCycle(head) != hasCycle1(head)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
