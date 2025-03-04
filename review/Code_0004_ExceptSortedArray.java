package review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
    给定两个递增有序数组a和b
    表示两个集合，每个集合中的元素不重复，求a-b的结果（差集），用递增有序数组c表示，同时要求数组c中的元素不重复。
    例如，a=[1，2，5，8]，b=[1，3，4，5，8，10]，则c=[2]。
 */
public class Code_0004_ExceptSortedArray {
    public static int[] exceptSortedArray(int[] arrA, int[] arrB) {
        if (arrA == null || arrB == null) {
            return null;
        }

        int i = 0;
        int j = 0;
        int m = arrA.length;
        int n = arrB.length;
        ArrayList<Integer> ans = new ArrayList<>();
        while (i < m && j < n) {
            if (arrA[i] < arrB[j]) {
                ans.add(arrA[i]);
                ++i;
            } else if (arrA[i] > arrB[j]) {
                ++j;
            } else {
                ++i;
                ++j;
            }
        }

        while (i < m) {
            ans.add(arrA[i++]);
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

        int m = arrA.length;
        int n = arrB.length;

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < n; ++i) {
            set.add(arrB[i]);
        }

        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            if (!set.contains(arrA[i])) {
                ans.add(arrA[i]);
            }
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[] generateRandomSortedData(int maxlen, int minValue, int maxValue) {
        int len = (int) (Math.random() * maxlen) + 1; // Generate a random length between 1 and maxlen
        int[] data = new int[len];
        HashSet<Integer> uniqueElements = new HashSet<>();

        while (uniqueElements.size() < len) {
            uniqueElements.add((int) (Math.random() * (maxValue - minValue + 1)) + minValue); // Generate unique random integers within the range
        }

        int index = 0;
        for (int num : uniqueElements) {
            data[index++] = num;
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
        int[] arrA = {1, 2, 5, 8};
        int[] arrB = {1, 3, 4, 5, 8, 10};
        int[] ans = exceptSortedArray(arrA, arrB);
        System.out.println(isEqual(ans, new int[]{2}));
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
            int[] ans2 = exceptSortedArray(arrA, arrB);
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
