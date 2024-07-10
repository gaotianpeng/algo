package leetcode.all;

/*
    191. 位1的个数
        编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中数字位数为 '1' 的个数（也被称为汉明重量）。
 */
public class Code_0191_NumberOfOneBits {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int bits = 0;
        int right_one = 0;
        while (n != 0) {
            ++bits;
            right_one = n &(-n);
            n ^= right_one;
        }
        return bits;
    }
}
