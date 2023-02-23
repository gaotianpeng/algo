package leetcode;

/*
    340. 至多包含 K 个不同字符的最长子串
        给定一个字符串 s ，找出 至多 包含 k 个不同字符的最长子串 T。

    示例
        输入: s = "eceba", k = 2
        输出: 3
        解释: 则 T 为 "ece"，所以长度为 3。

        输入: s = "aa", k = 1
        输出: 2
        解释: 则 T 为 "aa"，所以长度为 2。
 */
public class Code_0340_LongestSubstringWithAtMostKDistinctCharacters {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k < 1) {
            return 0;
        }

        char[] str = s.toCharArray();
        int n = str.length;
        int[] count = new int[256];
        int diff = 0;
        int right = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            while (right < n && (diff < k || (diff == k && count[str[right]] > 0))) {
                diff += count[str[right]] == 0 ? 1: 0;
                count[str[right++]]++;
            }
            ans = Math.max(ans, right - i);
            diff -= count[str[i]] == 1 ? 1: 0;
            count[str[i]]--;
        }

        return ans;
    }
}
