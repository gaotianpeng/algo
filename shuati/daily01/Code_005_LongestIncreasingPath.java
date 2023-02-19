package shuati.daily01;
/*
    leetcode 329: 矩阵中的最长递增路径
        给定一个二维数组matrix，
        你可以从任何位置出发，走向上下左右四个方向
        返回能走出来的最长的递增链长度
 */
public class Code_005_LongestIncreasingPath {
    public int longestIncreasingPath(int[][] matrix) {
        int ans = 0;
        int n = matrix.length;
        int m = matrix[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, process1(matrix, i, j));
            }
        }
        return ans;
    }

    public static int process1(int[][] m, int i, int j) {
        int up = i > 0 && m[i][j] < m[i-1][j] ? process1(m, i - 1, j) : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process1(m, i + 1, j) : 0;
        int left = j > 0 && m[i][j] < m[i][j-1] ? process1(m, i, j - 1) : 0;
        int right = j < m[0].length && m[i][j] < m[i][j+1] ? process1(m, i, j + 1) : 0;
        return Math.max(Math.max(up, down),Math.max(left, right)) + 1;
    }

    public static int longestIncreasingPath2(int[][] matrix) {
        int ans = 0;
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ans = Math.max(ans, process2(matrix, i, j, dp));
            }
        }
        return ans;
    }

    public static int process2(int[][] m, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int up = i > 0 && m[i][j] < m[i-1][j] ? process2(m, i - 1, j, dp) : 0;
        int down = i < (m.length - 1) && m[i][j] < m[i+1][j] ? process2(m, i + 1, j, dp) : 0;
        int left = j > 0 && m[i][j] < m[i][j-1] ? process2(m, i, j - 1, dp) : 0;
        int right = j < (m[0].length - 1) && m[i][j] < m[i][j+1] ? process2(m, i, j + 1, dp) : 0;
        dp[i][j] = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
        return dp[i][j];
    }
}
