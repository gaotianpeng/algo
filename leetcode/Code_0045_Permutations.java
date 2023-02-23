package leetcode;

import java.util.ArrayList;
import java.util.List;

/*
    0045 全排列
        给定一个不含重复数字的数组nums ，返回其所有可能的全排列 。你可以按任意顺序返回答案

    示例
        Input: nums = [1,2,3]
        Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 */
public class Code_0045_Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        process(nums, 0, ans);
        return ans;
    }


    public static void process(int[] nums, int index, List<List<Integer>> ans) {
        if (index == nums.length) {
            ArrayList<Integer> cur = new ArrayList<>();
            for (int num: nums) {
                cur.add(num);
            }
            ans.add(cur);
        } else {
            for (int j = index; j < nums.length; j++) {
                swap(nums, index, j);
                process(nums, index + 1, ans);
                swap(nums, index, j);
            }
        }
    }


    public static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
