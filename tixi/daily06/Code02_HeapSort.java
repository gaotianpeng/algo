package tixi.daily06;

import java.util.Arrays;

/*
    堆排序
        1 先让整个数组都变成大根堆结构, 建立堆的过程
            1）从上到下的方法, 时间复杂度为O(N*logN)
            2) 从下到上的方法, 时间复杂度为O(N)
        2 把堆的最大值和堆末尾的值交换, 减少堆的大小，调整堆，周而复始
        3 堆的大小减少成0后，排序完成
 */
public class Code02_HeapSort {

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            heapInsert(arr, i);
        }

        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }

    public static void heapSort1(int[] arr) {
        if (arr == null || arr.length <2) {
            return;
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }

        int heapSize = arr.length;
        while (heapSize > 0) {
            swap(arr, 0, --heapSize);
            heapify(arr, 0, heapSize);
        }
    }


    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left] < arr[left + 1] ? 
                            left + 1 : left;
            largest = arr[index] < arr[largest] ? largest : index;
            if (largest == index) {
                break;
            }

            swap(arr, index, largest);
            index = largest;
            left = 2 * index + 1;
        }
    }

    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index-1)/2]) {
            swap(arr, index, (index - 1)/2);
            index = (index - 1) / 2;
        }
    }
    
    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }

        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /*
        for test
     */
    public static void test(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArray(int maxVal, int maxLen) {
        int arrLen = (int)(Math.random() * (maxLen + 1));
        int[] ret = new int[arrLen];
        for (int i = 0; i < arrLen; i++) {
            ret[i] = generateRandomValue(maxVal);
        }

        return ret;
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
            int[] arr1 = generateRandomArray(maxValue, maxLen);
            int[] arr2 = copyArray(arr1);
            int[] arr3 = copyArray(arr1);
            heapSort(arr1);
            test(arr2);
            heapSort1(arr3);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                printArray(arr2);
                success = false;
                break;
            }
            if (!isEqual(arr2, arr3)) {
                printArray(arr2);
                printArray(arr3);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
