package review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/*
    给定k个非空递增有序数组(int)，将求将全部元素合并到递增有序数组中
 */
public class Code_0006_MkergeKSortedArray {
    public static class Element implements Comparable<Element> {
        int row;
        int col;
        int value;

        public Element(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        @Override
        public int compareTo(Element o) {
            return this.value - o.value;
        }
    }

    public static int[] mergeKSortedArray(ArrayList<ArrayList<Integer>> arr) {
        if (arr == null || arr.size() == 0) {
            return null;
        }

        int k = arr.size();
        PriorityQueue<Element> minQueue = new PriorityQueue<>();

        for (int i = 0; i < k; ++i) {
            if (arr.get(i) != null) {
                minQueue.add(new Element(i, 0, arr.get(i).get(0)));
            }
        }

        ArrayList<Integer> ans = new ArrayList<>();
        while (!minQueue.isEmpty()) {
            Element curMin = minQueue.poll();
            ans.add(curMin.value);
            if (curMin.col + 1 < arr.get(curMin.row).size()) {
                minQueue.add(new Element(curMin.row, curMin.col + 1, arr.get(curMin.row).get(curMin.col + 1)));
            }
        }

        return ans.stream().mapToInt(Integer::intValue).toArray();
    }

    /*
        for test
    */
    public static int[] test(ArrayList<ArrayList<Integer>> arr) {
        if (arr == null || arr.size() == 0) {
            return null;
        }

        int k = arr.size();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.get(i).size(); ++j) {
                ans.add(arr.get(i).get(j));
            }
        }

        ans.sort(Integer::compareTo);
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }


    public static ArrayList<ArrayList<Integer>> generateRandomSortedData(int k, int maxlen, int minValue, int maxValue) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < k; ++i) {
            ArrayList<Integer> data = new ArrayList<>();
            int len = (int) (Math.random() * maxlen) + 1;
            for (int j = 0; j < len; j++) {
                data.add((int) (Math.random() * (maxValue - minValue + 1)) + minValue); 
            }
            data.sort(Integer::compareTo);
            ans.add(data);
        }

        return ans;
    }

    public static void simpleTest() {
        Integer[] a = new Integer[]{1, 1, 3, 5};
        Integer[] b = new Integer[]{3, 4, 7, 7};
        Integer[] c = new Integer[]{4, 10, 12, 34};
        ArrayList<ArrayList<Integer>> arr = new ArrayList<>();
        arr.add(new ArrayList<>(Arrays.asList(a)));
        arr.add(new ArrayList<>(Arrays.asList(b)));
        arr.add(new ArrayList<>(Arrays.asList(c)));

        int[] result = mergeKSortedArray(arr);
        System.out.println(Arrays.toString(result));
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 10000;
        int maxValue = 100;
        int minValue = 1;
        int maxK = 10;
        int maxLen = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            int k = (int) (Math.random() * maxK) + 1;
            ArrayList<ArrayList<Integer>> arr = generateRandomSortedData(k, 100, minValue, maxValue);
            int[] result = mergeKSortedArray(arr);
            int[] testResult = test(arr);
            if (!Arrays.equals(result, testResult)) {
                System.out.println(Arrays.toString(result));
                System.out.println(Arrays.toString(testResult));
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
