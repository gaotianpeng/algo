package shuati.daily05;

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

            int n = pre.length;
            int[] nearest_big = new int[n];
            for (int i = 0; i < n; ++i) {
                nearest_big[i] = -1;
            }

            Stack<Integer> stack = new Stack<>();
            for (int i = 0; i < n; ++i) {
                while (!stack.isEmpty() && pre[stack.peek()] < pre[i]) {
                    nearest_big[stack.pop()] = i;
                }
                stack.push(i);
            }

            return process2(pre, 0, n - 1, nearest_big);
        }

        public static TreeNode process2(int[] pre, int left, int right, int[] nearest_big) {
            if (left > right) {
                return null;
            }

            int first_big = (nearest_big[left] == -1 || nearest_big[left] > right) ? right + 1:nearest_big[left];
            TreeNode root = new TreeNode(pre[left]);
            root.left = process2(pre, left + 1, first_big -1, nearest_big);
            root.right = process2(pre, first_big, right, nearest_big);
            return root;
        }

        public static TreeNode bstFromPreorder3(int[] pre) {
            if (pre == null || pre.length == 0) {
                return null;
            }

            int n = pre.length;
            int[] nearest_big = new int[n];
            for (int i = 0; i < n; ++i) {
                nearest_big[i] = -1;
            }

            int[] stack = new int[n];
            int stack_size = -1;
            for (int i = 0; i < n; ++i) {
                while ( stack_size != -1 && pre[stack[stack_size]] < pre[i]) {
                    nearest_big[stack[stack_size]] = i;
                    stack_size--;
                }
                stack[++stack_size] = i;
            }

            return process3(pre, 0, n - 1, nearest_big);
        }

        public static TreeNode process3(int[] pre, int left, int right, int[] nearest_big) {
            if (left > right) {
                return null;
            }

            int first_big = (nearest_big[left] == -1 || nearest_big[left] > right) ? right + 1: nearest_big[left];
            TreeNode root = new TreeNode(pre[left]);
            root.left = process3(pre, left + 1, first_big - 1, nearest_big);
            root.right = process3(pre, first_big, right, nearest_big);
            return root;
        }
    }
}
