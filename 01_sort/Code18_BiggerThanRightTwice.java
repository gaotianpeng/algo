package sort;

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
public class Code18_BiggerThanRightTwice {

    public static int biggerTwice(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left)>>1);
        return process(arr, left, mid)
                + process(arr, mid + 1, right)
                + merge(arr, left, mid, right);
    }

    public static int merge(int[] arr, int left, int mid, int right) {
       int ans = 0;

       int window_r = mid + 1;
       for (int i = left; i <= mid; i++) {
           while (window_r <= right && arr[i] > arr[window_r]*2) {
               window_r++;
           }
           ans += window_r - mid - 1;
       }


       int[] helper = new int[right - left + 1];
       int i = 0;
       int pos1 = left;
       int pos2 = mid + 1;
       while (pos1 <= mid && pos2 <= right) {
           helper[i++] = arr[pos1] <= arr[pos2] ? arr[pos1++] : arr[pos2++];
       }

       while (pos1 <= mid) {
           helper[i++] = arr[pos1++];
       }

       while (pos2 <= right) {
           helper[i++] = arr[pos2++];
       }

       for (i = 0; i < helper.length; i++) {
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
                if (arr[i] > (arr[j]<<1)) {
                    ++ans;
                }
            }
        }

        return ans;
    }


    public static int[] generateRandomArray(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        if (len == 0) {
            return null;
        }

        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = generateRandomNum(max_val);
        }

        return ans;
    }

    public static int generateRandomNum(int max_val) {
        return (int)(Math.random()*(max_val + 1)) - (int)(Math.random()*max_val);
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
        int max_val = 30;
        int max_len = 25;
        int test_times = 1000000;
        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[] arr = generateRandomArray(max_val, max_len);
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