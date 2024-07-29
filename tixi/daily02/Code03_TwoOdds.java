package tixi.daily02;

import java.util.HashMap;

/*
    一个数组中有两种数出现了奇数次，其他数都出现了偶数次，找到这两种数并返回
    注：假设arr中所有数都为正数，如果找不至均返回-1
       传入数组的中数据合理性由外部保证
*/
public class Code03_TwoOdds {
    public static int[] getTwoOddsNum(int [] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        int N = arr.length; 
        int xor = 0;
        for (int i = 0; i < N; ++i) {
            xor ^= arr[i];
        }

        int rightOne = xor &(-xor);
        int xor2 = 0;
        for (int i = 0; i < N; ++i) {
            if ((arr[i] & rightOne) == 0) {
                xor2 ^= arr[i];
            }
        }

        return new int[] {xor2, xor^xor2};
    }

    /*
        for test
     */
    public static int[] test(int[] arr) {
        if (arr == null || arr.length < 2) {
            return null;
        }

        HashMap<Integer, Integer> numTimesMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (numTimesMap.containsKey(arr[i])) {
                numTimesMap.put(arr[i], numTimesMap.get(arr[i]) + 1);
            } else {
                numTimesMap.put(arr[i], 1);
            }
        }

        int retArrIndex = 0;
        int[] ret = new int[2];
        for (HashMap.Entry<Integer, Integer> entry: numTimesMap.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                ret[retArrIndex] = entry.getKey();
                retArrIndex++;
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

    public static int[] generateTwoOddArray(int max_val, int max_len, int max_val_times) {
        int arrLen = 0;
        // 产生随机数据长度
        while (true) {
            arrLen = generateEvenNum(max_len);
            if (arrLen >= 2) {
                break;
            }
        }

        int [] arr = new int[arrLen];
        // 产生两个出现奇数次数并存放HashMap
        int odd1NumTimes = 0;
        int odd2NumTimes = 0;
        while (true) {
            odd1NumTimes = generateOddNum(Math.min(max_len/2, max_val_times));
            odd2NumTimes = generateOddNum(Math.min(max_len/2, max_val_times));
            if (odd1NumTimes + odd2NumTimes > arr.length) {
                continue;
            }
            if (odd1NumTimes > 0 && odd2NumTimes > 0) {
                break;
            }

        }

        HashMap<Integer, Integer> valTimesMap = new HashMap<>();
        int odd1Num = 0;
        int odd2Num = 0;
        while (true) {
            odd1Num = generateRandomNum(max_val);
            odd2Num = generateRandomNum(max_val);

            if (odd1Num != odd2Num) {
                valTimesMap.put(odd1Num, odd1NumTimes);
                valTimesMap.put(odd2Num, odd2NumTimes);
                break;
            }
        }

        // 产生其余出现偶数次的数
        int leftEvenNums = arrLen - odd1NumTimes - odd2NumTimes;
        while (leftEvenNums > 0) {
            int evenNumTimes = generateEvenNum(Math.min(leftEvenNums, max_val_times));
            int evenNum = generateRandomNum(max_val);
            if (!valTimesMap.containsKey(evenNum)) {
                int oldLeftEvenNums = leftEvenNums;
                leftEvenNums -= evenNumTimes;
                if (leftEvenNums < 0) {
                    evenNumTimes = oldLeftEvenNums;
                }
                valTimesMap.put(evenNum, evenNumTimes);
            }
        }

        // 随机数组填充值
        int arrIndex = 0;
        for (HashMap.Entry<Integer, Integer> entry: valTimesMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                arr[arrIndex] = entry.getKey();
                arrIndex++;
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
    
    public static void randomArray(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }

        int ranodmTimes = arr.length;
        while (ranodmTimes > 0) {
            int i = generateRandomNum(arr.length - 1);
            int j = generateRandomNum(arr.length - 1);
            if (i != j) {
                swap(arr, i, j);
                ranodmTimes--;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxValue = 30;
        int maxValTimes = 5;
        int maxLen = 30;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateTwoOddArray(maxValue, maxLen, maxValTimes);
            randomArray(arr);
            if (!isEqual(getTwoOddsNum(arr), test(arr))) {
                printArr(arr);
                success = false;
                break;
            }
        }
        System.out.println(success ? "success": "failed");
        System.out.println("test end");
    }
}
