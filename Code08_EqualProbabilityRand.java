package AlgoNew;

// 01不等概率随机到01等概率随机

public class Code07_RandToRand {
    /*
        等概率返回1~5
     */
    static int rand1Range5() {
        return (int)(Math.random() * 5) + 1;
    }

    static void testRand1Range5(int testTimes) {
        int arr[] = new int[5];
        for (int i = 0; i < testTimes; ++i) {
            int randNum = rand1Range5();
            arr[randNum-1] += 1;
        }

        printArr(arr);
    }

    /*
        等概率返回0和1
     */
    static int rand0Range1() {
        int ans = 0;
        do {
            ans = rand1Range5();
        } while (ans == 3);
        return ans < 3 ? 0: 1;
    }

    static void testRand0Range1(int testTimes) {
        int[] arr = new int[2];
        for (int i = 0; i < testTimes; ++i) {
            arr[rand0Range1()] += 1;
        }

        printArr(arr);
    }

    /*
        等概率返回 0~6
     */
    static int rand0Range6() {
        int ans = 0;
        do {
            ans = ((rand0Range1() << 2) + (rand0Range1() << 1) + rand0Range1());
        } while (ans == 7);
        return ans;
    }

    static void testRand0Range6(int testTimes) {
        int[] arr = new int[7];
        for (int i = 0; i < testTimes; ++i) {
            arr[rand0Range6()] += 1;
        }

        printArr(arr);
    }
    /*
        等概率返回 1~7
     */
    static int rand1Range7() {
        return rand0Range6() + 1;
    }

    static void testRand1Range7(int testTimes) {
        int[] arr = new int[7];
        for (int i = 0; i < testTimes; ++i) {
            arr[rand1Range7() - 1] += 1;
        }

        printArr(arr);
    }
    // -------------------------------------------------
    static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // -------------------------------------------------
    public static class RandomBox {
        private final int min;
        private final int max;

        public RandomBox(int min, int max) {
            this.min = min;
            this.max = max;
        }

        /*
            13 ~ 17
            13 + [0, 4]
         */
        public int random() {
            return min + (int)(Math.random()*(max - min + 1));
        }

        public int min() {
            return this.min;
        }

        public int max() {
            return this.max;
        }
    }
    // 利用条件RandomBox，如何等概率返回0和1
    static int rand0And1(RandomBox randomBox) {
        int min = randomBox.min();
        int max = randomBox.max();

        int size = max - min + 1;
        boolean odd = (size&1) != 0;
        int mid = size >> 2;
        int ans = 0;
        do {
            ans = randomBox.random() - min;
        } while (odd && ans == mid);

        return ans < mid ? 0 : 1;
    }

    static void testRand0And1(int testTimes) {
        RandomBox randBox = new RandomBox(0, 1);
        int[] arr = new int[2];
        for (int i = 0; i < testTimes; ++i) {
            arr[randBox.random()] += 1;
        }

        printArr(arr);
    }
    // --------------------------------------------------

    public static void main(String[] args) {
        // ----------------------------------------------
        System.out.println("testRand1Range5-----------------------");
        testRand1Range5(10000000);
        // ----------------------------------------------
        System.out.println("testRand0Range1-----------------------");
        testRand0Range1(10000000);
        // ----------------------------------------------
        System.out.println("testRand0Range6-----------------------");
        testRand0Range6(10000000);
        // ----------------------------------------------
        System.out.println("testRand1Range7-----------------------");
        testRand1Range7(10000000);
        // -----------------------------------------------
        System.out.println("test for RandomBox 0 and 1-----------------------");
        testRand0And1(10000000);
    }
}
