package tixi.daily01;

import java.util.Arrays;

public class Code06_BSNearRight {
    /* 
        在一个有序数组中，找<=某个数最右侧的位置
    */
    public static int rightestValue(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int ans = -1;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] <= num) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

    /*
        for test
     */
    public static int test(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        for (int i = arr.length - 1; i >= 0; --i) {
            if (arr[i] <= num) {
                return i;
            }
        }

        return -1;
    }

    public static int[] genRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
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

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 5;
        int maxSize = 20;
        int maxValue = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = genRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != rightestValue(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(rightestValue(arr, value));
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
