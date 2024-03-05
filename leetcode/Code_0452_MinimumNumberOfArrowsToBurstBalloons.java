package leetcode;

import java.util.Arrays;
public class Code_0452_MinimumNumberOfArrowsToBurstBalloons {
    /*
        https://leetcode.cn/problems/minimum-number-of-arrows-to-burst-balloons/description/
        452 用最少数量的箭引爆气球
     */
    public int findMinArrowShots(int[][] points) {
        if (points == null || points.length == 0) {
            return 0;
        }

        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        int pos = points[0][1];
        int ans = 1;
        for (int[] balloon: points) {
            if (balloon[0] > pos) {
                pos = balloon[1];
                ++ans;
            }
        }
        return ans;
    }
}
