package tixi.daily19;
import java.util.HashMap;
import java.util.Random;

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
    public static int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return 0;
        }


        int ans = process(stickers, target);
        return ans == Integer.MAX_VALUE ? -1: ans;
    }

    private static int process(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            String rest = minus(target, stickers[i]);
            if (rest.length() != target.length()) {
                min = Math.min(min, process(stickers, rest));
            }
        }  

        return min + (min == Integer.MAX_VALUE ? 0: 1);
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

    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process2(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process2(int[][] stickers, String t, HashMap<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
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

    public static String[] generateRandomStringArray(int maxStrLen, int maxArrLen) {
        int arrayLength = RANDOM.nextInt(maxArrLen) + 1;
        String[] randomStrings = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            randomStrings[i] = generateRandomString(maxStrLen);
        }
        return randomStrings;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 10000;
        boolean success = true;
        int maxStrLen = 10;
        int maxArrLen = 10;
        for (int i = 0; i < testTimes; ++i) {
            String target = generateRandomString(maxStrLen);
            String[] stickers = generateRandomStringArray(maxStrLen, maxArrLen);
            if (minStickers(stickers, target) != minStickers2(stickers, target)) {
                success = false;
                break;
            }
        }

        System.out.println(success? "success" : "failed");
        System.out.println("test end");
    }
}
