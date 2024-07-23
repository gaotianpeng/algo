package tixi.daily03;

/*
    返回数组中的最大值
 */
public class Code11_GetMax {

    public static int getMax(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        return process(nums, 0, nums.length - 1);
    }

    public static int process(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int mid = left + ((right - left) >> 1);
        int leftMax = process(nums, left, mid);
        int rightMax = process(nums, mid + 1, right);
        return Math.max(leftMax, rightMax);
    }
    /*
        for test
     */
    public static int test(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int maxIndex = 0;
        for (int i = 1; i < nums.length; i++) {
            maxIndex = nums[i] > nums[maxIndex] ? i : maxIndex;
        }
        return nums[maxIndex];
    }


    public static int[] generateRandomArray(int maxSize, int max_value) {
        int[] arr = new int[(int)(maxSize * Math.random() + 1)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((max_value + 1) * Math.random()) - (int)(max_value * Math.random());
        }

        return arr;
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

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.println(arr[i] + " ");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = 1000;
        int maxLen = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(maxLen, maxVal);
            if (getMax(arr) != test(arr)) {
                success = false;
                printArray(arr);
                System.out.println(getMax(arr));
                System.out.println(test(arr));
                break;
            }
        }
        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
