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
            
            }
        }

        public void remove(K key) {
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
            return null;
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

        public K ceilingKey(K key) {
            return null;
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

        // 找到 <= key 最近的
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

        // 找到 >=key 的最近的
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
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        System.out.println("test end");
    }
}
