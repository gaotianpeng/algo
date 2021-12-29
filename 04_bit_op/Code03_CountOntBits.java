package bit_op;

public class Code03_CountOntBits {
    /*
        计算n中的二进制表示型式中1的个数
     */
    public static int countOneBitsOfInt(int n) {
        int ret = 0;
        while (n != 0) {
            int right_one = n & (~n + 1);
            ++ret;
            n ^= right_one;
        }

        return ret;
    }

    public static int GenerateRandomNum(int max_val) {
        return (int)(Math.random()*max_val + 1) - (int)(Math.random()*max_val);
    }

    public static int test(int n) {
        int ret = 0;
        char[] str = Integer.toBinaryString(n).toCharArray();
        for (int i = 0; i < str.length; ++i) {
            if (str[i] == '1') {
                ++ret;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println("start test...");
        int test_times = 1000000;
        int max_val = Integer.MAX_VALUE;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int num = GenerateRandomNum(max_val);
            if (countOneBitsOfInt(num) != test(num)) {
                success = false;
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("end test");
    }
}
