package tixi.daily04;

/*
    在一个数组中，任何一个前面的数a，和任何一个后面的数b，如果(a,b)是降序的，就称为逆序对
    返回数组中所有的逆序对

 */
public class Code05_ReversePair {
    public static int reversePair(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);

        return process(arr, left, mid)
                + process(arr, mid + 1, right)
                + merge(arr, left, mid, right);
    }

    public static int merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int rightmost = right;
        int leftmost = mid;
        int index = helper.length - 1;
        int ans = 0;
        
        while (leftmost >= left && rightmost > mid) {
            ans += arr[leftmost] > arr[rightmost] ? rightmost - mid : 0;
            helper[index--] = arr[leftmost] > arr[rightmost] ? arr[leftmost--] : arr[rightmost--];
        }

        while (leftmost >= left) {
            helper[index--] = arr[leftmost--];
        }

        while (rightmost > mid) {
            helper[index--] = arr[rightmost--];
        }

        for (int i = 0; i < helper.length; ++i) {
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
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    ans += 1;
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
            if (reversePair(arr) != test(arr1)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
