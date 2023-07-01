package tixi.daily37;

public class Code01_CountOfRangeSum {

    /*
        给定一个数组arr，和两个整数a和b（a<=b）
        求arr中有多少个子数组，累加和在[a,b]这个范围上
        返回达标的子数组数量
     */
    public static int countRangeSum1(int[] nums, int lower, int upper) {
        int n = nums.length;
        long[] sums = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            sums[i + 1] = sums[i] + nums[i];
        }

        return countWhileMergeSort(sums, 0, n + 1, lower, upper);
    }

    private static int countWhileMergeSort(long[] sums, int start, int end, int lower, int upper) {
        if (end - start <= 1) {
            return 0;
        }

        int mid = start + (end - start) >> 1;
        int count = countWhileMergeSort(sums, start, mid, lower, upper)
                + countWhileMergeSort(sums, mid, end, lower, upper);




        return 0;
    }



    public static void main(String[] args) {
        System.out.println("test start...");
        System.out.println("test end");
    }
}
