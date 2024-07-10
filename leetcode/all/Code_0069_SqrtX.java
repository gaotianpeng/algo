package leetcode.all;

/*
    69. x 的平方根
        给你一个非负整数 x ，计算并返回x的 算术平方根 。
        由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
        注意：不允许使用任何内置指数函数和算符，例如 pow(x, 0.5) 或者 x ** 0.5
 */
public class Code_0069_SqrtX {
    public int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        if (x <= 3) {
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
