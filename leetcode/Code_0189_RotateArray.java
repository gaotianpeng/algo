package leetcode;
/*
    189 轮转数组
        给你一个数组，将数组中的元素向右轮转 k 个位置，其中 k 是非负数

    示例
        输入: nums = [1,2,3,4,5,6,7], k = 3
        输出: [5,6,7,1,2,3,4]
        解释:
        向右轮转 1 步: [7,1,2,3,4,5,6]
        向右轮转 2 步: [6,7,1,2,3,4,5]
        向右轮转 3 步: [5,6,7,1,2,3,4]

        输入：nums = [-1,-100,3,99], k = 2
        输出：[3,99,-1,-100]
        解释:
        向右轮转 1 步: [99,-1,-100,3]
        向右轮转 2 步: [3,99,-1,-100]
 */
public class Code_0189_RotateArray {
    public void rotate(int[] nums, int k) {
        if (nums == null || nums.length < k) {
            return;
        }
        int n = nums.length;
        k = k % n;
        reverse(nums, 0, n - k - 1);
        reverse(nums,  n - k, n - 1);
        reverse(nums, 0, n - 1);
    }

    public static void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int tmp = nums[left];
            nums[left++] = nums[right];
            nums[right--] = tmp;
        }
    }
}
