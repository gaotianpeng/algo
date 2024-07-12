package leetcode.top150;

/*
300. Longest Increasing Subsequence
Given an integer array nums, return the length of the longest strictly increasing
subsequence

Example 1:

    Input: nums = [10,9,2,5,3,7,101,18]
    Output: 4
    Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
Example 2:
    Input: nums = [0,1,0,3,2,3]
    Output: 4

Example 3:
    Input: nums = [7,7,7,7,7,7,7]
    Output: 1


Constraints:
    1 <= nums.length <= 2500
    -104 <= nums[i] <= 104
 */
public class Code_0300_LongestIncreasingSubsequence {
    public static int lengthOfLIS(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] ends = new int[arr.length];
        ends[0] = arr[0];
        int right = 0;
        int l = 0;
        int r = 0;
        int m = 0;
        int max = 1;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if (arr[i] > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = arr[i];
            max = Math.max(max, l + 1);
        }
        return max;
    }

    public static  int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int N = nums.length;
        int[] dp = new int[N];
        dp[0] = 1;
        int ans = 1;
        for (int i = 1; i < N; ++i) {
            int cur = 1;
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    cur = Math.max(dp[j] + 1, cur);
                }
            }

            dp[i] = cur;
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int [] nums1 = new int []{10,9,2,5,3,7,101,18};
        if (lengthOfLIS(nums1) != 4) {
            success = false;
        }
        if (test(nums1) != 4) {
            success = false;
        }
        int [] nums2 = new int []{0,1,0,3,2,3};
        if (lengthOfLIS(nums2) != 4) {
            success = false;
        }
        if (test(nums2) != 4) {
            success = false;
        }
        int [] nums3 = new int []{7,7,7,7,7,7,7};
        if (lengthOfLIS(nums3) != 1) {
            success = false;
        }
        if (test(nums3) != 1) {
            success = false;
        }
        System.out.println(success ? "true" : "failed");
        System.out.println("test end");
    }
}
