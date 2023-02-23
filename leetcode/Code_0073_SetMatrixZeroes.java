package leetcode;
/*
    73. 矩阵置零
        给定一个m x n的矩阵，如果一个元素为0，则将其所在行和列的所有元素都设为0 。请使用原地算法

    示例
        输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]
        输出：         [[1,0,1],[0,0,0],[1,0,1]]
 */
public class Code_0073_SetMatrixZeroes {
    public void setZeroes(int[][] matrix) {
        boolean col0 = false;
        int i = 0;
        int j = 0;
        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    if (j == 0) {
                        col0 = true;
                    } else {
                        matrix[0][j] = 0;
                    }
                }
            }
        }

        for (i = matrix.length - 1; i >= 0; i--) {
            for (j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (col0) {
            for (i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    public void setZeros(int[][] matrix) {
        boolean row0_zero = false;
        boolean col0_zero = false;
        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                row0_zero = true;
                break;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0_zero = true;
                break;
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }

        if (row0_zero) {
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }

        if (col0_zero) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
