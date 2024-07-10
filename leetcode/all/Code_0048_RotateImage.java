package leetcode.all;
/*
    0048 旋转图像
      给定一个 n×n 的二维矩阵matrix 表示一个图像。请你将图像顺时针旋转 90 度。
        你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像

    示例
        输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
        输出：[[7,4,1],[8,5,2],[9,6,3]]
 */
public class Code_0048_RotateImage {
    public void rotate(int[][] matrix) {
        int start_row = 0;
        int start_col = 0;
        int end_row = matrix.length - 1;
        int end_col = matrix.length - 1;
        while (start_row < end_row) {
            rotateEdge(matrix, start_row++, start_col++, end_row--, end_col--);
        }
    }

    public static void rotateEdge(int[][] m, int start_row, int start_col,
                                  int end_row, int end_col) {
        int times = end_col - start_col;
        int tmp = 0;
        for (int i = 0; i != times; i++) {
            tmp = m[start_row][start_col+i];
            m[start_row][start_col + i] = m[end_row - i][start_col];
            m[end_row - i][start_col] = m[end_row][end_col - i];
            m[end_row][end_col - i] = m[start_row + i][end_col];
            m[start_row + i][end_col] = tmp;
        }
    }
}
