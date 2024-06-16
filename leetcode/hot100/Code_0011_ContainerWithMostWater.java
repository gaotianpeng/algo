package leetcode.hot100;

public class Code_0011_ContainerWithMostWater {

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
