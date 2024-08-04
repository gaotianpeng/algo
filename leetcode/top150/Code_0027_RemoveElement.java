package leetcode.top150;

public class Code_0027_RemoveElement {
    /*
        27 移除元素
        给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素。
        元素的顺序可能发生改变。然后返回 nums 中与 val 不同的元素的数量。
        假设 nums 中不等于 val 的元素数量为 k，要通过此题，您需要执行以下操作：
        更改 nums 数组，使 nums 的前 k 个元素包含不等于 val 的元素。nums 的其余元素和 nums 的大小并不重要。
        返回 k。
     */
    public static int removeElement(int[] nums, int val) {
        int ans = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != val) {
                nums[ans++] = nums[i];
            }
        }
        return ans;
    }

    public static int test(int[] nums, int  val) {
        int[] tmp = new int[nums.length];
        int index = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == val) {
                continue;
            }
            tmp[index++] = nums[i];
        }

        for (int i = 0; i < index; ++i) {
            nums[i] = tmp[i];
        }
        return index;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
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

    public static boolean isEqual(int[] arr1, int[] arr2, int n) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < n; i++) {
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
        int testTimes = 100000;
        int maxVal = 50;
        int maxLen = 10;
        boolean success = true;

        for (int i = 0; i < testTimes; i++) {
            int[] nums1 = generateRandomArray(maxLen, maxVal);
            int[] nums2 = copyArray(nums1);
            int val  = (int)((maxVal + 1) * Math.random()) - (int)(maxVal * Math.random());
            int ans1 = removeElement(nums1, val);
            int ans2 = test(nums2, val);
            if (ans1 != ans2) {
                success = false;
                break;
            }

            if (!isEqual(nums1, nums2, ans1)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
