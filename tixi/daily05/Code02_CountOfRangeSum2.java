package tixi.daily05;

public class Code02_CountOfRangeSum2 {
    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        int N = nums.length;
        long[] preSum= new long[N];
        preSum[0] = nums[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }

        for (int i = 0; i < N; ++i) {
            if (preSum[i] >= lower && preSum[i] <= upper) {
                ++ans;
            }
        }

        int mergeSize = 1;
        while (mergeSize < N) {
            int left = 0;
            while (left < N) {
                if (N - left < mergeSize) {
                    break;
                }

                int mid = left + mergeSize - 1;
                int right = mid + Math.min(mergeSize, N - mid - 1);
                ans += merge(preSum, left, mid, right, lower, upper);
                left = right + 1;
            }

            if (mergeSize > N / 2) {
                break;
            }

            mergeSize <<= 1;
        }
 

        return ans;
    }

    public static int merge(long[] preSum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int winLeft = left;
        int winRight = left;

        for (int i = mid + 1; i <= right; i++) {
            long max = preSum[i] - lower;
            long min = preSum[i] - upper;
            while (winRight <= mid && preSum[winRight] <= max) {
                ++winRight;
            }

            while (winLeft <= mid && preSum[winLeft] < min) {
                ++winLeft;
            }

            ans += winRight - winLeft;
        }

        int index = 0;
        long[] helper = new long[right - left + 1];
        int p1 = left;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            helper[index++] = preSum[p1] <= preSum[p2] ? preSum[p1++] : preSum[p2++];
        }

        while (p1 <= mid) {
            helper[index++] = preSum[p1++];
        }

        while (p2 <= right) {
            helper[index++] = preSum[p2++];
        }

        for (int i = 0; i < helper.length; ++i) {
            preSum[left + i] = helper[i];
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
