package tixi.daily17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Code02_PrintAllSubsequencesRepeat {
    public static List<String> subSeqRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process(str, 0, ans, path);
        return ans;
    }

    /*
        str: 固定参数
        index: 来到了str[index]字符, str[0...index-1]已经走过了，之前的决定都在path上
            str[index...] 还能做决定
        ans: 把所有生成了子序列，放入到ans里去
     */
    public static void process(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        process(str, index + 1, ans, path);
        process(str, index + 1, ans, path + String.valueOf(str[index]));
    }

    // for test
    public static List<String> test(String s) {
        List<String> ans = new ArrayList<>();
        ans.add(""); // 添加空子序列

        for (char c : s.toCharArray()) {
            int n = ans.size();
            for (int i = 0; i < n; i++) {
                ans.add(ans.get(i) + c);
            }
        }

        return ans;
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
            List<String> ans1 = subSeqRepeat(str);
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
