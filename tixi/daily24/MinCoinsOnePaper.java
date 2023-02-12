package tixi.daily24;
/*
    arr是货币数组，其中的值都是正数。再给定一个正数aim。
    每个值都认为是一张货币，
    返回组成aim的最少货币数
    注意：
    因为是求最少货币数，所以每一张货币认为是相同或者不同就不重要了
 */
public class MinCoinsOnePaper {
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    public static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return Integer.MAX_VALUE;
        }

        if (index == arr.length) {
            return rest == 0 ? 0: Integer.MAX_VALUE;
        } else {
            int p1 = process(arr, index + 1, rest);
            int p2 = process(arr, index + 1, rest - arr[index]);
            if (p2 != Integer.MAX_VALUE) {
                p2++;
            }
            return Math.min(p1, p2);
        }
    }

    public static int[] randomArray(int n, int max_val) {
        int[] arr = new int[n];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)(Math.random() * max_val) + 1;
        }

        return arr;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int max_len = 20;
        int max_val = 30;
        int test_time = 30000;
        for (int i = 0; i < test_time; ++i) {
            int n = (int)(Math.random() * max_val);
            int[] arr = randomArray(n, max_val);
            int aim = (int)(Math.random() * max_val);
        }

        System.out.println("test end");
    }
}
