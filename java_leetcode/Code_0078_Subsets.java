package leetcode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
    0078 子集
        给你一个整数数组nums, 数组中的元素互不相同. 返回该数组所有可能的子集（幂集）
        解集不能包含重复的子集. 你可以按任意顺序返回解集

    示例
        输入：nums = [1,2,3]
        输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

        输入：nums = [0]
        输出：[[],[0]]
 */
public class Code_0078_Subsets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        LinkedList<Integer> path = new LinkedList<>();
        process(nums, 0, path, ans);
        return ans;
    }

    public static void process(int nums[], int index, LinkedList<Integer> path, List<List<Integer>> ans) {
        if (index == nums.length) {
            ans.add(copy(path));
        } else {
            process(nums, index + 1, path, ans);
            path.addLast(nums[index]);
            process(nums, index + 1, path, ans);
            path.removeLast();
        }
    }

    public static ArrayList<Integer> copy(LinkedList<Integer> path) {
        ArrayList<Integer> ans = new ArrayList<>();
        for (Integer num: path) {
            ans.add(num);
        }
        return ans;
    }
}
