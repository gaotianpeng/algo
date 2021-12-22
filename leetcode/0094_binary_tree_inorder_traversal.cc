#include <vector>
#include <stack>
using namespace std;
/*
    94 二叉树的中序遍历
*/

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode() : val(0), left(nullptr), right(nullptr) {}
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
    TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
};

// 非递归版本
class Solution {
public:
    vector<int> inorderTraversal(TreeNode* root) {
        if (!root) {
            return {};
        }

        vector<int> ret;
        stack<TreeNode*> st;
        TreeNode* cur = root;
        while (cur != nullptr || !st.empty()) {
            if (cur != nullptr) {
                st.push(cur);
                cur = cur->left;
            } else {
                cur = st.top();
                ret.push_back(cur->val);
                st.pop();
                cur = cur->right;
            }
        }

        return ret;
    }
};

// 递归版本
class Solution1 {
public:
	vector<int> inorderTraversal(TreeNode* root) {
        if (!root) {
            return {};
        }

        vector<int> ret;
        inorderTraversal(root, ret);
        return ret;
	}


    void inorderTraversal(TreeNode* node, vector<int>& ret) {
        if (!node) {
            return;
        }

        inorderTraversal(node->left, ret);
        ret.push_back(node->val);
        inorderTraversal(node->right, ret);
    }
};
