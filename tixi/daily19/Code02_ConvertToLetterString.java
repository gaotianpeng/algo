package tixi.daily19;
/*
    规定1和A对应、2和B对应、3和C对应...26和Z对应
    那么一个数字字符串比如"111”就可以转化为:
    "AAA"、"KA"和"AK"
    给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 */
public class Code02_ConvertToLetterString {
    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    /*
        str[0....i-1] 已经处理好
        str[i...] 待处理
     */
    private static int process(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }

        if (str[i] == '0') {
            return 0;
        }

        int ways = process(str, i + 1);
        if (i + 1 < str.length && (str[i] - '0') * 10 + str[i+1] - '0' < 27) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    public static int number2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int n = s.length();
        char[] str = s.toCharArray();
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < str.length && (str[i] - '0') * 10 + str[i+1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }
        }

        return dp[0];
    }

    /*
        for test
     */
    public static String randomString(int max_len) {
        int len = (int)(Math.random() * (max_len + 1));
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char)((int)(Math.random() * 10) + '0');
        }

        return String.valueOf(str);
    }

    public static void main(String[] args) {
        System.out.println("test start....");
        int test_times = 500000;
        int max_len = 20;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            String str = randomString(max_len);
            if (number(str) != number2(str)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
