package review;

import java.util.ArrayList;

/*
    给定两个递增有序数组a和b，每个数组中可能存在重复的元素，求a和b合并的结果，
    用递增有序数组c表示，同时要求数组c中的元素不重复。
    例如，
        a=[1，2，5，5，8]
        b=[2，2，4，5，8，10]
        则c=[1，2，4，5，8，10]
*/

import java.util.Arrays;
import java.util.TreeSet;

public class Code_0001_UniqueMergeSortedArray {
    public static int[] unqiueMergeSortedArray(int[] arrA, int[] arrB) {
        int m = arrA != null ? arrA.length : 0;
        int n = arrB != null ? arrB.length : 0;
        ArrayList<Integer> ans = new ArrayList<>();
        int i = 0;
        int j = 0;
        int index = 0;
        while (i < m && j < n) {
            while (i + 1 < m && arrA[i]== arrA[i+1]) {
                i++;
            }
            while (j + 1 < n && arrB[j] == arrB[j+1]) {
                j++;
            }
            if (arrA[i] < arrB[j]) {
                ans.add(arrA[i++]);
            } else if (arrA[i] == arrB[j]) {
                ans.add( arrA[i]);
                i++;
                j++;
            } else {
                ans.add(arrB[j++]);
            }
        }

        while (i < m) {
            while (i + 1 < m && arrA[i]== arrA[i+1]) {
                i++;
            }
            ans.add(arrA[i++]); 
        }
        while (j < n) {
            while (j + 1 < n && arrB[j] == arrB[j+1]) {
                j++;
            }
            ans.add(arrB[j++]);
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    /*
        for test
     */
    public static int[] test(int[] arrA, int[] arrB) {
        TreeSet<Integer> set = new TreeSet<>();
        int m = arrA != null ? arrA.length : 0;
        int n = arrB != null ? arrB.length : 0;
        for (int i = 0; i < m; i++) {
            set.add(arrA[i]);
        }
        for (int j = 0; j < n; j++) {
            set.add(arrB[j]);
        }

        int[] ans = new int[set.size()];
        int index = 0;
        for (Integer num : set) {
            ans[index++] = num;
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

    public static void simpleTest() {
        int[] arrA = {1, 2, 5, 5, 8};
        int[] arrB = {2, 2, 4, 5, 8, 10};
        int[] ans = unqiueMergeSortedArray(arrA, arrB);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
        
        System.out.println();
    }

    public static void main(String[] args) {
        // simpleTest();
        System.out.println("test start...");
        int testTime = 100000;
        int maxlen = 100;
        int minValue = 0;
        int maxValue = 1000;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arrA = generateRandomSortedData(maxlen, minValue, maxValue);
            int[] arrB = generateRandomSortedData(maxlen, minValue, maxValue);
            if (!isEqual(unqiueMergeSortedArray(arrA, arrB), test(arrA, arrB))) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end...");
    }
}
