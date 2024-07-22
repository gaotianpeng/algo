package leetcode.all;

import tixi.daily11.Code05_TreeMaxWidth;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/*
    662 二叉树的最大宽度
    给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。
    这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。

    每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度
 */
public class Code_0662_MaximumWidthOfBinaryTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode() {}
        public TreeNode(int val) { this.val = val; }
        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static class AnnotatedNode {
        public TreeNode node;
        public int depth;
        public int pos;
        AnnotatedNode(TreeNode n, int d, int p) {
            node = n;
            depth = d;
            pos = p;
        }
    }

    public static int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<AnnotatedNode> queue = new LinkedList();
        queue.add(new AnnotatedNode(root, 0, 0));
        int curDepth = 0;
        int left = 0;
        int max = 0;
        while (!queue.isEmpty()) {
            AnnotatedNode a = queue.poll();
            if (a.node != null) {
                queue.add(new AnnotatedNode(a.node.left, a.depth + 1, a.pos * 2));
                queue.add(new AnnotatedNode(a.node.right, a.depth + 1, a.pos * 2 + 1));
                if (curDepth != a.depth) {
                    curDepth = a.depth;
                    left = a.pos;
                }
                max = Math.max(max, a.pos - left + 1);
            }
        }

        return max;
    }

    public static int widthOfBinaryTree2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int max = 0;
        Map<TreeNode, Integer> map = new HashMap<>();
        map.put(root,1);
        while(!queue.isEmpty()){
            int size = queue.size();
            int begin = -1, end = -1;
            for(int i = 0; i < size; i++){
                TreeNode out = queue.poll();
                int index = map.get(out);
                if (i == 0) {
                    begin = index;
                }
                if (i == size - 1){
                    end = index;
                }
                if (out.left != null) {
                    queue.offer(out.left);
                    map.put(out.left, index * 2);
                }
                if (out.right != null) {
                    queue.offer(out.right);
                    map.put(out.right, index * 2 + 1);
                }
            }
            max = Math.max(max, end - begin + 1);
        }
        return max;
    }

    public static TreeNode generateRandomBT(int maxLevel, int maxVal) {
        return generate(0, maxLevel, maxVal);
    }

    private static TreeNode generate(int curLevel, int maxLevel, int maxVal) {
        if (curLevel > maxLevel || Math.random() > 0.5) {
            return null;
        }

        TreeNode node = new TreeNode(randomValue(maxVal));
        node.left = generate(curLevel + 1, maxLevel, maxVal);
        node.right = generate( curLevel + 1, maxLevel, maxVal);
        return node;
    }

    private static int randomValue(int maxVal) {
        return (int)(Math.random() * (maxVal + 1));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLevel = 20;
        int maxVal = 30;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            TreeNode root = generateRandomBT(maxLevel, maxVal);
            int width1 = widthOfBinaryTree(root);
            int width2 = widthOfBinaryTree2(root);
            if (width1 != width2) {
                System.out.println(width2);
                System.out.println(width2);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
