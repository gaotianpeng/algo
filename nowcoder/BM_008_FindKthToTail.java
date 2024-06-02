package nowcoder;

// 链表中倒数最后k个结点
public class BM_008_FindKthToTail {
    public static class ListNode {
        int val;
        ListNode next = null;
        public ListNode(int val) {
             this.val = val;
        }
    }

    /*
        输入：
        {1,2,3,4,5},2
        返回值：{4,5}

        输入：
        {2},8
        返回值：{}
     */
    public ListNode FindKthToTail (ListNode pHead, int k) {
        // write code here
        int n = 0;
        ListNode cur = pHead;
        while (cur != null) {
            ++n;
            cur = cur.next;
        }

        if (n < k) {
            return null;
        }
        if (n == k) {
            return pHead;
        }

        int cnt = n - k;
        cur = pHead;
        while (cur != null) {
            --cnt;
            if (cnt == 0) {
                return cur.next;
            }
            cur = cur.next;
        }

        return null;
    }
}
