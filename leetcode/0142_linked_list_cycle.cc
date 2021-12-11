/*
    142 环形链表
        给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null
*/

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(nullptr) {}
};

class Solution {
public:
    ListNode* detectCycle(ListNode* head) {
        ListNode* fast_node = head;
        ListNode* slow_node = head;
        while (fast_node != nullptr && fast_node->next != nullptr) {
            slow_node = slow_node->next;
            fast_node = fast_node->next->next;
            if (fast_node == slow_node) {
                ListNode* node1 = fast_node;
                ListNode* node2 = head;
                while (node1 != node2) {
                    node1 = node1->next;
                    node2 = node2->next;
                }

                return node1;
            }
    
        }
        return nullptr;
    }
};