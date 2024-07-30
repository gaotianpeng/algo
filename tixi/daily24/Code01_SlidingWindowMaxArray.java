package tixi.daily24;

import java.util.Arrays;
import java.util.LinkedList;

public class Code01_SlidingWindowMaxArray {
    /*
        https://leetcode.cn/problems/sliding-window-maximum/submissions/
        leetcode 239 滑动窗口最大值
            假设一个固定大小为W的窗口，依次划过arr，
            返回每一次滑出状况的最大值
            例如，arr = [4,3,5,4,3,3,6,7], W = 3
            返回：[5,5,5,4,6,7]
     */
    // [6, 4, 2, 3, 5, 7, 0, 5]
    //  0  1  2  3  4  5  6  7
    public static int[] maxSlidingWindow(int[] arr, int W) {
        if (arr == null || arr.length < W || W < 1) {
            return null;
        }

        int N = arr.length;
        int[] ans = new int[N - W + 1];
        int index = 0;
        // 假设让窗口依次缩小，哪些位置会依次成为窗口内的最大值
        LinkedList<Integer> qmax = new LinkedList<>();
        for (int R = 0; R < N; ++R) {
            while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
                qmax.pollLast();
            }
            qmax.addLast(R);
            // R-W 位置过期
            if (qmax.peekFirst() == R - W) {
                qmax.pollFirst();
            }

            // 此时，形成了一个正常的窗口，需要收集答案
            if (R >= W - 1) {
                ans[index++] = arr[qmax.peekFirst()];
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
