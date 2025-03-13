package review;
/*
    leetcode 26 删除有序列数组中的重复项
        给你一个 非严格递增排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，
        返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
        考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
        更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
        返回 k

        示例 1：
        输入：nums = [1,1,2]
        输出：2, nums = [1,2,_]
        解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
        示例 2：
        输入：nums = [0,0,1,1,1,2,2,3,3,4]
        输出：5, nums = [0,1,2,3,4]
        解释：函数应该返回新的长度 5 ， 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4 。不需要考虑数组中超出新长度后面的元素。
        提示：
        1 <= nums.length <= 3 * 104
        -104 <= nums[i] <= 104
        nums 已按 非严格递增 排列
 */

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class Code_0010_LC_26_RemoveDuplicates {
    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 1; i < nums.length; ++i) {
            if (nums[ans] != nums[i]) {
                nums[++ans] = nums[i];
            }
        }

        return ans + 1;
    }

    /*
        for test
     */
    public static int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

       Set<Integer> sortedSet = new TreeSet<>();
        for (int i = 0; i < nums.length; ++i) {
            if (sortedSet.contains(nums[i])) {
                continue;            }
            sortedSet.add(nums[i]);
        }

        int ans = 0;
        for (Integer item: sortedSet) {
            nums[ans++] = item;
        }

        return ans;
    }

    public static int[] generateRandomSortedData(int maxlen, int minValue, int maxValue) {
        int len = (int) (Math.random() * maxlen) + 1; // Generate a random length between 1 and maxlen
        int[] data = new int[len];
        for (int i = 0; i < len; i++) {
            data[i] = (int) (Math.random() * (maxValue - minValue + 1)) + minValue; // Generate random integers within the range
        }
        Arrays.sort(data);
        return data;
    }

    public static boolean isEqual(int[] arrA, int[] arrB, int k) {
        if (arrA == null && arrB == null) {
            return true;
        }
        if (arrA == null || arrB == null) {
            return false;
        }
        if (arrA.length != arrB.length) {
            return false;
        }
        for (int i = 0; i < k; i++) {
            if (arrA[i] != arrB[i]) {
                return false;
            }
        }
        return true;
    }

    public static void simpleTest() {
        int[] nums = new int[] {0,0,1,1,1,2,2,3,3,4};
        int k = test(nums);
        System.out.println(k);
        for (int i = 0; i < k; ++i) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        simpleTest();
        System.out.println("test start...");
        boolean success = true;
        int maxLen = 50;
        int maxValue = 100;
        int minValue = -100;
        int testTimes = 500000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomSortedData(maxLen, minValue, maxValue);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            int k1 = removeDuplicates(arr1);
            int k2 = test(arr2);
            if (k1 != k2) {
                success = false;
                break;
            }

            if (!isEqual(arr1, arr2, k1)) {
                success = false;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
