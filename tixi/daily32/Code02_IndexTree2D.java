package tixi.daily32;

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
