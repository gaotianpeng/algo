package shuati.daily02;
import java.util.Arrays;

public class Code06_MinLengthForSort {
    /*
        https://leetcode.cn/problems/shortest-unsorted-continuous-subarray/
        leetcode 581
            给定一个数组arr，只能对arr中的一个子数组排序，
            但是想让arr整体都有序
            返回满足这一设定的子数组中，最短的是多长
     */
    public static int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int N = nums.length;
        int right = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            if (max > nums[i]) {
                right = i;
            }
            max = Math.max(max, nums[i]);
        }
        int min = Integer.MAX_VALUE;
        int left = N;
        for (int i = N - 1; i >= 0; i--) {
            if (min < nums[i]) {
                left = i;
            }
            min = Math.min(min, nums[i]);
        }
        return Math.max(0, right - left + 1);
    }

    public static int test(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        if (isSorted(nums)) {
            return 0;
        }


        int n = nums.length;
        int[] copy = copyArray(nums);
        Arrays.sort(copy);
        int left = 0;
        int right = n - 1;
        while (copy[left] == nums[left]) {
            left++;
        }

        while (copy[right] == nums[right]) {
            right--;
        }

        return right - left + 1;
    }

    public static boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }

    public static int[] copyArray(int[] nums) {
        if (nums == null) {
            return null;
        }

        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = nums[i];
        }

        return ans;
    }

    public static int[] genRandomArray(int max_n, int max_val) {
        int n = (int)(Math.random() * max_n);
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = (int)(Math.random() * max_val);
        }
        return ans;
    }
    public static void print(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 20;
        int max_n = 10;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = genRandomArray(max_n,max_val);
            if (test(arr) != findUnsortedSubarray(arr)) {
                System.out.println(test(arr));
                System.out.println(findUnsortedSubarray(arr));
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
