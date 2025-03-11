package review;
/*
    189. Rotate Array

    Given an integer array nums, rotate the array to the right by k steps, 
    where k is non-negative.
    Example 1:
        Input: nums = [1,2,3,4,5,6,7], k = 3
        Output: [5,6,7,1,2,3,4]
        Explanation:
        rotate 1 steps to the right: [7,1,2,3,4,5,6]
        rotate 2 steps to the right: [6,7,1,2,3,4,5]
        rotate 3 steps to the right: [5,6,7,1,2,3,4]

    Example 2:
        Input: nums = [-1,-100,3,99], k = 2
        Output: [3,99,-1,-100]
        Explanation: 
        rotate 1 steps to the right: [99,-1,-100,3]
        rotate 2 steps to the right: [3,99,-1,-100]
        

        Constraints:

        1 <= nums.length <= 105
        -231 <= nums[i] <= 231 - 1
        0 <= k <= 105
 */
public class Code_0009_LC189_RorateArray {
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k = k % n;
        int count = gcd(k, n);
        for (int start = 0; start < count; ++start) {
            int current = start;
            int prev = nums[start];
            do {
                int next = (current + k) % n;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                current = next;
            } while (start != current);
        }
    }

    public static int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }

    /*
     * for test
     */
    public static void test(int[] nums, int k) {
        int n = nums.length; 
        k = k % n;
        int[] temp = new int[2*n];
        for (int i = 0; i < nums.length; i++) {
            temp[i]  = nums[i];
            temp[i+n]  = nums[i];
        }

        for (int i = 0; i < nums.length; ++i) {
            nums[i]  = temp[i + n - k];
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int size = (int)((maxSize + 1) * Math.random());
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ret[i] = arr[i];
        }
        return ret;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
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
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i != arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (arr.length == 0) {
                continue;
            }
            int k = (int)(Math.random() * arr.length);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            test(arr1, k);
            rotate(arr2, k);
            if (!isEqual(arr1, arr2)) {
                success = false;
                System.out.print("Original array: ");
                printArray(arr);
                System.out.print("Expected: ");
                printArray(arr1);
                System.out.print("Output: ");
                printArray(arr2);
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}