package sort;
import java.util.Arrays;

public class Code09_MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
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

    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int merge_size = 1;
        int n = arr.length;
        while (merge_size < n) {
            int left = 0;
            while (left < n) {
                if (merge_size >= n - left) {
                    break;
                }

                int mid = left + merge_size - 1;
                int right = mid + Math.min(merge_size, n - mid - 1);
                merge(arr, left, mid, right);
                left = right + 1;
            }
            if (merge_size > n / 2) {
                break;
            }
            merge_size <<= 1;
        }
    }

    public static void merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int i = 0;
        int pos1 = left;
        int pos2 = mid + 1;
        while (pos1 <= mid && pos2 <= right) {
            helper[i++] = arr[pos1] <= arr[pos2] ? arr[pos1++] : arr[pos2++];
        }

        while (pos1 <= mid) {
            helper[i++] = arr[pos1++];
        }

        while (pos2 <= right) {
            helper[i++] = arr[pos2++];
        }

        for (i = 0; i < helper.length; i++) {
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
            int[] arr3 = copyArray(arr1);
            mergeSort(arr1);
            mergeSort2(arr2);
            test(arr3);
            if (!isEqual(arr1, arr3)) {
                success = false;
                printArray(arr1);
                printArray(arr3);
                break;
            }
            if (!isEqual(arr2, arr3)) {
                success = false;
                printArray(arr2);
                printArray(arr3);
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
