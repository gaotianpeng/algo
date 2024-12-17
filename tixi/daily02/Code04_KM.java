package tixi.daily02;

import java.util.HashMap;
import java.util.HashSet;

/*
    一个数组中有一种数出现K次, 其他数都出现了M次，M > 1, K < M. 找到, 出现了K次的数
    要求: 额外空间复杂度O(1)，时间复杂度O(N)
 */
public class Code04_KM {
    /*
        注：请保证数据中只有一个数出现k次，其余数都出现M次
    */
    public static int km(int[] arr, int k, int m) {
        int N = arr.length;
        int[] helper = new int[32];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < 32; ++j) {
                helper[j] += ((arr[i] >> j) & 1);
            }
        }

        int ans = 0;
        for (int i = 0; i < 32; ++i) {
            if (helper[i] % m != 0) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

    public static int findOnlyKTimes(int[] arr, int k , int m) {
        HashMap<Integer, Integer> map = createMap();
        int[] t = new int[32];
        for (int num: arr) {
            while (num != 0) {
                int right_one = num &(~num + 1);
                t[map.get(right_one)]++;
                num ^= right_one;
            }
        }

        int ret = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) {
                if (t[i] % m == k) {
                    ret |= (1<<i);
                } else {
                    return -1;
                }
            }
        }

        if (ret == 0) {
            int cnt = 0;
            for (int num: arr) {
                if (num == 0) {
                    cnt++;
                }
            }

            if (cnt != k) {
                return -1;
            }
        }

        return ret;
    }

    public static HashMap<Integer, Integer> createMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        int value = 1;
        for (int i = 0; i < 32; i++) {
            map.put(value, i);
            value <<= 1;
        }

        return map;
    }

    /*
        for test
     */
    // 请外部保证arr的合法性
    public static int test(int[] arr, int k, int m) {
        // <value, value出现次数>
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], map.get(arr[i]) + 1);
            } else {
                map.put(arr[i], 1);
            }
        }

        for (int num: map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }

        return -1;
    }

    public static int[] randomArray(int maxKinds, int range, int k, int m) {
        int ktimeNum = randomNumber(range);
        // 真命天子出现的次数
        int times = k;
        // 2
        int numKinds = (int) (Math.random() * maxKinds) + 2;
        // k * 1 + (numKinds - 1) * m
        int[] arr = new int[times + (numKinds - 1) * m];
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktimeNum;
        }
        numKinds--;
        HashSet<Integer> set = new HashSet<>();
        set.add(ktimeNum);
        while (numKinds != 0) {
            int curNum = 0;
            do {
                curNum = randomNumber(range);
            } while (set.contains(curNum));
            set.add(curNum);
            numKinds--;
            for (int i = 0; i < m; i++) {
                arr[index++] = curNum;
            }
        }
        // arr 填好了
        for (int i = 0; i < arr.length; i++) {
            // i 位置的数，我想随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr;
    }

    // 为了测试
    // [-range, +range]
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1)) - (int) (Math.random() * (range + 1));
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void printArr(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int kinds = 5;
        int range = 30;
        int testTime = 100000;
        int max = 9;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int a = (int) (Math.random() * max) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * max) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            int[] arr = randomArray(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = findOnlyKTimes(arr, k, m);
            int ans3 = km(arr, k, m);
            if (ans1 != ans2 || ans1 != ans3) {
                printArr(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                success = false;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
