#include <vector>
#include <stack>
using namespace std;
/*
    145 二叉树的后序遍历
        给定一个二叉树，返回它的 后序 遍历

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
    vector<int> postorderTraversal(TreeNode* root) {
        stack<TreeNode*> st;
        if (!root) { 
            return {}; 
        }
        vector<int> ret;
        st.push(root);
        while (!st.empty()) {
            TreeNode* node = st.top();
            st.pop();
            ret.push_back(node->val);
            if (node->left) {
                st.push(node->left);
            }
            if (node->right) {
                st.push(node->right);
            }
        }
        reverse(ret.begin(), ret.end());
        return ret;
    }
};