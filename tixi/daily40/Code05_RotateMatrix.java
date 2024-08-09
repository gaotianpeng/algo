package tixi.daily40;

/*
    给定一个正方形矩阵matrix，原地调整成顺时针90度转动的样子
        a  b  c		  g  d  a
        d  e  f	 ->   h  e  b
        g  h  i		  i  f  c
 */
public class Code05_RotateMatrix {
    public static void rotate(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || 
                matrix.length != matrix[0].length) {
            return;
        }
        // 左上角 (a, b)
        // 右下角 (c, d)
        int a = 0;
        int b = 0;
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    public static void rotateEdge(int[][] m, int a, int b, int c, int d) {
        int tmp = 0;
        for (int i = 0; i < d - b; ++i) {
            tmp = m[a][b + i];
            m[a][b+i] = m[c-i][b];
            m[c-i][b] = m[c][d-i];
            m[c][d-i] = m[a+i][d];
            m[a+i][d] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i != matrix.length; i++) {
            for (int j = 0; j != matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        
        printMatrix(matrix);
		rotate(matrix);
		System.out.println("=========");
		printMatrix(matrix);

        System.out.println("test end");
    }
}
