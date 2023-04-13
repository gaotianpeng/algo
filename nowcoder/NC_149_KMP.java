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
        int m = S.length(), n = T.length();
        if (m > n || n == 0) {
            return 0;
        }

        //初始化计数，获取next数组
        int cnt = 0;
        int[] next = getNext(S);

        //遍历主串和模式串
        for (int i = 0,j = 0; i < n; i++){
            //只要不相等，回退到next数组记录的下一位
            while(j > 0 && T.charAt(i) != S.charAt(j)){
                j = next[j-1];
            }
            if(T.charAt(i) == S.charAt(j)) {
                j++;
            }

            //如果j为m，说明完全匹配一次
            if(j == m){
                //计数加一，索引回退到next数组记录的下一位
                cnt++;
                j = next[j-1];
            }
        }
        return cnt;
    }

    //确定next数组
    private static int[] getNext(String S){
        int m = S.length();
        int[] next=new int[m];
        for(int i = 1, j = 0; i < m; i++){
            //只要不相等，回退到next数组记录的下一位
            while(j > 0 && S.charAt(i) != S.charAt(j)){
                j = next[j-1];
            }
            //前缀索引后移
            if(S.charAt(i) == S.charAt(j)) {
                j++;
            }
            //确定应该回退到的下一个索引
            next[i] = j;
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
        int t_size = 12;
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
