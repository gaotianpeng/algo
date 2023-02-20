package shuati.daily01;
import java.util.Arrays;

public class Code_001_CordCoverMaxPoint {
    /*
        给定一个有序数组arr，代表坐落在X轴上的点
        给定一个正数K，代表绳子的长度
        返回绳子最多压中几个点？
        即使绳子边缘处盖住点也算盖住
     */
    public static int maxPoint1(int[] arr, int len) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < arr.length; ++i) {
            int index = getNearestIndex(arr, i, arr[i] - len);
            max = Math.max(max, i - index + 1);
        }
        return max;
    }

    public static int getNearestIndex(int[] arr, int right, int val) {
        int left = 0;
        int index = 0;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if (arr[mid] >= val) {
                index = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return index;
    }

    public static int maxPoint2(int[] arr, int len) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = 0;
        int left = 0;
        int right = 0;
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            while (right < n && arr[right] - arr[left] <= len) {
                right++;
            }
            max = Math.max(max, right - left);
            left++;
        }

        return max;
    }

    /*
        for test
     */
    public static int test(int[] arr, int len) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < arr.length; ++i) {
            int pre = i - 1;
            while (pre >= 0 && arr[i] - arr[pre] <= len) {
                --pre;
            }
            max = Math.max(max, i - pre);
        }

        return max;
    }

    public static int[] generateArray(int len, int max) {
        int[] ans = new int[(int)(Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int)(Math.random() * max);
        }
        Arrays.sort(ans);
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int len = 100;
        int max = 1000;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int line_len = (int)(Math.random() * max);
            int[] arr = generateArray(len, max);
            int ans1 = maxPoint1(arr, line_len);
            int ans2 = maxPoint2(arr, line_len);
            int ans3 = test(arr, line_len);
            if (ans1 != ans3 || ans2 != ans3) {
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end...");
    }
}
