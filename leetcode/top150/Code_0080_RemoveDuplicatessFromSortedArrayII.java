package leetcode.top150;

public class Code_0080_RemoveDuplicatessFromSortedArrayII {
    /*
        80. 删除有序数组中的重复项 II
        给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，
        返回删除后数组的新长度。
        不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     */
    public static int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return n;
        }

        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow++] = nums[fast];
            }
            ++fast;
        }

        return slow;
    }
}
