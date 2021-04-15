public class Code01_PrintBin {
    public static void print(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1<<i)) == 0 ? "0": "1");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        print(1);
        print(2);
        print(0);
        print(-1);
        print(-2);
        print(Integer.MAX_VALUE);
        print(Integer.MIN_VALUE);
    }
}