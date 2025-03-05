package review;

/*
    给定两个递增有序数组a和b，将所有元素归并到数组c中，并且要求c中的元素也是递增有序的
*/

import java.util.Arrays;

public class Code_0001_MergeSortedArray {
    public static int[] mergeSortedArray(int[] arrA, int[] arrB) {
        if (arrA == null) {
            return arrB;
        }

        if (arrB == null) {
            return arrA;
        }

        int m = arrA.length;
        int n = arrB.length;
        int[] ans = new int[m+n];
        int i = 0;
        int j = 0;
        int index = 0;
        while (i < m && j < n) {
            if (arrA[i] <= arrB[j]) {
                ans[index++] = arrA[i++];
            } else {
                ans[index++] = arrB[j++];
            }
        }

        while (i < m) {
            ans[index++]  = arrA[i++];
        }
        while (j < n) {
            ans[index++] = arrB[j++];
        }
        return ans;
    }

    /*
        for test
     */
    public static int[] test(int[] arrA, int[] arrB) {
        if (arrA == null) {
            return arrB;
        }
        if (arrB == null) {
            return arrA;
        }

        int m = arrA.length;
        int n = arrB.length;
        int[] ans = new int[m+n];
        int index = ans.length;
        while (m > 0 && n > 0) {
            if (arrA[m-1] >= arrB[n-1]) {
                ans[--index] = arrA[--m];
            } else {
                ans[--index] = arrB[--n];
            }
        }

        while (m > 0) {
            ans[--index] = arrA[--m];
        }

        while (n > 0) {
            ans[--index] = arrB[--n];
        }

        return ans;
    }

    public static int[] generateRandomSortedData(int maxlen, int minValue, int maxValue) {
        int len = (int) (Math.random() * maxlen) + 1; // Generate a random length between 1 and maxlen
        int[] data = new int[len];
        for (int i = 0; i < len; i++) {
            data[i] = (int) (Math.random() * (maxValue - minValue + 1)) + minValue; // Generate random integers within the range
        }
        Arrays.sort(data);
        return data;
    }

    public static boolean isEqual(int[] arrA, int[] arrB) {
        if (arrA == null && arrB == null) {
            return true;
        }
        if (arrA == null || arrB == null) {
            return false;
        }
        if (arrA.length != arrB.length) {
            return false;
        }
        for (int i = 0; i < arrA.length; i++) {
            if (arrA[i] != arrB[i]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        int testTime = 100000;
        int maxlen = 100;
        int minValue = 0;
        int maxValue = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arrA = generateRandomSortedData(maxlen, minValue, maxValue);
            int[] arrB = generateRandomSortedData(maxlen, minValue, maxValue);
            if (!isEqual(mergeSortedArray(arrA, arrB), test(arrA, arrB))) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end...");
    }
}
