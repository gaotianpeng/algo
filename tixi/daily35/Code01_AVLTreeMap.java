package tixi.daily35;

public class Code01_AVLTreeMap {
    public static class AVLNode<K extends Comparable<K>, V> {
        public K key;
        public V val;
        public AVLNode<K, V> l;
        public AVLNode<K, V> r;
        public int height;

        public AVLNode(K k, V v) {
            key = k;
            val = v;
            height = 1;
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
            return lastNode != null && key.compareTo(lastNode.key) == 0 ? true : false;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.val = value;
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

            AVLNode<K, V> last_node = findLastIndex(key);
            if (last_node != null && key.compareTo(last_node.key) == 0) {
                return last_node.val;
            }

            return null;
        }

        // 获取最小的key
        public K fistKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> ans = root;
            while (ans.l != null) {
                ans = ans.l;
            }
            return ans.key;
        }

        // 获取最大的key
        public K lastKey() {
            if (root == null) {
                return null;
            }

            AVLNode<K, V> ans = root;
            while (ans.r != null) {
                ans = ans.r;
            }

            return ans.key;
        }

        // 函数返回小于或等于key的最大key
        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        // 返回大于等于给定键的最小键
        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> last_no_big = findLastNoBigIndex(key);
            return last_no_big == null ? null : last_no_big.key;
        }

        /*
                   A       右旋         B
                  /  \               /    \
                B     T             C      A
               / \                 / \    /  \
              C   K               S   F   K   T
             / \
            S   F
         */
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            cur.height = Math.max((cur.l != null ? cur.l.height: 0), cur.r != null ? cur.r.height : 0) + 1;
            left.height = Math.max((left.l != null ? left.l.height : 0), (left.r != null ? left.r.height: 0)) + 1;
            return left;
        }

        /*
                 A          左旋         B
               /   \                   /   \
              T     B                 A     C
                  /   \              / \   /  \
                 K     C            T   K  S  F
                      /  \
                     S   F
         */
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.height = Math.max((cur.l != null ? cur.l.height: 0), (cur.r != null ? cur.r.height: 0)) + 1;
            right.height = Math.max((right.l != null ? right.l.height: 0), (right.r != null? right.r.height : 0)) + 1;
            return right;
        }

        // 找到 <= key 于给定键值的最大节点，返回该节点
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = root;
            AVLNode<K, V> cur = root;
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

        // 用于查找 >=key的最小节点并返回该节点
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
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

        // 找到 <= key 的第一个
        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
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

        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.key) > 0) {
                cur.r = delete(cur.r, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.l = delete(cur.l, key);
            } else {
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else {
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = delete(cur.r, des.key);
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }

            if (cur != null) {
                cur.height = Math.max(cur.l != null ? cur.l.height : 0, cur.r != null ? cur.r.height : 0) + 1;
            }

            return maintain(cur);
        }

        /*

         */
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }

            int left_height = cur.l != null ? cur.l.height : 0;
            int right_height = cur.r != null ? cur.r.height : 0;
            if (Math.abs(left_height - right_height) > 1) {
                // 左子树比右子树高，需要进行右旋操作
                /*
                           A(cur)  右旋         B
                          /  \               /    \
                        B     T             C      A
                       / \                 / \    /  \
                      C   K               S   F   K   T
                     / \
                    S   F
                 */
                if (left_height > right_height) {
                    int left_left_height = cur.l != null && cur.l.l != null ? cur.l.l.height : 0;
                    int left_right_height = cur.l != null && cur.l.r != null ? cur.l.r.height : 0;
                    if (left_left_height >= left_right_height) {
                        cur = rightRotate(cur); // cur.l.l.height >= cur.l.r.height 对cur右旋
                    } else {
                        /*
                                   A(cur)  对cur.l左旋    A(cur) 对cur右旋    K
                                  /  \                 /  \               /   \
                                B     T               K    T             B     A
                               / \                   / \                / \   / \
                              C   K                 B   F              C   S  F  T
                                 / \               / \
                                S   F             C   S
                         */
                        cur.l = leftRotate(cur.l); // cur.l.l.height < cur.l.r.height 先对cur.l 进行左旋，再对cur进程右旋
                        cur = rightRotate(cur);
                    }
                } else { // 右子树比左子树高，需要进行左旋操作
                    /*
                         A(cur)      左旋         B
                       /   \                   /   \
                      T     B                 A     C
                          /   \              / \   /  \
                         K     C            T   K  S  F
                              /  \
                             S   F
                    */
                    int right_left_height = cur.r != null && cur.r.l != null ? cur.r.l.height : 0;
                    int right_right_height = cur.r != null && cur.r.r != null  ? cur.r.r.height : 0;
                    if (right_right_height > right_left_height) {
                        cur = leftRotate(cur);
                    } else {
                        cur.r = leftRotate(cur.r);
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
                if (key.compareTo(cur.key) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }

                cur.height = Math.max(cur.l != null ? cur.l.height : 0, cur.r != null ? cur.r.height : 0) + 1;
                return maintain(cur);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        AVLTree<Integer, Integer> tree = new AVLTree<>();
        tree.put(1, 2);
        tree.put(2, 3);
        System.out.println(tree.size());
        System.out.println(tree.get(1));
        System.out.println(tree.get(2));
        tree.remove(2);
        System.out.println(tree.size());
        System.out.println("test end");
    }
}
