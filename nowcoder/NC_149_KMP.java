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
        // write code here
        // write code here
        if (S == null || T == null || T.length() == 0 || T.length() < S.length()) {
            return 0;
        }

        int ans = 0;
        char[] str1 = T.toCharArray();
        char[] str2 = S.toCharArray();
        int[] next = getNextArray(str2);

        int pre = 0;
        for (int i = 0; i < str1.length; ++i) {
            while (pre > 0 && str1[i] != str2[pre]) {
                pre = next[pre-1];
            }
            if (str1[i] == str2[pre]) {
                pre++;
            }
            if (pre == str2.length) {
                ++ans;
                pre = next[pre-1];
            }
        }

        return ans;
    }

    private static int[] getNextArray(char[] str) {
        int[] next = new int[str.length];
        int pre = 0;
        next[0] = 0;
        for(int sufix = 1; sufix< str.length; sufix++){
            while(pre > 0 && str[pre]!= str[sufix]){
                pre = next[sufix-1];
            }
            if(str[pre] == str[sufix]){
                pre++;
            }
            next[sufix] = pre;
        }

        return next;
    }
    public static void main(String[] args) {
        String s = "aa";
        String t = "aaaaa";
        System.out.println(kmp(s, t));
    }
}
