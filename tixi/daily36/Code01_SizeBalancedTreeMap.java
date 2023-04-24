package tixi.daily36;

public class Code01_SizeBalancedTreeMap {
    public static class SBTNode<K extends Comparable<K>, V> {
        public K key;
        public V val;
        public SBTNode<K, V> l;
        public SBTNode<K, V> r;
        public int size;

        public SBTNode(K k, V v) {
            key = k;
            val = v;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        private SBTNode<K, V> root;

        private SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return left;
        }

        private SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size: 0) + 1;
            return right;
        }

        private SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            if (cur == null) {
                return null;
            }

            int left_size = cur.l != null ? cur.l.size : 0;
            int left_left_size = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int left_right_size = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int right_size = cur.r != null ? cur.r.size : 0;
            int right_left_size = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int right_right_size = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            if (left_left_size > right_size) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (left_right_size > right_left_size) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (right_right_size > left_size) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (right_left_size > left_size) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }

            return cur;
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }
        }
    }

    public static void main(String[] args) {
    }
}

