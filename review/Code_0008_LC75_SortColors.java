package review;

import java.util.Arrays;

/*
    leetcode 75 sort colors
        Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent,
        with the colors in the order red, white, and blue.
        We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
        You must solve this problem without using the library's sort function.

        Example 1:

        Input: nums = [2,0,2,1,1,0]
        Output: [0,0,1,1,2,2]
        Example 2:

        Input: nums = [2,0,1]
        Output: [0,1,2]
        

        Constraints:

        n == nums.length
        1 <= n <= 300
        nums[i] is either 0, 1, or 2.
 */
public class Code_0008_LC75_SortColors {

    public static void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        int i = 0, j = -1, k = nums.length;
        while (i < k) {
            if (nums[i] == 0) {
                j++;
                if (i != j) {
                    swap(nums, i, j);
                }
                i++;
            } else if (nums[i] == 2) {
                k--;
                if (i != k) {
                    swap(nums, i, k);
                }
            } else {
                i++;
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp; 
    }

    /*
     * for test
     */
    private static void test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        Arrays.sort(nums);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTime = 100000;
        int maxLen = 30;
        int maxVal = 2;
        for (int i = 0; i < testTime; ++i) {
            int len = (int)(Math.random() * maxLen);
            int[] nums = new int[len];
            for (int j = 0; j < len; ++j) {
                nums[j] = (int)(Math.random() * maxVal);
            }

            int[] copy = Arrays.copyOf(nums, nums.length);
            sortColors(nums);
            test(copy);
            if (!Arrays.equals(nums, copy)) {
                success = false;
                System.out.println("test failed...");
                break;
            }
        }
        System.out.println(success? "success": "failed");
        System.out.println("test end");
    }
}