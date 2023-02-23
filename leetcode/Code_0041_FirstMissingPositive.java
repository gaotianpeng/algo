package leetcode;
/*
    41 缺失的第一个正数
        给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数
        请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案

    示例
        输入：nums = [1,2,0]
        输出：3

        输入：nums = [3,4,-1,1]
        输出：2

        输入：nums = [7,8,9,11,12]
        输出：1
 */
public class Code_0041_FirstMissingPositive {
    public int firstMissingPositive(int[] nums) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == left + 1) {
                left++;
            } else if (nums[left] <= left || nums[left] > right
                        || nums[nums[left] - 1] == nums[left]) {
                swap(nums, left, --right);
            } else {
                swap(nums, left, nums[left] - 1);
            }
        }

        return left + 1;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
