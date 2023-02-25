package tixi.daily01;

import java.util.Arrays;

public class Code05_BSNearLeft {
    /*
        在一个有序数组中，找>=某个数最左侧的位置
     */
    public static int nearestIndex(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int ans = -1;
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] >= num) {
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
    public static int test(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] >= num) {
                return i;
            }
        }

        return -1;
    }

    public static int[] genRandomArray(int max_size, int max_value) {
        int[] arr = new int[(int) ((max_size + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((max_value + 1) * Math.random()) - (int) (max_value * Math.random());
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
        int test_time = 500000;
        int max_size = 10;
        int max_value = 100;
        boolean succeed = true;
        for (int i = 0; i < test_time; i++) {
            int[] arr = genRandomArray(max_size, max_value);
            Arrays.sort(arr);
            int value = (int) ((max_value + 1) * Math.random()) - (int) (max_value * Math.random());
            if (test(arr, value) != nearestIndex(arr, value)) {
                printArray(arr);
                System.out.println(value);
                System.out.println(test(arr, value));
                System.out.println(nearestIndex(arr, value));
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Success!" : "Failed");
        System.out.println("test end");
    }
}
