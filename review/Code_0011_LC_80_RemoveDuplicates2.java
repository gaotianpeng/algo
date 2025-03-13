package review;

import java.util.*;

/*
    80. 删除有序数组中的重复项 II
    给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，
    返回删除后数组的新长度。
    不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
 */

public class Code_0011_LC_80_RemoveDuplicates2 {
    public static int removeDuplicates2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length < 3) {
            return nums.length;
        }

        int slow = 2, fast = 2;
        while (fast < nums.length) {
            if (nums[slow -2] != nums[fast]) {
                nums[slow++] = nums[fast];
            }
            ++fast;
        }


        return slow;
    }

    /*
        for test
     */
    public static int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length < 3) {
            return nums.length;
        }

        Map<Integer, Integer> orderedMap = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (orderedMap.containsKey(nums[i])) {
                orderedMap.put(nums[i], orderedMap.get(nums[i]) + 1);
            } else {
                orderedMap.put(nums[i], 1);
            }
        }

        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : orderedMap.entrySet()) {
            if (entry.getValue() >= 2) {
                nums[ans++] = entry.getKey();
                nums[ans++] = entry.getKey();
            } else  {
                nums[ans++] = entry.getKey();
            }
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
        int[] nums = new int[] {0,0,1,1,1,2,2,2,3,3,4};
        System.out.println("before size: " + nums.length);
        int k = test(nums);
        System.out.println("after size: " + k);
        for (int i = 0; i < k; ++i) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        simpleTest();
        System.out.println("test start...");
        boolean success = true;
        int maxLen = 100;
        int maxValue = 10;
        int minValue = 0;
        int testTimes = 500000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomSortedData(maxLen, minValue, maxValue);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            int k1 = removeDuplicates2(arr1);
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
