package tixi.daily04;

/*
    在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
    例子： [1,3,4,2,5]
    1左边比1小的数：没有
    3左边比3小的数：1
    4左边比4小的数：1、3
    2左边比2小的数：1
    5左边比5小的数：1、3、4、 2
    所以数组的小和为1+1+3+1+1+3+4+2=16
 */

public class Code04_SmallSum2 {
    public static int smallSum(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int ans = 0;
        int mergeSize = 1;
        int n = arr.length;
        
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
        int[] helper = new int[right - left + 1];
        int leftPos = left;
        int rightPos = mid + 1;
        int index = 0;
        int ans = 0;
        while (leftPos <= mid && rightPos <= right) {
            ans += arr[leftPos] < arr[rightPos] ? (right - rightPos + 1)*arr[leftPos] : 0;
            helper[index++] = arr[leftPos] < arr[rightPos] ? arr[leftPos++] : arr[rightPos++];
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
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    ans += arr[j];
                }
            }
        }

        return ans;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }

        return arr;
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] ret = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            ret[i] = arr[i];
        }

        return ret;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 10000000;
        int maxValue = 30;
        int maxLen = 20;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxLen, maxValue);
            int[] arr2 = copyArray(arr1);
            if (smallSum(arr1) != test(arr2)) {
                System.out.println(smallSum(arr1));
                System.out.println(test(arr2));
                printArray(arr1);
                success = false;
                break;
            }
        }

        System.out.println(success? "success" : "failed");
        System.out.println("test end");
    }
}
