package tixi.daily24;
/*
    arr是货币数组，其中的值都是正数。再给定一个正数aim。
    每个值都认为是一张货币，
    返回组成aim的最少货币数
    注意：
    因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 */
public class Code04_MinCoinsOnePaper {
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }

        if (index == arr.length) {
            return rest == 0 ? 0: Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    // dp1时间复杂度为：O(arr长度 * aim)
    public static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[N][j] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : Integer.MAX_VALUE;
                if (p2 != Integer.MAX_VALUE) {
                    p2++;
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    // for test
    public static int[] randomArray(int N, int maxValue) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLen = 20;
        int maxVal = 30;
        int testTimes = 30000;
        for (int i = 0; i < testTimes; ++i) {
            int n = (int)(Math.random() * maxLen);
            int[] arr = randomArray(n, maxVal);
            int aim = (int)(Math.random() * maxVal);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
