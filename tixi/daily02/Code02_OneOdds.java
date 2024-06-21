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
        HashMap<Integer, Integer> countMap = new HashMap<>();
        for (int i = 0; i < arr.length; ++i) {
            if (countMap.containsKey(arr[i])) {
                countMap.put(arr[i], countMap.get(arr[i]) + 1);
            } else {
                countMap.put(arr[i], 1);
            }
        }

        int ret = 0;
        for (HashMap.Entry<Integer, Integer> entry: countMap.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                ret = entry.getKey();
            }
        }

        return ret;
    }

    public static int[] generateOneOddArray(int maxVal, int maxLen, int valMaxTimes) {
        int arrLen = 0;
        while (true) {
            arrLen = (int)(Math.random()*maxLen + 1);
            if (arrLen % 2 != 0) {
                break;
            }
        }

        int[] arr = new int[arrLen];
        // <value, value出现次数>
        HashMap<Integer, Integer> map = new HashMap<>();

        int oddValCount = generateOddValue(Math.min(arrLen, valMaxTimes));
        int oddTimesVal = generateOddValue(maxVal);
        map.put(oddTimesVal, oddValCount);
        int leftOddCount = arrLen - oddValCount;
        while (leftOddCount > 0) {
            int curEventCount = generateEvenValue(leftOddCount);
            int curVAlue = generateRandomValue(maxVal);
            if (map.containsKey(curVAlue)) {
                continue;
            }
            map.put(curVAlue, curEventCount);
            leftOddCount -= curEventCount;
        }

        int arrIndex = 0;
        for (HashMap.Entry<Integer, Integer> entry: map.entrySet()) {
            int i = 0;
            for (i = 0; i < entry.getValue(); ++i) {
                arr[arrIndex+i] = entry.getKey();
            }
            arrIndex += i;
        }

        return arr;
    }

    public static int generateOddValue(int maxVal) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random()*maxVal + 1);
            if (ret % 2 != 0) {
                break;
            }
        }

        return ret;
    }

    public static int generateEvenValue(int maxVal) {
        int ret = 0;
        while (true) {
            ret = (int)(Math.random()*maxVal + 1);
            if (ret % 2 == 0) {
                break;
            }
        }

        return ret;
    }

    public static int generateRandomValue(int maxVal) {
        return (int)(Math.random()*maxVal + 1);
    }

    public static void randomArray(int[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }

        int randomTime = arr.length;
        while (randomTime > 0) {
            int i = generateRandomValue(arr.length - 1);
            int j = generateRandomValue(arr.length - 1);
            if (i != j) {
                swap(arr, i, j);
                randomTime--;
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

        int testTimes = 100000;
        int maxValue = 40;
        int maxLen = 50;
        boolean success = true;

        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateOneOddArray(maxValue, maxLen, 7);
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
