package leetcode.all;

/*
    329. Longest Increasing Path in a Matrix
        Given an m x n integers matrix, return the length of the longest increasing path in matrix.
        From each cell, you can either move in four directions: left, right, up, or down.
        You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).

    Example 1:
        Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
        Output: 4
        Explanation: The longest increasing path is [1, 2, 6, 9].

    Example 2:
        Input: matrix = [[3,4,5],[3,2,6],[2,2,1]]
        Output: 4
        Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.

    Example 3:
        Input: matrix = [[1]]
        Output: 1

    Constraints:
        m == matrix.length
        n == matrix[i].length
        1 <= m, n <= 200
        0 <= matrix[i][j] <= 231 - 1
 */
public class Code_0329_LongestIcreasignPath {

    public static int longest(int[][] matrix) {
        int longest = 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        // dp[i][j] == 0 process(i,j)
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                longest = Math.max(longest, process(matrix, i, j, dp));
            }
        }
        return longest;
    }

    // 从matrix[i][j]出发，可以走上下左右四个方向，走出的最长递增链是多长，返回
    public static int process(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        // 不越界
        int next = 0; // 往左右上下四个方向，能走出最长的后续是多少？
        // i, j i-1,j
        if (i > 0 && matrix[i - 1][j] > matrix[i][j]) {
            next = process(matrix, i - 1, j, dp);
        }
        // i + 1
        if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
            next = Math.max(next, process(matrix, i + 1, j, dp));
        }
        if (j > 0 && matrix[i][j - 1] > matrix[i][j]) {
            next = Math.max(next, process(matrix, i, j - 1, dp));
        }
        if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
            next = Math.max(next, process(matrix, i, j + 1, dp));
        }
        dp[i][j] = 1 + next;
        return 1 + next;
    }

    public static int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        int ans = 0;
        int N = matrix.length;
        int M = matrix[0].length;
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ans = Math.max(ans, lip(matrix, i, j, dp));
            }
        }
        return ans;
    }

    public static int lip(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) {
            return dp[i][j];
        }
        int next = 0;
        if (canWalk(matrix, i, j, i - 1, j)) {
            next = Math.max(next, lip(matrix, i - 1, j, dp));
        }
        if (canWalk(matrix, i, j, i + 1, j)) {
            next = Math.max(next, lip(matrix, i + 1, j, dp));
        }
        if (canWalk(matrix, i, j, i, j - 1)) {
            next = Math.max(next, lip(matrix, i, j - 1, dp));
        }
        if (canWalk(matrix, i, j, i, j + 1)) {
            next = Math.max(next, lip(matrix, i, j + 1, dp));
        }
        dp[i][j] = 1 + next;
        return dp[i][j];
    }

    public static boolean canWalk(int[][] matrix, int i1, int j1, int i2, int j2) {
        return i2 >= 0 && i2 < matrix.length && j2 >= 0 && j2 < matrix[0].length && matrix[i1][j1] < matrix[i2][j2];
    }
}
