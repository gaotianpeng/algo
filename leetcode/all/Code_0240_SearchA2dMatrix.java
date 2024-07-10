package leetcode.all;

public class Code_0240_SearchA2dMatrix {
/*
        leetcode 240. 搜索二维矩阵 II
            编写一个高效的算法来搜索mxn矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
            每行的元素从左到右升序排列。
            每列的元素从上到下升序排列。

        输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 5
        输出：true
        输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
        输出：false
 */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0;
        int col = n - 1;
        while ( row < m && col >= 0) {
            if (matrix[row][col] > target) {
                --col;
            } else if (matrix[row][col] < target ) {
                ++row;
            } else {
                return true;
            }
        }

        return false;
    }
}
