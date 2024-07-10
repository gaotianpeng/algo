package leetcode.all;

public class Code_0010_RegularExpressionMatching {
    public static boolean isValid(char[] str, char[] pattern) {
        for (int i = 0; i < str.length; ++i) {
            if (str[i] == '.' || str[i] == '*') {
                return false;
            }
        }

        for (int i = 0; i < str.length; ++i) {
            if (pattern[i] == '*' && (i == 0 || pattern[i-1] == '*')) {
                return false;
            }
        }

        return true;
    }

    public static boolean isMatch(String str, String pattern) {
        if (str == null || pattern == null) {
            return false;
        }

        char[] s = str.toCharArray();
        char[] p = pattern.toCharArray();

        return process(s, p, 0, 0);
    }

    /*
        str[si...] 能否被  pattern[pi...] 变出来
        潜台词，pi 位置，pattern[pi] != '*'
     */
    public static boolean process(char[] str, char[] pattern, int si, int pi) {
        // si 越界了
        if (si == str.length) {
            if (pi == pattern.length) {
                return true;
            }
            if (pi + 1 < pattern.length && pattern[pi+1] == '*') {
                return process(str, pattern, si, pi+2);
            }
            return false;
        }

        // si 没越界
        if (pi == pattern.length) {
            return si == str.length;
        }

        // si 没越界 pi 没越界
        if (pi + 1 >= pattern.length || pattern[pi + 1] != '*') {
            return ((str[si] == pattern[pi]) || (pattern[pi] == '.')) && process(str, pattern, si + 1, pi + 1);
        }

        // si 没越界，pi 没越界 pi + 1 *
        if (pattern[pi] != '.' && str[si] != pattern[pi]) {
            return process(str, pattern, si, pi+2);
        }

        // si 没越界，pi 没越界 pi+1 * [pi] 可配[si]
        if (process(str, pattern, si, pi + 2)) {
            return true;
        }

        while (si < str.length && (str[si] == pattern[pi] || pattern[pi] == '.')) {
            if (process(str, pattern, si + 1, pi + 2)) {
                return true;
            }
            si++;
        }

        return false;
    }

}
