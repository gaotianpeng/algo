package tixi.daily28;

public class Code01_Manacher {
    /*
        假设字符串str长度为N，想返回最长回文子串的长度,时间复杂度O(N)
     */
    public static int manacher(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // "12132" -> "#1#2#1#3#2#"
        char[] str = manacherString(s);
        // 回文半径的大小
        int[] pArr = new int[str.length];
        int C = -1;
        // 讲述中：R代表最右的扩成功的位置
        // coding：最右的扩成功位置的，再下一个位置
        int R = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < str.length; i++) { // 0 1 2
            // R第一个违规的位置，i>= R
            // i位置扩出来的答案，i位置扩的区域，至少是多大。
            pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]])
                    pArr[i]++;
                else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
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

    public static int test(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = manacherString(s);
        int n = str.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right < n && str[left] == str[right]) {
                --left;
                ++right;
            }
            ans = Math.max(right - left - 1, ans);
        }

        return ans/2;
    }

    // for test
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
        int str_size = 30;
        int test_times = 100000;
        for (int i = 0; i < test_times; ++i) {
            String s = getRandomString(posibilities, str_size);
            if (test(s) != manacher(s)) {
                System.out.println("test failed");
                System.out.println(s);
                System.out.println(test(s));
                System.out.println(manacher(s));
                break;
            }
        }

        System.out.println("test end");
    }
}
