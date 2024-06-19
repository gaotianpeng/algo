package tixi.daily35;

import java.util.TreeMap;

public class Code02_AVLTreeMap1 {
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
            return lastNode != null && key.compareTo(lastNode.k) == 0;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            root = addNonRecursive(root, key, value);
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                root = deleteNonRecursive(root, key);
            }
        }

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

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.k;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }

            AVLNode<K, V> lastNoBig = findLastNoSmallIndex(key);
            return lastNoBig == null ? null : lastNoBig.k;
        }

        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            left.r = cur;
            left.h = Math.max(left.l != null ? left.l.h : 0, left.r != null ? left.r.h : 0) + 1;
            return left;
        }

        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            cur.h = Math.max(cur.l != null ? cur.l.h : 0, cur.r != null ? cur.r.h : 0) + 1;
            right.l = cur;
            right.h = Math.max(right.l != null ? right.l.h : 0, right.r != null ? right.r.h : 0) + 1;
            return right;
        }

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

        private AVLNode<K, V> findLastNoBigIndex(K key) {
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

        private AVLNode<K, V> deleteNonRecursive(AVLNode<K, V> root, K key) {
            AVLNode<K, V> cur = root;
            AVLNode<K, V> parent = null;
            while (cur != null && !cur.k.equals(key)) {
                parent = cur;
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            if (cur == null) {
                return root; // Key not found
            }
            if (cur.l == null || cur.r == null) {
                AVLNode<K, V> child = (cur.l != null) ? cur.l : cur.r;
                if (parent == null) {
                    root = child;
                } else if (cur == parent.l) {
                    parent.l = child;
                } else {
                    parent.r = child;
                }
            } else {
                AVLNode<K, V> successorParent = cur;
                AVLNode<K, V> successor = cur.r;
                while (successor.l != null) {
                    successorParent = successor;
                    successor = successor.l;
                }
                if (successorParent != cur) {
                    successorParent.l = successor.r;
                    successor.r = cur.r;
                }
                successor.l = cur.l;
                if (parent == null) {
                    root = successor;
                } else if (cur == parent.l) {
                    parent.l = successor;
                } else {
                    parent.r = successor;
                }
            }
            size--;
            return balance(root, key);
        }

        private AVLNode<K, V> balance(AVLNode<K, V> node, K key) {
            if (node == null) {
                return null;
            }
            node.h = Math.max(height(node.l), height(node.r)) + 1;
            int balanceFactor = getBalance(node);
            if (balanceFactor > 1) {
                if (key.compareTo(node.l.k) < 0) {
                    return rightRotate(node);
                } else {
                    node.l = leftRotate(node.l);
                    return rightRotate(node);
                }
            }
            if (balanceFactor < -1) {
                if (key.compareTo(node.r.k) > 0) {
                    return leftRotate(node);
                } else {
                    node.r = rightRotate(node.r);
                    return leftRotate(node);
                }
            }
            return node;
        }

        private int height(AVLNode<K, V> node) {
            return (node == null) ? 0 : node.h;
        }

        private int getBalance(AVLNode<K, V> node) {
            return (node == null) ? 0 : height(node.l) - height(node.r);
        }

        private AVLNode<K, V> addNonRecursive(AVLNode<K, V> root, K key, V value) {
            AVLNode<K, V> newNode = new AVLNode<>(key, value);
            if (root == null) {
                size++;
                return newNode;
            }
            AVLNode<K, V> cur = root;
            AVLNode<K, V> parent = null;
            while (cur != null) {
                parent = cur;
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    cur.v = value; // Key already exists, update value
                    return root;
                }
            }
            if (key.compareTo(parent.k) < 0) {
                parent.l = newNode;
            } else {
                parent.r = newNode;
            }
            size++;
            return balance(root, key);
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
                    if (treeMap.ceilingKey(key) != treeMap.ceilingKey(key)) {
                        success = false;
                        System.out.println("test failed 7");
                        break;
                    }
                } else {
                    int key = (int)(Math.random() * maxVal);
                    if (treeMap.floorKey(key) != treeMap.floorKey(key)) {
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
