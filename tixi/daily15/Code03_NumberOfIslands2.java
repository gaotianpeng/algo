package tixi.daily15;

import java.util.ArrayList;
import java.util.List;

/*
    https://leetcode.cn/problems/number-of-islands-ii/?
    leetcode 305 岛屿数量
        给你一个大小为 m x n 的二进制网格 grid 。网格表示一个地图，其中，0 表示水，1 表示陆地。
        最初，grid 中的所有单元格都是水单元格（即，所有单元格都是 0）。
        可以通过执行 addLand 操作，将某个位置的水转换成陆地。给你一个数组 positions ，
        其中 positions[i] = [ri, ci] 是要执行第 i 次操作的位置 (ri, ci) 。
        返回一个整数数组 answer ，其中 answer[i] 是将单元格 (ri, ci) 转换为陆地后，地图中岛屿的数量。
        岛屿 的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。你可以假设地图网格的四边均被无边无际的「水」所包围
 */
public class Code03_NumberOfIslands2 {
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        UnionFind union_find = new UnionFind(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position: positions) {
            ans.add(union_find.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row
                || c1 < 0 || c1 == col || c2 < 0 || c2 == col)  {
                return;
            }

            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }

            int f1 = findParent(i1);
            int f2 = findParent(i2);
            if (f1 != f2) {
                if (size[f1] > size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        private int findParent(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private int index(int r, int c) {
            return r * col + c;
        }
    }
}
