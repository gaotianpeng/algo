package leetcode;
/*
    0011 盛最多水的容器
        给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i,ai) 。
        在坐标内画 n 条垂直线，垂直线 i的两个端点分别为(i,ai) 和 (i, 0) 。
        找出其中的两条线，使得它们与x轴共同构成的容器可以容纳最多的水。

        说明：你不能倾斜容器。

 */
public class Code_0011_ContainerWithMostWater {
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int ans = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            ans = Math.max(ans, Math.min(height[left], height[right])*(right - left));
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }

        return ans;
    }
}
