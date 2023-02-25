package leetcode;

import java.util.LinkedList;

public class Code_0239_MaxSlidingWindow {
    /*
        leetcode 239 滑动窗口最大值
            给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
            你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位，返回 滑动窗口中的最大值 。

         示例 1：
            输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
            输出：[3,3,5,5,6,7]
            解释：
            滑动窗口的位置                最大值
            ---------------               -----
            [1  3  -1] -3  5  3  6  7       3
             1 [3  -1  -3] 5  3  6  7       3
             1  3 [-1  -3  5] 3  6  7       5
             1  3  -1 [-3  5  3] 6  7       5
             1  3  -1  -3 [5  3  6] 7       6
             1  3  -1  -3  5 [3  6  7]      7
     */

    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k < 1 || nums.length < k) {
            return null;
        }

        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int index = 0;
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int right = 0; right < n; ++right) {
            while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[right]) {
                qmax.pollLast();
            }
            qmax.add(right);
            if (qmax.peekFirst() == right - k) {
                qmax.pollFirst();
            }
            if (right >= k - 1) {
                ans[index++] = nums[qmax.peekFirst()];
            }
        }
        return ans;
    }
}
