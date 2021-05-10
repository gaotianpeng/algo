public class Code03_BubbleSort {
    static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int end = arr.length; end > 0; --end) {
            for (int i = 0; i < end - 1; ++i) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
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
        bubbleSort(arr);
        printArr(arr);
    }
}
