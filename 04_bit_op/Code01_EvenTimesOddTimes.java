public class Code01_EvenTimesOddTimes {
    /*
        一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
     */
    public static void printOddTimesNum1(int[] arr) {
        if (arr == null) {
            System.out.println("The array is empty");
            return;
        }

        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        System.out.println(eor);
    }
    /*
        怎么把一个int类型的数，提取出最右侧的1来
     */
    public static int extractRightOneFromInt(int value) {
        return value &((~value) + 1);
    }

    /*
        计算一个整数中二位制为1的个数
     */
    public static int countOneBitOfInt(int value) {
        int cnt = 0;
        while (value != 0) {
            int rightOne = value &((~value) + 1);
            cnt++;
            value ^= rightOne;
        }
        return cnt;
    }
}
