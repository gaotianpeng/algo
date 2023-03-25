package tixi.daily24;

import java.util.LinkedList;

public class Code02_AllLessNumSubArray {
    /*
        给定一个整型数组arr，和一个整数sum
        某个arr中的子数组sub，如果想达标，必须满足：
        sub中最大值 – sub中最小值 <= sum，
        返回arr中达标子数组的数量

        leetcode 相关题目
        1438. 绝对差不超过限制的最长连续子数组
        https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
     */
    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int ans = 0;
        int right = 0;
        int n = arr.length;
        LinkedList<Integer> qmax = new LinkedList<>();
        LinkedList<Integer> qmin = new LinkedList<>();
        for (int left = 0; left < n; ++left) {
            while (right < n) {
                while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[right]) {
                    qmax.pollLast();
                }
                qmax.addLast(right);

                while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[right]) {
                    qmin.pollLast();
                }
                qmin.addLast(right);

                if (arr[qmax.peekFirst()] - arr[qmin.peekFirst()] > sum) {
                    break;
                } else {
                    ++right;
                }
            }

            ans += right - left;
            if (qmax.peekFirst() == left) {
                qmax.pollFirst();
            }
            if (qmin.peekFirst() == left) {
                qmin.pollFirst();
            }
        }

        return ans;
    }

    public static int test(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int ans = 0;
        int n = arr.length;

        for (int left = 0; left < n; ++left) {
            for (int right = left; right < n; ++right) {
                int max = arr[left];
                int min = arr[left];
                for (int i = left + 1; i <= right; ++i) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }
                if (max - min <= sum) {
                    ++ans;
                }
            }
        }

        return ans;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {
        System.out.println("test starting...");
        int test_times = 100000;
        int max_num = 100;
        int max_val = 50;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = generateRandomArray(max_num, max_val);
            int sum = (int) (Math.random() * (max_val + 1));
            int ans1 = num(arr, sum);
            int ans2 = test(arr, sum);
            if (ans1 != ans2) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
