/*
    101 对称二叉树
        给定一个二叉树，检查它是否是镜像对称的
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
    bool isSymmetric(TreeNode* root) {
        if (root == nullptr) {
            return true;
        }
        return compare(root->left, root->right);
    }

private:
    bool compare(TreeNode* left, TreeNode* right) {
        if ((left != nullptr && right == nullptr) || 
                (left == nullptr && right != nullptr)) {
            return false;
        }

        if (left == nullptr && right == nullptr) {
            return true;
        }

        if (left->val != right->val) {
            return false;
        }

        return compare(left->left, right->right) && compare(left->right, right->left);
    }
};


