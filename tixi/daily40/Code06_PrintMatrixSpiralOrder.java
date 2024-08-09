package tixi.daily40;

/*
 * 给定一个长方形矩阵matrix，实现转圈(螺旋)打印
        a  b   c  d
        e  f   g  h
        i  j   k  L
        打印顺序：a b c d h L k j I e f g
 */
public class Code06_PrintMatrixSpiralOrder {
    public static void spiralOrderPrint(int[][] matrix) {
        int leftTopRow = 0;
        int leftTopCol = 0;
        int rightDownRow = matrix.length - 1;
        int rightDownCol = matrix[0].length - 1;
        while (leftTopRow <= rightDownRow && leftTopCol <= rightDownCol) {
            printEdge(matrix, leftTopRow++, leftTopCol++, rightDownRow--, rightDownCol--);
        }
    }

    public static void printEdge(int[][] m, int leftTopRow, int leftTopCol,
                int rightDownRow, int rightDownCol) {
        if (leftTopRow == rightDownRow) {
            for (int i = leftTopCol; i <= rightDownCol; i++) {
                System.out.print(m[leftTopRow][i] + " ");
            }
        } else if (leftTopCol == rightDownCol) {
            for (int i = leftTopRow; i <= rightDownRow; ++i) {
                System.out.print(m[i][leftTopCol] + " ");
            }
        } else {
            int curC = leftTopCol;
            int curR = leftTopRow;
            while (curC != rightDownCol) {
                System.out.print(m[leftTopRow][curC] + " ");
                curC++;
            }
            while (curR != rightDownRow) {
                System.out.print(m[curR][rightDownCol] + " ");
                curR++;
            }
            while (curC != leftTopCol) {
                System.out.print(m[rightDownRow][curC] + " ");
                curC--;
            }
            while (curR != leftTopRow) {
                System.out.print(m[curR][leftTopCol] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int[][] matrix = {
            {1,  2,  3,  4},
            {5,  6,  7, 8},
            {9, 10, 11, 12},
            {13,14, 15, 16}
        };
        spiralOrderPrint(matrix);

        System.out.println();
        System.out.println("test end");
    }
}
