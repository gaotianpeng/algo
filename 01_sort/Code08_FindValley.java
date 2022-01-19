package sort;

public class Code08_FindValley {
    /*
        找到nums局部最小值，并返回其下标
     */
    public int findValleyElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length == 1 || nums[0] < nums[1]) {
            return 0;
        }

        if (nums[nums.length - 1] < nums[nums.length - 2]) {
            return nums.length - 1;
        }

        int left = 1;
        int right = nums.length - 2;
        int mid = 0;
        while (left < right) {
            mid = left + ((right - left)>>1);
            if (nums[mid] > nums[mid - 1]) {
                right = mid - 1;
            } else if (nums[mid] > nums[mid + 1]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }

        return left;
    }
}
