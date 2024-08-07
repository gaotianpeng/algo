package tixi.daily40;

import java.util.Random;

/*
    给定一个整数组成的无序数组arr，值可能正、可能负、可能0
    给定一个整数值K
    找到arr的所有子数组里，哪个子数组的累加和<=K，并且是长度最大的
    返回其长度
 */
public class Code03_LongestLessSumSubArrayLength {
    /*
     *  arr         [ 3, 7, 4, -6, 6, 3, -2, 0, 7, -3, 2]
     * minSums      [                    -2, 0, 4, -3, 2]
     * minSumEnds   [                     8, 8, 9,  9,10]          
     */
    public static int maxLengthAwesome(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        // minSum以i开头，最小的累加和
        int[] minSums = new int[N];
        // minSum以i开头，最小的累加和数组的结尾位置
        int[] minSumEnds = new int[N];
        minSums[N - 1] = arr[N - 1];
        minSumEnds[N - 1] = N - 1;
        for (int i = N - 2; i >= 0; i--) {
            if (minSums[i + 1] < 0) {
                minSums[i] = arr[i] + minSums[i + 1];
                minSumEnds[i] = minSumEnds[i + 1];
            } else {
                minSums[i] = arr[i];
                minSumEnds[i] = i;
            }
        }
        // 迟迟扩不进来那一块儿的开头位置
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            // while循环结束之后：
            // 1) 如果以i开头的情况下，累加和<=k的最长子数组是arr[i..end-1]，看看这个子数组长度能不能更新res；
            // 2) 如果以i开头的情况下，累加和<=k的最长子数组比arr[i..end-1]短，更新还是不更新res都不会影响最终结果；
            while (end < N && sum + minSums[end] <= k) {
                sum += minSums[end];
                end = minSumEnds[end] + 1;
            }
            ans = Math.max(ans, end - i);
            if (end > i) { // 还有窗口，哪怕窗口没有数字 [i~end) [4,4)
                sum -= arr[i];
            } else { // i == end,  即将 i++, i > end, 此时窗口概念维持不住了，所以end跟着i一起走
                end = i + 1;
            }
        }
        return ans;
    }

    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int[] h = new int[arr.length + 1];
        int sum = 0;
        h[0] = sum;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            h[i + 1] = Math.max(sum, h[i]);
        }
        sum = 0;
        int res = 0;
        int pre = 0;
        int len = 0;
        for (int i = 0; i != arr.length; i++) {
            sum += arr[i];
            pre = getLessIndex(h, sum - k);
            len = pre == -1 ? 0 : i - pre + 1;
            res = Math.max(res, len);
        }
        return res;
    }

    public static int getLessIndex(int[] arr, int num) {
        int low = 0;
        int high = arr.length - 1;
        int mid = 0;
        int res = -1;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] >= num) {
                res = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return res;
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

        return sum <= K;
    }

    public static int[] generateRandomArray(int maxLen, int maxVal) {
        if (Math.random() < 0.01) {
            return null;
        }

        if (maxLen <= 0 || maxVal <= 0) {
            return new int[0];
        }

        Random random = new Random();
        int len = random.nextInt(maxLen) + 1;
        int[] res = new int[len];
        for (int i = 0; i != res.length; i++) {
            res[i] = (int) (Math.random() * maxVal) - (maxVal / 3);
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 1000000;
        boolean success = true;
        int maxValue = 100;
        int maxLen = 50;

        for (int i = 0; i < testTimes; i++) {
            int len = (int) (Math.random() * maxLen) + 1;
            int[] arr = generateRandomArray(len, maxValue);
            int k = (int) (Math.random() * maxValue) +1;
            if (maxLengthAwesome(arr, k) != test(arr, k)) {
                success = false;
                break;
            }
            if (maxLength(arr, k) != test(arr, k)) {
                success = false;
                break;
            }
        }

        System.out.println(success? "success": "failed");
        System.out.println("test end");
    }
}
