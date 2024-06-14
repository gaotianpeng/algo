package tixi.daily25;

import java.util.Stack;

public class Code03_LargestRectangleInHistogram {
    /*
        给定一个非负数组arr，代表直方图返回直方图的最大长方形面积

        leetcode 84 柱状图中最大的矩形
        https://leetcode.cn/problems/largest-rectangle-in-histogram/
    */
    public static int largestRectangleArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        Stack<Integer> stack = new Stack<>();
        int n = height.length;
        int maxArea = 0;
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - leftLessIndex- 1) * height[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int leftLessIndex = stack.isEmpty() ? -1 : stack.peek();
            int cur_area = (height.length - leftLessIndex - 1) * height[j];
            maxArea = Math.max(maxArea, cur_area);
        }

        return maxArea;
    }

    public static int largestRectangleArea1(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int maxArea = 0;
        int n = heights.length;
        int[] stack = new int[n];
        int stackSize = 0;
        for (int i = 0; i < n; ++i) {
            while ( stackSize != 0 && heights[stack[stackSize - 1]] >= heights[i]) {
                int j = stack[--stackSize];
                int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
                int curArea = (i - leftLessIndex - 1) * heights[j];
                maxArea = Math.max(curArea, maxArea);
            }
            stack[stackSize++] = i;
        }

        while (stackSize != 0) {
            int j = stack[--stackSize];
            int leftLessIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
            int curArea = (n - leftLessIndex - 1) * heights[j];
            maxArea = Math.max(maxArea, curArea);
        }

        return maxArea;
    }

    public static int test(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        int n = heights.length;
        int maxArea = 0;
        for (int i = 0; i < n; ++i) {
            int left = i;
            int right = i;
            while (left >= 0 && heights[left] >= heights[i]) {
                --left;
            }
            while (right < n && heights[right] >= heights[i]) {
                ++right;
            }
            maxArea = Math.max(maxArea, heights[i] * (right - left - 1));
        }

        return maxArea;
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
        int maxVal = 20;
        int maxLen = 30;
        int testTimes = 500000;
        for (int i = 0; i < testTimes; ++i) {
            int[] heights = getRandomArray(maxLen, maxVal);
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
