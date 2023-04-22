package tixi.daily36;

import java.util.ArrayList;

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
            return other_key != null && (key != null || key.compareTo(other_key) < 0);
        }

        public boolean isKeyEqual(K other_key) {
            return (key == null && other_key == null)
                    || (key != null && other_key != null && key.compareTo(other_key) == 0);
        }
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
                    head.next_nodes.add(null);
                    max_level++;
                }
                int level = max_level;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLesNodeInLeve(key, pre, level);
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
                cur = mostRightLesNodeInLeve(key, cur, level--);
            }
            return cur;
        }

        /*
            在第 level 层里，如何向右移动
            现在来到的节点是 cur, 来到了cur的level层, 在level层上，找到 <key最后一个节点返回
         */
        private SkipListNode<K, V> mostRightLesNodeInLeve(K key,
                  SkipListNode<K, V> cur,
                  int level) {
            SkipListNode<K, V> next = cur.next_nodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.next_nodes.get(level);
            }
            return cur;
        }


    }


    public static void main(String[] args) {
        System.out.println("test start...");
        System.out.println("test end");
    }
}
