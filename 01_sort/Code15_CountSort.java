package sort;
/*
    计数排序
 */

import java.util.Arrays;

public class Code15_CountSort {
    public static void countSort(int[] arr) {
        if (arr.length == 0 || arr.length < 2) {
            return;
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        int[] bucket = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }

        int i = 0;
        for (int j = 0; j < bucket.length; ++j) {
            while(bucket[j]-- > 0) {
                arr[i++] = j;
            }
        }
    }

    /*
        for test
    */
    public static void test(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random());
        }

        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            ret[i] = arr[i];
        }

        return ret;
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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_len = 200;
        int max_val = 150;
        int test_times = 100000;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            int[] arr1 = generateRandomArray(max_len, max_val);
            int[] arr2 = copyArray(arr1);
            countSort(arr1);
            test(arr2);
            if (!isEqual(arr1, arr2)) {
                success = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end...");
    }
}
