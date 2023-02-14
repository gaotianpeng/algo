package tixi.daily25;
/*
    https://leetcode.cn/problems/count-submatrices-with-all-ones
    给定一个二维数组matrix，其中的值不是0就是1，
    返回全部由1组成的子矩形数量
 */
public class CountSubmatricesWithAllOnes {
    public static int numSubmat(int[][] mat) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return 0;
        }

        int nums = 0;
        int[] heights = new int[mat[0].length];
        for (int i = 0; i < mat.length; ++i) {
            for (int j = 0; j < mat[0].length; ++j) {
                heights[j] = mat[i][j] == 0 ? 0 : heights[j] + 1;
            }
            nums += countFromBottom(heights);
        }

        return nums;
    }

    public static int countFromBottom(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }
        int nums = 0;
        int[] stack = new int[heights.length];
        int si = -1;
        for (int i = 0; i < heights.length; ++i) {
            while (si != -1 && heights[stack[si]] >= heights[i]) {
                int cur = stack[si--];
                if (heights[cur] > heights[i]) {
                    int left = si == -1 ? -1 : stack[si];
                    int n = i - left - 1;
                    int down = Math.max(left == -1 ? 0 : heights[left], heights[i]);
                    nums += (heights[cur] - down) * num(n);
                }
            }
            stack[++i] = i;
        }

        while (si != -1) {
            int cur = stack[si--];
            int left = si == -1 ? -1 : stack[si];
            int n = heights.length - left - 1;
            int down = left == -1 ? 0 : heights[left];
            nums += (heights[cur] - down) * num(n);
        }

        return nums;
    }

    public static int num(int n) {
        return ((n * (n+1)) >> 1);
    }
}
