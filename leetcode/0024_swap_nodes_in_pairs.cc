/*
    0024. 两两交换链表中的节点
        给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点
        你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）
    
    示例
        输入：head = [1,2,3,4]
        输出：[2,1,4,3]
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
    ListNode* swapPairs(ListNode* head) {
        ListNode* dummy_node = new ListNode(0);
        dummy_node->next = head;
        ListNode* cur_node = dummy_node;

        while (cur_node->next != nullptr && cur_node->next->next != nullptr) {
            ListNode* tmp = cur_node->next;
            ListNode* tmp1 = cur_node->next->next->next;
            
            cur_node->next = tmp->next;
            cur_node->next->next = tmp;
            cur_node->next->next->next = tmp1;

            cur_node = cur_node->next->next;
        }

        return dummy_node->next;
    }
};