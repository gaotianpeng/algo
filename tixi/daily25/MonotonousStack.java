package tixi.daily25;

import java.util.HashSet;
import java.util.Stack;

public class MonotonousStack {
    /*
        单调栈：一种特别设计的栈结构，为了解决如下的问题：
            给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
            1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
            2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
            如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int[][] ans = new int[arr.length][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; ++i) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int j = stack.pop();
                int left_less_index = stack.isEmpty() ? -1 : stack.peek();
                ans[j][0] = left_less_index;
                ans[j][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int j = stack.pop();
            int left_less_index = stack.isEmpty() ? -1: stack.peek();
            ans[j][0] = left_less_index;
            ans[j][1] = -1;
        }

        return ans;
    }

    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }

        for (int i = 0; i < res1.length; ++i) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    public static int[] getRandomArray(int size, int max) {
        int[] arr =new int[(int)(Math.random() * size) + 1];
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = (int)(Math.random()*max) - (int)(Math.random()*max);
        }
        return arr;
    }

    public static int[] getRandomArrayNoRepeat(int max_size, int max_val) {
        int n = (int)(Math.random() * max_size);
        HashSet<Integer> set = new HashSet<>();
        int cnt = n;
        while (cnt > 0) {
            int val = (int)(Math.random() * max_val);
            if (set.contains(val)) {
                continue;
            } else {
                --cnt;
                set.add(val);
            }
        }
        int[] ans = new int[n];
        int index = 0;
        for (Integer i : set) {
            ans[index++] = i;
        }

        return ans;
    }

    public static int[][] test(int[] arr) {
        int[][] res = new int[arr.length][2];
        for (int i = 0; i < arr.length; ++i) {
            int left_less_index = -1;
            int right_less_index = -1;
            int cur = i -1;
            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    left_less_index = cur;
                    break;
                }
                cur--;
            }

            cur = i + 1;
            while (cur < arr.length) {
                if (arr[cur] < arr[i]) {
                    right_less_index = cur;
                    break;
                }
                cur++;
            }
            res[i][0] = left_less_index;
            res[i][1] = right_less_index;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 50000;
        int max_size = 50;
        int max_val = 100;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = getRandomArrayNoRepeat(max_size, max_val);
            int[][] ans1 = getNearLessNoRepeat(arr);
            int[][] ans2 = test(arr);
            if (!isEqual(ans1, ans2)) {
                System.out.println("test failed");
                break;
            }
        }

        System.out.println("test end");
    }
}
