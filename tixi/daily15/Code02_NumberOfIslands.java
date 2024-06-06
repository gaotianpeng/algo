package tixi.daily15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
    leetcode 200 岛屿数量
        给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
        岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
        此外，你可以假设该网格的四条边均被水包围。

    示例
    输入：grid = [
          ["1","1","1","1","0"],
          ["1","1","0","1","0"],
          ["1","1","0","0","0"],
          ["0","0","0","0","0"]
        ]
    输出：1
 */
public class Code02_NumberOfIslands {
    public static int numIslands(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }

        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    ans++;
                    infect(matrix, i, j);
                }
            }
        }

        return ans;
    }

    private static void infect(char[][] matrix, int i, int j) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length
                || matrix[i][j] != '1') {
            return;
        }

        matrix[i][j] = 0;
        infect(matrix, i - 1, j);
        infect(matrix, i, j - 1);
        infect(matrix, i + 1, j);
        infect(matrix, i, j + 1);
    }

    public static int numIslands1(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        int ans = 0;
        Dot[][] dots = new Dot[matrix.length][matrix[0].length];
        List<Dot> list = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dots[i][j] = new Dot();
                    list.add(dots[i][j]);
                }
            }
        }

        UnionFind<Dot> unionFind = new UnionFind(list);
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i-1][0] == '1' && matrix[i][0] == '1') {
                unionFind.union(dots[i-1][0], dots[i][0]);
            }
        }

        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[0][j-1] == '1' && matrix[0][j] == '1') {
                unionFind.union(dots[0][j-1], dots[0][j]);
            }
        }

        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    if (matrix[i][j-1] == '1') {
                        unionFind.union(dots[i][j], dots[i][j-1]);
                    }

                    if (matrix[i-1][j] == '1') {
                        unionFind.union(dots[i][j], dots[i-1][j]);
                    }
                }
            }
        }

        return unionFind.sets();
    }

    private static class Dot {
    }

    private static class Node<V> {
        public V value;

        public Node(V v) {
            value = v;
        }
    }

    private static class UnionFind<V> {
        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Node<V>> parents;
        private HashMap<Node<V>, Integer> setSize;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            setSize = new HashMap<>();
            for (V v: values) {
                Node<V> node = new Node<>(v);
                nodes.put(v, node);
                parents.put(node, node);
                setSize.put(node, 1);
            }
        }

        public void union(V a, V b) {
            Node<V> pa = findFather(nodes.get(a));
            Node<V> pb = findFather(nodes.get(b));

            if (pa != pb) {
                int size_a = setSize.get(pa);
                int size_b = setSize.get(pb);
                Node<V> big = size_a > size_b ? pa : pb;
                Node<V> small = big == pa ? pb : pa;
                parents.put(small, big);
                setSize.put(big, size_a + size_b);
                setSize.remove(small);
            }
         }

        private Node<V> findFather(Node<V> v) {
            Stack<Node<V>> stack = new Stack<>();
            while (v != parents.get(v)) {
                stack.push(v);
                v = parents.get(v);
            }

            while (!stack.isEmpty()) {
                parents.put(stack.pop(), v);
            }

            return v;
        }

        public int sets() {
            return setSize.size();
        }
    }

    private static char[][] generateRandomMatrix(int maxRow, int maxCol) {
        int row = (int)(Math.random() * maxRow + 1);
        int col = (int)(Math.random() * maxCol + 1);
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return ans;
    }

    private static char[][] copyMatrix(char[][] matrix) {
        if (matrix == null) {
            return null;
        }

        char[][] ans = new char[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                ans[i][j] = matrix[i][j];
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int maxRow = 100;
        int maxCol = 100;
        int testTimes = 10000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            char[][] matrix1 = generateRandomMatrix(maxRow, maxCol);
            char[][] matrix2 = copyMatrix(matrix1);
            if (numIslands(matrix1) != numIslands1(matrix2)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
