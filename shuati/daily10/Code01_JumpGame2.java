package shuati.daily10;

public class Code01_JumpGame2 {
    /*
        https://leetcode.cn/problems/jump-game-ii/
        leetcode 45. 跳跃游戏 II
        给定一个长度为 n 的 0 索引整数数组 nums。初始位置为 nums[0]。
        每个元素 nums[i] 表示从索引 i 向前跳转的最大长度。换句话说，如果你在 nums[i] 处，你可以跳转到任意 nums[i + j] 处:
        返回到达 nums[n - 1] 的最小跳跃次数
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int step = 0;
        int cur = 0;
        int next = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (cur < i) {
                ++step;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }

        return step;
    }
}
