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

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int index = 0;
        for (; index < Math.min(arr.length, k); index++) {
            heap.add(arr[index]);
        }

        int i = 0;
        for (; index < arr.length; index++, i++) {
            heap.add(arr[index]);
            arr[i] = heap.poll();
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

    public static int[] generateRandomLessKArray(int max_val, int max_len, int k) {
        int arr_len = (int)(Math.random() * (max_len + 1));
        int[] ret_arr = new int[arr_len];
        for (int i = 0; i < arr_len; i++) {
            ret_arr[i] = generateRandomValue(max_val);
        }

        Arrays.sort(ret_arr);
        boolean[] is_swapped = new boolean[arr_len];
        for (int i= 0; i < ret_arr.length; i++) {
            int j = Math.min(i + (int)(Math.random() * (k+1)), ret_arr.length - 1);
            if (!is_swapped[i] && !is_swapped[j]) {
                is_swapped[i] = true;
                is_swapped[j] = true;
                swap(ret_arr, i, j);
            }
        }

        return ret_arr;
    }

    public static int generateRandomValue(int max_val) {
        return (int)(Math.random()*(max_val + 1)) - (int)(Math.random() * max_val);
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
        int max_len = 40;
        int max_value = 50;
        int test_times = 1000000;
        boolean success = true;

        for (int i = 0; i < test_times; ++i) {
            int k = (int)(Math.random() * max_len) + 1;
            int[] arr1 = generateRandomLessKArray(max_value, max_len, k);
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
