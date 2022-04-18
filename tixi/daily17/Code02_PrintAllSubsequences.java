package tixi.daily17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
    打印一个字符串的全部子序列
 */
public class Code02_PrintAllSubsequences {

    public static List<String> subSeq(String s) {
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

    public static List<String> subMoRepreat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process2(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur: set) {
            ans.add(cur);
        }
        return ans;
    }

    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);ßßß
            return;
        }

        String no = path;
        process2(str, index + 1, set, no);
        String yes = path + String.valueOf(str[index]);
        process2(str, index + 1, set, yes);
    }

    public static void main(String[] args) {
        String test = "acc";
        List<String> ans1 = subSeq(test);
        List<String> ans2 = subMoRepreat(test);
        for (String str: ans1) {
            System.out.println(str);
        }
        System.out.println("=========================");

        for (String str: ans2) {
            System.out.println(str);
        }
        System.out.println("=========================");
    }
}
