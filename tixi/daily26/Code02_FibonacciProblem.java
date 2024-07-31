package tixi.daily26;

import java.util.HashMap;

public class Code02_FibonacciProblem {
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib(n-1) + fib(n-2);
    }

    public static int fib1(int n) {
        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        HashMap<Integer, Integer> dp = new HashMap<>();
        dp.put(0, 0);
        dp.put(1, 1);
        return process(n, dp);
    }

    public static int process(int n, HashMap<Integer, Integer> dp) {
        if (dp.containsKey(n)) {
            return dp.get(n);
        }

        int ans1 = process(n-1, dp);
        int ans2 = process(n-2, dp);
        dp.put(n, ans1 + ans2);
        return ans1 + ans2;
    }

    public static int fib2(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return 1;
        }

        int ans = 1;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; ++i) {
            tmp = ans;
            ans = pre + ans;
            pre = tmp;
        }

        return ans;
    }

    // O(logN)
    public static int fib3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        // [ 1 ,1 ]
        // [ 1, 0 ]
        int[][] base = {
                { 1, 1 },
                { 1, 0 }
        };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int[][] matrixPower(int[][] m, int p) {
        int[][] res = new int[m.length][m[0].length];
        for (int i = 0; i < res.length; i++) {
            res[i][i] = 1;
        }
        // res = 矩阵中的1
        int[][] t = m;// 矩阵1次方
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = product(res, t);
            }
            t = product(t, t);
        }
        return res;
    }

    // 两个矩阵乘完之后的结果返回
    public static int[][] product(int[][] a, int[][] b) {
        int n = a.length;
        int m = b[0].length;
        int k = a[0].length; // a的列数同时也是b的行数
        int[][] ans = new int[n][m];
        for(int i = 0 ; i < n; i++) {
            for(int j = 0 ; j < m;j++) {
                for(int c = 0; c < k; c++) {
                    ans[i][j] += a[i][c] * b[c][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; ++i) {
            int n = (int)(Math.random()*1000);
            if (fib1(n) != fib2(n)) {
                success = false;
                break;
            }

            if (fib1(n) != fib3(n)) {
                success = false;
                break;
            }

        }

        System.out.println(success ? "success":"failed");
        System.out.println("test end");
    }
}
