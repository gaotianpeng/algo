package list;

public class Code09_GetMax {
    public static int getMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("数组非法");
        }

        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }

        int mid = L + ((R - L) >> 1);
        int left_max = process(arr, L, mid);
        int right_max = process(arr, mid+1, R);
        return Math.max(left_max, right_max);
    }

    public static int test(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("数组非法");
        }

        int max_idx = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[max_idx]) {
                max_idx = i;
            }
        }

        return arr[max_idx];
    }

    public static int[] generateRandomArray(int max_val, int max_len)  {
        int len = (int)(Math.random() * (max_len + 1));
        int[] arr = new int[len];

        for (int i = 0; i < len; i++) {
            arr[i] = (int)(Math.random() * (max_val + 1));
        }
        return arr;
    }

    public static void printArr(int[] arr) {
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_time = 100000;
        int max_len = 30;
        int max_value = 100;
        boolean success = true;
        for (int i = 0; i < test_time; i++) {
            try {
                int[] arr = generateRandomArray(max_value, max_len);
                if (getMax(arr) != test(arr)) {
                    printArr(arr);
                    System.out.println(getMax(arr));
                    System.out.println(test(arr));
                    success = false;
                    break;
                }
            } catch (Exception e) {
                continue;
            }
        }

        System.out.println(success ? "success": "failed");
        System.out.println("test end...");
    }
}
