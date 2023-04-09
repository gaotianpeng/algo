package tixi.daily26;

public class Code04_NumberOfCow {
    /*
        第一年农场有1只成熟的母牛A，往后的每年：
        1）每一只成熟的母牛都会生一只母牛
        2）每一只新出生的母牛都在出生的第三年成熟
        3）每一只母牛永远不会死
        返回N年后牛的数量
     */
    public static int numberOfCow(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int ans = 3;
        int pre = 2;
        int prepre = 1;
        int tmp1 = 0;
        int tmp2 = 0;
        for (int i = 4; i <= n; ++i) {
            tmp1 = ans;
            tmp2 = pre;
            ans = ans + prepre;
            pre = tmp1;
            prepre = tmp2;
        }

        return ans;
    }

    public static int numberOfCow1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }
        int[][] base = {
                { 1, 1, 0 },
                { 0, 0, 1 },
                { 1, 0, 0 } };
        int[][] res = matrixPower(base, n - 3);
        return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
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
        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        return test(n-1) + test(n-3);
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
            if (numberOfCow(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
            if (numberOfCow1(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test end");
    }
}
