package leetcode.all;

public class Code_0005_LongestPalindromicSubstring {
    /*
        https://leetcode.cn/problems/longest-palindromic-substring/
        leetcode 05 最长回文子串
            给你一个字符串 s，找到 s 中最长的回文子串
     */
    public static String longestPalindrome(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] charArr = manacherString(str);
        int[] pArr = new int[charArr.length];
        int index = -1;
        int pR = -1;
        int max = Integer.MIN_VALUE;
        int mid = 0;
        for (int i = 0; i != charArr.length; i++) {
            pArr[i] = pR > i ? Math.min(pArr[2 * index - i], pR - i) : 1;
            while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
                if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > pR) {
                pR = i + pArr[i];
                index = i;
            }
            if (max < pArr[i]) {
                max = pArr[i];
                mid = i;
            }
        }

        mid = (mid - 1) / 2;
        max = max - 1;
        return str.substring((max & 1) == 0 ? mid - (max / 2) + 1 : mid - (max / 2), mid + (max / 2) + 1);
    }

    public static char[] manacherString(String str) {
        char[] charArr = str.toCharArray();
        char[] res = new char[str.length() * 2 + 1];
        int index = 0;
        for (int i = 0; i != res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : charArr[index++];
        }
        return res;
    }

    public static String test(String s) {
        if ( s == null || s.length() == 0) {
            return "";
        }

        char[] str = manacherString(s);
        int n = str.length;
        int max = Integer.MIN_VALUE;
        int mid = 0;
        for (int i = 0; i < n; ++i) {
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right < n && str[left] == str[right]) {
                --left;
                ++right;
            }
            if (right - left - 1 > max) {
                max = right - left - 1;
                mid = i;
            }
        }

        max = max / 2;
        mid = (mid - 1)/2;

        int start = (max&1) == 0 ? mid - max/2 + 1 : mid - max/2;
        int end = mid + max/2 + 1;
        return s.substring(start, end);
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
        int posibilities = 5;
        int str_size = 50;
        int test_times = 500000;
        for (int i = 0; i < test_times; ++i) {
            String s = getRandomString(posibilities, str_size);
            if (test(s).compareTo(longestPalindrome(s)) != 0) {
                break;
            }
        }

        System.out.println("test end");
    }
}
