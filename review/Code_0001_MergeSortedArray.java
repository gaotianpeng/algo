package review;

/*
    给定两个递增有序数组a和b，将所有元素归并到数组c中，并且要求c中的元素也是递增有序的
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Code_0001_MergeSortedArray {
    public static int[] mergeSortedArray(int[] arrA, int[] arrB) {
        if (arrA == null) {
            return arrB;
        }

        if (arrB == null) {
            return arrA;
        }

        ArrayList<Integer> ans = new ArrayList<>();
        int m = arrA.length;
        int n = arrB.length;
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (arrA[i] <= arrB[j]) {
                ans.add(arrA[i++]);
            } else {
                ans.add(arrB[j++]);
            }
        }

        while (i < m) {
            ans.add(arrA[i++]);
        }
        while (j < n) {
            ans.add(arrB[j++]);
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    /*
        for test
     */
    public static int[] test(int[] arrA, int[] arrB) {
        if (arrA == null) {
            return arrB.clone();
        }

        if (arrB == null) {
            return arrA.clone();
        }

        int[] copyA = arrA.clone();
        int[] copyB = arrB.clone();

        ArrayList<Integer> ans = new ArrayList<>();
        int m = copyA.length;
        int n = copyB.length;
        int i = 0;
        int j = 0;
        while (i < m && j < n) {
            if (copyA[i] <= copyB[j]) {
                ans.add(copyA[i++]);
            } else {
                ans.add(copyB[j++]);
            }
        }

        while (i < m) {
            ans.add(copyA[i++]);
        }
        while (j < n) {
            ans.add(copyB[j++]);
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] generateRandomSortedData(int maxlen) {
        Random random = new Random();
        int len = random.nextInt(maxlen) + 1; // 生成 1 到 maxlen 之间的随机长度
        int[] data = new int[len];
        for (int i = 0; i < len; i++) {
            data[i] = random.nextInt(); // 生成随机整数
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
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arrA = generateRandomSortedData(maxlen);
            int[] arrB = generateRandomSortedData(maxlen);
            if (!isEqual(mergeSortedArray(arrA, arrB), test(arrA, arrB))) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end...");
    }
}
