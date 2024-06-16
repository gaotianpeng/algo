package leetcode.hot100;
/*
    283. 移动零
    给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
    请注意 ，必须在不复制数组的情况下原地对数组进行操作。

    示例 1:
        输入: nums = [0,1,0,3,12]
        输出: [1,3,12,0,0]
    示例 2:
        输入: nums = [0]
        输出: [0]

    提示:

    1 <= nums.length <= 104
    -231 <= nums[i] <= 231 - 1


    进阶：你能尽量减少完成的操作次数吗
 */
public class Code_0283_MoveZeroes {
    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int j = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != 0) {
                nums[j++] = nums[i];
            }
        }

        for (int i = j; i < nums.length; ++i) {
            nums[i] = 0;
        }
    }

    public static void test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int N = nums.length;
        int[] copy = new int[N];
        int j = 0;
        for (int i = 0; i < N; ++i) {
            if (nums[i] != 0) {
                copy[j++] = nums[i];
            }
        }

        for (; j < N; j++) {
            copy[j] = 0;
        }

        for (int i = 0; i < N; ++i) {
            nums[i] = copy[i];
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] nums = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
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

    public static boolean isEqual(int[] nums1, int[] nums2) {
        if ((nums1 == null && nums2 != null) || (nums1 != null && nums2 == null)) {
            return false;
        }

        if (nums1 == null && nums2 == null) {
            return true;
        }

        if (nums1.length != nums2.length) {
            return false;
        }

        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] != nums2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int max_val = 40;
        int max_len = 50;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] nums1 = generateRandomArray(max_len, max_val);
            int[] nums2 = copyArray(nums1);
            moveZeroes(nums1);
            test(nums2);
            if (!isEqual(nums1, nums2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
