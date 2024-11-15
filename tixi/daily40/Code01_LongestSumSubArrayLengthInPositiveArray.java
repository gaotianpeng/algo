package tixi.daily40;

import java.util.Random;

/*
    给定一个正整数组成的无序数组arr，给定一个正整数值K
    找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
    返回其长度
 */
public class Code01_LongestSumSubArrayLengthInPositiveArray {
    // 具有单调性，可以使用滑动窗口的思想来解决
    public static int getMaxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }

        int N = arr.length;
        int L = 0;
        int R = 0;
        int sum = arr[0];
        int ans = 0;
        while (R < N) {
            if (sum == K) {
                ans = Math.max(ans, R - L + 1);
                sum -= arr[L++];
            } else if (sum < K) {
                ++R;
                if (R == N) {
                    break;
                }
                
                sum += arr[R];
            } else {
                sum -= arr[L++];
            }
        }

        return ans;
    }


    // for test
    public static int test(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K <= 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (valid(arr, i, j, K)) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }

    public static boolean valid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == K;
    }

    public static int[] generatePositiveArray(int maxLen, int maxVal) {
        if (Math.random() < 0.01) {
            return null;
        }

        if (maxLen <= 0 || maxVal <= 0) {
            return new int[0];
        }

        Random random = new Random();
        int len = random.nextInt(maxLen) + 1;  // 生成1到maxLen之间的随机长度
        int[] array = new int[len];

        for (int i = 0; i < len; i++) {
            array[i] = random.nextInt(maxVal) + 1;  // 生成1到maxVal之间的随机正整数
        }

        return array;
    }

    // for test
    public static void printArray(int[] arr) {
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 10000;
        int maxValue = 100;
        int maxLen = 50;
        for (int i = 0; i < testTimes; ++i) {
            int K = (int) (Math.random() * maxValue) + 1;
            int[] arr = generatePositiveArray(maxLen, maxValue);
            int ans1 = getMaxLength(arr, K);
            int ans2 = test(arr, K);
            if (ans1 != ans2) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
