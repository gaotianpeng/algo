package tixi.daily26;

public class Code05_ZeroLeftOneStringNumber {
    /*
    给定一个数N，想象只由0和1两种字符，组成的所有长度为N的字符串
    如果某个字符串,任何0字符的左边都有1紧挨着,认为这个字符串达标
    返回有多少达标的字符串
     */


    public static int getNum(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int pre = 1;
        int cur = 1;
        int tmp = 0;
        for (int i = 2; i < n + 1; i++) {
            tmp = cur;
            cur += pre;
            pre = tmp;
        }
        return cur;
    }

    public static int getNum1(int n) {
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
        int[][] tmp = m;
        for (; p != 0; p >>= 1) {
            if ((p & 1) != 0) {
                res = product(res, tmp);
            }
            tmp = product(tmp, tmp);
        }
        return res;
    }

    public static int test(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }
        return process(i + 1, n) + process(i + 2, n);
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

    public static int randomNumber(int n) {
        return (int)(Math.random() * n) + 1;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 20;
        for (int i = 0; i < test_times; ++i) {
            int n = randomNumber(max_val);
            if (getNum(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
            if (getNum1(n) != test(n)) {
                System.out.println("test failed1");
                break;
            }
        }
        System.out.println("test end");
    }
}
