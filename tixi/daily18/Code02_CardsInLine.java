package tixi.daily18;
/*
    给定一个整型数组arr，代表数值不同的纸牌排成一条线
    玩家A和玩家B依次拿走每张纸牌
    规定玩家A先拿，玩家B后拿
    但是每个玩家每次只能拿走最左或最右的纸牌
    玩家A和玩家B都绝顶聪明
    请返回最后获胜者的分数
 */
public class Code02_CardsInLine {
    // 返回获胜者的分数
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    // arr[L...R]，先手获得最好的分数返回
    private static int f1(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        int p1 = arr[left] + g1(arr, left + 1, right);
        int p2 = arr[right] + g1(arr, left, right - 1);
        return Math.max(p1, p2);
    }

    // arr[L...R], 后手获得最好的分数返回
    private static int g1(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int p1 = f1(arr, left + 1, right);
        int p2 = f1(arr, left, right - 1);
        return Math.min(p1, p2);
    }

    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] fmap = new int[n][n];
        int[][] gmap = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    private static int f2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (fmap[left][right] != -1) {
            return fmap[left][right];
        }

        int ans = 0;
        if (left == right) {
            ans = arr[left];
        } else {
            int p1 = arr[left] + g2(arr, left + 1, right, fmap, gmap);
            int p2 = arr[right] + g2(arr, left, right - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }

        fmap[left][right] = ans;
        return ans;
    }

    private static int g2(int[] arr, int left, int right, int[][] fmap, int[][] gmap) {
        if (gmap[left][right] != -1) {
            return gmap[left][right];
        }
        int ans = 0;
        if (left != right) {
            int p1 = f2(arr, left + 1, right, fmap, gmap);
            int p2 = f2(arr, left, right - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }

        gmap[left][right] = ans;
        return ans;
    }

    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] fmap = new int[n][n];
        int[][] gmap = new int[n][n];
        for (int i = 0; i < n; i++) {
            fmap[i][i] = arr[i];
        }
        for (int start_col = 1; start_col < n; start_col++) {
            int left = 0;
            int right = start_col;
            while (right < n) {
                fmap[left][right] = Math.max(arr[left] + gmap[left + 1][right], arr[right] + gmap[left][right-1]);
                gmap[left][right] = Math.min(fmap[left + 1][right], fmap[left][right - 1]);
                left++;
                right++;
            }
        }
        return Math.max(fmap[0][n-1], gmap[0][n-1]);
    }

    /*
        for test
     */
    public static int[] generateRandomArr(int max_val, int max_len) {
        int len = (int)(Math.random()*(max_len + 1));
        int[] ans = new int[len];

        for (int i = 0; i < len; i++) {
            ans[i] = (int)(Math.random() * (max_val + 1));
        }
        return ans;
    }

    public static void printArr(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start....");
        int testTimes = 100000;
        int maxLen = 6;
        int maxVal = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArr(maxVal, maxLen);
            if (win1(arr) != win2(arr)) {
                printArr(arr);
                success = false;
                break;
            }
            if (win1(arr) != win3(arr)) {
                printArr(arr);
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
