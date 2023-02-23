package leetcode;
/*
    45 跳跃游戏
        给你一个非负整数数组nums ，你最初位于数组的第一个位置。
        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        你的目标是使用最少的跳跃次数到达数组的最后一个位置。
        假设你总是可以到达数组的最后一个位置。

    示例
        输入: nums = [2,3,1,1,4]
        输出: 2
        解释: 跳到最后一个位置的最小跳跃数是 2。
            从下标为 0 跳到下标为 1 的位置，跳1步，然后跳3步到达数组的最后一个位置。
 */
public class Code_0045_JumpGame2 {
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int step = 0;
        int cur = 0;
        int next = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }

        return step;
    }
}
