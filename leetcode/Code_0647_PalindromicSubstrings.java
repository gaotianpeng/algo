package leetcode;

public class Code_0647_PalindromicSubstrings {
    /*
        https://leetcode.cn/problems/palindromic-substrings/
        leetcode 647 回文子串
            给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目
    */
    public static int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = manacherString(s);
        int n = str.length - 1;
        int[] p_radius_arr = new int[n];
        int p_center = -1;
        int right = -1;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            p_radius_arr[i] = right > i ? Math.min(right - i, p_radius_arr[2*p_center - i]) : 1;
            while (i + p_radius_arr[i] < str.length && i - p_radius_arr[i] > -1) {
                if (str[i+p_radius_arr[i]] == str[i-p_radius_arr[i]]) {
                    p_radius_arr[i]++;
                } else {
                    break;
                }
            }

            if (i + p_radius_arr[i]> right) {
                right = i + p_radius_arr[i];
                p_center = i;
            }
            ans += p_radius_arr[i]/2;
        }

        return ans;
    }

    public static char[] manacherString(String s) {
        int n = s.length();
        char[] str = s.toCharArray();
        char[] ans = new char[2*n + 1 + 2];
        int index = 0;
        for (int i = 1; i < ans.length - 1; ++i) {
            ans[i] = ((i-1) & 1) == 0 ? '#' : str[index++];
        }
        ans[0] = '*';
        ans[ans.length - 1] = '?';
        return ans;
    }
}
