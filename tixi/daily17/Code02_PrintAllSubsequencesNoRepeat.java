package tixi.daily17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

// 打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
public class Code02_PrintAllSubsequencesNoRepeat {
    public static List<String> subSeqNoRepeat(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null) {
            return ans;
        }

        String path = "";
        HashSet<String> set = new HashSet<>();
        process(s.toCharArray(), 0, path, set);
        for (String cur: set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process(char[] str, int index, String path, HashSet<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }

        process(str, index + 1, path, ans);
        process(str, index + 1, path + String.valueOf(str[index]), ans);
    }

    // for test
    /*
        使用 Set 来存储子序列，确保没有重复。
        n 是输入字符串的长度。
        通过循环生成所有可能的子序列。循环的范围是 0 到 2^n - 1，
        其中 n 是字符串的长度。这种方法利用二进制数的每一位来决定是否包含字符串的每一个字符。
        对于每个整数 i，使用二进制掩码来构建子序列。对于每个 j，如果 i 的第 j 位为 1，则包含字符串的第 j 个字符。
        将生成的子序列添加到 resultSet 中。
     */
    public static List<String> test(String s) {
        Set<String> resultSet = new HashSet<>();
        int n = s.length();

        // 使用二进制掩码生成所有子序列
        for (int i = 0; i < (1 << n); i++) {
            StringBuilder subsequence = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subsequence.append(s.charAt(j));
                }
            }
            resultSet.add(subsequence.toString());
        }

        return new ArrayList<>(resultSet);
    }

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String randomString(int maxLen) {
        if (maxLen <= 0) {
            throw new IllegalArgumentException("maxLen must be positive");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder(maxLen);

        for (int i = 0; i < maxLen; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    public static boolean isEqual(List<String> list1, List<String> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        Map<String, Integer> frequencyMap1 = getFrequencyMap(list1);
        Map<String, Integer> frequencyMap2 = getFrequencyMap(list2);

        return frequencyMap1.equals(frequencyMap2);
    }

    private static Map<String, Integer> getFrequencyMap(List<String> list) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String str : list) {
            frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
        }
        return frequencyMap;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 10000;
        int maxStrLen = 10;
        for (int i = 0; i < testTimes; ++i) {
            String str = randomString(maxStrLen);
            List<String> ans1 = subSeqNoRepeat(str);
            List<String> ans2 = test(str);

            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end");
    }
}
