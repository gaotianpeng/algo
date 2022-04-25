package tixi.daily20;
/*
    leetcode 516: 最长回文子序列
        给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
        子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列

    示例
        输入：s = "bbbab"
        输出：4
        解释：一个可能的最长回文子序列为 "bbbb" 。
 */
public class Code01_PalindromeSubsequence {

    public static int longestPalindromeSubseq(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        return process(str, 0, s.length() - 1);
    }

    public static int process(char[] str, int left, int right) {
        if (left == right) {
            return 1;
        }

        if (left == right - 1) {
            return str[left] == str[right] ? 2 : 1;
        }

        int p1 = process(str, left + 1, right - 1);
        int p2 = process(str, left, right - 1);
        int p3 = process(str, left + 1, right);
        int p4 = str[left] != str[right] ? 0 : (2 + process(str, left+1, right - 1));

        return Math.max(Math.max(p1, p2), Math.max(p1, p2));
    }


}
