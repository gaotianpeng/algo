package shuati.daily01;
/*
    给定一个非负整数num，
    如何不用循环语句，
    返回>=num，并且离num最近的，2的某次方
 */
public class Code_003_Near2Power {
    public static int near2PowerNumber(int n) {
        n--;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : n + 1;
    }

    /*
        for test
     */
    public static int test(int n) {
        int ans = 0;
        while (get2Power(ans) <= n) {
            ++ans;
        }

        if (get2Power(ans) != n) {
            ++ans;
        }
        return ans;
    }

    public static int generateNumber(int max_val) {
        return (int)(Math.random() * (max_val + 1));
    }

    public static int get2Power(int n) {
        return (int)(Math.pow(2, n));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int max_val = Integer.MAX_VALUE;
        int test_times = 1000000;
        for (int i = 0; i < test_times; i++) {
            int val = generateNumber(max_val);
            if (test(val) != near2PowerNumber(val)) {
                success = false;
                System.out.println(test(val));
                System.out.println(near2PowerNumber(val));
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end...");
    }
}
