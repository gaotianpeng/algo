package tixi.daily26;

public class Code03_ClimbingStairs {
    /*
        一个人可以一次往上迈1个台阶，也可以迈2个台阶, 返回这个人迈上N级台阶的方法数

        leetcode 70
        https://leetcode.cn/problems/climbing-stairs/
     */
    public static int climbStairs(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int res = 2;
        int pre = 1;
        int tmp = 0;
        for (int i = 3; i <= n; i++) {
            tmp = res;
            res = res + pre;
            pre = tmp;
        }
        return res;
    }

    public static int test(int n) {
        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        return test(n-1) + test(n-2);
    }

    public static int randomNumber(int n) {
        return (int)(Math.random() * n) + 1;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 20;
        for (int i = 0; i < test_times; ++i) {
            int n = randomNumber(max_val);
            if (climbStairs(n) != test(n)) {
                System.out.println("test failed");
                break;
            }
        }
        System.out.println("test end");
    }
}
