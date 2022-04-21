package tixi.daily19;
/*
    leetcode 691: 贴纸拼图
        给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
        arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
        返回需要至少多少张贴纸可以完成这个任务。
        例子：str= "babac"，arr = {"ba","c","abcd"}
        ba + ba + c  3  abcd + abcd 2  abcd+ba 2
        所以返回2
 */
public class Code03_StickersToSpellWord {
    public int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return -1;
        }

        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1: ans;
    }

    private static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (String str: stickers) {
            String rest = minus(target, str);
            if (rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }

        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    private static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        for (char ch: str1) {
            count[ch - 'a']++;
        }

        for (char ch: str2) {
            count[ch - 'a']--;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char)(i + 'a'));
                }
            }
        }

        return builder.toString();
    }
}
