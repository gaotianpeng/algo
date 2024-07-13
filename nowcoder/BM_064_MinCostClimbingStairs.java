package nowcoder;

public class BM_064_MinCostClimbingStairs {
    public static int minCostClimbingStairs (int[] cost) {
        // write code here
        if (cost == null || cost.length == 0) {
            return 0;
        }

        if (cost.length == 1) {
            return cost[0];
        }

        return Math.min(process(cost, 0), process(cost, 1));
    }

    public static int process(int[] cost, int index) {
        if (index >= cost.length) {
            return 0;
        }

        return Math.min(process(cost, index + 1), process(cost, index + 2))
                + cost[index];
    }

    public static int dp1(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }

        if (cost.length == 1) {
            return cost[0];
        }

        // dp[i] 表示到达第i个阶梯的最小花费
        int N = cost.length;
        int[] dp = new int[N+1];
        dp[N] = 0;
        dp[0] = cost[0];
        dp[1] = cost[1];

        for (int i = 2; i < dp.length; ++i) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + (i == cost.length ? 0 : cost[i]);
        }

        return dp[dp.length - 1];
    }

    public static int dp2(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }

        int n = cost.length;
        if (n == 1) {
            return cost[0];
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 0;

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return dp[n];
    }

    public static int dp3(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }

        int n = cost.length;
        if (n == 1) {
            return cost[0];
        }

        int prev2 = 0;
        int prev1 = 0;

        for (int i = 2; i <= n; i++) {
            int current = Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    public static int minCostClimbingStairs1(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }

        if (cost.length == 1) {
            return cost[0];
        }

        return Math.min(process1(cost, cost.length - 1), process1(cost, cost.length - 2));
    }

    public static int process1(int[] cost, int index) {
        if (index == 0) {
            return cost[0];
        }

        if (index == 1) {
            return cost[1];
        }

        return Math.min(process1(cost, index - 1),
                process1(cost, index - 2)) + cost[index];
    }
}
