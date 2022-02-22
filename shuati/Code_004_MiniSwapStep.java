package shuati;
/*
    一个数组中只有两种字符'G'和’B’，
    可以让所有的G都放在左侧，所有的B都放在右侧
    或者可以让所有的G都放在右侧，所有的B都放在左侧
    但是只能在相邻字符之间进行交换操作，
    返回至少需要交换几次
*/
public class Code_004_MiniSwapStep {
    public static int minStep(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        char[] str = s.toCharArray();
        int step_g = 0;
        int index_g = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step_g += i - index_g;
                index_g++;
            }
        }

        int step_b = 0;
        int index_b = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'B') {
                step_b += i - index_b;
                index_b++;
            }
        }
        return Math.min(step_g, step_b);
    }

    /*
        for test
     */
    public static int test(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        char[] str = s.toCharArray();
        int step_g = 0;
        int step_b = 0;
        int index_g = 0;
        int index_b = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i] == 'G') {
                step_g += i - index_g;
                index_g++;
            } else {
                step_b += i - index_b;
                index_b++;
            }
        }

        return Math.min(step_g, step_b);
    }

    public static String randomString(int max_len) {
        char[] str = new char[(int)(Math.random() * max_len)];
        for (int i = 0; i < str.length; i++) {
            str[i] = Math.random() < 0.5 ? 'G' : 'B';
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int max_len = 40;
        int test_times = 1000000;
        for (int i = 0; i < test_times; i++) {
            String str = randomString(max_len);
            if (minStep(str) != test(str)) {
                success = false;
                System.out.println(minStep(str));
                System.out.println(test(str));
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end...");
    }
}
