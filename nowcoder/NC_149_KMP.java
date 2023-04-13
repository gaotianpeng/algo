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
    public static int kmp (String s, String t) {
        if (s == null || t == null || t.length() == 0 || t.length() < s.length()) {
            return 0;
        }

        char[] str1 = t.toCharArray();
        char[] str2 = s.toCharArray();
        int n = str1.length;

        int cnt = 0;
        int[] next = getNextArray(s);

        //遍历主串和模式串
        for (int i = 0, j = 0; i < n; i++) {
            //只要不相等，回退到next数组记录的下一位
            while(j > 0 && str1[i] != str2[j]){
                j = next[j-1];
            }
            if(str1[i] == str2[j]) {
                j++;
            }

            if(j == str2.length) {
                cnt++;
                j = next[j-1];
            }
        }
        return cnt;
    }

    private static int[] getNextArray(String s) {
        int n = s.length();
        int[] next = new int[n];
        char[] str = s.toCharArray();

        int i = 1;
        int j = 0;
        while ( i < n) {
            while (j > 0 && str[i] != str[j]) {
                j = next[j-1];
            }
            if (str[i] == str[j]) {
                ++j;
            }
            next[i++] = j;
        }

        return next;
    }

    // for test
    public static int test(String s, String t) {
        if (s == null || t == null || t.length() == 0 || t.length() < s.length()) {
            return 0;
        }

        int ans = 0;
        int t_n = t.length();
        int s_n = s.length();
        for (int x = 0; x <= t_n - s_n; ++x) {
            String tmp = t.substring(x, x + s_n);
            if (tmp.compareTo(s) == 0) {
                ++ans;
            }
        }

        return ans;
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
        int s_size = 6;
        int t_size = 100;
        int testTimes = 5000000;
        for (int i = 0; i < testTimes; i++) {
            String s = getRandomString(possibilities, s_size);
            String t  = getRandomString(possibilities, t_size);
            if (test(s, t) != kmp(s, t)) {
                System.out.println(s);
                System.out.println(t);
                System.out.println(test(s, t));
                System.out.println(kmp(s, t));
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test end");
    }
}
