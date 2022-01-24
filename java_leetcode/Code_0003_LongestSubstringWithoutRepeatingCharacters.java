package leetcode;

import java.util.HashMap;

/*
    0002 无重复字符的最长子串
        给定一个字符串s，请你找出其中不含有重复字符的最长子串的长度。


    示例
        输入: s = "abcabcbb"
        输出: 3
        解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。

        输入: s = "bbbbb"
        输出: 1
        解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。

        输入: s = ""
        输出: 0

    提示
        0 <= s.length <= 5 * 104
        s 由英文字母、数字、符号和空格组成
 */
public class Code_0003_LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < map.length; i++) {
            map[i] = -1;
        }
        int max_len = 0;
        int pre_len = -1;
        int cur = 0;
        for (int i = 0; i < str.length; i++) {
            pre_len = Math.max(pre_len, map[str[i]]);
            cur = i - pre_len;
            max_len = Math.max(cur, max_len);
            map[str[i]] = i;
        }

        return max_len;
    }

    public int lengthOfLongestSubstring1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        int max_len = 0;
        int pre_len = -1;
        int cur = 0;
        for (int i = 0; i < str.length; i++) {
            int tmp = -1;
            if (map.containsKey(str[i])) {
                tmp = map.get(str[i]);
            }
            pre_len = Math.max(tmp, pre_len);
            cur = i - pre_len;
            max_len = Math.max(cur, max_len);
            map.put(str[i], i);
        }

        return max_len;
    }
}
