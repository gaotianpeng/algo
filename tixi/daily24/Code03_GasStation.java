package tixi.daily24;

import java.util.LinkedList;

public class Code03_GasStation {
    /*
        134: gas station，加油站
        在一条环路上有 n个加油站，其中第 i个加油站有汽油gas[i]升。
        你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1个加油站需要消耗汽油cost[i]升。你从其中的一个加油站出发，开始时油箱为空。
        给定两个整数数组 gas 和 cost ，如果你可以绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的
     */
    // 这个方法的时间复杂度O(N)，额外空间复杂度O(N)
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] goodStations = findGoodStations(gas, cost);
        for (int i = 0; i < gas.length; i++) {
            if (goodStations[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] findGoodStations(int[] gas, int[] cost) {
        int n = gas.length;
        int doubledLength = n * 2;
        int[] netGas = new int[doubledLength];
        
        // 计算净汽油数组
        for (int i = 0; i < n; i++) {
            netGas[i] = gas[i] - cost[i];
            netGas[i + n] = gas[i] - cost[i];
        }
        
        // 前缀和数组
        for (int i = 1; i < doubledLength; i++) {
            netGas[i] += netGas[i - 1];
        }
        
        LinkedList<Integer> minDeque = new LinkedList<>();
        
        // 初始化滑动窗口的最小值队列
        for (int i = 0; i < n; i++) {
            while (!minDeque.isEmpty() && netGas[minDeque.peekLast()] >= netGas[i]) {
                minDeque.pollLast();
            }
            minDeque.addLast(i);
        }
        
        boolean[] result = new boolean[n];
        
        // 判断每个起点是否可以走完一圈
        for (int offset = 0, start = 0, end = n; end < doubledLength; offset = netGas[start++], end++) {
            if (netGas[minDeque.peekFirst()] - offset >= 0) {
                result[start] = true;
            }
            if (minDeque.peekFirst() == start) {
                minDeque.pollFirst();
            }
            while (!minDeque.isEmpty() && netGas[minDeque.peekLast()] >= netGas[end]) {
                minDeque.pollLast();
            }
            minDeque.addLast(end);
        }
        
        return result;
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
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxLen = 100;
        int maxVal = 50;
        for (int i = 0; i < testTimes; ++i) {
            int n =  (int) ((maxLen + 1) * Math.random());
            int[] gas = generateRandomArray(n, maxVal);
            int[] cost = generateRandomArray(n, maxVal);
            int ans1 = canCompleteCircuit(gas, cost);
            int ans2 = test(gas, cost);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
