package tixi.daily02;

import java.util.HashMap;

public class Code02_OneOdds {
    /*
        从数组arr中找到唯一一个出现了奇数次的数
        注：由外部保证数组中只有一个数出现了奇数次
    */
    public static int getOneOddNum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("Invalid array");
        }

        int ans = 0;
        for (int i = 0; i < arr.length; ++i) {
            ans ^= arr[i];
        }
        return ans;
    }

    /*
        for test
     */
    public static int test(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("Invalid array");
        }

        // <value, value出现的次数>
        HashMap<Integer, Integer> count_map = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            if (count_map.containsKey(arr[i])) {
                count_map.put(arr[i], count_map.get(arr[i]) + 1);
            } else {
                count_map.put(arr[i], 1);
            }
        }

        int ret = 0;
        for (HashMap.Entry<Integer, Integer> entry: count_map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                ret = entry.getKey();
            }
        }

        return ret;
    }

    public static int[] generateOneOddArray(int max_val, int max_len, int val_max_times) {
        int arr_len = 0;
        while (true) {
            arr_len = (int)(Math.random()*max_len + 1);
            if (arr_len % 2 != 0) {
                break;
            }
        }

        int[] arr = new int[arr_len];
        // <value, value出现次数>
        HashMap<Integer, Integer> map = new HashMap<>();

        int odd_val_count = generateOddValue(Math.min(arr_len, val_max_times));
        int odd_times_val = generateOddValue(max_val);
        map.put(odd_times_val, odd_val_count);
        int left_odd_count = arr_len - odd_val_count;
        while (left_odd_count > 0) {
            int cur_even_count = generateEvenValue(left_odd_count);
            int cur_value = generateRandomValue(max_val);
            if (map.containsKey(cur_value)) {
                continue;
            }
            map.put(cur_value, cur_even_count);
            left_odd_count -= cur_even_count;
        }

        int arr_idx = 0;
        for (HashMap.Entry<Integer, Integer> entry: map.entrySet()) {
            int i = 0;
            for (i = 0; i < entry.getValue(); ++i) {
                arr[arr_idx+i] = entry.getKey();
            }
            arr_idx += i;
        }

        return arr;
    }

    public static int generateOddValue(int max_val) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random()*max_val + 1);
            if (ret % 2 != 0) {
                break;
            }
        }

        return ret;
    }

    public static int generateEvenValue(int max_val) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random()*max_val + 1);
            if (ret % 2 == 0) {
                break;
            }
        }

        return ret;
    }

    public static int generateRandomValue(int max_val) {
        return (int)(Math.random()*max_val + 1);
    }

    public static void randomArray(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }

        int random_time = arr.length;
        while (random_time > 0) {
            int i = generateRandomValue(arr.length - 1);
            int j = generateRandomValue(arr.length - 1);
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
        System.out.println("start test...");

        int test_times = 100000;
        int max_value = 40;
        int max_len = 50;
        boolean success = true;

        for (int i = 0; i < test_times; i++) {
            int[] arr = generateOneOddArray(max_value, max_len, 7);
            randomArray(arr);
            if (getOneOddNum(arr) != test(arr)) {
                success = false;
                System.out.println(getOneOddNum(arr));
                System.out.println(test(arr));
                printArr(arr);
                break;
            }
        }

        System.out.println(success? "success": "failed");
        System.out.println("test end");
    }
}
