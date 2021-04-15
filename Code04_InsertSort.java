package AlgoNew;

public class Code04_InsertSort {
    static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;
        for (int i = 1; i < N; ++i) {
            for (int end = i; end > 0; --end) {
                if (arr[end - 1] > arr[end]) {
                    swap(arr, end - 1, end);
                }
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
        insertSort(arr);
        printArr(arr);
        System.out.println("Code03_BubbleSort.main");
    }
}
