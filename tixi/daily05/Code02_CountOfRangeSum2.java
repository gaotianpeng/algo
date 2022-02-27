package tixi.daily05;

public class Code02_CountOfRangeSum2 {
    public static int countRangeSum(int[] nums, int lower,int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        int ans = 0;
        int merge_size = 1;
        int n = sum.length;
        for (int i = 0; i < n; i++) {
            ans += (sum[i] >= lower && sum[i] <= upper ? 1 : 0);
        }
        while (merge_size < n) {
            int left = 0;
            while (left < n) {
                if (merge_size > n - left) {
                    break;
                }
                int mid = left + merge_size - 1;
                int right = mid + Math.min(merge_size, n - mid - 1);
                ans += merge(sum, left, mid, right, lower, upper);
                left = right + 1;
            }

            if (merge_size > n / 2) {
                break;
            }
            merge_size <<= 1;
        }
        return ans;
    }

    private static int merge(long[] sum, int left, int mid, int right, int lower,int upper) {
        int ans = 0;
        int win_left = left;
        int win_right = left;
        for (int i = mid + 1; i <= right; i++) {
            long min = sum[i] - upper;
            long max = sum[i] - lower;
            while (win_right <= mid && sum[win_right] <= max) {
                win_right++;
            }
            while (win_left <= mid && sum[win_left] < min) {
                win_left++;
            }
            ans += win_right - win_left;
        }

        long[] helper = new long[right - left + 1];
        int left_index = left;
        int right_index = mid + 1;
        int index = 0;
        while (left_index <= mid && right_index <= right) {
            helper[index++] = sum[left_index] <= sum[right_index] ?
                    sum[left_index++] : sum[right_index++];
        }

        while (left_index <= mid) {
            helper[index++] = sum[left_index++];
        }

        while (right_index <= right) {
            helper[index++] = sum[right_index++];
        }

        for (int i = 0; i < helper.length; i++) {
            sum[left + i] = helper[i];
        }
        return ans;
    }

    /*
        for test
     */
    public static int test(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        long[] pre_sum = new long[nums.length + 1];
        pre_sum[0] = 0;
        pre_sum[1] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            pre_sum[i+1] += pre_sum[i] + nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
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
        int max_len = 10;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] arr1 = generateRandomArray(max_val, max_len);
            int[] arr2 = copyArray(arr1);
            int[] range = generateRandomRange(max_val);
            if (countRangeSum(arr1, range[0], range[1]) != test(arr2, range[0], range[1])) {
                success = false;
                System.out.println(countRangeSum(arr1, range[0], range[1]));
                System.out.println(test(arr2, range[0], range[1]));
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
