package leetcode.all;

public class Code_0200_NumberOfIslands {
    public int numIslands(char[][] grid) {
        if (grid == null) {
            return 0;
        }

        int m = grid.length;
        int n = grid[0].length;
        UnionFind union1 = new UnionFind(grid);
        for (int i = 1; i < n; ++i) {
            if (grid[0][i] == '1' && grid[0][i-1] == '1') {
                union1.union(0, i, 0, i-1);
            }
        }

        for (int i = 1; i < m; ++i) {
            if (grid[i][0] == '1' && grid[i-1][0] == '1') {
                union1.union(i, 0, i - 1, 0);
            }
        }

        for (int i = 1; i < m; ++i) {
            for (int j = 1; j < n; ++j) {
                if (grid[i][j] == '1') {
                    if (grid[i-1][j] == '1') {
                        union1.union(i, j, i - 1, j);
                    }
                    if (grid[i][j-1] == '1') {
                        union1.union(i, j, i, j-1);
                    }
                }
            }
        }

        return union1.sets();
    }

    public static class UnionFind {
        private int row;
        private int col;
        private int[] parents;
        private int[] help;
        private int[] size;
        private int sets;

        public UnionFind(char[][] grid) {
            row = grid.length;
            col = grid[0].length;
            int len = row * col;
            parents = new int[len];
            help = new int[len];
            size = new int[len];
            for (int i = 0; i < row; ++i) {
                for (int j = 0; j < col; ++j) {
                    if (grid[i][j] == '1') {
                        sets++;
                        int pos = getIndex(i, j);
                        size[pos] = 1;
                        parents[pos] = pos;
                    }
                }
            }
        }

        public void union(int r1, int c1, int r2, int c2) {
            int head1 = find(getIndex(r1, c1));
            int head2 = find(getIndex(r2, c2));
            if (head1 != head2) {
                if (size[head1] >= size[head2]) {
                    size[head1] += size[head2];
                    parents[head2] = head1;
                } else {
                    size[head2] += size[head1];
                    parents[head1] = head2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }

        private int find(int index) {
            int hi = 0;
            while (index != parents[index]) {
                help[hi++] = index;
                index = parents[index];
            }

            for (hi--; hi>= 0; hi--) {
                parents[help[hi]] = index;
            }

            return index;
        }

        private int getIndex(int r, int c) {
            return r * col + c;
        }
    }
}
