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
        int left_max = process(nums, left, mid);
        int right_max = process(nums, mid + 1, right);
        return Math.max(left_max, right_max);
    }
    /*
        for test
     */
    public static int test(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int max_idx = 0;
        for (int i = 1; i < nums.length; i++) {
            max_idx = nums[i] > nums[max_idx] ? i : max_idx;
        }
        return nums[max_idx];
    }


    public static int[] generateRandomArray(int max_size, int max_value) {
        int[] arr = new int[(int)(max_size * Math.random() + 1)];
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
        int test_times = 100000;
        int max_val = 1000;
        int max_len = 100;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = generateRandomArray(max_len, max_val);
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
