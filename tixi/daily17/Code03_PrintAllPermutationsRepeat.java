package tixi.daily17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    打印一个字符串的全部排列
 */
public class Code03_PrintAllPermutationsRepeat {
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

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        char[] str = s.toCharArray();
        process2(str, 0, ans);
        return ans;
    }

    private static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                swap(str, i, index);
                process2(str, index + 1, ans);
                swap(str, index, i);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char ch = str[i];
        str[i] = str[j];
        str[j] = ch;
    }
    /*
        for test
     */
    public static boolean isEqual(List<String> list1, List<String> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }

        if (list1 == null && list2 != null) {
            return false;
        }

        if (list1 != null && list2 == null) {
            return false;
        }

        if (list1.size() != list2.size()) {
            return false;
        }

        String[] strs1 = new String[list1.size()];
        String[] strs2 = new String[list2.size()];
        list1.toArray(strs1);
        list2.toArray(strs2);
        Arrays.sort(strs1);
        Arrays.sort(strs2);

        for (int i = 0; i < strs1.length; i++) {
            if (!strs1[i].equals(strs2[i])) {
                return false;
            }
        }
        return true;
    }

    public static void print(List<String> strs) {
        if (strs == null) {
            return;
        }

        for (String cur: strs) {
            System.out.print(cur + " ");
        }
        System.out.println();
    }

    public static String generateRandomString(int max_len) {
        int len = (int)(Math.random() * (max_len  + 1));
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char)(97 + (int)(Math.random()*26));
        }

        return String.valueOf(str);
    }

    public static void main(String[] args) {
        String s = "acc";
		List<String> ans = permutation(s);
		for (String str : ans) {
			System.out.println(str);
		}

        System.out.println("test start....");
        int testTimes = 100000;
        int maxLen = 6;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            String str = generateRandomString(maxLen);
            List<String> ans1 = permutation(str);
            List<String> ans2 = permutation2(str);
            if (!isEqual(ans1, ans2)) {
                print(ans1);
                print(ans2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
