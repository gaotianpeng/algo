package leetcode.all;

/*
    57. Insert Interval
        You are given an array of non-overlapping intervals intervals where intervals[i] = [starti, endi]
        represent the start and the end of the ith interval and intervals is sorted in ascending order by starti.
        You are also given an interval newInterval = [start, end] that represents the start and end of another interval.
        Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and
        intervals still does not have any overlapping intervals (merge overlapping intervals if necessary).

        Return intervals after the insertion.

        Note that you don't need to modify intervals in-place. You can make a new array and return it.

        Example 1:
            Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
            Output: [[1,5],[6,9]]

        Example 2:
            Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
            Output: [[1,2],[3,10],[12,16]]
            Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].

        Constraints:
            0 <= intervals.length <= 104
            intervals[i].length == 2
            0 <= starti <= endi <= 105
            intervals is sorted by starti in ascending order.
            newInterval.length == 2
            0 <= start <= end <= 105
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Code_0057_InsertInterval {
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        int left = newInterval[0];
        int right = newInterval[1];
        boolean placed = false;
        List<int[]> ansList = new ArrayList<int[]>();
        for (int[] interval : intervals) {
            if (interval[0] > right) {
                // 在插入区间的右侧且无交集
                if (!placed) {
                    ansList.add(new int[]{left, right});
                    placed = true;
                }
                ansList.add(interval);
            } else if (interval[1] < left) {
                // 在插入区间的左侧且无交集
                ansList.add(interval);
            } else {
                // 与插入区间有交集，计算它们的并集
                left = Math.min(left, interval[0]);
                right = Math.max(right, interval[1]);
            }
        }
        if (!placed) {
            ansList.add(new int[]{left, right});
        }
        int[][] ans = new int[ansList.size()][2];
        for (int i = 0; i < ansList.size(); ++i) {
            ans[i] = ansList.get(i);
        }
        return ans;
    }

    public static int[][] test(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();

        int i = 0;
        int n = intervals.length;

        // 先将所有不重叠的区间添加到结果中
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i]);
            i++;
        }

        // 合并所有重叠的区间
        while (i < n && intervals[i][0] <= newInterval[1]) {
            newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
            i++;
        }

        // 添加合并后的新区间
        result.add(newInterval);

        // 添加剩余的非重叠区间
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }

    public static int[][] generateIntervals(int numIntervals, int min, int max) {
        Random rand = new Random();
        List<int[]> intervals = new ArrayList<>();

        for (int i = 0; i < numIntervals; i++) {
            int start = rand.nextInt(max - min + 1) + min;
            int end = rand.nextInt(max - start + 1) + start;
            intervals.add(new int[]{start, end});
        }

        // Sort intervals by start time
        intervals.sort((a, b) -> Integer.compare(a[0], b[0]));

        return intervals.toArray(new int[intervals.size()][]);
    }

    public static int[] generateNewInterval(int min, int max) {
        Random rand = new Random();
        int start = rand.nextInt(max - min + 1) + min;
        int end = rand.nextInt(max - start + 1) + start;
        return new int[]{start, end};
    }

    public static boolean isEqual(int[][] arr1, int[][] arr2) {
        // 如果任意一个数组为空，则返回它们是否都为空
        if (arr1 == null || arr2 == null) {
            return arr1 == arr2;
        }

        // 检查数组的行数是否相同
        if (arr1.length != arr2.length) {
            return false;
        }

        // 检查每一行的长度和内容是否相同
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].length != arr2[i].length) {
                return false;
            }
            for (int j = 0; j < arr1[i].length; j++) {
                if (arr1[i][j] != arr2[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 100000;
        int maxN = 104;
        int min = 0;
        int max = 105;
        Random rand = new Random();
        for (int i = 0; i < testTimes; ++i) {
            int N = rand.nextInt(maxN + 1);
            int[][] intervals = generateIntervals(N, min, max);
            int[] newInterval = generateNewInterval(min, max);
            int[][] ans1 = insert(intervals, newInterval);
            int[][] ans2 = test(intervals, newInterval);
            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
