package bit_op;

public class Code07_PrintIntBinary {
    public static void printBinary(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print( (1<<i & num) == 0 ? "0": "1");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        System.out.println("test start....");
        printBinary(Integer.MAX_VALUE);
        printBinary(Integer.MIN_VALUE);
        printBinary(0);
        printBinary(1);
        printBinary(-1);
        printBinary(5);
        System.out.println("test end");
    }
}
