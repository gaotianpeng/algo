package tixi.daily20;

import java.util.Random;

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
        int p4 = str[left] != str[right] ? 0 : (2 + process(str, left + 1, right - 1));

        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }
    public static int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                if (str[L] == str[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    // for test
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final Random RANDOM = new Random();

    public static String generateRandomString(int maxLength) {
        // 生成随机长度，假设长度在1到maxLength之间
        int length = RANDOM.nextInt(maxLength) + 1;
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 10000;
        boolean success = true;
        int maxStrLen = 10;
        for (int i = 0; i < testTimes; ++i) {
            String str = generateRandomString(maxStrLen);
            if (longestPalindromeSubseq(str) != longestPalindromeSubseq2(str)) {
                success = false;
                break;
            }
        }

        System.out.println(success? "success" : "failed");
        System.out.println("test end");
    }
}
