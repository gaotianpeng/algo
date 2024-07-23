package tixi.daily04;
/*
    在一个数组中，
    对于每个数num，求有多少个后面的数 * 2 依然<num，求总个数
    比如：[3,1,7,0,2]
    3的后面有：1，0
    1的后面有：0
    7的后面有：0，2
    0的后面没有
    2的后面没有
    所以总共有5个
 */
public class Code08_BiggerThanTwice2 {
    public static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int mergeSize = 1;
        int n = arr.length;
        int ans = 0;
        while (mergeSize < n) {
            int left = 0;
            while (left < n) {
                if (mergeSize > n - left) {
                    break;
                }

                int mid = left + mergeSize - 1;
                int right = mid + Math.min(mergeSize, n - mid - 1);
                ans += merge(arr, left, mid, right);
                left = right + 1;
            }

            if (mergeSize > n/2) {
                break;
            }
            mergeSize <<= 1;
        }

        return ans;
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        int ans = 0;
        int winR = mid + 1;
        for (int i = left; i <= mid; i++) {
            while (winR <= right && (long)arr[i] > 2*(long)arr[winR]) {
                winR++;
            }
            ans += winR - mid - 1;
        }

        int[] helper = new int[right - left + 1];
        int leftPos = left;
        int rightPos = mid + 1;
        int index = 0;

        while (leftPos <= mid && rightPos <= right) {
            helper[index++] = arr[leftPos] <= arr[rightPos] ?
                    arr[leftPos++] : arr[rightPos++];
        }

        while (leftPos <= mid) {
            helper[index++] = arr[leftPos++];
        }

        while (rightPos <= right) {
            helper[index++] = arr[rightPos++];
        }

        for (int i = 0; i < helper.length; i++) {
            arr[left + i] = helper[i];
        }
        return ans;
    }

    /*
        for test
     */
    public static int test(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if ((long)arr[i] > ((long)arr[j]<<1)) {
                    ++ans;
                }
            }
        }

        return ans;
    }

    public static int[] generateRandomArray(int maxVal, int maxLen) {
        int len = (int)(Math.random()*(maxLen + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = generateRandomNum(maxVal);
        }

        return ans;
    }

    public static int generateRandomNum(int maxVal) {
        return (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*maxVal);
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int maxVal = 30;
        int maxLen = 25;
        int testTimes = 1000000;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxVal, maxLen);
            int[] arr1 = copyArray(arr);
            int ans1 = biggerTwice(arr);
            int ans2 = test(arr1);
            if (ans1 != ans2) {
                System.out.println(ans1);
                System.out.println(ans2);
                printArray(arr1);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
