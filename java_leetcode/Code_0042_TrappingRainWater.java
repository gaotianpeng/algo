package leetcode;
/*
    0042 接雨水
        给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
    示例
        输入：height = [4,2,0,3,2,5]
        输出：9
 */
public class Code_0042_TrappingRainWater {
    public int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        int len = height.length;
        int left = 1;
        int left_max = height[0];
        int right = len - 2;
        int right_max = height[len - 1];
        int total_water = 0;
        while (left <= right) {
            if (left <= right_max) {
                total_water += Math.max(0, left_max - height[left]);
                left_max = Math.max(left_max, height[left++]);
            } else {
                total_water += Math.max(0, right_max - height[right]);
                right_max = Math.max(right_max, height[right--]);
            }
        }
        return total_water;
    }
}
