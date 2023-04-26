package tixi.daily36;

import java.util.TreeMap;

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

            SBTNode<K, V> last_node = findLastIndex(key);
            return last_node != null && key.compareTo(last_node.key) == 0 ? true : false;
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        public void put(K key, V val) {
            if (key == null) {
                return;
            }

            SBTNode<K, V> last_node = findLastIndex(key);
            if (last_node != null && key.compareTo(last_node.key) == 0) {
                last_node.val = val;
            } else {
                root = add(root, key, val);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }

            SBTNode<K, V> last_node = findLastIndex(key);
            if (last_node != null && key.compareTo(last_node.key) == 0) {
                return last_node.val;
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
            } else if (left_right_size > right_size) {
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
            SBTNode<K, V> no_small = findNoSmallNodeInde(key);
            return no_small != null ? no_small.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            SBTNode<K, V> no_big = findNoBigNodeInde(key);
            return no_big != null ? no_big.key : null;
        }
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 100;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            TreeMap<Integer, Integer> tree_map = new TreeMap<>();
            SizeBalancedTreeMap<Integer, Integer> sb_tree = new SizeBalancedTreeMap<>();
            for (int j = 0; j < 100; ++j) {
                if (Math.random() < 0.1) {
                    int key = (int)(Math.random() * max_val);
                    if (tree_map.containsKey(key) != sb_tree.containsKey(key)) {
                        success = false;
                        System.out.println("test failed 1");
                        break;
                    }
                } else if (Math.random() < 0.2) {
                    int key = (int)(Math.random() * max_val);
                    int val = (int)(Math.random() * max_val);
                    tree_map.put(key, val);
                    sb_tree.put(key, val);
                    if (tree_map.size() != sb_tree.size()) {
                        success = false;
                        System.out.println("test failed 2");
                        break;
                    }
                } else if (Math.random() < 0.4) {
                    int key = (int) (Math.random() * max_val);
                    if (tree_map.get(key) == sb_tree.get(key)) {
                        continue;
                    } else {
                        success = false;
                        System.out.println("test faield 3");
                        break;
                    }
                }
//                } else if (Math.random() < 0.6) {
//                    int key = (int)(Math.random() * max_val);
//                    tree_map.remove(key);
//                    sb_tree.remove(key);
//                    if (tree_map.size() != sb_tree.size()) {
//                        success = false;
//                        System.out.println("test failed 4");
//                        break;
//                    }
//                } else if (Math.random() < 0.7) {
//                    if (tree_map.isEmpty()) {
//                        continue;
//                    }
//                    if (tree_map.firstKey() != sb_tree.firstKey()) {
//                        success = false;
//                        System.out.println("test failed 5");
//                        break;
//                    }
//                } else if (Math.random() < 0.8) {
//                    if (tree_map.isEmpty()) {
//                        continue;
//                    }
//                    if (tree_map.lastKey() != sb_tree.lastKey()) {
//                        success = false;
//                        System.out.println("test failed 6");
//                        break;
//                    }
//                } else if (Math.random() < 0.9) {
//                    int key = (int)(Math.random() * max_val);
//                    if (tree_map.ceilingKey(key) != tree_map.ceilingKey(key)) {
//                        success = false;
//                        System.out.println("test failed 7");
//                        break;
//                    }
//                } else {
//                    int key = (int)(Math.random() * max_val);
//                    if (tree_map.floorKey(key) != tree_map.floorKey(key)) {
//                        success = false;
//                        System.out.println("test failed 7");
//                        break;
//                    }
//                }
            }
            if (!success) {
                break;
            }
        }
        System.out.println("test end...");
    }
}

