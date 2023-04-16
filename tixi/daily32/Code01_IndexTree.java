package tixi.daily32;

public class Code01_IndexTree {
    /*
        IndexTree
            特点：
            1）支持区间查询
            2）没有线段树那么强，但是非常容易改成一维、二维、三维的结构
            3）只支持单点更新
     */
    public static class IndexTree {
        private int[] tree;
        private int n;

        public IndexTree(int size) {
            n = size;
            tree = new int[n + 1];
        }

        public int sum(int index) {
            int ans = 0;
            while (index > 0) {
                ans += tree[index];
                index -= index & -index;
            }

            return ans;
        }

        public void add(int index, int d) {
            while (index <= n) {
                tree[index] += d;
                index += index & -index;
            }
        }
    }

    public static class Right {
        private int[] nums;
        private int N;

        public Right(int size) {
            N = size + 1;
            nums = new int[N + 1];
        }

        public int sum(int index) {
            int ret = 0;
            for (int i = 1; i <= index; i++) {
                ret += nums[i];
            }
            return ret;
        }

        public void add(int index, int d) {
            nums[index] += d;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 1000000;
        int max_val = 100;
        int max_n = 100;
        IndexTree tree = new IndexTree(max_n);
        Right test = new Right(max_n);
        for (int i = 0; i < test_times; ++i) {
            int index = (int)(Math.random() * max_n) + 1;
            if (Math.random() <= 0.5) {
                int add = (int)(Math.random() * max_val);
                tree.add(index, add);
                test.add(index, add);
            } else {
                if (tree.sum(index) != test.sum(index)) {
                    System.out.println("test failed");
                    break;
                }
            }
        }

        System.out.println("test end");
    }
}
