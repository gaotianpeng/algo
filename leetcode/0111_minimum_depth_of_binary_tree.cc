#include <algorithm>
using namespace std;
/*
    111 二叉树的最小深度
        给定一个二叉树，找出其最小深度
        最小深度是从根节点到最近叶子节点的最短路径上的节点数量
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
    int minDepth(TreeNode* root) {
        return getMinDepth(root);
    }

private:
    int getMinDepth(TreeNode* node) {
        if (node == nullptr) {
            return 0;
        }

        int left_depth = getMinDepth(node->left);
        int right_depth = getMinDepth(node->right);
        
        if (node->left == nullptr && node->right != nullptr) {
            return 1 + right_depth;
        }

        if (node->left != nullptr && node->right == nullptr) {
            return 1 + left_depth;
        }

        return 1 + std::min(left_depth, right_depth);
    }
};