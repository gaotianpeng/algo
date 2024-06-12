package tixi.daily21;

import java.util.Random;

/*
    给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
    沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
    返回最小距离累加和

    https://leetcode.cn/problems/minimum-path-sum/
    leetcode 64 最小路径和
 */
public class Code01_MinPathSum {
    public static int minPathSum(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        // 第1列
        for (int i = 1; i < row; ++i) {
            dp[i][0] = dp[i-1][0] + m[i][0];
        }

        // 第1行
        for (int i = 1; i < col; ++i) {
            dp[0][i] = dp[0][i-1] + m[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = m[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
            }
        }

        return dp[row-1][col-1];
    }

    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }

        int row = m.length;
        int col = m[0].length;
        int[] dp = new int[col];
        dp[0] = m[0][0];
        for (int j = 1; j < col; j++) {
            dp[j] = dp[j-1] + m[0][j];
        }

        for (int i = 1; i < row; i++) {
            dp[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j-1], dp[j])+ m[i][j];
            }
        }

        return dp[col - 1];
    }

    // for test
    public static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i != result.length; i++) {
            for (int j = 0; j != result[0].length; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }

    public static int generateRandomInt(int minVal, int maxVal) {
        if (minVal > maxVal) {
            throw new IllegalArgumentException("minVal should be less than or equal to maxVal");
        }
        Random random = new Random();
        return random.nextInt((maxVal - minVal) + 1) + minVal;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        boolean success = true;
        int minN = 0;
        int maxN = 20;

        for (int i = 0; i < testTimes; ++i) {
            int rowSize = generateRandomInt(minN, maxN);
            int colSize = generateRandomInt(minN, maxN);
            int[][] m = generateRandomMatrix(rowSize, colSize);
            if (minPathSum(m) != minPathSum2(m)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
