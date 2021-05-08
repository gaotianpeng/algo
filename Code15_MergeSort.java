package AlgoNew;
import java.util.Arrays;

public class Code16_MergeSort {
    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }
    /*
        把 arr[L...R] 排有序
        O(N*logN)
     */
    public static void process(int[] arr, int L, int R) {
        // base case
        if (L == R) {
            return;
        }

        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    public static void merge(int[] arr, int L, int M, int R) {
        int[] tmp_arr = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            tmp_arr[i++] = arr[p1] <= arr[p2] ? arr[p1++]: arr[p2++];
        }

        while (p1 <= M) {
            tmp_arr[i++] = arr[p1++];
        }
        while (p2 <= R) {
            tmp_arr[i++] = arr[p2++];
        }

        for (i = 0; i < tmp_arr.length; ++i) {
            arr[L + i] = tmp_arr[i];
        }
    }

    /*
        for test
        --------------------------------------------------------------------
     */
    public static int[] genRandomArray(int maxSize, int maxVal) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)((maxVal + 1) * Math.random()) - (int)(maxVal * Math.random());
        }

        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }

        return res;
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

    public static void printArr(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxVal = 200;
        System.out.println("Test start...");
        for (int i = 0; i < testTime; ++i) {
            int[] arr1 = genRandomArray(maxSize, maxVal);
            int[] arr2 = copyArray(arr1);
            Arrays.sort(arr1);
            mergeSort1(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("error");
                printArr(arr1);
                printArr(arr2);
                break;
            }
        }

        System.out.println("Test end");
    }
}
