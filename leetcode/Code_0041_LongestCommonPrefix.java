package leetcode;
/*
    0014 最长公共前缀
        编写一个函数来查找字符串数组中的最长公共前缀。
        如果不存在公共前缀，返回空字符串 ""。

    示例
        输入：strs = ["flower","flow","flight"]
        输出："fl"

    提示
        1 <= strs.length <= 200
        0 <= strs[i].length <= 200
        strs[i] 仅由小写英文字母组成
 */
public class Code_0041_LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        int min = Integer.MAX_VALUE;
        char[] chs = strs[0].toCharArray();
        for (String str: strs) {
            int index = 0;
            char[] tmp = str.toCharArray();
            while (index < tmp.length && index < chs.length) {
                if (chs[index] != tmp[index]) {
                    break;
                }
                index++;
            }
            min = Math.min(index, min);
            if (min == 0) {
                return "";
            }
        }

        return strs[0].substring(0, min);
    }
}
