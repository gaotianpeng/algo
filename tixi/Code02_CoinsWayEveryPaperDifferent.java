package tixi.daily21;
/*
    arr是货币数组，其中的值都是正数。再给定一个正数aim。
    每个值都认为是一张货币，
    即便是值相同的货币也认为每一张都是不同的，
    返回组成aim的方法数
    例如：arr = {1,1,1}，aim = 2
    第0个和第1个能组成2，第1个和第2个能组成2，第0个和第2个能组成2
    一共就3种方法，所以返回3
 */
public class Code02_CoinsWayEveryPaperDifferent {
    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }

        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        } else {
            return process(arr, index + 1, rest)
                + process(arr, index + 1, rest - arr[index]);
        }
    }

    public static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }
}
