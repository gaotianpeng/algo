package leetcode.all;
/*
    76 最小覆盖字符串
        给你一个字符串s 、一个字符串t. 返回s中涵盖t所有字符的最小子串. 如果s中不存在涵盖t所有字符的子串，则返回空字符串 ""

    注意：
        对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
        如果 s 中存在这样的子串，我们保证它是唯一的答案

    示例
        输入：s = "ADOBECODEBANC", t = "ABC"
        输出："BANC"

        输入：s = "a", t = "a"
        输出："a"
 */
public class Code_0076_MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) {
            return "";
        }

        char[] str = s.toCharArray();
        char[] target = t.toCharArray();
        int[] map = new int[256];
        for (char ch: target) {
            map[ch]++;
        }

        int all = target.length;
        int left = 0;
        int right = 0;
        int min_len = -1;
        int ans_left = -1;
        int ans_right = -1;
        while (right != str.length) {
            map[str[right]]--;
            if (map[str[right]] >= 0) {
                all--;
            }
            if (all == 0) {
                while (map[str[left]] < 0) {
                    map[str[left++]]++;
                }
                if (min_len == -1 || min_len > right - left + 1) {
                    min_len = right - left + 1;
                    ans_left = left;
                    ans_right = right;
                }
                all++;
                map[str[left++]]++;
            }
            right++;
        }

        return min_len == -1 ? "" : s.substring(ans_left, ans_right + 1);
    }
}
