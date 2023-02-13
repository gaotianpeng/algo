package tixi.daily25;

import java.util.Stack;

public class LargestRectangleInHistogram {
    public static int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int n = height.length;
        int max_area = 0;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int left_less_index = stack.isEmpty() ? -1 : stack.peek();
                int cur_area = (i - left_less_index- 1) * height[j];
                max_area = Math.max(cur_area, max_area);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int left_less_index = stack.isEmpty() ? -1 : stack.peek();
            int cur_area = (height.length - left_less_index - 1) * height[j];
            max_area = Math.max(max_area, cur_area);
        }

        return max_area;
    }

    public static int largestRectangleArea1(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int max_area = 0;
        int n = heights.length;
        int[] stack = new int[n];
        int stack_size = 0;
        for (int i = 0; i < n; ++i) {
            while ( stack_size != 0 && heights[stack[stack_size - 1]] >= heights[i]) {
                int j = stack[--stack_size];
                int left_less_index = stack_size == 0 ? -1 : stack[stack_size - 1];
                int cur_area = (i - left_less_index - 1) * heights[j];
                max_area = Math.max(cur_area, max_area);
            }
            stack[stack_size++] = i;
        }

        while (stack_size != 0) {
            int j = stack[--stack_size];
            int left_less_index = stack_size == 0 ? -1 : stack[stack_size - 1];
            int cur_area = (n - left_less_index - 1) * heights[j];
            max_area = Math.max(max_area, cur_area);
        }

        return max_area;
    }

    public static int test(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int n = heights.length;
        int max_area = 0;
        for (int i = 0; i < n; ++i) {
            int left = i;
            int right = i;
            while (left >= 0 && heights[left] >= heights[i]) {
                --left;
            }
            while (right < n && heights[right] >= heights[i]) {
                ++right;
            }
            max_area = Math.max(max_area, heights[i] * (right - left - 1));
        }

        return max_area;
    }

    public static int[] getRandomArray(int max_size, int max_val) {
        int n = (int)(Math.random()*max_size) + 1;
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = (int)(Math.random()*max_val);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 20;
        int max_n = 30;
        int test_times = 500000;
        for (int i = 0; i < test_times; ++i) {
            int[] heights = getRandomArray(max_n, max_val);
            if (test(heights) != largestRectangleArea(heights)) {
                System.out.println("test1 failed");
                break;
            }
            if (test(heights) != largestRectangleArea1(heights)) {
                System.out.println("test2 failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
