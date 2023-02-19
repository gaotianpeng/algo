package shuati.daily01;

import java.util.HashMap;

/*
    leetcode 494
        给定一个数组arr，你可以在每个数字之前决定+或者-
        但是必须所有数字都参与
        再给定一个数target，请问最后算出target的方法数是多少
 */
public class Code_007_TargetSum {
    public int findTargetSumWays(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return process(nums, 0, target);
    }

    public static int process(int[] nums, int index, int rest) {
        if (index == nums.length) {
            return rest == 0 ? 1 : 0;
        }

        return process(nums, index + 1, rest - nums[index])
                + process(nums, index + 1, rest + nums[index]);
    }

    /*
        可以自由使用arr[index...]所有的数字，搞出rest这个数, 方法数是多少?
        index == 7, rest = 13
        map <index, <rest, ans>>
     */
    public int findTargetSumWays2(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        return process2(nums, 0, target, new HashMap<>());
    }

    public static int process2(int[] arr, int index, int rest,
                               HashMap<Integer, HashMap<Integer, Integer>> dp) {
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }

        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1: 0;
        } else {
            ans = process2(arr, index + 1, rest - arr[index], dp)
                    + process2(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }
}
