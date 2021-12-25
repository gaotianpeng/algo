#include <iostream>
using namespace std;
/*
    404 左叶子之和
        计算给定二叉树的所有左叶子之和
*/

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

class Solution {
public:
    int sumOfLeftLeaves(TreeNode* root) {
        if (root == nullptr) {
            return 0;
        }

        int left_val = sumOfLeftLeaves(root->left);
        int right_val = sumOfLeftLeaves(root->right);

        int val = 0;
        if (root->left && !root->left->left && !root->left->right) {
            val = root->left->val;
        }

        return val + left_val + right_val;
    }
};