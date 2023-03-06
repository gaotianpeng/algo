package leetcode;
/*
    https://leetcode.cn/problems/median-of-two-sorted-arrays/
    0004 寻找两个正序数的中位数
        给定两个大小分别为 m 和 n 的正序（从小到大）数组nums1 和nums2。请你找出并返回这两个正序数组的 中位数 。
        算法的时间复杂度应该为 O(log (m+n))

    示例
        输入：nums1 = [1,3], nums2 = [2]
        输出：2.00000
        解释：合并数组 = [1,2,3] ，中位数 2

        输入：nums1 = [1,2], nums2 = [3,4]
        输出：2.50000
        解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5

        输入：nums1 = [0,0], nums2 = [0,0]
        输出：0.00000

        输入：nums1 = [], nums2 = [1]
        输出：1.00000

        输入：nums1 = [2], nums2 = []
        输出：2.00000

    提示
        nums1.length == m
        nums2.length == n
        0 <= m <= 1000
        0 <= n <= 1000
        1 <= m + n <= 2000
        -106 <= nums1[i], nums2[i] <= 106
 */
public class Code_0004_MedianOfTwoSortedArrays {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean even = (size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double)(findKthNum(nums1, nums2, size / 2)
                    + findKthNum(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double)(nums1[(size - 1)/2] + nums1[size/2])/2;
            } else {
               return nums1[size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double)(nums2[(size - 1)/2] + nums2[size / 2]) / 2;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }

    public static int findKthNum(int[] arr1, int[] arr2, int kth) {
        int[] longs = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shorts = arr1.length < arr2.length ? arr1 : arr2;
        int long_len = longs.length;
        int short_len = shorts.length;
        if (kth <= short_len) {
            return getUpMedian(shorts, 0, kth -1, longs, 0, kth - 1);
        }

        if (kth > long_len) {
            if (shorts[kth - long_len - 1] >= longs[long_len - 1]) {
                return shorts[kth - long_len - 1];
            }
            if (longs[kth - short_len - 1] >= shorts[short_len - 1]) {
                return longs[kth - short_len - 1];
            }
            return getUpMedian(shorts, kth - long_len, short_len - 1, longs,
                    kth - short_len, long_len - 1);
        }

        if (longs[kth - short_len - 1] >= shorts[short_len = 1]) {
            return longs[kth - short_len - 1];
        }

        return getUpMedian(shorts, kth - long_len, short_len - 1,
                            longs, kth - short_len, long_len - 1);
    }

    public static int getUpMedian(int[] arr1, int start1, int end1, int[] arr2, int start2, int end2) {
        int mid1 = 0;
        int mid2 = 0;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            }

            if (((end1 - start1 + 1)&1) == 1) { // 奇数长度
                if (arr1[mid1] > arr2[mid2]) {
                    if (arr2[mid2] >= arr1[mid1 - 1]) {
                        return arr2[mid2];
                    }
                    end1 = mid1 - 1;
                    start2 = mid2 + 1;
                } else {
                    if (arr1[mid1] >= arr2[mid2 - 1]) {
                        return arr1[mid1];
                    }
                    end2 = mid2 - 1;
                    start1 = mid1 + 1;
                }
            } else { // 偶数长度
                if (arr1[mid1] > arr2[mid2]) {
                    end1 = mid1;
                    start2 = mid2 + 1;
                } else {
                    end2 = mid2;
                    start1 = mid1 + 1;
                }
            }
        }
        return Math.min(arr1[start1], arr2[start2]);
    }
}
