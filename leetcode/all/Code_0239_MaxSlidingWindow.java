package leetcode.all;

import java.util.LinkedList;

public class Code_0239_MaxSlidingWindow {
    /*
        leetcode 239 滑动窗口最大值
            给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
            你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位，返回 滑动窗口中的最大值 。

         示例 1：
            输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
            输出：[3,3,5,5,6,7]
            解释：
            滑动窗口的位置                最大值
            ---------------               -----
            [1  3  -1] -3  5  3  6  7       3
             1 [3  -1  -3] 5  3  6  7       3
             1  3 [-1  -3  5] 3  6  7       5
             1  3  -1 [-3  5  3] 6  7       5
             1  3  -1  -3 [5  3  6] 7       6
             1  3  -1  -3  5 [3  6  7]      7
     */

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || k < 1 || nums.length < k) {
            return null;
        }

        int n = nums.length;
        int[] ans = new int[n - k + 1];
        int index = 0;
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int right = 0; right < n; ++right) {
            while (!qmax.isEmpty() && nums[qmax.peekLast()] <= nums[right]) {
                qmax.pollLast();
            }
            qmax.add(right);
            if (qmax.peekFirst() == right - k) {
                qmax.pollFirst();
            }
            if (right >= k - 1) {
                ans[index++] = nums[qmax.peekFirst()];
            }
        }
        return ans;
    }

    
    public static int[] test(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        int n = arr.length;
        int[] ans = new int[n - w + 1];
        int index = 0;
        int left = 0;
        int right = w - 1;
        while (right < n) {
            int max = arr[left];
            for (int i = left + 1; i <= right; ++i) {
                max = Math.max(max, arr[i]);
            }
            ans[index++] = max;
            left++;
            right++;
        }

        return ans;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; ++i) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxNum = 100;
        int maxVal = 50;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(maxNum, maxVal);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = maxSlidingWindow(arr, w);
            int[] ans2 = test(arr, w);
            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
