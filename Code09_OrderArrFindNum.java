package AlgoNew;

import java.util.Arrays;

public class Code09_OrderArrFindNum {
    static boolean find(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return false;
        }

        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int mid = (L + R)/2;
            if (arr[mid] == num) {
                return true;
            } else if (arr[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }

        return false;
    }

    static boolean test(int[] arr, int num) {
        for (int cur: arr) {
            if (cur == num) {
                return true;
            }
        }

        return false;
    }

    static int[] genRandomArray(int maxSize, int maxVal) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)((maxVal + 1) * Math.random()) - (int)(maxVal * Math.random());
        }

        return arr;
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 30;
        int maxValue = 40;
        boolean succeed = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = genRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
            if (test(arr, value) != find(arr, value)) {
                System.out.println("Has error!");
                succeed = false;
                break;
            }
        }

        System.out.println(succeed ? "Success!" : "Failed!");
    }
}
