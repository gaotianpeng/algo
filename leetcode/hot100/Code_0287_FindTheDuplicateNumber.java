package leetcode.hot100;

import java.util.HashSet;
import java.util.Random;

public class Code_0287_FindTheDuplicateNumber {
    /*
        287 寻找重复数
        给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），
        可知至少存在一个重复的整数。
        假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
        你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。

        示例 1：
        输入：nums = [1,3,4,2,2]
        输出：2

        示例 2：
        输入：nums = [3,1,3,4,2]
        输出：3

        示例 3 :
        输入：nums = [3,3,3,3,3]
        输出：3

        提示：
        1 <= n <= 105
        nums.length == n + 1
        1 <= nums[i] <= n
        nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次

        进阶：
        如何证明 nums 中至少存在一个重复的数字?
        你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？
     */
    public static int findDuplicate(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }
        int slow = nums[0];
        int fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        fast = 0;
        while (slow != fast) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }

    // for test
    public static int test(int[] nums) {
        if (nums == null || nums.length < 2) {
            return -1;
        }

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; ++i) {
            if (set.contains(nums[i])) {
                return nums[i];
            }
            set.add(nums[i]);
        }
        return -1;
    }

    public static int[] generateRandomArray(int minLen, int maxLen) {
        int length = randomVal(minLen, maxLen);

        int[] randomArray = new int[length];
        if (length == 1) {
            randomArray[0] = 1;
            return randomArray;
        }
        for (int i = 0; i < length - 1; ++i) {
            randomArray[i] = i + 1;
        }

        randomArray[length - 1] = randomVal(1, length - 1);
        for (int i = 0; i < length; ++i) {
            int a = randomVal(0, length - 1);
            int b = randomVal(0, length - 1);
            swap(randomArray, a, b);
        }

        return randomArray;
    }

    public static int randomVal(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i == j) {
            return;
        }

        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void printArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        for (int i = 0; i < nums.length; ++i) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 1000000;
        int minLen = 1;
        int maxLen = 10;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(minLen, maxLen);
            int ans1 = findDuplicate(arr);
            int ans2 = test(arr);
            if (ans1 != ans2) {
                printArray(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
