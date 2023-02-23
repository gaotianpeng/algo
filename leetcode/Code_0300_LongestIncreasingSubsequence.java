package leetcode;
/*
    leetcode 300
       给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
       子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
       例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。

    示例
        输入：nums = [10,9,2,5,3,7,101,18]
        输出：4
        解释：最长递增子序列是 [2,3,7,101]，因此长度为 4

        输入：nums = [0,1,0,3,2,3]
        输出：4

        输入：nums = [7,7,7,7,7,7,7]
        输出：1
 */
public class Code_0300_LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int[] ends = new int[nums.length];
        ends[0] = nums[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int mid = 0;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                mid = l + ((r - l) >> 1);
                if (nums[i] > ends[mid]) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = nums[i];
            max = Math.max(max, l + 1);
        }

        return max;
    }
}
