#include <string>
#include <vector>
using namespace std;

/*
    257 二叉树的所有路径
        给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径

    示例
        root = [1,2,3,null,5]
        ["1->2->5","1->3"]
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
    vector<string> binaryTreePaths(TreeNode* root) {
        if (root == nullptr) return {};
        vector<string> ret;
        string path;
        traversal(root, path, ret);
        return ret;
    }

private:
    void traversal(TreeNode* cur, string path, vector<string>& ret) {
        path += to_string(cur->val);
        if (cur->left == nullptr && cur->right == nullptr) {
            ret.push_back(path);
            return;
        }

        if (cur->left) {
            traversal(cur->left, path + "->", ret);
        }
        if (cur->right) {
            traversal(cur->right, path + "->", ret);
        }
    }
};