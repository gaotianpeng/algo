package leetcode;

import java.util.HashMap;

/*
    70 爬楼梯
        假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
        每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢
 */
public class Code_0070_ClimbingStairs {
    public static int climbStairs(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return 2 * res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }

        // res = 矩阵中的1
        int[][] tmp = m;// 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = muliMatrix(res, tmp);
            }
            tmp = muliMatrix(tmp, tmp);
        }
        return res;
    }

    public static int[][] muliMatrix(int[][] m1, int[][] m2) {
        int[][] res = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    res[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return res;
    }

    /*
        for test
     */
    public static int test(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        return test(n - 1) + test(n - 2);
    }

    public static int test1(int n) {
        HashMap<Integer, Integer> dp = new HashMap<>();
        dp.put(0, 0);
        dp.put(1, 1);
        dp.put(2, 2);
        return process(n, dp);
    }

    public static int process(int n, HashMap<Integer, Integer> dp) {
        if (dp.containsKey(n)) {
            return dp.get(n);
        }

        int ans = process(n-1, dp) + process(n - 2, dp);
        dp.put(n, ans);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start....");
        boolean success = true;
        int test_times = 1000;
        int max = 40;
        for (int i = 0; i < test_times; i++) {
            int n = (int)(Math.random() * max);
            if (climbStairs(n) != test(n)) {
                success = false;
                break;
            }

            if (climbStairs(n) != test1(n)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
