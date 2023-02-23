package leetcode;

import java.util.ArrayList;
import java.util.HashMap;

/*
    350. 两个数组的交集 II
        给你两个整数数组nums1 和 nums2 ，请你以数组形式返回两数组的交集。返回结果中每个元素出现的次数，应与元素在两个数组中都出现的次数一致（如果出现次数不一致，则考虑取较小值）。
        可以不考虑输出结果的顺序。

    示例
        输入：nums1 = [1,2,2,1], nums2 = [2,2]
        输出：[2,2]

        输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
        输出：[4,9]
 */
public class Code_0350_IntersectionOfTwoArrays2 {
    public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        for (int num: nums1) {
            if (!map1.containsKey(num)) {
                map1.put(num, 1);
            } else {
                map1.put(num, map1.get(num) + 1);
            }
        }

        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int num: nums2) {
            if (!map2.containsKey(num)) {
                map2.put(num, 1);
            } else {
                map2.put(num, map2.get(num) + 1);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for( int key: map1.keySet()) {
            if (map2.containsKey(key)) {
                int n = Math.min(map1.get(key), map2.get(key));
                for (int i = 0; i < n; i++) {
                    list.add(key);
                }
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = list.get(i);
        }

        return ans;
    }
}
