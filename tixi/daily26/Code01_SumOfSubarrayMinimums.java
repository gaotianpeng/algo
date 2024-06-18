package tixi.daily26;

/*
    https://leetcode.cn/problems/sum-of-subarray-minimums/
    leetcode 907 给定一个数组arr，返回所有子数组最小值的累加和
 */
public class Code01_SumOfSubarrayMinimums {
    public static int sumSubarrayMins(int[] arr) {
        int[] left = nearLeftEqual(arr);
        int[] right = nearRight(arr);

        long ans = 0;
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += end * start * (long)arr[i];
        }
        ans %= 1000000007;
        return (int)ans;
    }

    public static int[] nearLeftEqual(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        int size = 0;
        int[] stack = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            while (size != 0 && arr[i] <= arr[stack[size - 1]]) {
                left[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            left[stack[--size]] = -1;
        }
        return left;
    }

    public static int[] nearRight(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        int size = 0;
        int[] stack = new int[n];
        for (int i = 0; i < n; i++) {
            while (size != 0 && arr[stack[size - 1]] > arr[i]) {
                right[stack[--size]] = i;
            }
            stack[size++] = i;
        }
        while (size != 0) {
            right[stack[--size]] = n;
        }
        return right;
    }

    public static int sumSubarrayMins1(int[] arr) {
        int[] left = leftNearLessEqual(arr);
        int[] right = rightNearLess(arr);
        int n = arr.length;
        long ans = 0;
        for (int i = 0; i < n; ++i) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long)arr[i];
        }
        ans %= 1000000007;
        return (int)ans;
    }

    public static int[] leftNearLessEqual(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        for (int i = 0; i < n; ++i) {
            int ans = -1;
            for (int j = i - 1; j >= 0; --j) {
                if (arr[j] <= arr[i]) {
                    ans = j;
                    break;
                }
            }
            left[i] = ans;
        }
        return left;
    }

    public static int[] rightNearLess(int[] arr) {
        int n = arr.length;
        int[] right = new int[n];
        for (int i = 0; i < n; ++i) {
            int ans = n;
            for (int j = i + 1; j < n; ++j) {
                if (arr[i] > arr[j]) {
                    ans = j;
                    break;
                }
            }
            right[i] = ans;
        }

        return right;
    }

    public static int test(int[] arr) {
        long ans = 0;
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i; j < n; ++j) {
                long min = arr[i];
                for (int k = i + 1; k <= j; ++k) {
                    min = Math.min(min, arr[k]);
                }
                ans += min;
            }
        }
        ans %= 1000000007;
        return (int)ans;
    }

    public static int[] getRandomArray(int maxN, int maxVal) {
        int n = (int)(Math.random() * maxN);
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = (int)(Math.random() * maxVal);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = Integer.MAX_VALUE;
        int maxN = 100;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = getRandomArray(maxN, maxVal);
            if (test(arr) != sumSubarrayMins(arr)) {
                System.out.println("best test failed");
                break;
            }
            if (test(arr) != sumSubarrayMins1(arr)) {
                System.out.println("best test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
