package leetcode;

public class Code_0069_SqrtX {
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }

        if (x < 3) {
            return 1;
        }

        long ans = 1;
        long left = 1;
        long right = x;
        long mid = 0;
        while (left <= right) {
            mid = left + ((right - left)>>1);
            if (mid * mid <= x) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (int)ans;
    }
}
