package leetcode;

/*
    0007 整数反转
        给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
        如果反转后整数超过 32 位的有符号整数的范围[−2^31, 2^31− 1] ，就返回 0。
        假设环境不允许存储 64 位整数（有符号或无符号）
 */
public class Code_0007_ReverseInteger {
    public int reverse(int x) {
        boolean neg = x < 0;
        // boolean neg = ((x >>> 31) & 1) == 1;
        x = neg ? x: -x;
        int m = Integer.MIN_VALUE / 10;
        int o = Integer.MIN_VALUE % 10;
        int ans = 0;
        while (x != 0) {
            if (ans < m || (ans == m && x % 10 < o)) {
                return 0;
            }
            ans = ans * 10 + x % 10;
            x /= 10;
        }

        return neg ? ans: Math.abs(ans);
    }
}
