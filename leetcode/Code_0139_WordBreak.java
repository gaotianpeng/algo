package leetcode;

import java.util.HashSet;
import java.util.List;

/*
    139 单词拆分
        给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s
        注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用

    示例
        输入: s = "leetcode", wordDict = ["leet", "code"]
        输出: true
        解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。

        输入: s = "applepenapple", wordDict = ["apple", "pen"]
        输出: true
        解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
        注意，你可以重复使用字典中的单词。
 */
public class Code_0139_WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        return process(s, 0, new HashSet<String>(wordDict)) != 0;
    }

    public static int process(String s, int index, HashSet<String> wordDict) {
        if (index == s.length()) {
            return 1;
        }

        int ways = 0;
        for (int end = index; end < s.length(); end++) {
            String pre = s.substring(index, end + 1);
            if (wordDict.contains(pre)) {
                ways += process(s, end + 1, wordDict);
            }
        }

        return ways;
    }

    public boolean wordBreak1(String s,  List<String> wordDict) {
        HashSet<String> set = new HashSet<>(wordDict);
        int n = s.length();
        int[] dp = new int[n+1];
        dp[n] = 1;
        for (int index = n - 1; index >= 0; index--) {
            int ways = 0;
            for (int end = index; end < n; end++) {
                String pre = s.substring(index, end + 1);
                if (set.contains(pre)) {
                    ways += dp[end + 1];
                }
            }
            dp[index] = ways;
        }

        return dp[0] != 0;
    }
}
