package leetcode;

public class Code_0204_PartitionListLcci {
    /*
        https://leetcode.cn/problems/partition-list-lcci/
        面试题 02.04. 分割链表
            给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，
            使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
            你不需要保留每个分区中各节点的初始相对位置
     */
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode small = new ListNode(0);
        ListNode small_head = small;
        ListNode large = new ListNode(0);
        ListNode large_head = large;

        while (head != null) {
            if (head.val < x) {
                small.next = head;
                small = small.next;
            } else {
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        large.next = null;
        small.next = large_head.next;

        return small_head.next;
    }
}
