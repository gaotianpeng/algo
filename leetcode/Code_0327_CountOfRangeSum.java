package leetcode;

public class Code_0327_CountOfRangeSum {
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        long[] sum = new long[nums.length];
        sum[0] = (long)nums[0];
        for (int i = 1; i < nums.length; ++i) {
            sum[i] = sum[i-1] + nums[i];
        }

        return process(sum, 0, sum.length - 1, lower, upper);
    }

    static int process(long[] sum, int left, int right, int lower, int upper) {
        if (left == right) {
            return isValid(sum[left], lower, upper) == true ? 1 : 0;
        }

        int mid = left + ((right - left) >> 1);
        return process(sum, left, mid, lower, upper)
                + process(sum, mid + 1, right, lower, upper)
                + merge(sum, left, mid, right, lower, upper);
    }

    static boolean isValid(long num, int lower, int upper) {
        return num >= lower && num  <= upper;
    }


    public static int merge(long[] sum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;

        int win_left = left;
        int win_right = left;

        for (int i = mid + 1; i <= right; ++i) {
            long max = sum[i] - lower;
            long min = sum[i] - upper;
            while (win_right <= mid && sum[win_right] <= max) {
                ++win_right;
            }
            while (win_left <= mid && sum[win_left] < min) {
                ++win_left;
            }

            ans += win_right - win_left;
        }

        long[] helper = new long[right - left + 1];
        int i = 0;
        int pos1 = left;
        int pos2 = mid + 1;
        while (pos1 <= mid && pos2 <= right) {
            helper[i++] = sum[pos1] <= sum[pos2] ? sum[pos1++] : sum[pos2++];
        }

        while (pos1 <= mid) {
            helper[i++] = sum[pos1++];
        }

        while (pos2 <= right) {
            helper[i++] = sum[pos2++];
        }

        for (i = 0; i < helper.length; i++) {
            sum[left + i] = helper[i];
        }

        return ans;
    }
}
