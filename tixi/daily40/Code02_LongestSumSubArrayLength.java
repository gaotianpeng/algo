package tixi.daily40;

import java.util.HashMap;
import java.util.Random;

/*
    给定一个整数组成的无序数组arr，值可能正、可能负、可能0
    给定一个整数值K
    找到arr的所有子数组里，哪个子数组的累加和等于K，并且是长度最大的
    返回其长度
 */
public class Code02_LongestSumSubArrayLength {
    public static int maxLength(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // key: 前缀和
        // value : 最早出现key所代表的前缀和的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (map.containsKey(sum - K)) {
                len = Math.max(i - map.get(sum - K), len);
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }


    // for test
    public static int test(int[] arr, int K) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int ans = 0;
        for (int i = 0; i < N; ++i) {
            for (int j = i; j < N; ++j) {
                if (isValid(arr, i, j , K)) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    public static boolean isValid(int[] arr, int L, int R, int K) {
        int sum = 0;
        for (; L <= R; ++L) {
            sum += arr[L];
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
            int len = (int) (Math.random() * maxLen) + 1;
            int K = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
            int[] arr = generatePositiveArray(maxLen, maxValue);
            int ans1 = maxLength(arr, K);
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