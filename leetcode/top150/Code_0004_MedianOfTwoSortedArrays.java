package leetcode.top150;

import java.util.Arrays;

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
        int allSize = nums1.length + nums2.length;
        boolean even = (allSize & 1) == 0;
        if (nums1.length != 0 && nums2.length != 0) {
            if (even) {
                return (double)(findKthNum(nums1, nums2, allSize /2) +
                        findKthNum(nums1, nums2, allSize / 2 + 1))/2D;
            } else {
                return findKthNum(nums1, nums2, allSize / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (even) {
                return (double)(nums1[(allSize - 1) / 2] + nums1[allSize / 2]) / 2;
            } else {
                return nums1[allSize / 2];
            }
        } else if (nums2.length != 0) {
            if (even) {
                return (double)(nums2[(allSize - 1) / 2] + nums2[allSize / 2]) / 2;
            } else {
                return nums2[allSize / 2];
            }
        } else {
            return 0;
        }
    }

    /*
        arr1 和 arr2 整体排完序后，第k个小的数是谁？(k从1开始)

        arr1: 10个数
        arr2: 17个数
        分三种情况
            1） 1 =< k <= 10
            2） 10 < k <= 17
            3)  17 < k <= 27
     */
    public static int findKthNum(int[] arr1, int[] arr2, int k) {
        int[] longArr = arr1.length >= arr2.length ? arr1 : arr2;
        int[] shortArr = arr1.length < arr2.length ? arr1 : arr2;
        int longLen = longArr.length;
        int shortLen = shortArr.length;

        /*
            1) k <= shortLen
            1 =< k <= 10
            short:  1 2 3 4 5 6 7 8 9 10
                    --------------------
            long :  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
                    --------------------
         */
        if (k <= shortLen) {
            return getUpMedian(shortArr, 0, k - 1, longArr, 0, k - 1);
        }
        /*
            2) longLen < k <= longLen + shortLen
                 17  < k <= 27
            short:  1 2 3 4 5 6 7 8 9 10
                            ------------
            long :  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
                                               --------------
            5 + 12 + 5th = 22
            手动难short 5位置和long 13位置
            6 + 13 + 4th = 23
         */
        if (k > longLen) {
            if (shortArr[k - longLen - 1] >= longArr[longLen - 1]) {
                return shortArr[k - longLen - 1];
            }
            if (longArr[k - shortLen - 1] >= shortArr[shortLen - 1]) {
                return longArr[k - shortLen - 1];
            }

            return getUpMedian(shortArr, k - longLen, shortLen - 1,
                            longArr, k - shortLen, longLen - 1);
        }

        /*
            2) shortLen < k <= longLen
            10 < k <= 17
            eg: k = 15
            short:  1 2 3 4 5 6 7 8 9 10
                    --------------------                       10个
            long :  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
                            ---------------------------        11个
            判断long中 5位置是否是第15小，然后
            short:  1 2 3 4 5 6 7 8 9 10
                    --------------------                       10个
            long :  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17
                              -------------------------        10 个
         */
        if (longArr[k - shortLen - 1] >= shortArr[shortLen - 1]) {
            return longArr[k - shortLen - 1];
        }
        return getUpMedian(shortArr, 0, shortLen - 1, longArr, k - shortLen, k - 1);
    }

    /*
        返回上中位数
        arr1 在 [L1, R1] 有序
        arr2 在 [L2, R2] 有序
        [L1, R1]，[L2, R2]等长
     */
    public static int getUpMedian(int[] arr1, int L1, int R1,
                  int[] arr2, int L2, int R2) {
        int mid1 = 0;
        int mid2 = 0;
        while (L1 < R1) {
            // 偶数，上中点；奇数，中点
            mid1 = (L1 + R1) / 2;
            mid2 = (L2 + R2) / 2;

            if (arr1[mid1] == arr2[mid2]) {
                return arr1[mid1];
            }

            // [L1, R1]有奇数个
            if (((R1 - L1 + 1) & 1) == 1) {
                /*
                    arr1: 3 4 5 8 9
                          ---
                    arr2: 2 3 4 7 8
                                ---
                 */
                if (arr1[mid1] > arr2[mid2]) {
                    if (arr2[mid2] >= arr1[mid1- 1]) {
                        return arr2[mid2];
                    }
                    R1 = mid1 - 1;
                    L2 = mid2 + 1;
                } else {
                /*
                    arr1: 2 3 4 7 8
                                ---
                    arr2: 3 4 5 8 9
                          ---
                 */
                    if (arr1[mid1] >= arr2[mid2 - 1]) {
                        return arr1[mid1];
                    }
                    L1 = mid1 + 1;
                    R2 = mid2 - 1;
                }
            // [L1, R1]有偶数个
            } else {
                /*
                    arr1: 3 4 8 9
                    arr2: 2 3 7 8
                    在[L1, mid1] 和 [mid2 + 1, R2]之间找
                 */
                if (arr1[mid1] > arr2[mid2]) {
                    R1 = mid1;
                    L2 = mid2 + 1;
                } else {
                /*
                    arr1: 2 3 7 8
                    arr2: 3 4 8 9
                    在[mid1+1, R1] 和 [L2, mid2]之间找
                 */
                    L1 = mid1 + 1;
                    R2 = mid2;
                }
            }
        }

        return Math.min(arr1[L1], arr2[L2]);
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

    public static int[] generateRandomArray(int maxN, int min, int max) {
        int n = RandomVal(0, maxN);
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = RandomVal(min, max);
        }
        Arrays.sort(ans);

        return ans;
    }

    static public double test(int[] nums1, int[] nums2) {
        int n = nums1.length + nums2.length;
        int [] allNum = new int[n];

        int index = 0;
        for (int i = 0; i < nums1.length; ++i, ++index) {
            allNum[index] = nums1[i];
        }

        for (int i = 0;  i < nums2.length; ++i, ++index) {
            allNum[index] = nums2[i];
        }
        Arrays.sort(allNum);

        if (n % 2 == 0) {
            return ((double)(allNum[n/2 - 1] + allNum[n/2]))/2;
        } else {
            return (double)allNum[n/2];
        }
    }

    public static void main(String[] args) {
        System.out.println("test start ...");
        boolean success = true;
        int testTimes = 100000;
        int maxN = 100;
        int min = 10;
        int max = 100;

        for (int i = 0; i < testTimes; ++i) {
            int[] arr1 = generateRandomArray(maxN, min, max);
            int[] arr2 = generateRandomArray(maxN, min, max);
            if (arr1.length + arr2.length < 1) {
                continue;
            }
            if (test(arr1, arr2) != findMedianSortedArrays(arr1, arr2)) {
                System.out.println("test failed");
                System.out.println(test(arr1, arr2));
                System.out.println(findMedianSortedArrays(arr1, arr2));
                PrintArray(arr1);
                PrintArray(arr2);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success":"failed");
        System.out.println("test end");
    }
}
