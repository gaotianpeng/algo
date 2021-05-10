public class Code15_EvenOddTimes {
    // arr中，只有一种数，出现奇数次，打印出来
    static void printOddTimesOfArr(int[] arr) {
        int xor = 0;
        for(int i = 0; i < arr.length; i++) {
            xor ^= arr[i];
        }
        System.out.println(xor);
    }

    // arr中，有两种数，出现奇数次，打印出来
    static void printTwoOddTimesOfArr(int[] arr) {
        int xor = 0;
        for (int i = 0; i < arr.length; ++i) {
            xor ^= arr[i];
        }

        int rightOne = xor & (-xor);
        System.out.println(rightOne);

        int onlyOne = 0;
        for (int i = 0; i < arr.length; ++i) {
            if ((arr[i] & rightOne) != 0) {
                onlyOne ^= arr[i];
            }
        }

        System.out.println(onlyOne + " " + (xor^onlyOne));
    }

    static int countBits(int N) {
        int count = 0;
        while (N != 0) {
            int rightOne = N & ((~N) + 1);
            count++;
            N ^= rightOne;
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        int[] arr = {3, 3, 2, 3, 1, 1, 3, 1, 1, 1};
        printOddTimesOfArr(arr);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printTwoOddTimesOfArr(arr2);

        System.out.println();
        System.out.println(countBits(3));
        System.out.println(countBits(5));
        System.out.println(countBits(111));
    }
}
