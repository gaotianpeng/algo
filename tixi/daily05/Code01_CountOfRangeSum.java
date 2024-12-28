package tixi.daily05;
/*
    给定一个数组arr，两个整数lower和upper，
    返回arr中有多少个子数组的累加和在[lower, upper]范围上
 */
public class Code01_CountOfRangeSum {
    /*
     * 题目转化为
     *      假设 0-i 整体累加和是 X, 所求范围是 [lower, upper]
     *      求必须以 i 位置结尾的子数组，有多个少在[lower, upper]上
     *      
     *  等同于
     *      求 i 之前所有前缀和中有多个个前缀和在[X-upper, X-lower]上
     * 
     *  等同于求
     *      1）arr[] -> sum[]
     *      2）求 sum[i] 之前有多少个数落在 [sum[i] - upper，sum[i] - lower] 之间
     */

     /*
            sum[]  [lower, up] = [1, 5]
            [1, 3, 4, 4, 5]  [2, 7, 8, 8, 9]
            左               右
            当右来到sum[right]时，求 sum[left] 中有多少个数满足 sum[right] - 5 <= sum[left] <= sum[right] - 1
      */
    public static int countRangeSum(int[] nums, int lower,int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        long[] preSum = new long[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            preSum[i] = preSum[i-1] + nums[i];
        }

        return process(preSum, 0, preSum.length - 1, lower, upper);
    }

    private static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return checkValid(sum[L], lower, upper) ? 1: 0;
        }

        int M = L+ ((R - L) >> 1);
        return process(sum, L, M, lower, upper) 
                + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    private static int merge(long[] sum, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int winL = L;
        int winR = L;

        for (int i = M+ 1; i <= R; ++i) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (winR <= M && sum[winR] <= max) {
                winR++;
            }
            while (winL <= M && sum[winL] < min) {
                winL++;
            }

            ans += winR - winL;
        }

        long[] helper = new long[R - L + 1];
        int index = 0;
        int leftIdx = L;
        int rightIdx = M + 1;
        while (leftIdx <= M && rightIdx <= R) {
            helper[index++] = sum[leftIdx] <= sum[rightIdx] ? sum[leftIdx++] : sum[rightIdx++];
        }

        while (leftIdx <= M) {
            helper[index++] = sum[leftIdx++];
        }

        while (rightIdx <= R) {
            helper[index++] = sum[rightIdx++];
        }

        for (int i = 0; i < helper.length; ++i) {
            sum[L+i] = helper[i];
        }

        return ans;
    }

    private static boolean checkValid(long val, int lower, int upper) {
        return val <= upper && val >= lower;
    }

    /*
        for test
     */
    public static int test(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        long[] preSum = new long[n + 1];
        preSum[0] = 0;
        preSum[1] = nums[0];
        for (int i = 1; i < n; i++) {
            preSum[i+1] = preSum[i] + nums[i];
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                long sum = preSum[j+1] - preSum[i];
                if (sum >= lower && sum <= upper) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int[] generateRandomArray(int maxVal, int maxLen) {
        int len = (int)(Math.random()*(maxLen + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = generateRandomNum(maxVal);
        }

        return ans;
    }

    public static int generateRandomNum(int maxVal) {
        return (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*maxVal);
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }

        return ans;
    }

    public static int[] generateRandomRange(int maxVal) {
        int lower = (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*(maxVal + 1));
        int upper = (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*(maxVal + 1));

        return lower <= upper ? new int[] {lower, upper}: new int[] {upper, lower};
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int maxVal = Integer.MAX_VALUE;
        int maxLen = 200;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr1 = generateRandomArray(maxVal, maxLen);
            int[] arr2 = copyArray(arr1);
            int[] range = generateRandomRange(maxVal);
            if (countRangeSum(arr1, range[0], range[1]) != test(arr2, range[0], range[1])) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
