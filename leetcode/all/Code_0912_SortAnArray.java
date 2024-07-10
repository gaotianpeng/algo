package leetcode.all;

public class Code_0912_SortAnArray {
    public static int[] sortArray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        int n = nums.length;
        int merge_size = 1;
        while (merge_size < n) {
            int left = 0;
            while (left < n) {
                if (merge_size > n - left) {
                    break;
                }

                int mid = left + merge_size - 1;
                int right = mid + Math.min(n - mid - 1, merge_size);
                merge(nums, left, mid, right);
                left = right + 1;
            }

            if (merge_size > n/2) {
                break;
            }
            merge_size <<= 1;
        }

        return nums;
    }

    public static void merge(int[] nums, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int index = 0;
        int left_index = left;
        int right_index = mid + 1;
        while (left_index <= mid && right_index <= right) {
            helper[index++] = nums[left_index] < nums[right_index] ? nums[left_index++] : nums[right_index++];
        }

        while (left_index <= mid) {
            helper[index++] = nums[left_index++];
        }

        while (right_index <= right) {
            helper[index++] = nums[right_index++];
        }

        for (int i = 0; i < helper.length; ++i) {
            nums[left  + i] = helper[i];
        }
    }
}
