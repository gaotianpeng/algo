package leetcode.hot100;

public class Code_0011_ContainerWithMostWater {
    /*
        11. 盛最多水的容器
        给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
        找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
        返回容器可以储存的最大水量。
        说明：你不能倾斜容器。


        示例 1：
        输入：[1,8,6,2,5,4,8,3,7]
        输出：49
        解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
        示例 2：
        输入：height = [1,1]
        输出：1

        提示：

        n == height.length
        2 <= n <= 105
        0 <= height[i] <= 104
     */
    public static int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int ans = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            ans = Math.max(ans, Math.min(height[left], height[right])*(right - left));
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }

        return ans;
    }
    // for test
    public static int test(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int N = height.length;
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = i + 1; j < N; j++) {
                ans = Math.max(ans, Math.min(height[i], height[j]) * (j - i));
            }
        }

        return ans;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] nums = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int)((maxValue + 1) * Math.random());
        }

        return nums;
    }

    public static int[] copyArray(int[] nums) {
        if (nums == null) {
            return null;
        }

        int[] ret = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            ret[i] = nums[i];
        }

        return ret;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int maxVal = 40;
        int maxLen = 50;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] height = generateRandomArray(maxLen, maxVal);
            int ans1 = maxArea(height);
            int ans2 = test(height);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
