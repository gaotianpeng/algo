package tixi.daily31;

/*
    1，一种支持范围整体修改和范围整体查询的数据结构
    2，解决的问题范畴：
        大范围信息可以只由左、右两侧信息加工出，
        而不必遍历左右两个子范围的具体状况

    给定一个数组arr，用户希望你实现如下三个方法
    1）void add(int L, int R, int V) :  让数组arr[L…R]上每个数都加上V
    2）void update(int L, int R, int V) :  让数组arr[L…R]上每个数都变成V
    3）int sum(int L, int R) :让返回arr[L…R]这个范围整体的累加和
    怎么让这三个方法，时间复杂度都是O(logN)

 */
public class Code01_SegmentTree {
    public static class SegmentTree {
        private int MAXN;
        private int[] arr;  // arr[]为原序列的信息从0开始，但在arr里是从1开始的
        private int[] sum;  // sum[]模拟线段树维护区间和
        private int[] lazy;  // lazy[]为累加和懒惰标记
        private int[] change; // change[]为更新的值
        private boolean[] update; // update[]为更新慵懒标记

        public SegmentTree(int[] origin) {
            MAXN = origin.length + 1;
            arr = new int[MAXN]; // arr[0] 不用 从1开始使用
            for (int i = 0; i < origin.length; ++i) {
                arr[i + 1] = origin[i];
            }
            sum = new int[MAXN << 2];  // 用来支持脑补概念中，某一个范围的累加和信息
            lazy = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
            change = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围有没有更新操作的任务
            update = new boolean[MAXN << 2]; // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
        }

        public void build(int left, int right) {
            build(left, right, 1);
        }

        public void add(int left, int right, int val) {
            add(left, right, val, 1, MAXN - 1, 1);
        }

        public void update(int left, int right, int val) {
            update(left, right, val, 1, MAXN - 1, 1);
        }

        public long query(int left, int right) {
            return query(left, right, 1, MAXN - 1, 1);
        }

        /*
            left ~ right 任务
         */
        private void add(int left, int right, int val, int L, int R, int rt) {
            // 任务把此时的范围全包了
            if (left <= L && right >= R) {
                sum[rt] += (R - L + 1) * val;
                lazy[rt] += val;
                return;
            }

            // 任务无法把范围全包了
            int mid = (L + R) >> 1;
            pushDown(rt, mid - L + 1, R - mid);
            if (left <= mid) {
                add(left, right, val, L, mid, rt << 1);
            }
            if (right > mid) {
                add(left, right, val, mid + 1, R, rt << 1 | 1);
            }
            pushUp(rt);
        }

        // 之前的，所有懒增加，和懒更新，从父范围，发给左右两个子范围
        // 分发策略是什么
        // nLeft表示左子树元素结点个数，nRight表示右子树结点个数
        private void pushDown(int rt, int nLeft, int nRight) {
            if (update[rt]) {
                update[rt<<1] = true;
                update[rt<<1 | 1] = true;
                change[rt<<1] = change[rt];
                change[rt<<1|1] = change[rt];
                lazy[rt<<1] = 0;
                lazy[rt<<1|1] = 0;
                sum[rt<<1] = change[rt] * nLeft;
                sum[rt<<1|1] = change[rt] * nRight;
                update[rt] = false;
            }

            if (lazy[rt] != 0) {
                lazy[rt<<1] += lazy[rt];
                sum[rt<<1] += lazy[rt] * nLeft;
                lazy[rt<<1|1] += lazy[rt];
                sum[rt<<1|1] += lazy[rt] * nRight;
                lazy[rt] = 0;
            }
        }
        // 计算sum[rt]位置的值
        private void pushUp(int rt) {
            sum[rt] = sum[rt << 1] + sum[rt << 1| 1];
        }

        private void update(int left, int right, int val, int L, int R, int rt) {
            // 当前任务把此时的范围全包了
            if (left <= L && right >= R) {
                update[rt] = true;
                change[rt] = val;
                sum[rt] = val * (R - L + 1);
                lazy[rt] = 0;
                return;
            }

            // 当前任务不能把此时的范围全包了
            int mid = (L + R) >> 1;
            pushDown(rt, mid - L + 1, R - mid);
            if (left <= mid) {
                update(left, right, val, L, mid, rt << 1);
            }
            if (right > mid) {
                update(left, right, val, mid + 1, R, rt << 1| 1);
            }
            pushUp(rt);
        }

        private long query(int left, int right, int L, int R, int rt) {
            if (left <= L & right >= R) {
                return sum[rt];
            }

            int mid = (L + R) >> 1;
            pushDown(rt, mid - L + 1, R - mid);
            long ans = 0;
            if (left <= mid) {
                ans += query(left, right, L, mid, rt << 1);
            }
            if (right > mid) {
                ans += query(left, right, mid + 1, R, rt << 1 |1);
            }

            return ans;
        }

        // 在初始化阶段，先把sum数组，填好
        // 在arr[L~R]范围上，去build，1~N，
        // rt : 这个范围在sum中的下标
        private void build(int L, int R, int rt) {
            if (L == R) {
                sum[rt] = arr[L];
                return;
            }

            int mid = (L + R) >> 1;
            build(L, mid, rt<<1);
            build(mid + 1, R, rt<<1 | 1);
            pushUp(rt);
        }
    }

    public static class Right {
        private int[] arr;

        public Right(int[] origin) {
            arr = new int[origin.length + 1];
            for (int i = 0; i < origin.length; ++i) {
                arr[i+1] = origin[i];
            }
        }

        public void update(int left, int right, int val) {
            for (int i = left; i <= right; ++i) {
                arr[i] = val;
            }
        }

        public void add(int left, int right, int val) {
            for (int i = left; i <= right; ++i) {
                arr[i] += val;
            }
        }

        public long query(int left, int right) {
            long ans = 0;
            for (int i = left; i <= right; ++i) {
                ans += arr[i];
            }

            return ans;
        }
    }

    public static int[] genarateRandomArray(int len, int max) {
        int size = (int) (Math.random() * len) + 1;
        int[] origin = new int[size];
        for (int i = 0; i < size; i++) {
            origin[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return origin;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int len = 100;
        int max = 1000;
        int testTimes = 5000;
        int addOrUpdateTimes = 1000;
        int queryTimes = 500;
        boolean success = true;
        for (int i = 0; i < testTimes; i++) {
            int[] origin = genarateRandomArray(len, max);
            SegmentTree seg = new SegmentTree(origin);
            int N = origin.length;
            seg.build(1, N);
            Right rig = new Right(origin);
            for (int j = 0; j < addOrUpdateTimes; j++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                int C = (int) (Math.random() * max) - (int) (Math.random() * max);
                if (Math.random() < 0.5) {
                    seg.add(L, R, C);
                    rig.add(L, R, C);
                } else {
                    seg.update(L, R, C);
                    rig.update(L, R, C);
                }
            }
            for (int k = 0; k < queryTimes; k++) {
                int num1 = (int) (Math.random() * N) + 1;
                int num2 = (int) (Math.random() * N) + 1;
                int L = Math.min(num1, num2);
                int R = Math.max(num1, num2);
                long ans1 = seg.query(L, R);
                long ans2 = rig.query(L, R);
                if (ans1 != ans2) {
                    success = false;
                    System.out.println(ans1);
                    System.out.println(ans2);
                    System.out.println("test failed");
                    break;
                }
            }

            if (!success) {
                break;
            }
        }
        System.out.println("test end");
    }
}
