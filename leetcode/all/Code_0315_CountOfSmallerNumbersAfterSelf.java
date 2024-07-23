package leetcode.all;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    315. Count of Smaller Numbers After Self

    Given an integer array nums, return an integer array counts where counts[i] is
    the number of smaller elements to the right of nums[i].

    Example 1:
        Input: nums = [5,2,6,1]
        Output: [2,1,1,0]
    Explanation:
        To the right of 5 there are 2 smaller elements (2 and 1).
        To the right of 2 there is only 1 smaller element (1).
        To the right of 6 there is 1 smaller element (1).
        To the right of 1 there is 0 smaller element.

    Example 2:
        Input: nums = [-1]
        Output: [0]

    Example 3:
        Input: nums = [-1,-1]
        Output: [0,0]

    Constraints:

        1 <= nums.length <= 105
        -104 <= nums[i] <= 104
 */
public class Code_0315_CountOfSmallerNumbersAfterSelf {
    public static List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int N = nums.length;
        List<Integer> result = new ArrayList<>(Collections.nCopies(N, 0));
        int[] indices = new int[N];
        int[] counts = new int[N];

        for (int i = 0; i < N; i++) {
            indices[i] = i;
        }

        int mergeSize = 1;

        while (mergeSize < N) {
            int left = 0;
            while (left < N) {
                if (mergeSize > N - left) {
                    break;
                }

                int mid = left + mergeSize - 1;
                int right = Math.min(left + 2 * mergeSize - 1, N - 1);
                merge(nums, indices, counts, left, mid, right);
                left = right + 1;
            }

            if (mergeSize > N / 2) {
                break;
            }

            mergeSize <<= 1;
        }

        for (int i = 0; i < N; i++) {
            result.set(i, counts[i]);
        }

        return result;
    }

    private static void merge(int[] nums, int[] indices, int[] counts, int left, int mid, int right) {
        int[] tempIndices = new int[right - left + 1];
        int leftIndex = left;
        int rightIndex = mid + 1;
        int tempIndex = 0;
        int rightCount = 0;

        while (leftIndex <= mid && rightIndex <= right) {
            if (nums[indices[rightIndex]] < nums[indices[leftIndex]]) {
                tempIndices[tempIndex++] = indices[rightIndex];
                rightCount++;
                rightIndex++;
            } else {
                tempIndices[tempIndex++] = indices[leftIndex];
                counts[indices[leftIndex]] += rightCount;
                leftIndex++;
            }
        }

        while (leftIndex <= mid) {
            tempIndices[tempIndex++] = indices[leftIndex];
            counts[indices[leftIndex]] += rightCount;
            leftIndex++;
        }

        while (rightIndex <= right) {
            tempIndices[tempIndex++] = indices[rightIndex];
            rightIndex++;
        }

        for (int i = 0; i < tempIndices.length; i++) {
            indices[left + i] = tempIndices[i];
        }
    }

    public static List<Integer> test(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        List<Integer> ans = new ArrayList<>();
        int N = nums.length;
        for (int i = 0; i < N; ++i) {
            int cnt = 0;
            for (int j = i+1; j < N; j++) {
                if (nums[j] < nums[i]) {
                    ++cnt;
                }
            }
            ans.add(cnt);
        }

        return ans;
    }

    public static boolean isEqual(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }

        if (list1 == null || list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        for (int i = 0; i < list1.size(); ++i) {
            if (list1.get(i) != list2.get(i)) {
                return false;
            }
        }

        return true;
    }

    public static int[] generateRandomArray(int maxVal, int maxLen) {
        int len = (int)(Math.random()*(maxLen + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = generateRandomNum(maxVal);
        }

        return ans;
    }

    public static int generateRandomNum(int maxVal) {
        return (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*maxVal);
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }

        return ans;
    }

    public static void print(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxVal = 104;
        int maxLen = 105;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr1 = generateRandomArray(maxVal, maxLen);
            int[] arr2 = copyArray(arr1);
            List<Integer> list1 = countSmaller(arr1);
            List<Integer> list2 = test(arr2);
            if (!isEqual(list1, list2)) {
                success = false;
                print(list1);
                print(list2);
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
