package tixi.daily25;

import java.sql.Array;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Code01_MonotonousStack {
    /*
        单调栈：一种特别设计的栈结构，为了解决如下的问题：
            给定一个可能含有重复值的数组arr，i位置的数一定存在如下两个信息
            1）arr[i]的左侧离i最近并且小于(或者大于)arr[i]的数在哪？
            2）arr[i]的右侧离i最近并且小于(或者大于)arr[i]的数在哪？
            如果想得到arr中所有位置的两个信息，怎么能让得到信息的过程尽量快
     */
    public static int[][] getNearLessNoRepeat(int[] arr) {
        int n = arr.length;
        int[][] ans = new int[n][2];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int cur = stack.pop();
                ans[cur][0] = stack.isEmpty() ? -1:stack.peek();
                ans[cur][1] = i;
            }
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int cur = stack.pop();
            ans[cur][0] = stack.isEmpty() ? -1 : stack.peek();
            ans[cur][1] = -1;
        }

        return ans;
    }

    public static int[][] getNearLesss(int[] arr) {
        int n = arr.length;
        int[][] ans = new int[n][2];

        Stack<List<Integer>> stack = new Stack<>();
        for (int i = 0; i < n; ++i) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                List<Integer> cur = stack.pop();
                int cur_left_index = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for (Integer idx : cur) {
                    ans[idx][0] = cur_left_index;
                    ans[idx][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().add(Integer.valueOf(i));
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                stack.add(list);
            }
        }

        while(!stack.isEmpty()) {
            List<Integer> cur = stack.pop();
            int cur_left_index = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for (Integer idx: cur) {
                ans[idx][0] = cur_left_index;
                ans[idx][1] = -1;
            }
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
        int n = arr.length;
        int[][] ans = new int[n][2];
        for (int i = 0; i < n; ++i) {
            int cur = i-1;
            int left_less_index = -1;
            int right_less_index = -1;

            while (cur >= 0) {
                if (arr[cur] < arr[i]) {
                    left_less_index = cur;
                    break;
                }
                --cur;
            }

            cur = i + 1;
            while (cur < n) {
                if (arr[cur] < arr[i]) {
                    right_less_index = cur;
                    break;
                }
                ++cur;
            }

            ans[i][0] = left_less_index;
            ans[i][1] = right_less_index;
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_size = 50;
        int max_val = 100;
        for (int i = 0; i < test_times; ++i) {
            int[] arr = getRandomArrayNoRepeat(max_size, max_val);
            int[][] ans1 = getNearLessNoRepeat(arr);
            int[][] ans2 = test(arr);
            if (!isEqual(ans1, ans2)) {
                System.out.println("no repeat test failed");
                break;
            }

            int[] arr1 = getRandomArray(max_size, max_val);
            int[][] ans3 = getNearLesss(arr1);
            int[][] ans4 = test(arr1);
            if (!isEqual(ans3, ans3)) {
                System.out.println("have repeat test failed");
            }
        }

        System.out.println("test end");
    }
}
