package tixi.daily24;

import java.util.LinkedList;

public class Code01_SlidingWindowMaxArray {
    /*
        假设一个固定大小为W的窗口，依次划过arr，
        返回每一次滑出状况的最大值
        例如，arr = [4,3,5,4,3,3,6,7], W = 3
        返回：[5,5,5,4,6,7]
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        LinkedList<Integer> max_q = new LinkedList<Integer>();
        int[] ans = new int [arr.length - w + 1];
        int index = 0;
        for (int right = 0; right < arr.length; ++right) {
            while (!max_q.isEmpty() && arr[max_q.peekLast()] <= arr[right]) {
                max_q.pollLast();
            }

            max_q.addLast(right);
            if (max_q.peekFirst() == right - w) {
                max_q.pollFirst();
            }

            if (right >= w - 1) {
                ans[index++] = arr[max_q.peekFirst()];
            }
        }

        return ans;
    }


    public static int[] test(int[] arr, int w) {
        if (arr == null || w < 1 || arr.length < w) {
            return null;
        }

        int n = arr.length;
        int [] ans = new int[n - w + 1];
        int left = 0;
        int right = w - 1;
        int index = 0;
        while (right < n) {
            int max = arr[left];
            for (int i = left + 1; i <= right; ++i) {
                max = Math.max(max, arr[i]);
            }
            ans[index++] = max;
            ++left;
            ++right;
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
        System.out.println("test starting...");
        int test_times = 100000;
        int max_num = 100;
        int max_val = 50;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = generateRandomArray(max_num, max_val);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = test(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
