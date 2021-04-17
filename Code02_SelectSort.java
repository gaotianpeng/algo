package AlgoNew;

public class Code02_SelectSort {
    static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i+1; j < arr.length; ++j) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, i, minIdx);
            }
        }
    }

    static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    static void print(int [] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void swap(int [] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    static int[] generateRandomArray(int maxSize, int maxVal) {
        int[] arr = new int[(int)(Math.random()*(maxSize + 1))];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)(Math.random()*(maxVal + 1)) - (int)(Math.random()*maxVal);
        }

        return arr;
    }

    static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }

        int[] retArr = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            retArr[i] = arr[i];
        }

        return retArr;
    }

    static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }

        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; ++i) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int testTime = 30;
        int maxVal = 20;
        int maxSize = 15;
        boolean success = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxVal);
            int[] arr2 = copyArray(arr1);
            selectSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                success = false;
                print(arr1);
                print(arr2);
            }
        }

        System.out.println(success ? "succeed": "fucked error");
    }
}
