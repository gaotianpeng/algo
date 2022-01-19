package daily;

import java.util.LinkedList;

public class Code001_SlidingWindowMaxArray {

    /*
        假设一个固定大小为W的窗口，依次划过arr， 返回每一次滑出状况的最大值
        例如，arr = [4,3,5,4,3,3,6,7], W = 3 返回：[5,5,5,4,6,7]
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        int[] ans = new int[arr.length - w + 1];
        LinkedList<Integer> max_queue = new LinkedList<>();
        int index = 0;
        for (int right = 0; right < arr.length; right++) {
            while (!max_queue.isEmpty() && arr[max_queue.peekLast()] <= arr[right]) {
                max_queue.pollLast();
            }
            // 进数
            max_queue.addLast(right);
            // 窗口没有形成w长度之前不弹出数字
            if (max_queue.peekFirst() == right - w) {
                max_queue.pollFirst();
            }

            if (right >= w - 1) {
                ans[index++] = arr[max_queue.peekFirst()];
            }
        }

        return ans;
    }

    /*
        for test
     */
    public static int[] test(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        int[] slide_window = new int[arr.length - w + 1];
        int index = 0;
        for (int i = w-1; i < arr.length; i++) {
            int start = i - w + 1;
            int max = arr[start];
            for (int j = start+1; j <= i; j++) {
                if (arr[j] > max) {
                    max = arr[j];
                }
            }
            slide_window[index++] = max;
        }

        return slide_window;
    }

    public static int[] generateRandomArray(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        if (len == 0) {
            return null;
        }

        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*max_val);
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
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
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 100;
        int max_len = 30;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[] arr = generateRandomArray(max_val, max_len);
            int w = (int)(Math.random()*(max_len + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = test(arr, w);
            if (!isEqual(ans1, ans2)) {
                printArray(arr);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
