package leetcode.all;

import java.util.Stack;

public class Code_1856_MaximumSubarrayMinProduct {
    public int maxSumMinProduct1(int[] arr) {
        int size = arr.length;
        long max = Long.MIN_VALUE;
        long[] sum = new long[size];
        sum[0] = arr[0];
        for (int i = 1; i < size; ++i) {
            sum[i] = sum[i-1] + arr[i];
        }

        int[] stack = new int[size];
        int stack_size = 0;
        for (int i = 0; i < size; ++i) {
            while (stack_size != 0 && arr[stack[stack_size - 1]] >= arr[i]) {
                int j = stack[--stack_size];
                long val = (stack_size == 0 ? sum[i-1] : sum[i-1] - sum[stack[stack_size - 1]]) * arr[j];
                max = Math.max(max, val);
            }

            stack[stack_size++] = i;
        }

        while (stack_size > 0) {
            int j = stack[--stack_size];
            long val = (stack_size == 0 ? sum[size - 1] : sum[size - 1] - sum[stack[stack_size-1]]) * arr[j];
            max = Math.max(max, val);
        }

        return (int)(max % 1000000007);
    }

    public int maxSumMinProduct(int[] arr) {
        int size = arr.length;
        long max = Long.MIN_VALUE;
        long[] sum = new long[size];
        sum[0] = arr[0];
        for (int i = 1; i < size; ++i) {
            sum[i] = sum[i-1] + arr[i];
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; ++i) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int j = stack.pop();
                long val = (stack.isEmpty() ? sum[i-1] : sum[i-1] - sum[stack.peek()]) * arr[j];
                max = Math.max(max, val);
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            long val = (stack.isEmpty() ? sum[size - 1] : sum[size - 1] - sum[stack.peek()]) * arr[j];
            max = Math.max(max, val);
        }

        return (int)(max % 1000000007);
    }


}
