package array;

// 暂不考虑数据溢出问题
public class Code01_PreSumOfArray {
    public static int[] preSum(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        int[] ans = new int[arr.length];
        ans[0] = arr[0];
        for (int i = 1; i < ans.length; i++) {
            ans[i] += ans[i-1] + arr[i];
        }

        return ans;
    }

    public static int[] test(int arr[]) {
        if (arr == null || arr.length == 0) {
            return null;
        }

        int[] ans = new int[arr.length];
        ans[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j <= i; j++) {
                ans[i] += arr[j];
            }
        }

        return ans;
    }

    public static int[] genRandomArr(int max_val, int max_len) {
        int len = (int)(Math.random() * (max_len + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*max_val);
        }
        return ans;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1 == null && arr2 != null) {
            return false;
        }

        if (arr1 != null && arr2 == null) {
            return false;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_val = 100;
        int max_len = 30;
        int test_times = 10000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[] arr = genRandomArr(max_val, max_len);
            int[] ans1 = preSum(arr);
            int[] ans2 = test(arr);
            if (!isEqual(ans1, ans2)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
