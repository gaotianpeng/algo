package tixi.daily26;

public class Code06_NumberOfTiling {
    /*
        用1*2的瓷砖，把N*2的区域填满, 返回铺瓷砖的方法数
     */
    public static int getNumberOfTiling(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }


        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; ++i) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }

        return res;
    }

    public static int getNumberOfTiling1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
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


    public static int test(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        return test(n-1) + test(n-2);
    }

    public static int randomNumber(int n) {
        return (int)(Math.random() * n) + 1;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 20;
        for (int i = 0; i < test_times; ++i) {
            int n = randomNumber(max_val);
            if (getNumberOfTiling(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
            if (getNumberOfTiling1(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
