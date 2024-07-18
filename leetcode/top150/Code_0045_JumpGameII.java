package leetcode.top150;
/*
    45. Jump Game II
    You are given a 0-indexed array of integers nums of length n. You are initially positioned at nums[0].
    Each element nums[i] represents the maximum length of a forward jump from index i. In other words,
    if you are at nums[i], you can jump to any nums[i + j] where:
        0 <= j <= nums[i] and i + j < n
    Return the minimum number of jumps to reach nums[n - 1].

    The test cases are generated such that you can reach nums[n - 1].

    Example 1:
        Input: nums = [2,3,1,1,4]
        Output: 2
        Explanation: The minimum number of jumps to reach the last index is 2.
        Jump 1 step from index 0 to 1, then 3 steps to the last index.

    Example 2:
        Input: nums = [2,3,0,1,4]
        Output: 2

    Constraints:
        1 <= nums.length <= 104
        0 <= nums[i] <= 1000
        It's guaranteed that you can reach nums[n - 1].
     */
public class Code_0045_JumpGameII {
    /*
            [3, 1, 7, 1, 2, 2, 1, 1, 2, 1]
      step   0  1  1  1  2
      cur    0  3  3  3  9
      next   3  3  9  9  9

      当前来到i,
        1) i > cur， step 不足以到i, step++, 并用next更新cur
        2) i <= cur, step步内还能到, 如果next能变大则更新next
     */
    public static int jump(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 当前最少跳几步能到i位置
        int step = 0;
        // 跳的步数不超过 step 的情况下，最右能到哪个位置
        int cur = 0;
        // 跳的步数不超过 step+1 的情况下，最右能到哪个位置
        int next = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (next >= nums.length - 1) {
                return step + 1;
            }
            if (cur < i) {
                step++;
                cur = next;
            }
            next = Math.max(next, i + nums[i]);
        }
        return step;
    }

    /*
        方法一：反向查找出发位置
            我们的目标是到达数组的最后一个位置，因此我们可以考虑最后一步跳跃前所在的位置，该位置通过跳跃能够到达最后一个位置。
            如果有多个位置通过跳跃都能够到达最后一个位置，那么我们应该如何进行选择呢？直观上来看，我们可以「贪心」
            地选择距离最后一个位置最远的那个位置，
            也就是对应下标最小的那个位置。因此，我们可以从左到右遍历数组，选择第一个满足要求的位置。
            找到最后一步跳跃前所在的位置之后，我们继续贪心地寻找倒数第二步跳跃前所在的位置，以此类推，直到找到数组的开始位置
     */
    public static int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }
}
