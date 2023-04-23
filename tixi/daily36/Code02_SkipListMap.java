package tixi.daily36;

import java.util.ArrayList;
import java.util.TreeMap;

public class Code02_SkipListMap {
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V val;

        public ArrayList<SkipListNode<K, V>> next_nodes; // 当前 SkipListNode 包括的每一层的head

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            next_nodes = new ArrayList<>();
        }

        // 头节点的 null 认为最小
        public boolean isKeyLess(K other_key) {
            return other_key != null && (key == null || key.compareTo(other_key) < 0);
        }

        public boolean isKeyEqual(K other_key) {
            return (key == null && other_key == null)
                    || (key != null && other_key != null && key.compareTo(other_key) == 0);        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int max_level;

        public SkipListMap() {
            head = new SkipListNode<K, V>(null, null);
            head.next_nodes.add(null);
            size = 0;
            max_level = 0;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.next_nodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        // 新增、改value
        public void put(K key, V val) {
            if (key == null) {
                return;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.next_nodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.val = val;
            } else {
                ++size;
                int new_node_level = 0;
                while (Math.random() < PROBABILITY) {
                    new_node_level++;
                }
                while (new_node_level > max_level) {
                    head.next_nodes.add(null);
                    ++max_level;
                }
                SkipListNode<K, V> new_node = new SkipListNode<K, V>(key, val);
                for (int i = 0; i <= new_node_level; ++i) {
                    new_node.next_nodes.add(null);
                }
                int level = max_level;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLesNodeInLevel(key, pre, level);
                    if (level <= new_node_level) {
                        new_node.next_nodes.set(level, pre.next_nodes.get(level));
                        pre.next_nodes.set(level, new_node);
                    }
                    level--;
                }
            }

        }

        public V get(K key) {
            if (key == null) {
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.next_nodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.val : null;
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }

            if (containsKey(key)) {
                size--;
                int level = max_level;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLesNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.next_nodes.get(level);
                    if (next != null && next.isKeyEqual(key)) {
                        pre.next_nodes.set(level, next.next_nodes.get(level));
                    }
                    if (level != 0 && pre == head && pre.next_nodes.get(level) == null) {
                        head.next_nodes.remove(level);
                        max_level--;
                    }
                    level--;
                }
            }
        }

        /*
            从最高层开始，一路找下去，最终，找到第0层的 <key 的最右节点
         */
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = max_level;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLesNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        /*
            在第 level 层里，如何向右移动
            现在来到的节点是 cur, 来到了cur的level层, 在level层上，找到 <key最后一个节点返回
         */
        private SkipListNode<K, V> mostRightLesNodeInLevel(K key,
                                                           SkipListNode<K, V> cur,
                                                           int level) {
            SkipListNode<K, V> next = cur.next_nodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.next_nodes.get(level);
            }
            return cur;
        }

        public int size() {
            return size;
        }

        public K firstKey() {
            return head.next_nodes.get(0) != null ? head.next_nodes.get(0).key : null;
        }

        public K lastKey() {
            int level = max_level;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.next_nodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.next_nodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.next_nodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.next_nodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        int test_times = 100000;
        int max_val = 10;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            TreeMap<Integer, Integer> tree_map = new TreeMap<>();
            SkipListMap<Integer, Integer> skip_list = new SkipListMap<>();
            for (int j = 0; j < 100; ++j) {
                if (Math.random() < 0.1) {
                    int key = (int)(Math.random() * max_val);
                    if (tree_map.containsKey(key) != skip_list.containsKey(key)) {
                        success = false;
                        System.out.println("test failed 1");
                        break;
                    }
                } else if (Math.random() < 0.2) {
                    int key = (int)(Math.random() * max_val);
                    int val = (int)(Math.random() * max_val);
                    tree_map.put(key, val);
                    skip_list.put(key, val);
                    if (tree_map.size() != skip_list.size()) {
                        success = false;
                        System.out.println("test failed 2");
                        break;
                    }
                } else if (Math.random() < 0.4) {
                    int key = (int)(Math.random() * max_val);
                    if (tree_map.get(key) == skip_list.get(key)) {
                        continue;
                    } else {
                        success = false;
                        System.out.println("test faield 3");
                        break;
                    }
                } else if (Math.random() < 0.6) {
                    int key = (int)(Math.random() * max_val);
                    tree_map.remove(key);
                    skip_list.remove(key);
                    if (tree_map.size() != skip_list.size()) {
                        success = false;
                        System.out.println("test failed 4");
                        break;
                    }
                } else if (Math.random() < 0.7) {
                    if (tree_map.isEmpty()) {
                        continue;
                    }
                    if (tree_map.firstKey() != skip_list.firstKey()) {
                        System.out.println(tree_map.firstKey());
                        System.out.println(skip_list.firstKey());
                        success = false;
                        System.out.println("test failed 5");
                        break;
                    }
                } else if (Math.random() < 0.8) {
                    if (tree_map.isEmpty()) {
                        continue;
                    }
                    if (tree_map.lastKey() != skip_list.lastKey()) {
                        success = false;
                        System.out.println("test failed 6");
                        break;
                    }
                } else if (Math.random() < 0.9) {
                    int key = (int)(Math.random() * max_val);
                    if (tree_map.ceilingKey(key) != skip_list.ceilingKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                } else {
                    int key = (int)(Math.random() * max_val);
                    if (tree_map.floorKey(key) != skip_list.floorKey(key)) {
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
        System.out.println("test end...");
    }
}
