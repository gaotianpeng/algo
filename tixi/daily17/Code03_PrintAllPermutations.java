package tixi.daily17;

import java.util.ArrayList;
import java.util.List;

/*
    打印一个字符串的全部排列
 */
public class Code03_PrintAllPermutations {

    public static List<String> permutation(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char ch: str) {
            rest.add(ch);
        }
        String path = "";
        process(rest, path, ans);
        return ans;
    }

    private static void process(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int n = rest.size();
            for (int i = 0; i < n; i++) {
                char cur = rest.get(i);
                rest.remove(i);
                process(rest, path + cur, ans);
                rest.add(i, cur);
            }
        }
    }


    public static void print(List<String> strs) {
        if (strs == null) {
            return;
        }

        for (String cur: strs) {
            System.out.println(cur);
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        List<String> ans = permutation("abc");
        print(ans);
        System.out.println("test end");
    }
}
