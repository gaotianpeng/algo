package tixi.daily04;

import java.util.Arrays;

public class Code01_MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }

        int mid = left + ((right - left)>>1);
        process(arr, left, mid);
        process(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int left_idx = left;
        int right_idx = mid + 1;
        int index = 0;

        while (left_idx <= mid && right_idx <= right) {
            helper[index++] = arr[left_idx] <= arr[right_idx] ? arr[left_idx++] : arr[right_idx++];
        }

        while (left_idx <= mid) {
            helper[index++] = arr[left_idx++];
        }

        while (right_idx <= right) {
            helper[index++] = arr[right_idx++];
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
        int test_times = 1000000;
        int max_val = 40;
        int max_len = 50;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] arr1 = generateRandomArray(max_len, max_val);
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