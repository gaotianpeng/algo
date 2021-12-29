package bit_op;

public class Code01_Swap2Num {
    // 注：数据的合法性由外部保证
    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i]^arr[j];
        arr[j] = arr[i]^arr[j];
        arr[i] = arr[i]^arr[j];
    }

    public static void main(String[] args) {
        int[] arr = new int[5];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((Math.random() * 100 + 1) - Math.random()*100);
        }

        int before_2 = arr[2];
        int before_3 = arr[3];
        swap(arr, 2, 3);

        if (arr[2] == before_3 && arr[3] == before_2) {
            System.out.println("nice");
        } else {
            System.out.println("bad");
        }
    }
}
