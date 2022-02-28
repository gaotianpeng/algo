package tixi.daily05;

import java.util.Arrays;
import java.util.Stack;

public class Code08_QuickSort5 {

    public static class Op {
        public int left;
        public int right;

        public Op(int l, int r) {
            left = l;
            right = r;
        }
    }

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        swap(arr, arr.length - 1, (int)(Math.random() * arr.length));
        int[] equal_area = netherlandsFlag(arr, 0, arr.length - 1);
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, equal_area[0] - 1));
        stack.push(new Op(equal_area[1] + 1, arr.length - 1));

        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.left < op.right) {
                swap(arr, op.right, op.left + (int)(Math.random() * (op.right - op.left + 1)));
                int[] area = netherlandsFlag(arr, op.left, op.right);
                stack.push(new Op(op.left, area[0] - 1));
                stack.push(new Op(area[1] + 1, op.right));
            }
        }
    }

    private static int[] netherlandsFlag(int[] arr, int left, int right) {
        if (left > right) {
            return new int[] { -1, -1};
        }

        if (left == right) {
            return new int[] { left, left};
        }

        int index = left;
        int less = left - 1;
        int more = right;
        while (index < more) {
            if (arr[index] == arr[right]) {
                index++;
            } else if (arr[index] < arr[right]) {
                ++less;
                swap(arr, less, index);
                ++index;
            } else {
                swap(arr, index, --more);
            }
        }

        swap(arr, right, more);
        return new int[] {less+1, more};
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] =  temp;
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
        int test_times = 100000;
        int max_val = 50;
        int max_len = 30;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            int[] arr1 = generateRandomArray(max_len, max_val);
            int[] arr2 = copyArray(arr1);
            quickSort(arr1);
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
