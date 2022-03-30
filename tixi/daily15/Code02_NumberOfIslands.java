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
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                if (matrix[i][j] == '1') {
                    ans++;
                    infect(matrix, i, j);
                }
            }
        }

        return ans;
    }

    // 从(i, j) 出发, 把所有连成一片的'1'字符变成'0'
    private static void infect(char[][] matrix, int i, int j) {
        if (i < 0 || i == matrix.length || j < 0 || j == matrix[0].length
            || matrix[i][j] != '1') {
            return;
        }
        matrix[i][j] = '0';
        infect(matrix, i - 1, j);
        infect(matrix, i + 1, j);
        infect(matrix, i, j - 1);
        infect(matrix, i, j + 1);
    }

    public static int numIslands1(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }

        int row = matrix.length;
        int col = matrix[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dot_list = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dot_list.add(dots[i][j]);
                }
            }
        }

        UnionFind<Dot> union_find = new UnionFind<>(dot_list);
        for (int j = 1; j < col; j++) {
            if (matrix[0][j-1] == '1' && matrix[0][j] == '1') {
                union_find.union(dots[0][j-1], dots[0][j]);
            }
        }

        for (int i = 1; i < row; i++) {
            if (matrix[i - 1][0] == '1' && matrix[i][0] == '1') {
                union_find.union(dots[i-1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == '1') {
                    if (matrix[i][j-1] == '1') {
                        union_find.union(dots[i][j-1], dots[i][j]);
                    }
                    if (matrix[i - 1][j] == '1') {
                        union_find.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }

        return union_find.sets();
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
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    private static char[][] generateRandomMatrix(int max_row, int max_col) {
        int row = (int)(Math.random() * max_row + 1);
        int col = (int)(Math.random() * max_col + 1);
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
        int max_row = 100;
        int max_col = 100;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            char[][] matrix1 = generateRandomMatrix(max_row, max_col);
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
