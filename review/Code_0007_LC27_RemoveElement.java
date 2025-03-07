package review;

public class Code_0007_LC27_RemoveElement {
    /*
        27 移除元素
        给你一个数组 nums 和一个值 val，请原地移除所有数值等于val的元素，并返回移除后数组的新长度。
        注意，不要使用额外的数组空间，元素的顺序可以改变，不需要考虑数组中超出新长度后面的元素
        nums = [3, 2, 2, 3], value = 3，返回的新长度为2
     */
    public static int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int ans = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] != val) {
                nums[ans++] = nums[i];
            }
        }
        return ans;
    }

    public static int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int k = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == val) {
                k++;
            } else {
                nums[i-k] = nums[i];
            }
        }
        return nums.length - k;
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
            int[] nums3 = copyArray(nums1);
            int val  = (int)((maxVal + 1) * Math.random()) - (int)(maxVal * Math.random());
            int ans1 = removeElement(nums1, val);
            int ans2 = removeElement2(nums2, val);
            int ans3 = test(nums3, val);
            if (ans1 != ans2) {
                success = false;
                break;
            }
            if (ans2 != ans3) {
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
