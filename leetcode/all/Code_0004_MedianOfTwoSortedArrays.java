package leetcode.all;

import java.util.Arrays;
import java.util.Random;

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
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int all_size = nums1.length + nums2.length;
        boolean even = (all_size & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double)(findKthNum(nums1, nums2, all_size /2) + findKthNum(nums1, nums2, all_size / 2 + 1))/2D;
            } else {
                return findKthNum(nums1, nums2, all_size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double)(nums1[(all_size - 1) / 2] + nums1[all_size / 2]) / 2;
            } else {
                return nums1[all_size / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double)(nums2[(all_size - 1) / 2] + nums2[all_size / 2]) / 2;
            } else {
                return nums2[all_size / 2];
            }
        } else {
            return 0;
        }
    }

    public static int findKthNum(int[] nums1, int[] nums2, int kth) {
        int[] long_arr = nums1.length >= nums2.length ? nums1 : nums2;
        int[] short_arr = nums1.length < nums2.length ? nums1 : nums2;
        int long_len = long_arr.length;
        int short_len = short_arr.length;

        // kth <= short_len
        if (kth <= short_len) {
            return getUpMedian(short_arr, 0, kth - 1, long_arr, 0, kth - 1);
        }
        // kth > long_len
        if (kth > long_len) {
            if (short_arr[kth - long_len - 1] >= long_arr[long_len - 1]) {
                return short_arr[kth - long_len - 1];
            }
            if (long_arr[kth - short_len - 1] >= short_arr[short_len - 1]) {
                return long_arr[kth - short_len - 1];
            }

            return getUpMedian(short_arr, kth - long_len, short_len - 1,
                            long_arr, kth - short_len, long_len - 1);
        }

        // short_len < kth < long_len
        if (long_arr[kth - short_len - 1] >= short_arr[short_len - 1]) {
            return long_arr[kth - short_len - 1];
        }


        return getUpMedian(short_arr, 0, short_len - 1, long_arr, kth - short_len, kth - 1);
    }

    public static int getUpMedian(int[] nums1, int start1, int end1,
                  int[] nums2, int start2, int end2) {
        int mid1 = 0;
        int mid2 = 0;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;

            if (nums1[mid1] == nums2[mid2]) {
                return nums1[mid1];
            }

            if (((end1 - start1 + 1) & 1) == 1) {
                if (nums1[mid1] > nums2[mid2]) {
                    if (nums2[mid2] >= nums1[mid1- 1]) {
                        return nums2[mid2];
                    }
                    end1 = mid1 - 1;
                    start2 = mid2 + 1;
                } else {
                    if (nums1[mid1] >= nums2[mid2 - 1]) {
                        return nums1[mid1];
                    }

                    start1 = mid1 + 1;
                    end2 = mid2 - 1;
                }
            } else {
                if (nums1[mid1] > nums2[mid2]) {
                    end1 = mid1;
                    start2 = mid2 + 1;
                } else {
                    start1 = mid1 + 1;
                    end2 = mid2;
                }
            }
        }

        return Math.min(nums1[start1], nums2[start2]);
    }

    /*
        for test
     */
    public static int RandomVal(int min, int max) {
        return min + (int)(Math.random()*(max - min + 1));
    }

    public static void PrintArray(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static int[] generateRandomArray(int max_n, int min, int max) {
        int n = RandomVal(0, max_n);
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = RandomVal(min, max);
        }
        Arrays.sort(ans);

        return ans;
    }

    static public double test(int[] nums1, int[] nums2) {
        int n = nums1.length + nums2.length;
        int [] all_num = new int[n];

        int index = 0;
        for (int i = 0; i < nums1.length; ++i, ++index) {
            all_num[index] = nums1[i];
        }

        for (int i = 0;  i < nums2.length; ++i, ++index) {
            all_num[index] = nums2[i];
        }
        Arrays.sort(all_num);

        if (n % 2 == 0) {
            return ((double)(all_num[n/2 - 1] + all_num[n/2]))/2;
        } else {
            return (double)all_num[n/2];
        }
    }

    public static void main(String[] args) {
        System.out.println("test start ...");
        int test_times = 100000;
        int max_n = 100;
        int min = 10;
        int max = 100;

        for (int i = 0; i < test_times; ++i) {
            int[] arr1 = generateRandomArray(max_n, min, max);
            int[] arr2 = generateRandomArray(max_n, min, max);
            if (arr1.length + arr2.length < 1) {
                continue;
            }
            if (test(arr1, arr2) != findMedianSortedArrays(arr1, arr2)) {
                System.out.println("test failed");
                System.out.println(test(arr1, arr2));
                System.out.println(findMedianSortedArrays(arr1, arr2));
                PrintArray(arr1);
                PrintArray(arr2);

                break;
            }
        }

        System.out.println("test end");
    }
}
