package tixi.daily36;

import java.util.TreeMap;

/*
    1）让每一个叔叔节点为头的树，节点个数都不少于其任何一个侄子节点
    2）也是从底层被影响节点开始向上做路径每个节点检查
    3）与AVL树非常像，也是四种违规类型：LL、RR、LR、RL
    4）与AVL树非常像，核心点是：
        LL（做一次右旋）、RR（做一次左旋）
        LR和RL（利用旋转让底层那个上到顶部）
    5）与AVL树不同的是，每轮经过调整后，谁的孩子发生变化了，谁就再查

    SB树在使用时候的改进
        1）删除时候可以不用检查
        2）就把平衡性的调整放在插入的时候
        3）因为这种只要变就递归的特性，别的树没有
        4）可以在节点上封装别的数据项，来增加功能
 */
public class Code01_SizeBalancedTreeMap {
    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V val;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;

        public SBTNode(K k, V v) {
            size = 1;
            key = k;
            val = v;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        private SBTNode<K, V> root;

        public SizeBalancedTreeMap() {
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }

            SBTNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }

            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.val = val;
            } else {
                root = add(root, key, val);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }

            SBTNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.val;
            } else {
                return null;
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }

            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        private SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            if (cur == null) {
                return null;
            }
            cur.size--;

            if (key.compareTo(cur.key) > 0) {
                cur.r = delete(cur.r, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.l = delete(cur.l, key);
            } else { // 当前要删除cur
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    SBTNode<K, V> pre = null;
                    SBTNode<K, V> des = cur.r;
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
                    cur = des;
                }
            }

            return cur;
        }


        private SBTNode<K, V> add(SBTNode<K, V> cur, K key, V val) {
            if (cur == null) {
                return new SBTNode<>(key, val);
            } else {
                ++cur.size;
                if (key.compareTo(cur.key) < 0) {
                    cur.l = add(cur.l, key, val);
                } else {
                    cur.r = add(cur.r, key, val);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
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

        /*
                    A(cur)  右旋         B
                    /  \               /    \
                B     T             C      A
                / \                 / \    /  \
                C   K               S   F   K   T
                / \
            S   F
        */
        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> leftNode = cur.l;
            cur.l = leftNode.r;
            leftNode.r = cur;
            leftNode.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return right;
        }


        // 找到最后一个 <= key
        private SBTNode<K, V> findLastIndex(K key) {
            if (key == null) {
                return null;
            }

            SBTNode<K, V> ans = root;
            SBTNode<K, V> cur = root;
            while (cur != null) {
                ans = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }

            return ans;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }

            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }

            return cur.key;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }

            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.key;
        }

        // >= key
        private SBTNode<K, V> findNoSmallNodeInde(K key) {
            if (key == null) {
                 return null;
            }

            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null){
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }

            return ans;
        }
        // <= key
        private SBTNode<K, V> findNoBigNodeInde(K key) {
            if (key == null) {
                 return null;
            }

            SBTNode<K, V> ans = null;
            SBTNode<K, V> cur = root;
            while (cur != null){
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    ans = cur;
                    cur = cur.r;
                }
            }

            return ans;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SBTNode<K, V> noSmall = findNoSmallNodeInde(key);
            return noSmall != null ? noSmall.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            SBTNode<K, V> noBig = findNoBigNodeInde(key);
            return noBig != null ? noBig.key : null;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            SizeBalancedTreeMap<Integer, Integer> sbTree = new SizeBalancedTreeMap<>();
            for (int j = 0; j < 100; ++j) {
                if (Math.random() < 0.1) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.containsKey(key) != sbTree.containsKey(key)) {
                        success = false;
                        System.out.println("test failed 1");
                        break;
                    }
                } else if (Math.random() < 0.2) {
                    int key = (int)(Math.random() * maxVal);
                    int val = (int)(Math.random() * maxVal);
                    treeMap.put(key, val);
                    sbTree.put(key, val);
                    if (treeMap.size() != sbTree.size()) {
                        success = false;
                        System.out.println("test failed 2");
                        break;
                    }
                } else if (Math.random() < 0.4) {
                    int key = (int) (Math.random() * maxVal);
                    if (treeMap.get(key) == sbTree.get(key)) {
                        continue;
                    } else {
                        success = false;
                        System.out.println("test faield 3");
                        break;
                    }
                } else if (Math.random() < 0.6) {
                    int key = (int)(Math.random() * maxVal);
                    treeMap.remove(key);
                    sbTree.remove(key);
                    if (treeMap.size() != sbTree.size()) {
                        success = false;
                        System.out.println("test failed 4");
                        break;
                    }
                } else if (Math.random() < 0.7) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.firstKey() != sbTree.firstKey()) {
                        success = false;
                        System.out.println("test failed 5");
                        break;
                    }
                } else if (Math.random() < 0.8) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.lastKey() != sbTree.lastKey()) {
                        success = false;
                        System.out.println("test failed 6");
                        break;
                    }
                } else if (Math.random() < 0.9) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.ceilingKey(key) != sbTree.ceilingKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                } else {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.floorKey(key) != sbTree.floorKey(key)) {
                        success = false;
                        System.out.println("test failed 8");
                        break;
                    }
                }
            }

            if (!success) {
                break;
            }
        }

        System.out.println(success? "success":"failed");
        System.out.println("test end...");
    }
}
