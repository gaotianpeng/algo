package leetcode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Code_0128_LongestConsecutiveSequence {
    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int longest = 1;

        for (int num : nums) {
            longest = Math.max(longest, longestConsecutiveRecursive(nums, num));
        }

        return longest;
    }

    private static int longestConsecutiveRecursive(int[] nums, int currentNum) {
        int count = 1;

        while (contains(nums, currentNum + 1)) {
            count++;
            currentNum++;
        }

        return count;
    }

    private static boolean contains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }

    public static int[] generateRandomArray(int minValue, int maxValue, int maxLength) {
        Random random = new Random();
        int length = random.nextInt(maxLength) + 1; // Ensure length is at least 1
        int[] randomArray = new int[length];

        for (int i = 0; i < length; i++) {
            randomArray[i] = random.nextInt(maxValue - minValue + 1) + minValue;
        }

        return randomArray;
    }

    public static int longestConsecutive1(int[] nums) {
        Set<Integer> numSet = new HashSet<Integer>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;

        for (int num : numSet) {
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxVal = 200;
        int minVal = 0;
        int maxLen = 30;

        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(minVal, maxVal, maxLen);
            if (longestConsecutive(arr) != longestConsecutive1(arr)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
