package shuati.daily05;

import com.sun.source.tree.Tree;

import java.util.Stack;

public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public static TreeNode bstFromPreorder1(int[] pre) {
            if (pre == null || pre.length == 0) {
                return null;
            }

            return process1(pre, 0, pre.length - 1);
        }

        public static TreeNode process1(int[] pre, int left, int right) {
            if (left > right) {
                return null;
            }

            int first_big = left + 1;
            for (; first_big <= right; ++first_big) {
                if (pre[first_big] > first_big) {
                    break;
                }
            }

            TreeNode root = new TreeNode(pre[left]);
            root.left = process1(pre, left + 1, first_big - 1);
            root.right = process1(pre, first_big, right);
            return root;
        }

        public static TreeNode bstFromPreorder2(int[] pre) {
            if (pre == null || pre.length == 0) {
                return null;
            }

            int[] near_big = new int[pre.length];
            for (int i = 0; i < near_big.length; ++i) {
                near_big[i] = -1;
            }

            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < pre.length; ++i) {
                while (!stack.isEmpty() && pre[stack.peek()] < pre[i]) {
                    near_big[stack.pop()] = i;
                }
                stack.push(i);
            }

            return process2(pre, 0, pre.length - 1, near_big);
        }

        public static TreeNode process2(int[] pre, int left, int right, int[] near_big) {
            if (left > right) {
                return null;
            }

            int firs_big = near_big[left] == -1 || near_big[left] > right ? right + 1 : near_big[left];
            TreeNode root = new TreeNode(pre[left]);
            root.left = process2(pre, left + 1, firs_big - 1, near_big);
            root.right = process2(pre, firs_big, right, near_big);
            return root;
        }
    }
}
