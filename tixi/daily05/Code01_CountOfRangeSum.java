package tixi.daily05;
/*
    给定一个数组arr，两个整数lower和upper，
    返回arr中有多少个子数组的累加和在[lower,upper]范围上
 */
public class Code01_CountOfRangeSum {
    public static int countRangeSum(int[] nums, int lower,int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int N = nums.length;
        long[] preSum = new long[N];
        preSum[0] = nums[0];
        for (int i = 1; i < N; ++i) {
            preSum[i] = preSum[i-1] + nums[i];
        }

        return process(preSum, 0, preSum.length - 1, lower, upper);
    }

    public static int process(long[] sums, int left, int right, int lower, int upper) {
        if (left == right) {
            return checkValid(sums[left], lower, upper) ? 1: 0;
        }

        int mid = left + ((right - left) >> 1);
        return process(sums, left, mid, lower, upper)
                + process(sums, mid + 1, right, lower, upper)
                + merge(sums, left, mid, right, lower, upper);
    }

    public static int merge(long[] sums, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int winLeft = left;
        int winRight = left;
        for (int i = mid + 1; i <= right; ++i) {
            long min = sums[i] - upper;
            long max = sums[i] - lower;
            
            while (winRight <= mid && sums[winRight] <= max) {
                winRight++;
            }
            
            while (winLeft <= mid && sums[winLeft] < min) {
                winLeft++;
            }

            ans += winRight - winLeft;
        }

        long[] helper = new long[right - left + 1];
        int index = 0;
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            helper[index++] = sums[p1] <= sums[p2] ? sums[p1++] : sums[p2++];
        }

        while (p1 <= mid) {
            helper[index++] = sums[p1++];
        }

        while (p2 <= right) {
            helper[index++] = sums[p2++];
        }

        for (int i = 0; i < helper.length; ++i) {
            sums[left + i] = helper[i];
        }

        return ans;
    }

    public static boolean checkValid(long sum, int lower, int upper) {
        return sum >= lower && sum <= upper;
    }

    /*
        for test
     */
    public static int test(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        long[] pre_sum = new long[n + 1];
        pre_sum[0] = 0;
        pre_sum[1] = nums[0];
        for (int i = 1; i < n; i++) {
            pre_sum[i+1] = pre_sum[i] + nums[i];
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                long sum = pre_sum[j+1] - pre_sum[i];
                if (sum >= lower && sum <= upper) {
                    ans++;
                }
            }
        }
        return ans;
    }

    public static int[] generateRandomArray(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = generateRandomNum(max_val);
        }

        return ans;
    }

    public static int generateRandomNum(int max_val) {
        return (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*max_val);
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

    public static int[] generateRandomRange(int max_val) {
        int lower = (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*(max_val + 1));
        int upper = (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*(max_val + 1));

        return lower <= upper ? new int[] {lower, upper}: new int[] {upper, lower};
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = Integer.MAX_VALUE;
        int max_len = 200;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] arr1 = generateRandomArray(max_val, max_len);
            int[] arr2 = copyArray(arr1);
            int[] range = generateRandomRange(max_val);
            if (countRangeSum(arr1, range[0], range[1]) != test(arr2, range[0], range[1])) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
