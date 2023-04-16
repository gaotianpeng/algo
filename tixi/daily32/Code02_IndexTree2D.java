package tixi.daily32;

/*
    https://leetcode.cn/problems/range-sum-query-2d-mutable/
    leetcode 308 二维区域和检索 - 可变
        给你一个二维矩阵 matrix ，处理以下类型的多个查询:
        更新 matrix 中单元格的值。
        计算由左上角 (row1, col1) 和右下角 (row2, col2) 定义的 matrix内矩阵元素的和。
        实现 NumMatrix 类：
    NumMatrix(int[][] matrix) 用整数矩阵matrix 初始化对象。
    void update(int row, int col, int val) 更新 matrix[row][col] 的值到 val 。
    int sumRegion(int row1, int col1, int row2, int col2) 返回矩阵matrix 中指定矩形区域元素的 和 ，该区域由 左上角 (row1, col1)
     和 右下角 (row2, col2) 界定
 */
public class Code02_IndexTree2D {
    public static class NumMatrix {
        private int[][] tree;
        private int[][] nums;
        private int m;
        private int n;

        public NumMatrix(int[][] matrix) {
            if (matrix == null || matrix.length == 0) {
                return;
            }
            m = matrix.length;
            n = matrix[0].length;
            tree = new int[m + 1][n + 1];
            nums = new int [m][n];
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        private int sum(int row, int col) {
            int ans = 0;
            for (int i = row + 1; i > 0; i -= i & (-i)) {
                for (int j = col + 1; j > 0; j -= j & (-j)) {
                    ans += tree[i][j];
                }
            }

            return ans;
        }

        public void update(int row, int col, int val) {
            if (n == 0 || m == 0) {
                return;
            }

            int add = val - nums[row][col];
            nums[row][col] = val;
            for (int i = row + 1; i <= m; i += i & (-i)) {
                for (int j = col + 1; j <= n; j += j &(-j)) {
                    tree[i][j] += add;
                }
            }
        }

        public int sumRegion(int row1, int col1, int row2, int col2) {
            if (m == 0 || n == 0) {
                return 0;
            }

            return sum(row2, col2) + sum(row1 - 1, col1 - 1) - sum(row1 - 1, col2) - sum(row2, col1 - 1);
        }
    }
}
