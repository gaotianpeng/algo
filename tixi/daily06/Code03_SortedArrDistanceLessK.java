package tixi.daily06;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
    已知一个几乎有序的数组. 几乎有序是指, 如果把数组排好顺序的话, 每个元素移动的距离一定不超过k, 并且k相对于数组长度来说是比较小的
    请选择一个合适的排序策略, 对这个数组进行排序
*/
public class Code03_SortedArrDistanceLessK {
    public static void heapSortLessK(int[] arr, int k) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int min = Math.min(k, arr.length);
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (index = 0; index < min; ++index) {
            heap.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; ++index) {
            heap.add(arr[index]);
            arr[i++] = heap.poll();
        }

        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    /*
        for test
     */
    public static void test(int[] arr, int k) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomLessKArray(int maxVal, int maxLen, int k) {
        int arrLen = (int)(Math.random() * (maxLen + 1));
        int[] ans = new int[arrLen];
        for (int i = 0; i < arrLen; i++) {
            ans[i] = generateRandomValue(maxVal);
        }

        Arrays.sort(ans);
        boolean[] isSwapped = new boolean[arrLen];
        for (int i= 0; i < ans.length; i++) {
            int j = Math.min(i + (int)(Math.random() * (k+1)), ans.length - 1);
            if (!isSwapped[i] && !isSwapped[j]) {
                isSwapped[i] = true;
                isSwapped[j] = true;
                swap(ans, i, j);
            }
        }

        return ans;
    }

    public static int generateRandomValue(int maxVal) {
        return (int)(Math.random()*(maxVal + 1)) - (int)(Math.random() * maxVal);
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }

        return ret;
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1 == null && arr2 != null) {
            return false;
        }

        if (arr1 != null && arr2 == null) {
            return false;
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
        int maxLen = 40;
        int maxValue = 50;
        int testTimes = 1000000;
        boolean success = true;

        for (int i = 0; i < testTimes; ++i) {
            int k = (int)(Math.random() * maxLen) + 1;
            int[] arr1 = generateRandomLessKArray(maxValue, maxLen, k);
            int[] arr2 = copyArray(arr1);
            heapSortLessK(arr1, k);
            test(arr2, k);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                printArray(arr2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
