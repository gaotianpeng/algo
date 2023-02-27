package tixi.offer;

public class Code_051_ReversePairsInArray {
    public int reversePairs(int[] arr) {
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

    public static int merge(int[] arr, int left, int mid ,int right) {
        int[] helper = new int[right - left + 1];
        int index = helper.length - 1;
        int left_index = mid;
        int right_index = right;

        int ans = 0;

        while (left_index >= left && right_index > mid) {
            ans += arr[left_index] > arr[right_index] ? right_index - mid: 0;
            helper[index--] = arr[left_index]  > arr[right_index] ? arr[left_index--] : arr[right_index--];
        }

        while (left_index >= left) {
            helper[index--] = arr[left_index--];
        }

        while (right_index > mid) {
            helper[index--] = arr[right_index--];
        }

        for (int i = 0; i < helper.length; ++i) {
            arr[left + i] = helper[i];
        }

        return ans;
    }

    public int reversePairs2(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int merge_size = 1;
        int ans = 0;
        int n = nums.length;
        while (merge_size < n) {
            int left = 0;
            while (left < n) {
                if (merge_size > n - left) {
                    break;
                }

                int mid = left + merge_size - 1;
                int right = mid + Math.min(merge_size, n - mid - 1);
                ans += merge(nums, left, mid, right);
                left = right + 1;
            }


            if (merge_size > n/2) {
                break;
            }
            merge_size <<= 1;
        }


        return ans;
    }
}
