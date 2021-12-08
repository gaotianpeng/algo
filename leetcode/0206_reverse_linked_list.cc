/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     ListNode *next;
 *     ListNode() : val(0), next(nullptr) {}
 *     ListNode(int x) : val(x), next(nullptr) {}
 *     ListNode(int x, ListNode *next) : val(x), next(next) {}
 * };
 */
/*
    206 反转链表
        给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
    
        示例
            输入：head = [1,2,3,4,5]
            输出：[5,4,3,2,1]
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
    ListNode* reverseList(ListNode* head) {
        ListNode* cur = head;
        ListNode* prev = nullptr;
        ListNode* tmp = nullptr;
        while (cur) {
            tmp = cur->next;
            cur->next = prev;
            prev = cur;
            cur = tmp;
        }

        return prev;
    }
};