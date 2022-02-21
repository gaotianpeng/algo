package leetcode;
/*
    384 打乱数组
        给你一个整数数组nums，设计算法来打乱一个没有重复元素的数组。打乱后，数组的所有排列应该是等可能的。
        实现 Solution class:
            Solution(int[] nums) 使用整数数组 nums 初始化对象
            int[] reset() 重设数组到它的初始状态并返回
            int[] shuffle() 返回数组随机打乱后的结果

        示例
            输入
                ["Solution", "shuffle", "reset", "shuffle"]
                [[[1, 2, 3]], [], [], []]
            输出
                [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]

            解释
                Solution solution = new Solution([1, 2, 3]);
                solution.shuffle();    // 打乱数组 [1,2,3] 并返回结果。任何 [1,2,3]的排列返回的概率应该相同。例如，返回 [3, 1, 2]
                solution.reset();      // 重设数组到它的初始状态 [1, 2, 3] 。返回 [1, 2, 3]
                solution.shuffle();    // 随机返回数组 [1, 2, 3] 打乱后的结果。例如，返回 [1, 3, 2]

 */
public class Code_0384_ShuffleAnArray {
    class Solution {
        private int[] origin;
        private int[] shuffle;
        private int n;

        public Solution(int[] nums) {
            origin = nums;
            n = nums.length;
            shuffle = new int[n];
            for (int i = 0; i < n; i++) {
                shuffle[i] = origin[i];
            }
        }

        public int[] reset() {
            return origin;
        }

        public int[] shuffle() {
            for (int i = n -1; i >= 0; i--) {
                int random_pos = (int)(Math.random() * (i + 1));
                int tmp = shuffle[random_pos];
                shuffle[random_pos] = shuffle[i];
                shuffle[i] = tmp;
            }

            return shuffle;
        }
    }

}
