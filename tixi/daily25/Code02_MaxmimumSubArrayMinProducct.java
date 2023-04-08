package tixi.daily25;

import java.util.Stack;

public class Code02_MaxmimumSubArrayMinProducct {
    /*
        https://leetcode.cn/problems/maximum-subarray-min-product/
        leetcode 1856 子数组最小乘积的最大值
        一个数组的 最小乘积定义为这个数组中 最小值乘以数组的 和。
        比方说，数组[3,2,5]（最小值是2）的最小乘积为2 * (3+2+5) = 2 * 10 = 20。
        给你一个正整数数组nums，请你返回nums任意非空子数组的最小乘积的最大值。由于答案可能很大，请你返回答案对10^9 + 7取余的结果。
        请注意，最小乘积的最大值考虑的是取余操作 之前的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数保存。
        子数组定义为一个数组的 连续部分
     */
    /*
        给定一个只包含正数的数组arr，arr中任何一个子数组sub，
        一定都可以算出(sub累加和 )* (sub中的最小值)是什么，
        那么所有子数组中，这个值最大是多少
     */
    public static int maxSumMinProduct(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int ans = Integer.MIN_VALUE;

        int[] pre_sum = new int[n];
        pre_sum[0] = arr[0];
        for (int i = 1; i < n; ++i) {
            pre_sum[i] = pre_sum[i-1] + arr[i];
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int cur = stack.pop();
                ans = Math.max(ans,
                        arr[cur] * (stack.isEmpty() ? pre_sum[i-1]: pre_sum[i-1] - pre_sum[stack.peek()]));
            }
            stack.add(i);
        }
        while (!stack.isEmpty()) {
            int cur = stack.pop();
            ans = Math.max(ans,
                    arr[cur] * (stack.isEmpty() ? pre_sum[n-1]: pre_sum[n-1] - pre_sum[stack.peek()]));
        }

        return ans;
    }

    public static int test(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int ans = Integer.MIN_VALUE;
        int[] pre_sum = new int[n];
        pre_sum[0] = arr[0];
        for (int i = 1; i < n; ++i) {
            pre_sum[i] = pre_sum[i-1] + arr[i];
        }

        for (int cur = 0; cur < n; ++cur) {
            int cur_left_limit = cur;
            int cur_right_limit = cur;
            for (int j = cur; j >= 0; j--) {
                if (arr[j] < arr[cur]) {
                    break;
                }
                cur_left_limit = j;
            }

            for (int j = cur; j < n; j++) {
                if (arr[j] < arr[cur]) {
                    break;
                }
                cur_right_limit = j;
            }

            ans = Math.max((pre_sum[cur_right_limit] - pre_sum[cur_left_limit] + arr[cur_left_limit]) * arr[cur], ans);
        }

        return ans;
    }

    public static int test1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int ans = Integer.MIN_VALUE;
        int n = arr.length;

        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; j++) {
                int sum = 0;
                int min = Integer.MAX_VALUE;
                for(int k = i; k <= j; ++k) {
                    min = Math.min(min, arr[k]);
                    sum += arr[k];
                }
                ans = Math.max(ans, sum * min);
            }
        }

        return ans;
    }

    public static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = gerenareRondomArray();
            if (test(arr) != maxSumMinProduct(arr)) {
                System.out.println("test failed");
                System.out.println(test(arr));
                System.out.println(maxSumMinProduct(arr));
                break;
            }
            if (test1(arr) != maxSumMinProduct(arr)) {
                System.out.println("test failed");
                System.out.println(test(arr));
                System.out.println(maxSumMinProduct(arr));
                break;
            }
        }


        System.out.println("test end");
    }
}
