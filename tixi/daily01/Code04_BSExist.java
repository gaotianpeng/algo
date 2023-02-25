package tixi.daily01;

import java.util.Arrays;

public class Code04_BSExist {
    /*
        在一个有序数组中，找某个数是否存在
     */
    public static boolean exist(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return false;
        }

        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] > num) {
                right = mid - 1;
            } else if (arr[mid] == num) {
                return true;
            } else {
                left = mid + 1;
            }
        }

        return arr[left] == num;
    }

    /*
        for test
     */
    public static boolean test(int[] arr, int num) {
        for(int cur : arr) {
            if(cur == num) {
                return true;
            }
        }
        return false;
    }


    public static int[] generateRandomArray(int max_size, int max_value) {
        int[] arr = new int[(int) ((max_size + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((max_value + 1) * Math.random()) - (int) (max_value * Math.random());
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_time = 500000;
        int max_size = 10;
        int max_value = 100;
        boolean succeed = true;
        for (int i = 0; i < test_time; i++) {
            int[] arr = generateRandomArray(max_size, max_value);
            Arrays.sort(arr);
            int value = (int) ((max_value + 1) * Math.random()) - (int) (max_value * Math.random());
            if (test(arr, value) != exist(arr, value)) {
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Success!" : "Failed");
        System.out.println("test end");
    }
}
