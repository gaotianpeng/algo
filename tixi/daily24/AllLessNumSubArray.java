package tixi.daily24;

import java.util.LinkedList;

public class AllLessNumSubArray {
    /*
        给定一个整型数组arr，和一个整数sum
        某个arr中的子数组sub，如果想达标，必须满足：
        sub中最大值 – sub中最小值 <= sum，
        返回arr中达标子数组的数量
     */
    public static int num(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int n = arr.length;
        int count = 0;
        LinkedList<Integer> max_win = new LinkedList<>();
        LinkedList<Integer> min_win = new LinkedList<>();
        int right = 0;
        for (int left = 0; left < n; ++left) {
            while (right < n) {
                while (!max_win.isEmpty() && arr[max_win.peekLast()] <= arr[right]) {
                    max_win.pollLast();
                }
                max_win.addLast(right);
                while (!min_win.isEmpty() && arr[min_win.peekLast()] >= arr[right]) {
                    min_win.pollLast();
                }
                min_win.addLast(right);
                if (arr[max_win.peekFirst()] - arr[min_win.peekFirst()] > sum) {
                    break;
                } else {
                    right++;
                }
            }

            count += right - left;
            if (max_win.peekFirst() == left) {
                max_win.pollFirst();
            }
            if (min_win.peekFirst() == left) {
                min_win.pollFirst();
            }
        }

        return count;
    }

    public static int test(int[] arr, int sum) {
        if (arr == null || arr.length == 0 || sum < 0) {
            return 0;
        }

        int n = arr.length;
        int count = 0;
        for (int left = 0; left < n; ++left) {
            for (int right = left; right < n; ++right) {
                int max = arr[left];
                int min = arr[left];
                for (int i = left + 1; i <= right; ++i) {
                    max = Math.max(max, arr[i]);
                    min = Math.min(min, arr[i]);
                }

                if (max - min <= sum) {
                    ++count;
                }
            }
        }

        return count;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }

        for (int i = 0; i < arr.length; ++i) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void main(String[] args) {
        System.out.println("test starting...");
        int test_times = 100000;
        int max_num = 100;
        int max_val = 50;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = generateRandomArray(max_num, max_val);
            int sum = (int) (Math.random() * (max_val + 1));
            int ans1 = num(arr, sum);
            int ans2 = test(arr, sum);
            if (ans1 != ans2) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
