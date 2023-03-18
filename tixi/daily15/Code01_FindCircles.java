package tixi.daily15;
/*

    https://leetcode.cn/problems/number-of-provinces/submissions/
    leetcode 547 省份数量
        有n个城市，其中一些彼此相连，另一些没有相连。如果城市a与城市b直接相连，且城市b与城市c直接相连，那么城市a与城市c间接相连。
        省份是一组直接或间接相连的城市，组内不含其他没有相连的城市。
        给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
        而 isConnected[i][j] = 0 表示二者不直接相连。返回矩阵中 省份 的数量
 */
public class Code01_FindCircles {
    public int findCircleNum(int[][] isConnected) {
        if (isConnected == null) {
            return 0;
        }

        int n = isConnected.length;
        UnionFind union_find = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    union_find.union(i, j);
                }
            }
        }
        return union_find.sets();
    }

    public static class UnionFind {
        private int[] size;
        private int[] help;
        private int[] parents;
        private int sets;

        public UnionFind(int n) {
            size = new int[n];
            help = new int[n];
            parents = new int[n];
            sets = n;

            for (int i = 0; i < n; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] > size[f2]) {
                    size[f1] += size[f2];
                    parents[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parents[f1] = f2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parents[i]) {
                help[hi++] = i;
                i = parents[i];
            }

            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = i;
            }

            return i;
        }
    }
}
