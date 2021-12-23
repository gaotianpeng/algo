#include <algorithm>
using namespace std;

/*
    104 二叉树的最大深度
        给定一个二叉树，找出其最大深度
        二叉树的深度为根节点到最远叶子节点的最长路径上的节点数
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
    int maxDepth(TreeNode* root) {
        return getDepth(root);
    }

private:
    int getDepth(TreeNode* node) {
        if (node == nullptr) {
            return 0;
        }
        return max(getDepth(node->left),  getDepth(node->right)) + 1;
    }
};