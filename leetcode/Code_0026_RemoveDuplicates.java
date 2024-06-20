package leetcode;

public class Code_0026_RemoveDuplicates {
    /*
        leetcode 26 删除有序列数组中的重复项
            给你一个升序排列的数组nums，请你原地删除重复出现的元素，使每个元素只出现一次
            返回删除后数组的新长度。元素的 相对顺序 应该保持 一致
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        }

        if (nums.length < 2) {
            return nums.length;
        }

        int done = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[done] != nums[i]) {
                nums[++done] = nums[i];
            }
        }

        return done + 1;
    }
}
