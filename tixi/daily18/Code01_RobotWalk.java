package tixi.daily18;
/*
    假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
    开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
    如果机器人来到1位置，那么下一步只能往右来到2位置；
    如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
    如果机器人来到中间位置，那么下一步可以往左走或者往右走；
    规定机器人必须走 K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
    给定四个参数 N、M、K、P，返回方法数
 */

public class Code01_RobotWalk {
    public static int ways1(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }

        return process(start, k, aim , n);
    }

    public static int process(int cur, int rest, int aim, int n) {
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }

        if (cur == 1) {
            return process(cur + 1, rest - 1, aim, n);
        }

        if (cur == n) {
            return process( n - 1, rest - 1, aim , n);
        }

        return process(cur - 1, rest - 1, aim, n)
                + process(cur + 1, rest - 1, aim, n);
    }

    public static int ways2(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }

        int[][] dp = new int [n+1][k+1];
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= k; ++j) {
                dp[i][j] = -1;
            }
        }

        return process(start, k, aim, n, dp);
    }

    private static int process(int cur, int rest, int aim , int n, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }

        int ans = 0;
        if (rest == 0) {
            ans = aim == cur ? 1 : 0;
        } else if (cur == n) {
            ans = process(n-1, rest - 1, aim, n, dp);
        } else if (cur == 1) {
            ans = process(2, rest - 1, aim , n, dp);
        } else {
            ans = process(cur - 1, rest - 1, aim , n, dp) + process(cur + 1, rest - 1, aim, n, dp);
        }

        dp[cur][rest] = ans;
        return ans;
    }


    public static int ways3(int n, int start, int aim, int k) {
        if (n < 2 || start < 1 || start > n || aim < 1 || aim > n || k < 1) {
            return -1;
        }

        int [][] dp = new int[n+1][k+1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= k; ++rest) {
            dp[1][rest] = dp[2][rest-1];
            for (int cur = 2; cur < n; ++cur) {
                dp[cur][rest] = dp[cur-1][rest-1] + dp[cur+1][rest-1];
            }
            dp[n][rest] = dp[n-1][rest-1];
        }

        return dp[start][k];
    }

    /*
        for test
     */
    public static void main(String[] args) {
        System.out.println("test start....");
        int test_times = 100000;
        int max_n = 10;
        int max_val = 20;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int n = (int)(Math.random() * (max_n + 1));
            int aim = (int)(Math.random() * (max_n + 1));
            int k = (int)(Math.random() * (max_val + 1));
            int start = (int)(Math.random() * (max_val + 1));
            if (ways1(n, start, aim, k) != ways2(n, start, aim, k)) {
                success = false;
                break;
            }
            if (ways1(n, start, aim, k) != ways3(n, start, aim, k)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
