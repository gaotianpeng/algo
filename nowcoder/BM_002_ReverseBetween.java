import java.util.*;

/*
 * public class ListNode {
 *   int val;
 *   ListNode next = null;
 *   public ListNode(int val) {
 *     this.val = val;
 *   }
 * }
 */

public class BM_002_ReverseBetween {
    public static class ListNode {
        int val;
        ListNode next = null;
        public ListNode(int val) {
            this.val = val;
        }
    }
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 
     * @param head ListNode类 
     * @param m int整型 
     * @param n int整型 
     * @return ListNode类
     */
    private static ListNode reverse(ListNode head) {
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
    
    public ListNode reverseBetween (ListNode head, int m, int n) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummyNode = new ListNode(-1);
        dummyNode.next = head;
        ListNode prev = dummyNode;
        // 来到 m - 1 节点
        for (int i = 0; i < m - 1; ++i) {
            prev = prev.next;
        }

        ListNode rightNode = prev;
        // 来到 n节点
        for (int i = 0; i < n - m + 1; ++i) {
            rightNode = rightNode.next;
        }

        // 取出 [m,n]之间的子链表
        ListNode leftNode = prev.next;
        ListNode next = rightNode.next;
        prev.next = null;
        rightNode.next = null;
        // 反转局部链表
        reverse(leftNode);

        // 连接链表
        prev.next = rightNode;
        leftNode.next = next;

        return dummyNode.next;
    }
}