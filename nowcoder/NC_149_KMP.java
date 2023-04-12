package nowcoder;

public class NC_149_KMP {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * 计算模板串S在文本串T中出现了多少次
     * @param S string字符串 模板串
     * @param T string字符串 文本串
     * @return int整型
     */
    public static int kmp (String S, String T) {
        if (S == null || T == null || T.length() == 0 || T.length() < S.length()) {
            return 0;
        }

        int ans = 0;
        char[] str1 = T.toCharArray();
        char[] str2 = S.toCharArray();
        int[] next = getNextArray(str2);

        int x = 0;
        int y = 0;
        while (x < str1.length && y < str2.length) {
            while (x > 0 && str1[x] != str2[y]) {
                y = next[y - 1];
            }
            if (str1[x] == str2[y]) {
                ++y;
            }
            if (y == str2.length) {
                ++ans;
                y = next[y-1];
            }
            ++x;
        }

        return ans;
    }

    private static int[] getNextArray(char[] str) {
        if (str.length == 1) {
            return new int[] {0};
        }
        int n = str.length;
        int[] next = new int[n];
        next[0] = 0;
        int cn = 0;
        int i = 1;
        while (i < n) {
            if (str[i-1] == str[cn]) {
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String s = "aa";
        String t = "aaaaa";
//        System.out.println(kmp(s, t));
        int[] next = getNextArray(s.toCharArray());
        for (int i = 0; i < next.length; ++i) {
            System.out.print(next[i] +  " ");
        }
        System.out.println();
    }
}
