package leetcode;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Code_0023_MergeKSortedLists {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public static class ListNodeComparator implements Comparator<ListNode> {
        @Override
        public int compare(ListNode o1, ListNode o2) {
            return o1.val - o2.val;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }

        PriorityQueue<ListNode> min_heap = new PriorityQueue<>(new ListNodeComparator());
        for (int i = 0; i < lists.length; ++i) {
            if (lists[i] != null) {
                min_heap.add(lists[i]);
            }
        }

        if (min_heap.isEmpty()) {
            return null;
        }

        ListNode head = min_heap.poll();
        ListNode pre = head;
        if (pre.next != null) {
            min_heap.add(pre.next);
        }

        while (!min_heap.isEmpty()) {
            ListNode cur = min_heap.poll();
            pre.next = cur;
            pre = cur;
            if (cur.next != null) {
                min_heap.add(cur.next);
            }
        }

        return head;
    }
}
