package leetcode.all;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
    525. Contiguous Array
        Given a binary array nums, return the maximum length of a contiguous subarray
        with an equal number of 0 and 1.


    Example 1:
        Input: nums = [0,1]
        Output: 2
    Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.

    Example 2:
        Input: nums = [0,1,0]
        Output: 2
        Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.


    Constraints:

    1 <= nums.length <= 10^5
    nums[i] is either 0 or 1.
 */
public class Code_0525_ContiguousArray {
    // 思路：遇到0当作-1，遇到1，当作1，求和为0的最长子数组长茺
    public static int findMaxLength(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        map.put(0, -1);
        int N = nums.length;
        int sum = 0;
        for (int i = 0; i < N; ++i) {
            sum += nums[i] == 0 ? -1: 1;
            if (map.containsKey(sum)) {
                ans = Math.max(ans, i - map.get(sum));
            }

            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }

        return ans;
    }

    public static int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxLength = 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int counter = 0;
        map.put(counter, -1);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            if (num == 1) {
                counter++;
            } else {
                counter--;
            }
            if (map.containsKey(counter)) {
                int prevIndex = map.get(counter);
                maxLength = Math.max(maxLength, i - prevIndex);
            } else {
                map.put(counter, i);
            }
        }
        return maxLength;
    }

    public static int[] generateBinaryArray(int maxLen) {
        if (Math.random() < 0.01) {
            return null;
        }

        if (maxLen <= 0) {
            return new int[0];
        }

        Random random = new Random();
        int len = random.nextInt(maxLen) + 1;
        int[] array = new int[len];

        for (int i = 0; i < len; i++) {
            array[i] = random.nextBoolean() ? 0 : 1;
        }

        return array;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            System.out.println("null");
        } else {
            for (int i = 0; i != arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 10000;
        int maxLen = 50;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateBinaryArray(maxLen);
            int ans1 = findMaxLength(arr);
            int ans2 = test(arr);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
