package leetcode.all;

import java.util.HashSet;
import java.util.List;

/*
111. Minimum Depth of Binary Tree
    Given a binary tree, find its minimum depth.
    The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
    Note: A leaf is a node with no children.

Example 1:
    Input: root = [3,9,20,null,null,15,7]
    Output: 2
Example 2:
    Input: root = [2,null,3,null,4,null,5,null,6]
    Output: 5

Constraints:
    The number of nodes in the tree is in the range [0, 105].
    -1000 <= Node.val <= 1000
 */
public class Code_0111_MinimumDepthOfBinaryTree {
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

    public static int minDepth(TreeNode head) {
        if (head == null) {
            return 0;
        }

        TreeNode cur = head;
        TreeNode mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    rightBoardSize++;
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) { // 第一次到达
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else { // 第二次到达
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            } else { // 只有一次到达
                curLevel++;
            }
            cur = cur.right;
        }
        int finalRight = 1;
        cur = head;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }

    /*
        for test
     */
    public static int test(TreeNode root) {
        return process(root);
    }

    public static int process(TreeNode head) {
        if (head == null) {
            return 0;
        }

        if (head.left == null && head.right == null) {
            return 1;
        }

        int ans = Integer.MAX_VALUE;

        if (head.left != null) {
            ans = Math.min(ans, process(head.left));
        }

        if (head.right != null) {
            ans = Math.min(ans, process(head.right));
        }

        return 1 + ans;
    }

    public static TreeNode generateRandomBT(int maxLevel, int maxValue) {
        HashSet<Integer> nums = new HashSet<>();
        return generate(1, maxLevel, maxValue, nums);
    }

    public static TreeNode generate(int level, int maxLevel, int maxValue, HashSet<Integer> nums) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        int val = (int)(Math.random()*maxValue);
        while (nums.contains(val)) {
            val = (int)(Math.random()*maxValue);
        }

        TreeNode head = new TreeNode(val);
        head.left = generate(level + 1, maxLevel, maxValue, nums);
        head.right = generate(level + 1, maxLevel, maxValue, nums);
        return head;
    }

    public static boolean isEqual(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); ++i) {
            if (!list1.get(i).equals(list2.get(i))) {
                return false;
            }
        }

        return true;
    }

    public static void printArray(List<Integer> list) {
        if (list == null) {
            return;
        }

        for (Integer elem: list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxLevel = 5;
        int maxValue = 200;
        for (int i = 0; i < testTimes; ++i) {
            TreeNode tree = generateRandomBT(maxLevel, maxValue);
            if (minDepth(tree) != test(tree)) {
                success = false;
                System.out.println("failed");
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }

}
