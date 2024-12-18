package tixi.daily35;

import java.util.TreeMap;

public class Code01_AVLTreeMap {
    public static class AVLNode<K extends Comparable<K>, V> {
        public K k;
        public V v;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public int h;

        public AVLNode(K key, V val) {
            k = key;
            v = val;
            h = 1;
        }
    }

    public static class AVLTree<K extends Comparable<K>, V> {
        private AVLNode<K, V> root;
        private int size;

        public AVLTree() {
            root = null;
            size = 0;
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }

            AVLNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.k) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                ++size;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        // 获取key 对应的 value
        public V get(K key) {
            if (root == null) {
                return null;
            }

            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return lastNode.v;
            }

            return null;
        }

        // 获取最小的K
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> ans = root;
            while (ans.l != null) {
                ans = ans.l;
            }
            return ans.k;
        }

        // 获取最大的 K
        public K lastKey() {
            if (root == null) {
                return null;
            }

            AVLNode<K, V> ans = root;
            while (ans.r != null) {
                ans = ans.r;
            }

            return ans.k;
        }

        // 函数返回 <= key的最大K
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        // 返回>=key的最小K
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBig = findLastNoSmallIndex(key);
            return lastNoBig == null ? null : lastNoBig.k;
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
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            // 1) 先用left保存cur.l
            AVLNode<K, V> left = cur.l;
            // 2) 更新cur的左右子树及高度信息
            cur.l = left.r;
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null? cur.r.h: 0) + 1;
            // 3) 更新新的头节点left信息
            left.r = cur;
            left.h = Math.max(left.l != null ? left.l.h : 0, left.r != null ? left.r.h : 0) + 1;
            return left;
        }

        /*
                 A(cur)     左旋         B
               /   \                   /   \
              T     B                 A     C
                  /   \              / \   /  \
                 K     C            T   K  S  F
                      /  \
                     S   F
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            // 1) 先用right保存cur.r
            AVLNode<K, V> right = cur.r;
            // 2) 更新cur信息
            cur.r = right.l;
            cur.h = Math.max(cur.l != null ? cur.l.h: 0, cur.r != null ? cur.r.h: 0) + 1;
            // 3) 更新right信息
            right.l = cur;
            right.h = Math.max(right.l != null ? right.l.h: 0, right.r != null ? right.r.h : 0);
            return right;
        }

        // 找到 <= key 于给定键值的最大节点，返回该节点
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) == 0) {
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return pre;
        }

        // 用于查找 >=key的最小节点并返回该节点, key <= ans.k
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }

            return ans;
        }

        // 找到 <= key 的第一个
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> ans = null;
			AVLNode<K, V> cur = root;
			while (cur != null) {
				if (key.compareTo(cur.k) == 0) {
					ans = cur;
					break;
				} else if (key.compareTo(cur.k) < 0) {
					cur = cur.l;
				} else {
					ans = cur;
					cur = cur.r;
				}
			}
			return ans;
        }

        /*
            0）先找到key所在的节点
            1）如果该节点没有左孩子、没有右孩子，直接删除即可
            2）如果该节点有左孩子、没有右孩子，直接用左孩子顶替该节点
            3）如果该节点没有左孩子、有右孩子，直接用右孩子顶替该节点
            4）如果该节点有左孩子、有右孩子，用该节点后继节点顶替该节点
         */
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            // 要删除的key位于cur节点右子树
            if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            // 要删除的key位于cur节点左子树
            } else if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            // 要删除cur节点
            } else {
                if (cur.l == null && cur.r == null) { // cur左右子树均为空
                    cur = null;
                } else if (cur.l == null && cur.r != null) { // cur左子树为空，右子树不为空
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) { // cur左子树不为空，右子树为空
                    cur = cur.l;
                } else { // cur左右子树均不为空
                    // 可以以cur右子树的最左节点来代替cur的位置
                    // 也可以以cur左子树的最右节点来代替cur的位置
                    // 这里以cur右子树的最左节点来代替cur的位置
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = delete(cur.r, des.k);
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }

            if (cur != null) {
                cur.h = Math.max(cur.l != null ? cur.l.h: 0, cur.r != null ? cur.r.h:0) + 1;
            }

            return maintain(cur);
        }

        /*
            确保树在节点插入或删除后保持平衡
         */
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }

            // 获取左右子树的高度
            int leftH = cur.l != null ? cur.l.h : 0;
            int rightH = cur.r != null ? cur.r.h : 0;

            // 如果树不平衡
            if (Math.abs(leftH - rightH) > 1) {
                //  1) 左子树比右子树高，需要进行右旋操作
                /*
                           A(cur)  右旋         B
                          /  \               /    \
                        B     T             C      A
                       / \                 / \    /  \
                      C   K               S   F   K   T
                     / \
                    S   F
                 */
                if (leftH > rightH) {
                    int leftLeftH = cur.l != null && cur.l.l != null ? cur.l.l.h : 0;
                    int leftRightH = cur.l != null && cur.l.r != null ? cur.l.r.h : 0;
                    if (leftLeftH > leftRightH) { // LL型，对cur右旋
                        /*
                                   A(cur)  对cur右旋    B
                                  /  \               /    \
                                B     T             C      A
                               / \                 / \    /  \
                              C   K               S   F   K   T
                             / \
                            S   F
                         */
                        cur = rightRotate(cur);
                    } else { // LR型，对cur右旋
                        /*
                               A(cur) 先对cur.l左旋   A(cur) 再对cur右旋  K
                              /  \                 /  \               /   \
                            B     T               K    T             B     A
                           / \                   / \                / \   / \
                          C   K                 B   F              C   S  F  T
                             / \               / \
                            S   F             C   S
                        */
                        // 1.1 )cur.l.l.h < cur.l.r.h 先对cur.l 进行左旋
                        cur.l = leftRotate(cur.l);
                        // 1.2 )对cur进程右旋
                        cur = rightRotate(cur);
                    }
                // 2) 右子树比左子树高，需要进行左旋操作
                } else {
                    /*
                         A(cur)      左旋         B
                       /   \                   /   \
                      T     B                 A     C
                          /   \              / \   /  \
                         K     C            T   K  S  F
                              /  \
                             S   F
                    */
                    int rightLeftH = cur.r != null && cur.r.l != null ? cur.r.l.h : 0;
                    int rightRightH = cur.r != null && cur.r.r != null ? cur.r.r.h : 0;
                    // 2.1) RR型, 对cur左旋
                    if (rightRightH > rightLeftH) {
                       /*
                             A(cur)      对cur左旋   B
                           /   \                   /   \
                          T     B                 A     C
                              /   \              / \   /  \
                             K     C            T   K  S  F
                                  /  \
                                 S   F
                        */
                        cur = leftRotate(cur);
                    // 2.2) RL型
                    } else {
                       /*
                             A(cur)  先对cur.r右旋  A(cur)  再对cur左旋   K
                           /   \                  /  \                 /  \
                          T     B                T    K              A     B
                              /   \                  / \            / \   / \
                             K     C                S   B          T   S  F  C
                           /  \                        /  \
                          S    F                      F   C
                        */
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }

            return cur;
        }

        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new AVLNode<K, V>(key, value);
            } else {
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }

                cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
                return maintain(cur);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = 100;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            AVLTree<Integer, Integer> avlTree = new AVLTree<>();
            for (int j = 0; j < 100; ++j) {
                if (Math.random() < 0.1) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.containsKey(key) != avlTree.containsKey(key)) {
                        success = false;
                        System.out.println("test failed 1");
                        break;
                    }
                } else if (Math.random() < 0.2) {
                    int key = (int)(Math.random() * maxVal);
                    int val = (int)(Math.random() * maxVal);
                    treeMap.put(key, val);
                    avlTree.put(key, val);
                    if (treeMap.size() != avlTree.size()) {
                        success = false;
                        System.out.println("test failed 2");
                        break;
                    }
                } else if (Math.random() < 0.4) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.get(key) == avlTree.get(key)) {
                        continue;
                    } else {
                        success = false;
                        System.out.println("test faield 3");
                        break;
                    }
                } else if (Math.random() < 0.6) {
                    int key = (int)(Math.random() * maxVal);
                    treeMap.remove(key);
                    avlTree.remove(key);
                    if (treeMap.size() != avlTree.size()) {
                        success = false;
                        System.out.println("test failed 4");
                        break;
                    }
                } else if (Math.random() < 0.7) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.firstKey() != avlTree.firstKey()) {
                        success = false;
                        System.out.println("test failed 5");
                        break;
                    }
                } else if (Math.random() < 0.8) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.lastKey() != avlTree.lastKey()) {
                        success = false;
                        System.out.println("test failed 6");
                        break;
                    }
                } else if (Math.random() < 0.9) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.ceilingKey(key) != avlTree.ceilingKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                } else {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.floorKey(key) != avlTree.floorKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                }
            }
            if (!success) {
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end...");
    }
}
