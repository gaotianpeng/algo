package tixi.daily37;

import java.util.Arrays;

/*
    leetcode 480
    有一个滑动窗口
        1）L是滑动窗口最左位置、R是滑动窗口最右位置，一开始LR都在数组左侧
        2）任何一步都可能R往右动，表示某个数进了窗口
        3）任何一步都可能L往右动，表示某个数出了窗口

    想知道每一个窗口状态的中位数
 */
public class Code02_SlidingWindowMedian {
    public static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> l;
        public SBTNode<K> r;
        public int size;

        public SBTNode(K k) {
            key = k;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>> {
        private SBTNode<K> root;

        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            SBTNode<K> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            SBTNode<K> rightNode = cur.r;
            cur.r = rightNode.l;
            rightNode.l = cur;
            rightNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<K> maintain(SBTNode<K> cur) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            int leftLeftSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int leftRightSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int rightLeftSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rightRightSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (leftLeftSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode<K> findLastIndex(K key) {
            SBTNode<K> pre = root;
            SBTNode<K> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        private SBTNode<K> add(SBTNode<K> cur, K key) {
            if (cur == null) {
                return new SBTNode<K>(key);
            } else {
                cur.size++;
                if (key.compareTo(cur.key) < 0) {
                    cur.l = add(cur.l, key);
                } else {
                    cur.r = add(cur.r, key);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K> delete(SBTNode<K> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.key) > 0) {
                cur.r = delete(cur.r, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.l = delete(cur.l, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    // free cur memory -> C++
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    // free cur memory -> C++
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    // free cur memory -> C++
                    cur = cur.l;
                } else {
                    SBTNode<K> pre = null;
                    SBTNode<K> des = cur.r;
                    des.size--;
                    while (des.l != null) {
                        pre = des;
                        des = des.l;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.l = des.r;
                        des.r = cur.r;
                    }
                    des.l = cur.l;
                    des.size = des.l.size + (des.r == null ? 0 : des.r.size) + 1;
                    // free cur memory -> C++
                    cur = des;
                }
            }
            return cur;
        }

        private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
            if (kth == (cur.l != null ? cur.l.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.l != null ? cur.l.size : 0)) {
                return getIndex(cur.l, kth);
            } else {
                return getIndex(cur.r, kth - (cur.l != null ? cur.l.size : 0) - 1);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }

        public void add(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            if (lastNode == null || key.compareTo(lastNode.key) != 0) {
                root = add(root, key);
            }
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).key;
        }

    }

    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.valueOf(value).compareTo(o.value)
                    : Integer.valueOf(index).compareTo(o.index);
        }
    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k || k < 1) {
            return null;
        }

        SizeBalancedTreeMap<Node> map = new SizeBalancedTreeMap<>();
        for (int i = 0; i < k - 1; i++) {
            map.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < nums.length; i++) {
            map.add(new Node(i, nums[i]));
            if (map.size() % 2 == 0) {
                Node upmid = map.getIndexKey(map.size() / 2 - 1);
                Node downmid = map.getIndexKey(map.size() / 2);
                ans[index++] = ((double) upmid.value + (double) downmid.value) / 2;
            } else {
                Node mid = map.getIndexKey(map.size() / 2);
                ans[index++] = (double) mid.value;
            }
            map.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }

    // for test
    public static double[] test(int[] nums, int k) {
        if (nums == null || nums.length < k || k < 1) {
            return null;
        }

        double[] ans = new double[nums.length - k + 1];
        int start = 0;
        for (int i = k - 1; i < nums.length; ++i) {
            ans[start] = getMeidan(nums, start, i);
            ++start;
        }

        return ans;
    }

    public static double getMeidan(int[] arr, int start, int end) {
        if (start == end) {
            return (double)arr[start];
        }
        int[] copy = new int[end - start + 1];
        int index = 0;
        for (int i = start; i <= end; ++i) {
            copy[index++] = arr[i];
        }

        int length = copy.length;
        Arrays.sort(copy);
        if (length % 2 == 1) {
            // If the number of elements is odd, return the middle element
            return copy[length / 2];
        } else {
            // If the number of elements is even, return the average of the two middle elements
            return (copy[length / 2 - 1] + copy[length / 2]) / 2.0;
        }
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int)((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)((maxValue + 1) * Math.random()) - (int)(maxValue * Math.random());
        }

        return arr;
    }

    public static boolean isEqual(double[] ans1, double[] ans2) {
        if (ans1 == null && ans2 == null) {
            return true;
        }

        if (ans1 == null || ans2 == null) {
            return false;
        }

        if (ans1.length != ans2.length) {
            return false;
        }

        for (int i = 0; i < ans1.length; ++i) {
            if (ans1[i] != ans2[i]) {
                return false;
            }
        }

        return true;
    }

    public static void printArray(double[] ans) {
        if (ans == null || ans.length == 0) {
            return;
        }
        for (int i = 0; i < ans.length; ++i) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();
    }

    public static void printArray(int[] ans) {
        if (ans == null || ans.length == 0) {
            return;
        }
        for (int i = 0; i < ans.length; ++i) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int maxLen = 20;
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; ++i) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int k = (int)((maxLen + 1) * Math.random());
            double[] ans1 = medianSlidingWindow(arr, k);
            double[] ans2 = test(arr, k);
            if (!isEqual(ans1, ans2)) {
                System.out.println(k);
                printArray(arr);
                printArray(ans1);
                printArray(ans2);
                success = false;
                break;
            }
        }

        System.out.println(success? "success": "failed");
        System.out.println("test end");
    }
}
