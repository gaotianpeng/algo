package shuati.daily19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Code01_LRUCache {
    public static class Node<K, V> {
        public K key;
        public V value;

        public Node<K, V> last;
        public Node<K, V> next;

        public Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    public static class NodeDoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public NodeDoubleLinkedList() {
            head = null;
            tail = null;
        }

        public void addNode(Node<K, V> new_node) {
            if (new_node == null) {
                return;
            }

            if (head == null) {
                head = new_node;
                tail = new_node;
            } else {
                tail.next = new_node;
                new_node.last = tail;
                tail = new_node;
            }
        }

        public void moveNodeToTail(Node<K, V> node) {
            if (tail == node) {
                return;
            }

            if (node == tail) {
                return;
            }

            if (head == node) {
                head = head.next;
                head.last = null;
            } else {
                node.last.next = node.next;
                node.next.last = node.last;
            }

            node.last = tail;
            tail.next = node;
            node.next = null;
            tail = node;
        }

        public Node<K, V> removeHead() {
            if (head == null) {
                return null;
            }

            Node<K, V> res = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = res.next;
                res.next = null;
                head.last = null;
            }

            return res;
        }
    }

    public static class MyCache<K, V> {
        private HashMap<K, Node<K, V>> key_node_map;
        private NodeDoubleLinkedList<K, V> node_list;
        private final int capacity;

        public MyCache(int cap) {
            key_node_map = new HashMap<K, Node<K, V>>();
            node_list = new NodeDoubleLinkedList<K, V>();
            capacity = cap;
        }

        public V get(K key) {
            if (key_node_map.containsKey(key)) {
                Node<K, V> res = key_node_map.get(key);
                node_list.moveNodeToTail(res);
                return res.value;
            }
            return null;
        }

        public void set(K key, V value) {
            if (key_node_map.containsKey(key)) {
                Node<K, V> node = key_node_map.get(key);
                node.value = value;
                node_list.moveNodeToTail(node);
            } else {
                Node<K, V> new_node = new Node<K, V>(key, value);
                key_node_map.put(key, new_node);
                node_list.addNode(new_node);
                if (key_node_map.size() == capacity + 1) {
                    removeMostUnusedCache();
                }
            }
        }

        private void removeMostUnusedCache() {
            Node<K, V> remove_node = node_list.removeHead();
            key_node_map.remove(remove_node.key);
        }
    }

    public static class LRUCache {
        private MyCache<Integer, Integer> cache;

        public LRUCache(int capacity) {
            cache = new MyCache<>(capacity);
        }

        public int get(int key) {
            Integer ans = cache.get(key);
            return ans == null ? -1 : ans;
        }

        public void put(int key, int value) {
            cache.set(key, value);
        }
    }

    public static class TestLRUCache {
        public static class Node {
            Integer key;
            Integer value;

            public Node(int k, int v) {
                key = k;
                value = v;
            }
        }

        private ArrayList<Node> cache_;
        private final int capacity_;

        public TestLRUCache(int capacity) {
            cache_ = new ArrayList<>();
            capacity_ = capacity;
        }

        public int get(int key) {
            int i = 0;
            Node tmp = null;
            for (i = 0; i < cache_.size(); ++i) {
                if (cache_.get(i).key == key) {
                    tmp = cache_.get(i);
                    break;
                }
            }

            if (tmp == null) {
                return -1;
            }

            if (i == cache_.size() - 1) {
                return tmp.value;
            }

            for (int j = i + 1; j < cache_.size(); ++j) {
                Node temp = cache_.get(j-1);
                cache_.set(j-1, cache_.get(j));
                cache_.set(j, temp);
            }

            cache_.set(cache_.size() - 1, tmp);

            return tmp.value;
        }

        public void put(int key, int value) {
            boolean has = false;
            int i = 0;
            for (i = 0; i < cache_.size(); ++i) {
                if (cache_.get(i).key == key) {
                    has = true;
                    break;
                }
            }

            if (has) {
                Node tmp = cache_.get(i);
                for (int j = i + 1; j < cache_.size(); ++j) {
                    Node temp = cache_.get(j-1);
                    cache_.set(j-1, cache_.get(j));
                    cache_.set(j, temp);
                }
                tmp.value = value;
                cache_.set(cache_.size() - 1, tmp);

            } else {
                cache_.add(new Node(key, value));
                if (cache_.size() == capacity_ + 1) {
                    cache_.remove(0);
                }
            }
        }
    }

    public static int RandomVal(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public static void main(String[] args) {
        System.out.println("test start");
        int test_times = 10000;
        int min = 0;
        int max = 300;
        boolean success = true;
        for (int i = 0; i < test_times; ++i) {
            if (success == true) {
                int capacity = RandomVal(100, 200);
                LRUCache lruCache = new LRUCache(capacity);
                TestLRUCache test = new TestLRUCache(capacity);
                for (int j = 0; j < 3000; ++j) {
                    if (Math.random() < 0.5) {
                        int k = RandomVal(min, max);
                        int v = RandomVal(min, max);
                        lruCache.put(k, v);
                        test.put(k, v);
                    } else {
                        int k = RandomVal(min, max);
                        if (lruCache.get(k) != test.get(k)) {
                            System.out.println("test failed");
                            System.out.println(lruCache.get(k));
                            System.out.println(test.get(k));
                            success = false;
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("test end");
    }
}
