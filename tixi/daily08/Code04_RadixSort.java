package tixi.daily08;

import java.util.Arrays;

public class Code04_RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        radixSort(arr, 0, arr.length - 1, maxBits(arr));   
    }

    private static void radixSort(int[] arr, int left, int right, int digit) {
        final int radix = 10;
        int i = 0, j = 0;
        int[] help = new int[right - left + 1];
        for (int d = 1; d <= digit; d++) {
            int[] count = new int[radix];
            for (i = left; i <= right; ++i) {
                j = getDigits(arr[i], d);
                count[j]++;
            }
    
            for (i = 1; i < radix; ++i) {
                count[i] = count[i] + count[i-1];
            }

            for (i = right; i >= left; --i) {
                j = getDigits(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }

            for (i = left, j = 0; i <= right; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    private static int getDigits(int x, int d) {
        return ((x/((int)Math.pow(10, d - 1))) % 10);
    }

    private static int maxBits(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; ++i) {
            max = Math.max(max, arr[i]);
        }

        int ans = 0;
        while (max != 0) {
            ans++;
            max /= 10;
        }

        return ans;
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
        int testTimes = 1000000;
        int maxVal = 200;
        int maxLen = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxVal, maxLen);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            radixSort(arr);
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
