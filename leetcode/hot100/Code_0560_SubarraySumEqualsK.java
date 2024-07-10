package leetcode.hot100;

import java.util.HashMap;

/*
    560. 和为 K 的子数组
        给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。
        子数组是数组中元素的连续非空序列。

        示例 1：
            输入：nums = [1,1,1], k = 2
            输出：2

        示例 2：
            输入：nums = [1,2,3], k = 3
            输出：2

        提示：
        1 <= nums.length <= 2 * 104
        -1000 <= nums[i] <= 1000
        -107 <= k <= 107
 */
public class Code_0560_SubarraySumEqualsK {
    public static int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        HashMap<Integer, Integer> preSumTimesMap = new HashMap<>();
        // <前缀和，出现的次数>
        preSumTimesMap.put(0, 1);
        int all = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; ++i) {
            all += nums[i];
            if (preSumTimesMap.containsKey(all - k)) {
                ans += preSumTimesMap.get(all-k);
            }
            if (!preSumTimesMap.containsKey(all)) {
                preSumTimesMap.put(all, 1);
            } else {
                preSumTimesMap.put(all, preSumTimesMap.get(all) + 1);
            }
        }

        return ans;
    }

    public static int test(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        int N = nums.length;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j <= i; j++) {
                int sum = 0;
                for (int m = j; m <= i; m++) {
                    sum += nums[m];
                }
                if (sum == k) {
                    ++ans;
                }
            }
        }

        return ans;
    }
}
