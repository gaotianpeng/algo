package tixi.daily08;

import java.util.Arrays;

public class Code03_CountSort {

    // 0 ~ 200
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int max = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            max = Math.max(arr[i], max);
        }

        int[] bucket = new int[max+1];
        for (int i = 0; i < arr.length; ++i) {
            bucket[arr[i]]++;
        }

        int i = 0;
        for (int j = 0; j < bucket.length; ++j) {
            while (bucket[j]-- > 0) {
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

    public static int[] generateRandomArray(int maxVal, int maxLen) {
        int len = (int)(Math.random() * (maxLen + 1));
        int[] arr = new int[len];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * (maxVal + 1));
        }

        return arr;
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

    public static void printArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = 200;
        int maxLen = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxVal, maxLen);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            countSort(arr);
            test(arr2);
            if (!isEqual(arr, arr2)) {
                printArr(arr);
                printArr(arr2);
                printArr(arr3);
                success = false;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
