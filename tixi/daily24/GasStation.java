package tixi.daily24;

import java.util.LinkedList;

public class GasStation {
    /*
        134: gas station，加油站
        在一条环路上有 n个加油站，其中第 i个加油站有汽油gas[i]升。
        你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1个加油站需要消耗汽油cost[i]升。你从其中的一个加油站出发，开始时油箱为空。
        给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] good = goodArray(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] goodArray(int[] g, int[] c) {
        int N = g.length;
        int M = N << 1;
        int[] arr = new int[M];
        for (int i = 0; i < N; i++) {
            arr[i] = g[i] - c[i];
            arr[i + N] = g[i] - c[i];
        }
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }
        LinkedList<Integer> w = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
                w.pollLast();
            }
            w.addLast(i);
        }
        boolean[] ans = new boolean[N];
        for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
            if (arr[w.peekFirst()] - offset >= 0) {
                ans[i] = true;
            }
            if (w.peekFirst() == i) {
                w.pollFirst();
            }
            while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
                w.pollLast();
            }
            w.addLast(j);
        }
        return ans;
    }
    public static int test(int[] gas, int[] cost) {
        // 无法过滤参数
        int n = gas.length;
        int[] left = new int [n];
        for (int i = 0; i < n; ++i) {
            left[i] = gas[i] - cost[i];
        }
        int[] help = new int[2*n];
        for (int i = 0; i < n; ++i) {
            help[i] = left[i];
        }
        for (int i = n; i < help.length; ++i) {
            help[i] = left[i - n];
        }

        for (int i = 0; i < n; ++i) {
            if (help[i] < 0) {
                continue;
            }

            int sum = 0;
            for (int j = i; j < i + n; j++) {
                sum += help[j];
                if (sum < 0) {
                    break;
                }
            }
            if (sum >= 0) {
                return i;
            }
        }

        return -1;
    }

    public static int[] generateRandomArray(int n, int maxValue) {
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test starting...");
        int test_times = 100000;
        int max_num = 100;
        int max_val = 50;
        for (int i = 0; i < test_times; ++i) {
            int n =  (int) ((max_num + 1) * Math.random());
            int[] gas = generateRandomArray(n, max_val);
            int[] cost = generateRandomArray(n, max_val);
            int sum = (int) (Math.random() * (max_val + 1));
            int ans1 = canCompleteCircuit(gas, cost);
            int ans2 = test(gas, cost);
            if (ans1 != ans2) {
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test end");
    }
}
