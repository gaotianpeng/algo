package tixi.daily04;

import java.util.Arrays;

public class Code02_MergeSort2 {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;
        int mergeSize = 1;
        while (mergeSize < N) {
            int left = 0;
            while (left < N) {
                if (N - left < mergeSize) {
                    break;
                }
                int mid = left + mergeSize - 1;
                int right = mid + Math.min(mergeSize, N - mid - 1);
                merge(arr, left, mid, right);
                left = right + 1;
            }

            if (mergeSize > N/2) {
                break;
            }

            mergeSize <<= 1;
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int leftPos = left;
        int rightPos = mid + 1;
        int pos = 0;

        while (leftPos <= mid && rightPos <= right) {
            helper[pos++] = arr[leftPos] <= arr[rightPos] ? arr[leftPos++] : arr[rightPos++];
        }

        while (leftPos <= mid) {
            helper[pos++] = arr[leftPos++];
        }

        while (rightPos <= right) {
            helper[pos++] = arr[rightPos++];
        }

        for (int i = 0; i < helper.length; i++) {
            arr[left + i] = helper[i];
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
        int testTimes = 1000000;
        int maxVal = 40;
        int maxLen = 50;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr1 = generateRandomArray(maxLen, maxVal);
            int[] arr2 = copyArray(arr1);
            mergeSort(arr1);
            test(arr2);
            if (!isEqual(arr1, arr2)) {
                success = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
