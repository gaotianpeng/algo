package tixi.daily21;
/*
    给定一个二维数组matrix，一个人必须从左上角出发，最后到达右下角
    沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
    返回最小距离累加和
 */
public class Code01_MinPathSum {
    public static int minPathSum(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m.length == 0) {
            return 0;
        }

        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i-1][0] + m[i][0];
        }

        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j-1] + m[0][j];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1] + m[i][j]);
            }
        }

        return dp[row - 1][col - 1];
    }
}
