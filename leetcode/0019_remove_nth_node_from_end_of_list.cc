/*
    0019 删除链表的倒数第N个结点
        给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点

    示例
        输入：head = [1,2,3,4,5], n = 2 输出：[1,2,3,5]
        输入：head = [1], n = 1 输出：[]
        输入：head = [1,2], n = 1 输出：[1]

*/

struct ListNode {
    int val;
    ListNode *next;
    ListNode() : val(0), next(nullptr) {}
    ListNode(int x) : val(x), next(nullptr) {}
    ListNode(int x, ListNode *next) : val(x), next(next) {}
}; 


class Solution {
public:
    ListNode* removeNthFromEnd(ListNode* head, int n) {
        ListNode* dummy_head = new ListNode(0);
        dummy_head->next = head;
        ListNode* slow_node = dummy_head;
        ListNode* fast_node = dummy_head;
        // fast_node 指向第n个结点
        while (n-- && fast_node != nullptr) {
            fast_node = fast_node->next;
        }
    
        // fast_node需要再提前走一步, 因为需要让slow_node指向删除节点的上一个节点
        fast_node = fast_node->next;

        // 同时移动快慢指针
        while(fast_node != nullptr) {
            fast_node = fast_node->next;
            slow_node = slow_node->next;
        }

        // 删除第n个结点
        ListNode* tmp = slow_node->next;
        slow_node->next = slow_node->next->next;
        delete tmp;

        return dummy_head->next;
    }
};