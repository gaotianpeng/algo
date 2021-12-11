/*
    160 相交链表
        给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 

*/
#include <iostream>

struct ListNode {
    int val;
    ListNode *next;
    ListNode(int x) : val(x), next(nullptr) {}
};
 

class Solution {
public:
    ListNode *getIntersectionNode(ListNode *headA, ListNode *headB) {
        ListNode* curA = headA;
        ListNode* curB = headB;

        int lenA = 0, lenB = 0;
        while (curA != nullptr) {
            ++lenA;
            curA = curA->next;
        }

        while (curB != nullptr) {
            ++lenB;
            curB = curB->next;            
        }

        curA = headA, curB = headB;
        if (lenB > lenA) {
            std::swap(lenA, lenB);
            std::swap(curA, curB);
        }

        int gap = lenA - lenB;
        while(gap--) {
            curA = curA->next;
        }

        while (curA != nullptr) {
            if (curA == curB) {
                return curA;
            }

            curA = curA->next;
            curB = curB->next;
        }

        return nullptr;
    }
};