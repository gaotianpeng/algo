package tixi.daily17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/*
    打印一个字符串的全部排列, 要求不要出现重复的排列
 */
public class Code04_PrintAllPermutationsNoRepeat {

    public static List<String> permutation(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        char[] str = s.toCharArray();
        HashSet<String> set = new HashSet<>();
        process1(str, 0, set);
        for (String cur: set) {
            ans.add(cur);
        }

        return ans;
    }

    private static void process1(char[] str, int index, HashSet<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {
                swap(str, i, index);
                process1(str, index + 1, ans);
                swap(str, index, i);
            }
        }
    }

    public static List<String> permutation2(String s) {
        ArrayList<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        ArrayList<Character> rest = new ArrayList<>();
        char[] str = s.toCharArray();
        for (char ch: str) {
            rest.add(ch);
        }
        HashSet<String> set = new HashSet<>();
        String path = "";
        process2(rest, path, set);
        for (String cur: set) {
            ans.add(cur);
        }
        return ans;
    }

    private static void process2(ArrayList<Character> rest, String path, HashSet<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            for (int i = 0; i < rest.size(); i++) {
                char ch = rest.get(i);
                rest.remove(i);
                process2(rest, path + ch, ans);
                rest.add(i, ch);
            }
        }
    }

    private static void swap(char[] str, int i, int j) {
        char ch = str[i];
        str[i] = str[j];
        str[j] = ch;
    }

    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }

        char[] str = s.toCharArray();
        process3(str, 0, ans);
        return ans;
    }

    private static void process3(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            boolean[] is_visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                if (!is_visited[str[i]]) {
                    is_visited[str[i]] = true;
                    swap(str, index, i);
                    process3(str, index + 1, ans);
                    swap(str, i, index);
                }
            }
        }
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
            List<String> ans3 = permutation3(str);

            if (!isEqual(ans1, ans2)) {
                print(ans1);
                print(ans2);
                success = false;
                break;
            }

            if (!isEqual(ans1, ans3)) {
                print(ans1);
                print(ans3);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
