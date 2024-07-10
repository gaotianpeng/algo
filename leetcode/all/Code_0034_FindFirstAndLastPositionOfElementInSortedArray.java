package leetcode.all;

public class Code_0034_FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int[] ans = new int[] {-1, -1};
        ans[0] = findFirst(nums, target);
        ans[1] = findLast(nums, target);
        return ans;
    }

    public static int findFirst(int[] nums, int num) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + ((right - left)>>1);
            if (nums[mid] < num) {
                left = mid + 1;
            } else if (nums[mid] > num) {
                right = mid - 1;
            } else {
                ans = mid;
                right = mid - 1;
            }
        }
        return ans;
    }

    public static int findLast(int[] nums, int num) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + ((right - left)>>1);
            if (nums[mid] < num) {
                left = mid + 1;
            } else if (nums[mid] > num) {
                right = mid - 1;
            } else {
                ans = mid;
                left = mid + 1;
            }
        }
        return ans;
    }
}
