#include <algorithm>
using namespace std;
/*
    110 平衡二叉树
        给定一个二叉树，判断它是否是高度平衡的二叉树

        平衡二叉树定义: 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 
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
    bool isBalanced(TreeNode* root) {
        return getHeight(root) == -1 ? false : true;
    }

private:
    int getHeight(TreeNode* node) {
        if (node == nullptr) {
            return 0;
        }

        int left_height = getHeight(node->left);
        if (left_height == -1) {
            return -1;
        }
        int right_height = getHeight(node->right);
        if (right_height == -1) {
            return -1;
        }

        return abs(left_height - right_height) > 1 ? -1: 1 + max(left_height, right_height);
    }
};