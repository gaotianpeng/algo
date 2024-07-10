package leetcode.all;

import java.util.LinkedList;

public class Code_1438_LongestSubarrayLessThanLimit {
    public int longestSubarray(int[] nums, int limit) {
        if (nums == null || nums.length == 0 || limit < 0) {
            return 0;
        }

        int ans = 0;
        int n = nums.length;

        for (int left = 0; left < n; ++left) {
            for (int right = left; right < n; ++right) {
                int max = nums[left];
                int min = nums[left];
                for (int i = left + 1; i <= right; ++i) {
                    max = Math.max(max, nums[i]);
                    min = Math.min(min, nums[i]);
                }
                if (max - min <= limit) {
                    ans = Math.max(ans, right - left + 1);
                }
            }
        }

        return ans;
    }

    public int longestSubarray1(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int n = arr.length;
        int count = 0;
        LinkedList<Integer> max_win = new LinkedList<>();
        LinkedList<Integer> min_win = new LinkedList<>();
        int right = 0;
        for (int left = 0; left < n; ++left) {
            while (right < n) {
                while (!max_win.isEmpty() && arr[max_win.peekLast()] <= arr[right]) {
                    max_win.pollLast();
                }
                max_win.addLast(right);
                while (!min_win.isEmpty() && arr[min_win.peekLast()] >= arr[right]) {
                    min_win.pollLast();
                }
                min_win.addLast(right);
                if (arr[max_win.peekFirst()] - arr[min_win.peekFirst()] > sum) {
                    break;
                } else {
                    right++;
                }
            }

            count = Math.max(count, right - left);
            if (max_win.peekFirst() == left) {
                max_win.pollFirst();
            }
            if (min_win.peekFirst() == left) {
                min_win.pollFirst();
            }
        }

        return count;
    }
}
