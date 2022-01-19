package sort;

import java.util.Arrays;

public class Code05_BSLeftest {
    /*
        在arr上，找满足>=value的最左位置
     */
    public static int leftestValue(int[] arr, int value) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int ans = -1;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + ((right-left) >> 1);
            if (arr[mid] >= value) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    /*
        for test
     */
    public static int test(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= value) {
                return i;
            }
        }
        return -1;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
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
        int testTime = 500000;
        int maxSize = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int value = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
            if (test(arr, value) != leftestValue(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(leftestValue(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Success!" : "Failed");
    }

}
