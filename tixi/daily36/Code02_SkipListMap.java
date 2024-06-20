package tixi.daily36;

import java.util.ArrayList;
import java.util.TreeMap;

/*
    1）结构上根本和搜索二叉树无关
    2）利用随机概率分布来使得高层索引可以无视数据规律，做到整体性能优良
    3）思想是所有有序表中最先进的
    4）结构简单就是多级单链表
 */
public class Code02_SkipListMap {

    // 跳表节点
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V val;
        // 当前 SkipListNode 包括的每一层的head
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }

        // 头节点的 null 认为最小
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        // < 0.5 继续做，>=0.5 停
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            head = new SkipListNode<K, V>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        // 新增、改value
        public void put(K key, V val) {
            if (key == null) {
                return;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.val = val;
            } else {
                ++size;
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    ++maxLevel;
                }
                SkipListNode<K, V> new_node = new SkipListNode<K, V>(key, val);
                for (int i = 0; i <= newNodeLevel; ++i) {
                    new_node.nextNodes.add(null);
                }
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    if (level <= newNodeLevel) {
                        new_node.nextNodes.set(level, pre.nextNodes.get(level));
                        pre.nextNodes.set(level, new_node);
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
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.val : null;
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }

            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> pre = head;
                while (level >= 0) {
                    pre = mostRightLessNodeInLevel(key, pre, level);
                    SkipListNode<K, V> next = pre.nextNodes.get(level);
                    // 1）在这一层中，pre下一个就是key
                    // 2）在这一层中，pre的下一个key是>要删除key
                    if (next != null && next.isKeyEqual(key)) {
                        pre.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    // level 层只有一个节点，就是默认的head
                    if (level != 0 && pre == head && pre.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
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
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                // 从上层跳下层
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        /*
            在第 level 层里，如何向右移动
            现在来到的节点是 cur, 来到了cur的level层, 在level层上，找到 <key最后一个节点返回
         */
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key,
                                                            SkipListNode<K, V> cur,
                                                            int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        public int size() {
            return size;
        }

        public K firstKey() {
            return head.nextNodes.get(0) != null ? head.nextNodes.get(0).key : null;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
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
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }
    }


    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        int maxVal = 10;
        boolean success = true;
        for (int i = 0; i < testTimes; ++i) {
            TreeMap<Integer, Integer> treeMap = new TreeMap<>();
            SkipListMap<Integer, Integer> skipList = new SkipListMap<>();
            for (int j = 0; j < 100; ++j) {
                if (Math.random() < 0.1) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.containsKey(key) != skipList.containsKey(key)) {
                        success = false;
                        System.out.println("test failed 1");
                        break;
                    }
                } else if (Math.random() < 0.2) {
                    int key = (int)(Math.random() * maxVal);
                    int val = (int)(Math.random() * maxVal);
                    treeMap.put(key, val);
                    skipList.put(key, val);
                    if (treeMap.size() != skipList.size()) {
                        success = false;
                        System.out.println("test failed 2");
                        break;
                    }
                } else if (Math.random() < 0.4) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.get(key) == skipList.get(key)) {
                        continue;
                    } else {
                        success = false;
                        System.out.println("test faield 3");
                        break;
                    }
                } else if (Math.random() < 0.6) {
                    int key = (int)(Math.random() * maxVal);
                    treeMap.remove(key);
                    skipList.remove(key);
                    if (treeMap.size() != skipList.size()) {
                        success = false;
                        System.out.println("test failed 4");
                        break;
                    }
                } else if (Math.random() < 0.7) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.firstKey() != skipList.firstKey()) {
                        System.out.println(treeMap.firstKey());
                        System.out.println(skipList.firstKey());
                        success = false;
                        System.out.println("test failed 5");
                        break;
                    }
                } else if (Math.random() < 0.8) {
                    if (treeMap.isEmpty()) {
                        continue;
                    }
                    if (treeMap.lastKey() != skipList.lastKey()) {
                        success = false;
                        System.out.println("test failed 6");
                        break;
                    }
                } else if (Math.random() < 0.9) {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.ceilingKey(key) != skipList.ceilingKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                } else {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.floorKey(key) != skipList.floorKey(key)) {
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
        System.out.println(success ? "success" : "failed");
        System.out.println("test end...");
    }
}
