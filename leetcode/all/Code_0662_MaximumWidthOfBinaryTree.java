package leetcode.all;

import tixi.daily11.Code05_TreeMaxWidth;

import java.util.LinkedList;
import java.util.Queue;

/*
    662 二叉树的最大宽度
    给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。
    这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。

    每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度
 */
public class Code_0662_MaximumWidthOfBinaryTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class AnnotatedNode {
        TreeNode node;
        int depth, pos;
        AnnotatedNode(TreeNode n, int d, int p) {
            node = n;
            depth = d;
            pos = p;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        Queue<AnnotatedNode> queue = new LinkedList();
        queue.add(new AnnotatedNode(root, 0, 0));
        int cur_depth = 0, left = 0, ans = 0;
        while (!queue.isEmpty()) {
            AnnotatedNode a = queue.poll();
            if (a.node != null) {
                queue.add(new AnnotatedNode(a.node.left, a.depth + 1, a.pos * 2));
                queue.add(new AnnotatedNode(a.node.right, a.depth + 1, a.pos * 2 + 1));
                if (cur_depth != a.depth) {
                    cur_depth = a.depth;
                    left = a.pos;
                }
                ans = Math.max(ans, a.pos - left + 1);
            }
        }

        return ans;
    }
}
