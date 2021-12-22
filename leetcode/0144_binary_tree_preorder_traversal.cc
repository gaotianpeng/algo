#include <vector>
#include <stack>
using namespace std;
/*
    144 二叉树的前序遍历
        给你二叉树的根节点 root ，返回它节点值的 前序 遍历
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
    vector<int> preorderTraversal(TreeNode* root) {
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
            if (node->right) {
                st.push(node->right);
            }
            if (node->left) {
                st.push(node->left);
            }
        }

        return ret;
    }
};