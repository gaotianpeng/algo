package tixi.daily25;

import java.util.Stack;

public class MaxmimumSubArrayMinProducct {
    /*
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
        int size = arr.length;
        int[] sums = new int[size];
        sums[0] = arr[0];
        for (int i = 1; i < size; i++) {
            sums[i] = sums[i - 1] + arr[i];
        }
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                max = Math.max(max, (stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()])) * arr[j]);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int j = stack.pop();
            max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
        }
        return max;
    }

    public static int test(int[] nums) {
        int n = nums.length;
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                int min_num = Integer.MAX_VALUE;
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += nums[k];
                    min_num = Math.min(min_num, nums[i]);
                }
                max = Math.max(max, min_num * sum);
            }
        }

        return 0;
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
                break;
            }
        }

        System.out.println("test end");
    }
}
