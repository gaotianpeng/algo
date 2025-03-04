package review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
    给定两个递增有序数组a和b表示两个集合，每个集合中的元素可重复，求a和b的交集，用递增有序数组c表示，同时要求数组c中的元素不重复。
    例如，
        a = [1，2，5, 5，8]
        b = [1，3，4，5，8，8, 10]
        则 c = [1，5，8]
 */

public class Code_0003_UnionSortedArray2 {
    public static int[] unionSortedArray(int[] arrA, int[] arrB) {
        if (arrA == null || arrB == null) {
            return null;
        }

        int i = 0;
        int j = 0;
        int m = arrA.length;
        int n = arrB.length;
        ArrayList<Integer> ans = new ArrayList<>();
        while (i < m && j < n) {
            while (i + 1 < m && arrA[i] == arrA[i+1]) {
                ++i;
            }
            while (j + 1 < n && arrB[j] == arrB[j+1]) {
                ++j;
            }
            if (arrA[i] < arrB[j]) {
                ++i;
            } else if (arrA[i] > arrB[j]) {
                ++j;
            } else {
                ans.add(arrA[i]);
                ++i;
                ++j;
            }
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    /*
        for test
     */
    public static int[] test(int[] arrA, int[] arrB) {
        if (arrA == null || arrB == null) {
            return null;
        }

        int[] copyA = arrA.clone();
        int[] copyB = arrB.clone();

        int m = copyA.length;
        int n = copyB.length;
        ArrayList<Integer> repeatAns = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (arrA[i] == arrB[j]) {
                    repeatAns.add(arrA[i]);
                }
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < repeatAns.size(); i++) {
            if (!set.contains(repeatAns.get(i))) {
                set.add(repeatAns.get(i));
                ans.add(repeatAns.get(i));
            }
        }


        return ans.stream().mapToInt(Integer::intValue).toArray();
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

    public static void simpleTest()  {
        int[] arrA = {1, 2, 5, 5, 8};
        int[] arrB = {1, 3, 4, 5, 8, 8, 10};
        int[] ans = unionSortedArray(arrA, arrB);

        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        // simpleTest();
        int testTimes = 100000;
        int maxValue = 1000;
        int minValue = 0;
        boolean succeed = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arrA = generateRandomSortedData(100, minValue, maxValue);
            int[] arrB = generateRandomSortedData(100, minValue, maxValue);
            int[] ans = test(arrA, arrB);
            int[] ans2 = unionSortedArray(arrA, arrB);
            if (!isEqual(ans, ans2)) {
                succeed = false;
                System.out.println("test failed");
                break;
            }
        }

        System.out.println(succeed ? "test succeed" : "test failed");
        System.out.println("test end");
    }
}
