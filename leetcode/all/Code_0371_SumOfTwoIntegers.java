package leetcode.all;
/*
    371. 两整数之和
        给你两个整数 a 和 b ，不使用 运算符 + 和 - ，计算并返回两整数之和。
 */
public class Code_0371_SumOfTwoIntegers {
    public int getSum(int a, int b) {
        int sum = a;
        while ( b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }

        return sum;
    }
}
