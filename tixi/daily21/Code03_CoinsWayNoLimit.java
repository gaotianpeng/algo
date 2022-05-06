package tixi.daily21;
/*
    arr是面值数组，其中的值都是正数且没有重复。再给定一个正数aim。
    每个值都认为是一种面值，且认为张数是无限的。
    返回组成aim的方法数
    例如：arr = {1,2}，aim = 4
    方法如下：1+1+1+1、1+1+2、2+2
    一共就3种方法，所以返回3
 */
public class Code03_CoinsWayNoLimit {
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1: 0;
        }

        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }
}
