package tixi.daily38;

/*
    leetcode 829
    定义一种数：可以表示成若干（数量>1）连续正数和的数
    比如:
        5 = 2+3，5就是这样的数
        12 = 3+4+5，12就是这样的数
        1不是这样的数，因为要求数量大于1个、连续正数和
        2 = 1 + 1，2也不是，因为等号右边不是连续正数
        给定一个参数N，返回是不是可以表示成若干连续正数和的数
 */
public class Code03_MSumToN {
    public static boolean isMSum1(int num) {
        for (int start = 1; start <= num; start++) {
            int sum = start;
            for (int j = start + 1; j <= num; j++) {
                if (sum + j > num) {
                    break;
                }
                if (sum + j == num) {
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMSum2(int num) {
        // return num == (num & (~num + 1));
        // return num == (num & (-num));
        return (num & (num - 1)) != 0;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        boolean success = true;
        int maxN = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int apple = (int)Math.random()*(maxN + 1);
            if (isMSum1(apple) != isMSum2(apple)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" :"failed");
        System.out.println("test end");
    }
}
