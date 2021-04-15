package AlgoNew;

public class Code02_SelectSort {
    static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;
        for (int i = 0; i < N - 1; ++i) {
            int minIdx = i;
            for (int j = i + 1; j < N; ++j) {
                if (arr[minIdx] > arr[j]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, minIdx, i);
            }
        }
    }

    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {7, 1, 3, 5, 6, 8, 1, 3, 5, 7, 5, 6};
        System.out.print("Before sort:");
        printArr(arr);
        System.out.print("After  sort:");
        selectSort(arr);
        printArr(arr);
    }
}
