package tixi.daily19;

/*
    给定两个长度都为N的数组weights和values，
    weights[i]和values[i]分别代表 i号物品的重量和价值。
    给定一个正数bag，表示一个载重bag的袋子，
    你装的物品不能超过这个重量。
    返回你能装下最多的价值是多少?
 */
public class Code01_Knapstack {
    public static int maxValue(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || weight.length == 0) {
            return 0;
        }

        return process(weight, value, 0, bag);
    }

    private static int process(int[] weight, int[] value, int index, int rest) {
        if (rest < 0) {
            return -1;
        }

        if (index == weight.length) {
            return 0;
        }

        int p1 = process(weight, value, index + 1, rest);
        int p2 = 0;
        int next = process(weight, value, index + 1, rest - weight[index]);
        if (next != -1) {
            p2 = value[index] + next;
        }

        return Math.max(p1, p2);
    }

    public static int maxValue1(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || weight.length == 0) {
            return 0;
        }
        int n = weight.length;

        int[][] dp = new int[n + 1][bag + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= bag; j++) {
                dp[i][j] = -1;
            }
        }

        return process1(weight, value, 0, bag, dp);
    }

    public static int process1(int[] weight, int[] value, int index, int rest, int[][] dp) {
        if (rest < 0) {
            return -1;
        }

        int ans = 0;
        if (index == weight.length) {
            return 0;
        }

        if (dp[index][rest] != -1) {
            return dp[index][rest];
        }
        int p1 = process1(weight, value, index + 1, rest, dp);
        int p2 = 0;
        int next = process1(weight, value, index + 1, rest - weight[index], dp);
        if (next != -1) {
            p2 = next + value[index];
        }

        ans = Math.max(p1, p2);
        dp[index][rest] = ans;
        return ans;
    }

    public static int maxValue2(int[] weight, int[] value, int bag) {
        if (weight == null || value == null || weight.length != value.length || weight.length == 0) {
            return 0;
        }

        int n = weight.length;
        int[][] dp = new int[n + 1][bag + 1];
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                int next = rest - weight[index] < 0 ? -1 : dp[index+1][rest - weight[index]];
                if (next != -1) {
                    p2 = value[index] + next;
                }
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][bag];
    }

    /*
        for test
     */
    public static int[] generateRandomArr(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int)(Math.random()*(max_val + 1));
        }

        return ans;
    }

    public static void printArr(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start....");
        int test_times = 1000000;
        int max_len = 20;
        int max_val = 30;
        int max_bag = 50;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[] weight = generateRandomArr(max_val, max_len);
            int[] value = generateRandomArr(max_val, max_len);
            int bag = (int)(Math.random()*(max_bag + 1));
            if (maxValue(weight, value, bag) != maxValue1(weight, value, bag)) {
                success = false;
                printArr(weight);
                printArr(value);
                break;
            }
            if (maxValue(weight, value, bag) != maxValue2(weight, value, bag)) {
                success = false;
                printArr(weight);
                printArr(value);
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
