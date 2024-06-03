package tixi.daily13;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/*
    给定一个由字符串组成的数组strs，
    必须把所有的字符串拼接起来，
    返回所有可能的拼接结果中，字典序最小的结果
 */
public class Code05_LowestLexicography {

    public static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        Arrays.sort(strs, new MyComparator());
        String ans = "";

        for (int i = 0; i < strs.length; ++i) {
            ans += strs[i];
        }
        return ans;
    }

    private static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }


    /*
        for test
     */

    public static String test(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.size() == 0 ? "" : ans.first();
    }

    private static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur: next) {
                ans.add(first + cur);
            }
        }

        return ans;
    }

    public static String[] removeIndexString(String[] arr, int index) {
        int n = arr.length;
        String[] ans = new String[n - 1];
        int ansIndex = 0;
        for (int i = 0; i < n; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int)(Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int)(Math.random() * 5);
            ans[i] = (Math.random() <= 0.5) ? (char)(65 + value) : (char)(97 + value);
        }
        return String.valueOf(ans);
    }

    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int)(Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }

    public static String[] copyStringArray(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 50000;
        int arrLen = 6;
        int strLen = 5;
        for (int i = 0; i < testTimes; i++) {
            String[] arr1 = generateRandomStringArray(arrLen, strLen);
            String[] arr2 = copyStringArray(arr1);
            if (!lowestString(arr1).equals(test(arr2))) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
