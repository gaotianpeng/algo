package shuati.daily04;

import java.util.ArrayList;
import java.util.HashMap;

public class Code01_QueryHobby {
    /*
        数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)
        意思是在数组里下标0~3这个范围上，有几个2？答案返回2。
        假设给你一个数组arr，
        对这个数组的查询非常频繁，都给出来
        请返回所有查询的结果
     */
    public static class QueryBox1 {
        private int[] arr;

        public QueryBox1(int[] array) {
            arr = new int[array.length];
            for (int i = 0; i < arr.length; ++i) {
                arr[i] = array[i];
            }
        }

        public int query(int left, int right, int v) {
            int ans = 0;
            for (; left <= right; ++left) {
                if (arr[left] == v) {
                    ++ans;
                }
            }
            return ans;
        }
    }

    public static class QueryBox2{
        private HashMap<Integer, ArrayList<Integer>> map;

        public QueryBox2(int[] arr) {
            map = new HashMap<>();
            for (int i = 0; i < arr.length; ++i) {
                if (!map.containsKey(arr[i])) {
                    map.put(arr[i], new ArrayList<>());
                }
                map.get(arr[i]).add(i);
            }
        }

        public int query(int left, int right, int v) {
            if (!map.containsKey(v)) {
                return 0;
            }

            ArrayList<Integer> index_arr = map.get(v);
            int a = countLess(index_arr, left);
            int b = countLess(index_arr, right + 1);

            return b - a;
        }

        private int countLess(ArrayList<Integer> arr, int limit) {
            int left = 0;
            int right = arr.size() - 1;
            int most_right = -1;
            while (left <= right) {
                int mid = left + ((right - left) >> 1);
                if (arr.get(mid) < limit) {
                    most_right = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return most_right + 1;
        }
    }

    public static int[] generateRandomArray(int len, int value) {
        int[] ans = new int[(int) (Math.random() * len) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("test start...");

        int test_time = 100000;
        int query_times = 1000;
        int max_n = 30;
        int max_v = 20;
        for (int i = 0; i < test_time; ++i) {
            int[] arr = generateRandomArray(max_n, max_v);
            int n = arr.length;
            QueryBox1 box1 = new QueryBox1(arr);
            QueryBox2 box2 = new QueryBox2(arr);
            for (int j = 0; j < query_times; ++j) {
                int a = (int)(Math.random() * n);
                int b = (int)(Math.random() * n);
                int left = Math.min(a, b);
                int right = Math.max(a, b);
                int v = (int) (Math.random() * max_v) + 1;
                if (box1.query(left, right, v) != box2.query(left, right, v)) {
                    System.out.println("test failed");
                    break;
                }
            }
        }

        System.out.println("test end");
    }
}
