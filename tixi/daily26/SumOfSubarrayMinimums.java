package tixi.daily26;

/*
    https://leetcode.cn/problems/sum-of-subarray-minimums/
    907 给定一个数组arr，返回所有子数组最小值的累加和
 */
public class SumOfSubarrayMinimums {
    public static int sumSubarrayMins(int[] arr) {
        int[] left = leftNearLess(arr);
        int[] right = rightNearLess(arr);
        long ans = 0;
        for (int i = 0; i < arr.length; ++i) {
            long start = i - left[i];
            long end = right[i] - i;
            ans += start * end * (long)arr[i];
        }

        ans %= 1000000007;
        return (int)ans;
    }

    public static int[] leftNearLess(int[] arr) {
        int n = arr.length;
        int[] left = new int[n];
        for (int i = 0; i < n; ++i) {
            int ans = -1;
            for (int j = i - 1; j >= 0; j--) {
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

    public static int[] getRandomArray(int max_n, int max_val) {
        int n = (int)(Math.random() * max_n);
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = (int)(Math.random() * max_val);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = Integer.MAX_VALUE;
        int max_n = 100;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = getRandomArray(max_n, max_val);
            if (test(arr) != sumSubarrayMins(arr)) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
