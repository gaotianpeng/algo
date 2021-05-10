import java.util.Arrays;

public class Code11_OrderArrMostRight {

    static int[] genRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }
        return arr;
    }

    static int mostRightNoBiggerIdx(int[] arr, int val) {
        int L = 0;
        int R = arr.length - 1;
        int index = -1;
        while (L <= R) {
            int mid = L + ((R-L) >> 1);
            if (arr[mid] <= val) {
                index = mid;
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return index;
    }

    static int test(int[] arr, int value) {
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] <= value) {
                return i;
            }
        }
        return -1;
    }

    static void printArr(int[] arr) {
        for(int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxSize = 30;
        int maxValue = 20;
        boolean succeed = true;
        for(int i = 0; i < testTimes; ++i) {
            int[] arr = genRandomArray(maxSize, maxValue);
            Arrays.sort(arr);
            int val = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
            if (test(arr, val) != mostRightNoBiggerIdx(arr, val)) {
                printArr(arr);
                System.out.println(val);
                System.out.println(mostRightNoBiggerIdx(arr, val));
                succeed = false;
                break;
            }
        }

        System.out.println(succeed ? "success" : "failed");
    }
}
