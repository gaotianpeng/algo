package tixi.daily25;

import java.util.Stack;

public class Code04_MaximalRectangle {
    /*
        给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩形，内部有多少个1

        leetcode 85 最大矩形
        https://leetcode.cn/problems/maximal-rectangle/submissions/
     */
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int max_area = 0;
        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            max_area = Math.max(maxxRecFromBottom(height), max_area);
        }

        return max_area;
    }

    public static int maxxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int max_area = 0;
        int n = height.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int less_left_index = stack.isEmpty() ? -1 : stack.peek();
                int cur_area = (i - less_left_index - 1) * height[j];
                max_area = Math.max(max_area, cur_area);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int less_left_index = stack.isEmpty() ? -1 : stack.peek();
            int cur_area = (n - less_left_index - 1) * height[j];
            max_area = Math.max(cur_area, max_area);
        }

        return max_area;
    }

    public static int maxxRecFromBottom1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int max_area = 0;
        int n = height.length;
        int[] stack = new int[n];
        int stack_size = 0;
        for (int i = 0; i < n; ++i) {
            while (stack_size != 0 && height[stack[stack_size - 1]] >= height[i]) {
                int j = stack[--stack_size];
                int less_left_index = stack_size == 0 ? -1 : stack[stack_size - 1];
                int cur_area = (i - less_left_index - 1) * height[j];
                max_area = Math.max(max_area, cur_area);
            }
            stack[stack_size++] = i;
        }

        while (stack_size != 0) {
            int j = stack[--stack_size];
            int less_left_index = stack_size == 0 ? -1 : stack[stack_size - 1];
            int cur_area = (n - less_left_index - 1) * height[j];
            max_area = Math.max(cur_area, max_area);
        }

        return max_area;
    }

}
