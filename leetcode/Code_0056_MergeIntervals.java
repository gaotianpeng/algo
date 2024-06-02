package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/*
    https://leetcode.cn/problems/merge-intervals/description/
    以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
    请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。

    示例 1：
        输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
        输出：[[1,6],[8,10],[15,18]]
        解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].

    示例 2：
        输入：intervals = [[1,4],[4,5]]
        输出：[[1,5]]
        解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。

    提示：
        1 <= intervals.length <= 104
        intervals[i].length == 2
        0 <= starti <= endi <= 104
 */
public class Code_0056_MergeIntervals {
    public static class Range {
        public int start;
        public int end;

        public Range(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static class RangeComparator implements Comparator<Range> {
        @Override
        public int compare(Range r1, Range r2) {
            return r1.start - r2.start;
        }
    }

    public int[][] merge(int[][] intervals) {
        Range[] arr = new Range[intervals.length];
        for (int i = 0; i < intervals.length; ++i) {
            arr[i] = new Range(intervals[i][0], intervals[i][1]);
        }

        Arrays.sort(arr, new RangeComparator());
        ArrayList<Range> ans = new ArrayList<>();
        if (arr.length == 0) {
            return new int[0][0];
        }

        int s = arr[0].start;
        int e = arr[0].end;
        for (int i = 1; i < arr.length; ++i) {
            if (arr[i].start > e) {
                ans.add(new Range(s, e));
                s = arr[i].start;
                e = arr[i].end;
            } else {
                e = Math.max(e, arr[i].end);
            }
        }

        ans.add(new Range(s, e));
        return generateMatrix(ans);
    }

    public static int[][] generateMatrix(ArrayList<Range> list) {
        int[][] matrix = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            matrix[i] = new int[] { list.get(i).start, list.get(i).end };
        }
        return matrix;
    }
}
