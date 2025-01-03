package tixi.daily04;

import java.util.Arrays;

public class Code01_MergeSort {
    public static void mergeSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        process(nums, 0, nums.length - 1);   
    }

    private static void process(int[] nums, int left, int right) {
        if (left == right) {
            return;
        }

        int mid = left + ((right - left) >> 1);
        process(nums, left, mid);
        process(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private static void merge(int[] nums, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int index = 0;
        int leftIndex = left;
        int rightIndex = mid + 1;

        while (leftIndex <= mid && rightIndex <= right) {
            helper[index++] = 
                nums[leftIndex] <= nums[rightIndex] ? nums[leftIndex++] : nums[rightIndex++];
        }

        while (leftIndex <= mid) {
            helper[index++] = nums[leftIndex++];
        }

        while (rightIndex <= right) {
            helper[index++] = nums[rightIndex++];
        }

        for (int i = 0; i < helper.length; ++i) {
            nums[left + i] = helper[i];
        }
    }


    /*
        for test
    */
    public static void test(int[] nums) {
        Arrays.sort(nums);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] nums = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }

        return nums;
    }

    public static int[] copyArray(int[] nums) {
        if (nums == null) {
            return null;
        }

        int[] ret = new int[nums.length];
        for (int i = 0; i < nums.length; ++i) {
            ret[i] = nums[i];
        }

        return ret;
    }

    public static boolean isEqual(int[] nums1, int[] nums2) {
        if ((nums1 == null && nums2 != null) || (nums1 != null && nums2 == null)) {
            return false;
        }

        if (nums1 == null && nums2 == null) {
            return true;
        }

        if (nums1.length != nums2.length) {
            return false;
        }

        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] != nums2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void printArray(int[] nums) {
        if (nums == null) {
            return;
        }

        for (int i = 0; i < nums.length; ++i) {
            System.out.print(nums[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 1000000;
        int maxVal = 40;
        int maxLen = 50;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] nums1 = generateRandomArray(maxLen, maxVal);
            int[] nums2 = copyArray(nums1);
            mergeSort(nums1);
            test(nums2);
            if (!isEqual(nums1, nums2)) {
                success = false;
                printArray(nums1);
                printArray(nums2);
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
