package leetcode.hot100;

import java.util.Random;

public class Code_0043_TrappingRainWater {
    /*
        43 接雨水
        给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

        示例1
        输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
        输出：6
        解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。

        示例 2：
        输入：height = [4,2,0,3,2,5]
        输出：9

        提示：
        n == height.length
        1 <= n <= 2 * 104
        0 <= height[i] <= 105
     */
    public static  int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int N = height.length;
        int ans = 0;
        int left = 1;
        int right = N - 2;
        int leftMax = height[0];
        int rightMax = height[N-1];
        while (left <= right) {
            if (leftMax <= rightMax) {
                ans += Math.max(0, leftMax - height[left]);
                leftMax = Math.max(leftMax, height[left++]);
            } else {
                ans += Math.max(0, rightMax - height[right]);
                rightMax = Math.max(rightMax, height[right--]);
            }
        }

        return ans;
    }

    public static int test(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int ans = 0;
        int N = height.length;
        for (int i = 1; i < N - 1; ++i) {
            int leftMax= height[0];
            int rightMax = height[N-1];
            for (int j = 0; j < i; j++) {
                leftMax = Math.max(leftMax, height[j]);
            }
            for(int j = i + 1; j < N - 1; j++) {
                rightMax = Math.max(rightMax, height[j]);
            }

            int cur = Math.max(0, Math.min(leftMax, rightMax) - height[i]);
            ans += cur;
        }

        return ans;
    }

    // for test
    public static int[] generateRandomArray(int minLen, int maxLen, int minVal, int maxVal) {
        Random random = new Random();

        // Generate a random length for the array between minLen and maxLen
        int length = random.nextInt((maxLen - minLen) + 1) + minLen;

        int[] randomArray = new int[length];

        // Populate the array with random values between minVal and maxVal
        for (int i = 0; i < length; i++) {
            randomArray[i] = random.nextInt((maxVal - minVal) + 1) + minVal;
        }

        return randomArray;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int minVal = 0;
        int maxVal = 105;
        int minLen = 1;
        int maxLen = 2 * 104;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] height = generateRandomArray(minLen, maxLen, minVal, maxVal);
            if (trap(height) != test(height)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
