package sort;

/*
    给定一个数组arr，两个整数lower和upper，
    返回arr中有多少个子数组的累加和在[lower,upper]范围上
 */

public class Code19_CountOfRangeSum {
    public static int countRangeSum(int[] nums, int lower,int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return checkValid(sum[left], lower, upper) ? 1 : 0;
        }

        int mid = left + ((right - left)>>1);
        return process(sum, left, mid, lower, upper)
                + process(sum, mid + 1, right, lower, upper)
                + merge(sum, left, mid, right, lower, upper);
    }

    public static int merge(long[] arr, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int win_left = left;
        int win_right = left;
        for (int i = mid + 1; i <= right; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (win_right <= mid && arr[win_right] <= max) {
                win_right++;
            }

            while (win_left <= mid && arr[win_left] < min) {
                win_left++;
            }
            ans += win_right - win_left;
        }

        long[] helper = new long[right - left + 1];
        int i = 0;
        int pos1 = left;
        int pos2 = mid + 1;
        while (pos1 <= mid && pos2 <= right) {
            helper[i++] = arr[pos1] <= arr[pos2] ? arr[pos1++] : arr[pos2++];
        }

        while (pos1 <= mid) {
            helper[i++] = arr[pos1++];
        }

        while (pos2 <= right) {
            helper[i++] = arr[pos2++];
        }

        for (i = 0; i < helper.length; i++) {
            arr[left + i] = helper[i];
        }

        return ans;
    }

    public static boolean checkValid(long num, int lower, int upper) {
        return num >= lower && num <= upper;
    }

    /*
        for test
     */

    public static int test(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        long[] pre_sum_arr = new long[nums.length + 1];
        pre_sum_arr[0] = 0;
        pre_sum_arr[1] = nums[0];
        // 计算前缀和
        for (int i = 1; i < nums.length; i++) {
            pre_sum_arr[i+1] = pre_sum_arr[i] + nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                long sum = pre_sum_arr[j+1] - pre_sum_arr[i];
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
        int test_times = 1000000;
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
