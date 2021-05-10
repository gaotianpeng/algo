/*
    给定一数组arr, 查询arr中某一段的累加和
 */

public class Code06_PreSum {
    public static class RangeSum {
        private long[] preSum;

        public RangeSum(int[] arr) {
            int N = arr.length;
            preSum = new long[N];
            preSum[0] = arr[0];
            for (int i = 1; i < N; ++i) {
                preSum[i] = arr[i] + preSum[i-1];
            }
        }

        public long rangeSum(int L, int R) {
            return L == 0 ? preSum[R]: preSum[R] - preSum[L -1];
        }
    }

    static int[] generateRandomLR(int arrLen) {
        int[] arr = new int[2];
        arr[0] = (int)(Math.random()*arrLen);
        arr[1] = (int)(Math.random()*arrLen);
        while (arr[0] > arr[1]) {
            arr[1] = (int)(Math.random()*arrLen);
        }

        return arr;
    }

    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static int[] generateRandomArray(int maxSize, int maxValue) {
        int arrLen = (int)((maxSize + 1) * Math.random());
        while (arrLen < 1) {
            arrLen = (int)((maxSize + 1) * Math.random());
        }
        int[] arr =  new int[arrLen];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)((maxValue + 1)*Math.random()) - (int)(maxValue*Math.random());
        }

        return arr;
    }

    public static long rangeSumComp(int[] arr, int L, int R) {
        long preSum = 0;
        for (int i = L; i <= R; ++i) {
            preSum += arr[i];
        }

        return preSum;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 100;
        int maxValue = 100;

        for(int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] LR = generateRandomLR(arr.length);
            RangeSum sumTest = new RangeSum(arr);
            if (sumTest.rangeSum(LR[0], LR[1]) != rangeSumComp(arr, LR[0], LR[1])) {
                System.out.println("failed");
                printArr(arr);
                return;
            }
        }

        System.out.println("success");
    }
}
