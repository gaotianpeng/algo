package tixi.daily02;

import java.util.HashMap;

/*
    一个数组中有一种数出现K次，其他数都出现了M次，
    M > 1,  K < M
    找到，出现了K次的数，
    要求，额外空间复杂度O(1)，时间复杂度O(N)
 */
public class Code04_KM {
    /*
        注：请保证这个数据中只有一个数出现k次，其余数都出现M次
    */
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

    public static int generateRandomNum(int max_val) {
        return (int)(Math.random()*max_val);
    }

    public static int[] generateRandomArr(int max_m, int max_arr_val, int k, int m) {
        int k_num = generateRandomNum(max_arr_val);
        int k_times = Math.random() < 0.5 ? k: (int)(Math.random()*(m-1) + 1);
        int elem_nums = (int) (Math.random() * max_m) + 2;
        int[] arr = new int[k_times + (elem_nums - 1) * m];
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(k_num, k_times);
        elem_nums--;
        while (elem_nums != 0) {
            int m_num = 0;
            do {
                m_num = generateRandomNum(max_arr_val);
            } while(map.containsKey(m_num));
            elem_nums--;
        }

        int arr_idx = 0;
        for (int num: map.keySet()) {
            int cnt = map.get(num);
            for (int i = 0; i < cnt; i++) {
                arr[arr_idx] = num;
            }
        }

        randomArray(arr);
        return arr;
    }

    // 将数组乱序
    public static void randomArray(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }

        int random_time = arr.length;
        while (random_time > 0) {
            int i = generateRandomNum(arr.length - 1);
            int j = generateRandomNum(arr.length - 1);
            if (i < 0 || j < 0) {
                continue;
            }
            if (i != j) {
                swap(arr, i, j);
                random_time--;
            }
        }
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
        int testTime = 10000000;
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
            int[] arr = generateRandomArr(kinds, range, k, m);
            int ans1 = test(arr, k, m);
            int ans2 = findOnlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                printArr(arr);
                System.out.println(ans1);
                System.out.println(ans2);
                success = false;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
