package leetcode.top150;
/*
    55. Jump Game
        You are given an integer array nums. You are initially positioned at the array's first index,
        and each element in the array represents your maximum jump length at that position.
        Return true if you can reach the last index, or false otherwise.

        Example 1:
            Input: nums = [2,3,1,1,4]
            Output: true
            Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

        Example 2:
            Input: nums = [3,2,1,0,4]
            Output: false
            Explanation: You will always arrive at index 3 no matter what. Its maximum jump
            length is 0, which makes it impossible to reach the last index.

        Constraints:
            1 <= nums.length <= 104
            0 <= nums[i] <= 105
 */
public class Code_0055_JumpGame {
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length < 2) {
            return true;
        }

        // 目前为止，能够蹦到的最右位置
        int max = nums[0];
        for (int i = 1; i < nums.length; ++i) {
            // 如果当前位置i>max,说明连i位置都到不了，返回false
            if (i > max) {
                return false;
            }

            max = Math.max(max, nums[i] + i);
        }

        return true;
    }
}
