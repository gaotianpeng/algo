package leetcode;
import java.util.HashMap;

/*
    0001 两数之和
        给定一个整数数组nums和一个整数目标值target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。
        你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
        你可以按任意顺序返回答案。

    提示
        2 <= nums.length <= 104
        -109 <= nums[i] <= 109
        -109 <= target <= 109
        只会存在一个有效答案

    你可以想出一个时间复杂度小于 O(n2) 的算法吗？
 */
public class Code_0001_TowSum {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[] {-1, -1};
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] == target - nums[i]) {
                    return new int[] {i, j};
                }
            }
        }

        return new int[] {-1, -1};
    }

    public int[] twoSum1(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[] { -1, -1};
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(nums[0], 0);
        for (int i = 1; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[] {i, map.get(target-nums[i])};
            }
            map.put(nums[i], i);
        }

        return new int[] {-1, -1};
    }
}
