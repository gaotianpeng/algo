package tixi.daily40;

/*
    给定一个正方形或者长方形矩阵matrix，实现zigzag打印
    0 1 2
    3 4 5
    6 7 8 
    打印: 0 1 3 6 4 2 5 7 8
 */
public class Code07_ZigZagPrintMatrix {
    public static void printMatrixZigZag(int[][] matrix) {
        if (matrix == null || matrix.length == 0 ||
                matrix[0].length == 0) {
            return;
        }

        int topRow = 0;
        int topCol = 0;
        int downRow = 0;
        int downCol = 0;
        int endRow = matrix.length - 1;
        int endCol = matrix[0].length - 1;
        boolean fromUp = false;
        while (topRow != endRow + 1) {
            printLevel(matrix, topRow, topCol, downRow, downCol, fromUp);
            topRow = topCol == endCol ? topRow + 1: topRow;
            topCol = topCol == endCol ? topCol: topCol + 1;
            downCol = downRow == endRow ? downCol + 1: downCol;
            downRow = downRow == endRow ? downRow : downRow + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int topRow, int topCol,
                    int downRow, int downCol, boolean fromUp) {
        if (fromUp) {
            while (topRow != downRow + 1) {
                System.out.print(m[topRow++][topCol--] + " ");
            }
        } else {
            while (downRow != topRow - 1) {
                System.out.print(m[downRow--][downCol++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { 
            { 0, 1, 2},
            { 3, 4, 5 }, 
            { 6, 7, 8} 
        };
		printMatrixZigZag(matrix);   
    }
}
