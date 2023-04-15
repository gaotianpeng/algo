package tixi.daily31;

import java.util.*;

public class Code02_FallingSuqares {
    /*
        leetcode 699 掉落的方块
            给你一个二维整数数组 positions ，其中 positions[i] = [lefti, sideLengthi] 表示：第 i 个方块边长为 sideLengthi ，
            其左侧边与 x 轴上坐标点lefti 对齐。
            每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 另一个正方形的顶边 或者是 x 轴上 。
            一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。
            在每个方块掉落后，你必须记录目前所有已经落稳的 方块堆叠的最高高度 。
            返回一个整数数组 ans ，其中 ans[i] 表示在第 i 块方块掉落后堆叠的最高高度
     */
    public static class SegmentTree {
        private int[] max;
        private int[] change;
        private boolean[] update;

        public SegmentTree(int size) {
            int N = size + 1;
            max = new int[N << 2];

            change = new int[N << 2];
            update = new boolean[N << 2];
        }

        private void pushUp(int rt) {
            max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
        }

        // ln表示左子树元素结点个数，rn表示右子树结点个数
        private void pushDown(int rt, int ln, int rn) {
            if (update[rt]) {
                update[rt << 1] = true;
                update[rt << 1 | 1] = true;
                change[rt << 1] = change[rt];
                change[rt << 1 | 1] = change[rt];
                max[rt << 1] = change[rt];
                max[rt << 1 | 1] = change[rt];
                update[rt] = false;
            }
        }

        public void update(int L, int R, int C, int l, int r, int rt) {
            if (L <= l && r <= R) {
                update[rt] = true;
                change[rt] = C;
                max[rt] = C;
                return;
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            if (L <= mid) {
                update(L, R, C, l, mid, rt << 1);
            }
            if (R > mid) {
                update(L, R, C, mid + 1, r, rt << 1 | 1);
            }
            pushUp(rt);
        }

        public int query(int L, int R, int l, int r, int rt) {
            if (L <= l && r <= R) {
                return max[rt];
            }
            int mid = (l + r) >> 1;
            pushDown(rt, mid - l + 1, r - mid);
            int left = 0;
            int right = 0;
            if (L <= mid) {
                left = query(L, R, l, mid, rt << 1);
            }
            if (R > mid) {
                right = query(L, R, mid + 1, r, rt << 1 | 1);
            }
            return Math.max(left, right);
        }

    }

    public static class Right {
        private int[] arr;

        public Right(int n) {
            arr = new int[n+1];
        }

        public void update(int left, int right, int val) {
            for (int i = left; i <= right; ++i) {
                arr[i] = val;
            }
        }

        public int query(int left, int right) {
            int ans = 0;
            for (int i = left; i <= right; ++i) {
                ans = Math.max(arr[i], ans);
            }

            return ans;
        }
    }

    static public HashMap<Integer, Integer> index(int[][] positions) {
        TreeSet<Integer> pos = new TreeSet<>();
        for (int[] arr : positions) {
            pos.add(arr[0]);
            pos.add(arr[0] + arr[1] - 1);
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (Integer index : pos) {
            map.put(index, ++count);
        }
        return map;
    }

    static public List<Integer> fallingSquares(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        SegmentTree segmentTree = new SegmentTree(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R, 1, N, 1) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height, 1, N, 1);
        }
        return res;
    }

    public static List<Integer> test(int[][] positions) {
        HashMap<Integer, Integer> map = index(positions);
        int N = map.size();
        Right segmentTree = new Right(N);
        int max = 0;
        List<Integer> res = new ArrayList<>();
        // 每落一个正方形，收集一下，所有东西组成的图像，最高高度是什么
        for (int[] arr : positions) {
            int L = map.get(arr[0]);
            int R = map.get(arr[0] + arr[1] - 1);
            int height = segmentTree.query(L, R) + arr[1];
            max = Math.max(max, height);
            res.add(max);
            segmentTree.update(L, R, height);
        }
        return res;
    }

    static int[][] randomArray(int m, int left_max, int len_max) {
        int n = (int)(Math.random() * m + 1);
        int[][] ans = new int[n][2];
        for (int i = 0; i < n; ++i) {
            ans[i][0] = (int)(Math.random()*left_max + 1);
            ans[i][1] = (int)(Math.random()*len_max + 2);
        }

        return ans;
    }

    static void print(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int left_max = 100;
        int len_max = 10;
        int max_n = 100;
        for (int i = 0; i < test_times; ++i) {
            int[][] positions = randomArray(max_n, left_max, len_max);
            List<Integer> ans1 = fallingSquares(positions);
            List<Integer> ans2 = test(positions);
            if (!ans1.equals(ans2)) {
                System.out.println("test failed");
                break;
            }
        }

       System.out.println("test end...");
    }
}
