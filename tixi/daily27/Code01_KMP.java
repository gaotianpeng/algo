package tixi.daily27;

/*
    假设字符串str长度为N，字符串match长度为M，M <= N
    想确定str中是否有某个子串是等于match的。
    时间复杂度O(N)

    leetcode 26
        https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/
    */
public class Code01_KMP {
    /*
     * aaaaaaaaaaab  -> aaab
     */
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || s.length() == 0 || s.length() < m.length()) {
            return -1;
        }

        char[] str = s.toCharArray();
        char[] match = m.toCharArray();

        int[] next = getNextArray(match);
        int x = 0;
        int y = 0;
        /*
         *      x      x - y
         *  1)  ^       不变
         *  2） ^        ^
         *  3） 不变     ^
         * 
         *  最大2N，所以复杂度O(N)
         */
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                x++;
            } else {
                y = next[y];
            }
        }

        return y == match.length ? x - y: -1;
    }

    // acdbstacdtxeacdbstacdbk
    /*
     *   a a b a a b c a a b a a b c s
     * [-1,0,1,0,1,2,3,...]
     */
    public static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[] { -1};
        }

        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int cn = 0; // cn和i-1位置比是否相等，相等next[i] = cn + 1
        int i = 2; // 目前在哪个位置求next数组的值
        /*
         *      i       i - cn
         * 1)   ^         不变
         * 2)   不变        ^
         * 3)   ^           ^
         * 
         * 最大2M，所以复杂度O(M)  
         */
        while (i < match.length) {
            if (match[i-1] == match[cn]) {
                /*
                 * next[i] = cn+1;
                 * ++i;
                 * ++cn;   
                 *  
                 * i位置求出的值是cn+1, 所以cn++直接可以供i+1使用
                 */
                next[i++] = ++cn; // ++cn代表i位置next[i]，供i+1使用
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
        boolean success = true;
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
