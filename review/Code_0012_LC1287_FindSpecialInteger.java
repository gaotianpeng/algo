package review;

/*
    1287——有序数组中出现次数超过元素总数25%的元素
        给定一个非递减的有序整数数组，已知这个数组中恰好有一个整数，它出现的次数超过数组元素总数的25%，请找到并返回这个整数。
        例如，arr=[1，2，2，6，6，6，6，7，10]，其中n=9，整数6出现了4次，答案为6

        1 ≤ arr.length ≤ 104, 0 ≤ arr[i] ≤ 105
 */

import java.util.Map;

public class Code_0012_LC1287_FindSpecialInteger {
    public static int findSpecialInteger(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length < 4) {
            return nums[0];
        }

        int ans = nums[0];
        int cnt = 0;
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == ans) {
                ++cnt;
                if (cnt * 4 > n) {
                    return ans;
                }
            } else {
                ans = nums[i];
                cnt = 1;
            }
        }
        return -1;
    }


    /*
        for test
     */
    public static int test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        if (nums.length < 4) {
            return nums[0];
        }

        Map<Integer, Integer> orderedMap = new java.util.TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (orderedMap.containsKey(nums[i])) {
                orderedMap.put(nums[i], orderedMap.get(nums[i]) + 1);
            } else {
                orderedMap.put(nums[i], 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : orderedMap.entrySet()) {
            if (entry.getValue() * 4 > nums.length) {
                return entry.getKey();
            }
        }

        return -1;
    }

    public static int[] generateRandomData(int maxLen, int minVal, int maxVal) {
        int n = (int)(Math.random() * maxLen) + 1;
        int special = (int)(Math.random() * (maxVal - minVal + 1)) + minVal;
        int[] arr = new int[n];

        // Calculate how many times the special number should appear
        int count = n / 4 + 1; // more than 25% of n

        // Fill array with the special number at the start
        for (int i = 0; i < count; i++) {
            arr[i] = special;
        }
        // Fill the rest with random numbers
        for (int i = count; i < n; i++) {
            arr[i] = (int)(Math.random() * (maxVal - minVal + 1)) + minVal;
        }
        // Sort to ensure non-decreasing order
        java.util.Arrays.sort(arr);
        return arr;
    }

    public static void simpleTest() {
        int[] arr = {1, 2, 2, 6, 6, 6, 6, 7, 10};
        System.out.println(findSpecialInteger(arr));
        System.out.println(test(arr));
    }

    public static void main(String[] args) {
        simpleTest();
        System.out.println("test start...");
        int testTime = 500000;
        boolean success = true;
        int maxValue = 100;
        int minValue = -100;
        int maxLen = 30;
        for (int i = 0; i < testTime; i++) {
            int[] nums1 = generateRandomData(maxLen, minValue, maxValue);
            int[] nums2 = nums1.clone();
            if (test(nums1) != findSpecialInteger(nums2)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
