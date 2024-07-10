package leetcode.all;
/*
    66 加一
        给定一个由整数组成的非空数组所表示的非负整数, 在该数的基础上加一
        最高位数字存放在数组的首位, 数组中每个元素只存储单个数字
        你可以假设除了整数0之外, 这个整数不会以零开头

    示例
        输入：digits = [1,2,3]
        输出：[1,2,4]
        解释：输入数组表示数字 123。

        输入：digits = [4,3,2,1]
        输出：[4,3,2,2]
        解释：输入数组表示数字 4321。
 */
public class Code_0066_PlusOne {
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] ans = new int [n + 1];
        ans[0] = 1;
        return ans;
    }
}
