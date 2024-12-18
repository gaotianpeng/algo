package tixi.daily01;

import java.util.Arrays;


public class Code01_SelectSort {
    /*
    选择排序
        arr[0～N-1]范围上，找到最小值所在的位置，然后把最小值交换到0位置
        arr[1～N-1]范围上，找到最小值所在的位置，然后把最小值交换到1位置
        arr[2～N-1]范围上，找到最小值所在的位置，然后把最小值交换到2位置
        …
        arr[N-1～N-1]范围上，找到最小值位置，然后把最小值交换到N-1位置
    */
    public static void selectSort(int arr[]) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;
        for (int i = 0; i < N - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < N; j++) {
                if (arr[minIndex] > arr[j]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, minIndex, i);
            }
        }
    }
    /*
    选择排序
        arr[0～N-1]范围上，找到最大值所在的位置，然后把最大值交换到 N-1 位置
        arr[0～N-2]范围上，找到最大值所在的位置，然后把最大值交换到 N-2 位置
        arr[0～N-3]范围上，找到最磊值所在的位置，然后把最大值交换到 M-3 位置
        …
        arr[0~0] 范围上，找到最大值位置，然后把最大值交换到 0 位置
    */
    public static void selectSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length - 1;
        for (int i = N; i > 0; i--) {
            int maxIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j] > arr[maxIndex]) {
                    maxIndex = j;
                }
            }
            
            if (maxIndex != i) {
                swap(arr, maxIndex, i);
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
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
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
            System.out.println(arr[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTime; ++i) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            test(arr1);
            selectSort(arr2);
            selectSort1(arr3);
            if (!isEqual(arr1, arr2) || !isEqual(arr1, arr3)) {
                success = false;
                printArray(arr1);
                printArray(arr2);
                printArray(arr3);
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
