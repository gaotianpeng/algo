package leetcode.all;

import java.util.Arrays;
public class Code_0452_MinimumNumberOfArrowsToBurstBalloons {
    /*
        https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/
        452 用最少数量的箭引爆气球
     */
    public static int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        int[][] copyPoints = new int[points.length][2];
        for (int i = 0; i < copyPoints.length; ++i) {
            copyPoints[i][0] = points[i][0];
            copyPoints[i][1] = points[i][1];
        }

        Arrays.sort(copyPoints, (a, b) -> Integer.compare(a[1], b[1]));
        int pos = copyPoints[0][1];
        int ans = 1;
        for (int[] point: copyPoints) {
            if (point[0] > pos) {
                pos = point[1];
                ++ans;
            }
        }

        return ans;
    }

    public static int test(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }


        int[][] copyPoints = new int[points.length][2];
        for (int i = 0; i < copyPoints.length; ++i) {
            copyPoints[i][0] = points[i][0];
            copyPoints[i][1] = points[i][1];
        }
        Arrays.sort(copyPoints, (a, b) -> Integer.compare(a[0], b[0]));

        int arrows = 1;
        int end = copyPoints[0][1];

        for (int i = 1; i < copyPoints.length; i++) {
            if (copyPoints[i][0] <= end) {
                end = Math.min(end, copyPoints[i][1]);
            } else {
                arrows++;
                end = copyPoints[i][1];
            }
        }

        return arrows;
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
            int ans2 = findMinArrowShots(lines);
            if (ans1 != ans2) {
                success = false;
                System.out.println(ans1);
                System.out.println(ans2);
                Arrays.sort(lines, (a, b) -> Integer.compare(a[1], b[1]));
                for (int[] line: lines) {
                    System.out.print(line[0] + "," + line[1] + "|");
                }
                System.out.println();
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
