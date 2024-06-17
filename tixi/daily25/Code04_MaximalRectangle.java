package tixi.daily25;

import java.util.Random;
import java.util.Stack;

public class Code04_MaximalRectangle {
    /*
        给定一个二维数组matrix，其中的值不是0就是1，返回全部由1组成的最大子矩形，内部有多少个1

        leetcode 85 最大矩形
        https://leetcode.cn/problems/maximal-rectangle/submissions/
     */
    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int maxArea = 0;
        int m = matrix.length;
        int n = matrix[0].length;

        int[] height = new int[n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                height[j] = matrix[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxRecFromBottom(height), maxArea);
        }

        return maxArea;
    }

    public static int maxRecFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int maxArea = 0;
        int n = height.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int j = stack.pop();
                int lessLeftIndex = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - lessLeftIndex - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int lessLeftIndex = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (n - lessLeftIndex - 1) * height[j];
            maxArea = Math.max(curArea, maxArea);
        }

        return maxArea;
    }

    public static int maxRecFromBottom1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int maxArea = 0;
        int n = height.length;
        int[] stack = new int[n];
        int stackSize = 0;
        for (int i = 0; i < n; ++i) {
            while (stackSize != 0 && height[stack[stackSize - 1]] >= height[i]) {
                int j = stack[--stackSize];
                int lessLeftIndex = stackSize == 0 ? -1 : stack[stackSize - 1];
                int curArea = (i - lessLeftIndex - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack[stackSize++] = i;
        }

        while (stackSize != 0) {
            int j = stack[--stackSize];
            int less_left_index = stackSize == 0 ? -1 : stack[stackSize - 1];
            int cur_area = (n - less_left_index - 1) * height[j];
            maxArea = Math.max(cur_area, maxArea);
        }

        return maxArea;
    }

    // for test
    public static int test(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int maxArea = 0;
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    int area = getMaxArea(matrix, i, j);
                    maxArea = Math.max(maxArea, area);
                }
            }
        }

        return maxArea;
    }

    private static int getMaxArea(char[][] matrix, int startRow, int startCol) {
        int maxArea = 0;
        int minWidth = Integer.MAX_VALUE;

        for (int i = startRow; i < matrix.length && matrix[i][startCol] == '1'; i++) {
            int width = 0;
            while (startCol + width < matrix[0].length && matrix[i][startCol + width] == '1') {
                width++;
            }
            minWidth = Math.min(minWidth, width);
            int height = i - startRow + 1;
            int area = minWidth * height;
            maxArea = Math.max(maxArea, area);
        }

        return maxArea;
    }

    public static char[][] randomMatrix(int maxM, int maxN) {
        Random rand = new Random();
        int rows = rand.nextInt(maxM) + 1; // 随机生成 1 到 maxM 之间的行数
        int cols = rand.nextInt(maxN) + 1; // 随机生成 1 到 maxN 之间的列数

        char[][] matrix = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextBoolean() ? '1' : '0'; // 随机填充 '0' 或 '1'
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxM = 50;
        int maxN = 50;
        for (int i = 0; i < testTimes; ++i) {
            char[][] matrix = randomMatrix(maxM, maxN);
            int ans1 = maximalRectangle(matrix);
            int ans2 = test(matrix);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
