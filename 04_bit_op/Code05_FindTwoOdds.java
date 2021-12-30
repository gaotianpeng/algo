package bit_op;

import java.util.HashMap;

public class Code05_FindTwoOdds {
    /*
        一个数组中有两种数出现了奇数次，其他数都出现了偶数次，找到这两种数并返回
        注：假设arr中所有数都为正数，如果找不至均返回-1
           传入数组的中数据合理性由外部保证
     */
    public static int[] findTwoOdds(int [] arr) {
        if (arr== null || arr.length < 2) {
            return null;
        }

        int[] ret= new int[2];
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        int right_one = eor & (~eor + 1);
        int eor2 = 0;
        for (int i = 0; i < arr.length; i++) {
            if ((arr[i] & right_one) != 0) {
                eor2 ^= arr[i];
            }
        }

        ret[0] = eor2;
        ret[1] = eor ^ eor2;

        return ret;
    }

    /*
        for test
     */
    public static int[] test(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        HashMap<Integer, Integer> num_times_map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (num_times_map.containsKey(arr[i])) {
                num_times_map.put(arr[i], num_times_map.get(arr[i]) + 1);
            } else {
                num_times_map.put(arr[i], 1);
            }
        }

        int ret_arr_index = 0;
        int[] ret = new int[2];
        for (HashMap.Entry<Integer, Integer> entry: num_times_map.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                ret[ret_arr_index] = entry.getKey();
                ret_arr_index++;
            }
        }

        return ret;
    }

    public static boolean isEqual(int[] arr1, int[] arr2)  {
        if (arr1 == null || arr2 == null ||
                arr1.length != arr2.length) {
            return false;
        }

        if (arr1[0] == arr2[0] && arr1[1] == arr2[1]) {
            return true;
        }

        if (arr1[0] == arr2[1] && arr1[1] == arr2[0]) {
            return true;
        }

        return false;
    }

    public static int[] generateTwoOddArray(int max_val, int max_len, int val_max_times) {
        int arr_len = 0;
        // 产生随机数据长度
        while (true) {
            arr_len = generateEvenNum(max_len);
            if (arr_len >= 2) {
                break;
            }
        }

        int [] arr = new int[arr_len];
        // 产生两个出现奇数次数并存放HashMap
        int odd1_num_times = 0;
        int odd2_num_times = 0;
        while (true) {
            odd1_num_times = generateOddNum(Math.min(max_len/2, val_max_times));
            odd2_num_times = generateOddNum(Math.min(max_len/2, val_max_times));
            if (odd1_num_times + odd2_num_times > arr.length) {
                continue;
            }
            if (odd1_num_times > 0 && odd2_num_times > 0) {
                break;
            }

        }

        HashMap<Integer, Integer> val_times_map = new HashMap<>();
        int odd1_num = 0;
        int odd2_num = 0;
        while (true) {
            odd1_num = generateRandomNum(max_val);
            odd2_num = generateRandomNum(max_val);

            if (odd1_num != odd2_num) {
                val_times_map.put(odd1_num, odd1_num_times);
                val_times_map.put(odd2_num, odd2_num_times);
                break;
            }
        }

        // 产生其余出现偶数次的数
        int left_even_nums = arr_len - odd1_num_times - odd2_num_times;
        while (left_even_nums > 0) {
            int even_num_times = generateEvenNum(Math.min(left_even_nums, val_max_times));
            int even_num = generateRandomNum(max_val);
            if (!val_times_map.containsKey(even_num)) {
                int old_left_even_nums = left_even_nums;
                left_even_nums -= even_num_times;
                if (left_even_nums < 0) {
                    even_num_times = old_left_even_nums;
                }
                val_times_map.put(even_num, even_num_times);
            }
        }

        // 随机数组填充值
        int arr_idx = 0;
        for (HashMap.Entry<Integer, Integer> entry: val_times_map.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                arr[arr_idx] = entry.getKey();
                arr_idx++;
            }
        }

        return arr;
    }

    public static int generateEvenNum(int max) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random() * max + 1);
            if (ret % 2 == 0) {
                break;
            }
        }
        return ret;
    };

    public static int generateOddNum(int max) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random() * max + 1);
            if (ret % 2 != 0) {
                break;
            }
        }
        return ret;
    };

    public static int generateRandomNum(int max_val) {
        return (int)(Math.random() * max_val + 1);
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

    // 将数组乱序
    public static void randomArray(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }

        int random_time = arr.length;
        while (random_time > 0) {
            int i = generateRandomNum(arr.length - 1);
            int j = generateRandomNum(arr.length - 1);
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

    public static void main(String[] args) {
        System.out.println("start test ...");
        int test_times = 100000;
        int max_value = 30;
        int val_max_times = 5;
        int max_len = 30;

        boolean success = true;
        for (int i = 0; i < test_times; i++) {
            int[] arr = generateTwoOddArray(max_value, max_len, val_max_times);
            randomArray(arr);
            if (!isEqual(findTwoOdds(arr), test(arr))) {
                printArr(arr);
                success = false;
                break;
            }
        }
        System.out.println(success? "success": "failed");
        System.out.println("end test");
    }
}
