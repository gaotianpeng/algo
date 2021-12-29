package bit_op;

public class Code02_ExtractRightOne {
    // 提取整数中最右侧的1
    public static int extractRightOne(int n) {
        return n & (~n + 1);
    }

    public static int test(int n) {
        char[] str = Integer.toBinaryString(n).toCharArray();
        int right_one_idx = -1;
        for (int i = str.length - 1; i >= 0; i--) {
            if (str[i] == '1') {
                right_one_idx = str.length - 1- i;
                break;
            }
        }

        if (right_one_idx != -1) {
            return 1 << right_one_idx;
        }

        return 0;
    }

    public static int GenerateRandomNum(int max_val) {
        return (int)(Math.random()*max_val + 1) - (int)(Math.random()*max_val);
    }

    public static void main(String[] args) {
        int test_times = 100000;
        int max_val = Integer.MAX_VALUE;

        for (int i = 0; i < test_times; i++) {
            int num = GenerateRandomNum(max_val);
            if (extractRightOne(num) != test(num)) {
                System.out.println("failed");
                break;
            }
        }

        System.out.println("nice");
    }
}
