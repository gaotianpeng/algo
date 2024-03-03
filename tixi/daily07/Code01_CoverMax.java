package tixi.daily07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
    给定很多线段，每个线段都有两个数[start, end]，
    表示线段开始位置和结束位置，左右都是闭区间
    规定：
        1）线段的开始和结束位置一定都是整数值
        2）线段重合区域的长度必须>=1
    返回线段最多重合区域中，包含了几条线段
 */
public class Code01_CoverMax {
    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static int maxCover(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            lines[i] = new Line(m[i][0], m[i][1]);
        }

        Arrays.sort(lines, new StartComparator());

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int ans = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i].start) {
                heap.poll();
            }
            heap.add(lines[i].end);
            ans = Math.max(ans, heap.size());
        }

        return ans;
    }

    public static class EndComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.end - o2.end;
        }
    }

    public static int maxCover1(int[][] m) {
        Arrays.sort(m, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int[] line : m) {
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    /*
        for test
     */
    public static int test(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; ++i) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }

        double start = min + 0.5;
        double end = max - 0.5;
        int ans = 0;
        for (double i = start; i <= end; ++i) {
            int cur = 0;
            for (int[] line: lines) {
                if (line[0] < i && line[1] > i) {
                    ++cur;
                }
            }

            ans = Math.max(ans, cur);
        }

        return ans;
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int N = 100;
        int L = 0;
        int R = 200;
        int test_times = 100000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[][] lines = generateLines(N, L, R);
            int ans1 = test(lines);
            int ans2 = maxCover(lines);
            int ans3 = maxCover1(lines);
            if (ans1 != ans2 || ans1 != ans3) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
