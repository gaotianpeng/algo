package tixi.daily27;

public class Code01_KMP {
    /*
        假设字符串str长度为N，字符串match长度为M，M <= N
        想确定str中是否有某个子串是等于match的。
        时间复杂度O(N)

        leetcode 26
        https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
     */
    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s1.length() < s2.length()) {
            return -1;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int[] next = getNextArray(str2);
        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            if (str1[x] == str2[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == str2.length ? x - y: -1;
    }

    public static int[] getNextArray(char[] str2) {
        if (str2.length == 1) {
            return new int[] { -1};
        }

        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0;
        int i = 2;
        while (i < str2.length) {
            if (str2[i-1] == str2[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }

        return next;
    }

    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test end");
    }
}
