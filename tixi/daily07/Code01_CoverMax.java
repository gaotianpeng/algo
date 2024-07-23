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
    public static int maxCover(int[][] lines) {
        if (lines == null || lines.length < 1) {
            return 0;
        }

        int ans = 0;
        int[][] copyLines = new int[lines.length][2];
        for (int i = 0; i < lines.length; ++i) {
            copyLines[i][0] = lines[i][0];
            copyLines[i][1] = lines[i][1];
        }

        Arrays.sort(copyLines, (a, b) ->(a[0] - b[0]));
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int[] line: copyLines) {
            while (!minHeap.isEmpty() && minHeap.peek() <= line[0]) {
                minHeap.poll();
            }
            minHeap.add(line[1]);
            ans = Math.max(ans, minHeap.size());
        }

        return ans;
    }


    public static int maxCover1(int[][] lines) {
        if (lines == null || lines.length < 1) {
            return 0;
        }

        int[][] copyLines = new int[lines.length][2];
        for (int i = 0; i < lines.length; ++i) {
            copyLines[i][0] = lines[i][0];
            copyLines[i][1] = lines[i][1];
        }

        Arrays.sort(copyLines, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int[] line : copyLines) {
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
        if (lines == null || lines.length == 0) {
            return 0;
        }

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
        int testTimes = 100000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
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
