package leetcode.top150;

import java.util.HashMap;
import java.util.Map;

/*
    137. 只出现一次的数字 II
        给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。
        请你找出并返回那个只出现了一次的元素。
        你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。
        示例 1：

        输入：nums = [2,2,3,2]
        输出：3
        示例 2：

        输入：nums = [0,1,0,1,0,1,99]
        输出：99

    提示：
    1 <= nums.length <= 3 * 104
    -231 <= nums[i] <= 231 - 1
    nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次
 */
public class Code_0137_SingleNumber2 {
    public static int singleNumber(int[] nums) {
        int ans = 0;
        for (int i = 0; i < 32; ++i) {
            int total = 0;
            for (int num: nums) {
                total += ((num >> i) & 1);
            }
            if (total % 3 != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

    /*
        for test
    */
    public static int test(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num: nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        int ans = 0;

        for (Map.Entry<Integer, Integer> entry: map.entrySet()) {
            int num = entry.getKey(), cnt = entry.getValue();
            if (entry.getValue() == 1) {
                ans = num;
                break;
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int[] nums1 = new int[] {2,2,3,2};
        int[] nums2 = new int[] {0,1,0,1,0,1,99};

        if (singleNumber(nums1) != test(nums1)) {
            success = false;
        }

        if (singleNumber(nums2) != test(nums2)) {
            success = false;
        }

        System.out.println(success ? "test success" : "test failed");
        System.out.println("test end");
    }
}
