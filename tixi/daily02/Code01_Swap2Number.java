package tixi.daily02;

public class Code01_Swap2Number {
    public static void swap(int[] arr, int i, int j) {
        /*
         * a = arr[i], b = arr[j]
         * arr[i] = a ^ b
         * arr[j] = a ^ b ^ b = a
         * arr[i] = a ^ b ^ a = b
         */
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];  
        arr[i] = arr[i] ^ arr[j];
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int[] arr = new int[] {1, 2};
        swap(arr, 0, 1);
        System.out.println(arr[0]);
        System.out.println(arr[1]);
        System.out.println("test end");
    }
}
