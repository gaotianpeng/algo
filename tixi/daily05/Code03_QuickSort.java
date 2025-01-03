package tixi.daily05;

import java.util.Arrays;

/*
    快速排序 1.0 递归版本
 */
public class Code03_QuickSort {
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }

        int M = partition(arr, L, R);
        process(arr, L, M - 1);
        process(arr, M + 1, R);
    }

    // 将 arr[] 以 arr[R] 分为界，<= arr[R] 放arr左边，> arr[R] 放arr右边 
    private static int partition(int[] arr, int L, int R) {
        if (L > R) {
            return -1;
        }

        if (L == R) {
            return L;
        }

        /*
        * 当前数 arr[index] 目标数 arr[R]
        * 1）当前数 <= 目标，当前数和<=区下一个数交换，<=区向右扩
        * 2）当前数 > 目标，当前跳下一个
        */
        int less = L-1;
        int index = L;
        while (index < R) {
            if (arr[index] <= arr[R]) {
                swap(arr, ++less, index);
            }
            ++index;
        }

        swap(arr, ++less, R);
        return less;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] =  temp;
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
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }

        return arr;
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
        int testTimes = 100000;
        int maxVal = 50;
        int maxLen = 10;
        boolean success = true;

        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxLen, maxVal);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
            test(arr2);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
