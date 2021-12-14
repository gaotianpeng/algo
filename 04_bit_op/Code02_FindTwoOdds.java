public class Code02_FindTwoOdds {
    /*
        一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
     */
    public static void printTwoOdds(int [] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        int right_one = eor &(-eor);
        int only_one = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & right_one) != 0) {
                only_one ^= arr[i];
            }
        }

        System.out.println(only_one + " " + (eor  ^ only_one));
    }
}
