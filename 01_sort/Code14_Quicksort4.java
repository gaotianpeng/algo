package sort;

import java.util.Arrays;
import java.util.Stack;

public class Code14_Quicksort4 {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] =  temp;
    }

    public static int[] netherlandsFlag(int[] arr, int left, int right) {
        if (left > right) {
            return new int[] {-1, -1};
        }

        if (left == right) {
            return new int[] {left, right};
        }

        int less_area = left - 1;
        int more_area = right;
        int index = left;
        while (index < more_area) {
            if (arr[index] == arr[right]) {
                ++index;
            } else if (arr[index] < arr[right]) {
                swap(arr, ++less_area, index++);
            } else {
                swap(arr, index, --more_area);
            }
        }

        swap(arr, more_area, right);
        return new int[] {less_area + 1, more_area};
    }

    public static class Op {
        public int l;
        public int r;

        public Op(int left, int right) {
            l = left;
            r = right;
        }
    }

    public static void quickSort4(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int len = arr.length;
        swap(arr, (int)(Math.random() * len), len - 1);
        int[] equal_area = netherlandsFlag(arr, 0, len - 1);
        int equal_left = equal_area[0];
        int equal_right = equal_area[1];
        Stack<Op> stack = new Stack();
        stack.push(new Op(0, equal_left -1));
        stack.push(new Op(equal_right + 1, len - 1));
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.l < op.r) {
                swap(arr, op.l + (int)(Math.random()*(op.r - op.l)), op.r);
                equal_area = netherlandsFlag(arr, op.l, op.r);
                equal_left = equal_area[0];
                equal_right = equal_area[1];
                stack.push(new Op(op.l, equal_left - 1));
                stack.push(new Op(equal_right + 1, op.r));
            }
        }
    }

    /*
        for test
    */
    public static void test(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }

        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            ret[i] = arr[i];
        }

        return ret;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 10000000;
        int max_val = 50;
        int max_len = 30;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            int[] arr1 = generateRandomArray(max_len, max_val);
            int[] arr2 = copyArray(arr1);
            quickSort4(arr1);
            test(arr2);
            if (!isEqual(arr1, arr2)) {
                printArray(arr1);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
