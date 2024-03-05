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
}
