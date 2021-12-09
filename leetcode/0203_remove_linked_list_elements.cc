/*
    203. 移除链表元素
        给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点
        示例
            输入：head = [1,2,6,3,4,5,6], val = 6
            输出：[1,2,3,4,5]
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
    ListNode* removeElements1(ListNode* head, int val) {
        while (head != nullptr && head->val == val) {
            ListNode* tmp = head;
            head = head->next;
            delete tmp;
        }

        ListNode* cur = head;
        while (cur != nullptr && cur->next != nullptr) {
            if (cur->next->val == val) {
                ListNode* tmp = cur->next;
                cur->next = tmp->next;
                delete tmp;
            } else {
                cur = cur->next;
            }
        } 

        return head;
    }

    ListNode* removeElements2(ListNode* head, int val) {
        ListNode* dummy_node = new ListNode(0);
        dummy_node->next = head;
        ListNode* cur_node = dummy_node;
        while (cur_node->next != nullptr) {
            if (cur_node->next->val == val) {
                ListNode* tmp = cur_node->next;
                cur_node->next = cur_node->next->next;
                delete tmp;
            } else {
                cur_node = cur_node->next;
            }
        }
        head = dummy_node->next;
        delete dummy_node;
        return head;
    }
};