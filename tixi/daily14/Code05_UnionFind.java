package tixi.daily14;


import java.util.*;

/*
    并查集
        1）有若干个样本a、b、c、d…类型假设是V
        2）在并查集中一开始认为每个样本都在单独的集合里
        3）用户可以在任何时候调用如下两个方法：
               boolean isSameSet(V x, V y) : 查询样本x和样本y是否属于一个集合
               void union(V x, V y) : 把x和y各自所在集合的所有样本合并成一个集合
        4） isSameSet和union方法的代价越低越好
 */
public class Code05_UnionFind {
    public static class Node<V> {
        V value;
        public Node(V v) {
            value = v;
        }
    }

    public static class UnionFind<V> {
        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Node<V>> parents;
        private HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur: values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                sizeMap.put(node, 1);
                parents.put(node, node);
            }
        }

        public boolean isSameSet(V x, V y) {
            return FindFather(nodes.get(x)) == FindFather(nodes.get(y));
        }

        public void union(V x, V y) {
            Node<V> xHead = FindFather(nodes.get(x));
            Node<V> yHead = FindFather(nodes.get(y));
            if (xHead != yHead) {
                int setXSize = sizeMap.get(xHead);
                int setYSize = sizeMap.get(yHead);
                Node<V> big = setYSize >= setYSize ? xHead : yHead;
                Node<V> small = big == xHead ? yHead : xHead;
                sizeMap.put(big, setXSize + setYSize);
                parents.put(small, big);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

        public List<V> getSet(V v) {
            List<V> ans = new LinkedList<>();
            return ans;
        }

        private Node<V> FindFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }

            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }

            return cur;
        }
    }

    public static class UnionFindTest<V> {
        private List<List<V>> sets;

        public UnionFindTest(List<V> values) {
            sets = new LinkedList<>();
            for (int i = 0; i < values.size(); i++) {
                List<V> set = new LinkedList<>();
                set.add(values.get(i));
                sets.add(set);
            }
        }

        public boolean isSameSet(V x, V y) {
            List<V> listX = null;
            List<V> listY = null;
            for (int i = 0; i < sets.size(); ++i) {
                HashSet<V> hashSet = new HashSet<>();
                for (int j = 0; j < sets.get(i).size(); ++j) {
                    hashSet.add(sets.get(i).get(j));
                }
                if (hashSet.contains(x)) {
                    listX = sets.get(i);
                }

                if (hashSet.contains(y)) {
                    listY = sets.get(i);
                }
            }

            if (listX == null || listY == null) {
                return false;
            }

            return listX == listY;
        }

        public void union(V x, V y) {
            if (isSameSet(x, y)) {
                return;
            }
            List<V> listX = null;
            List<V> listY = null;

            for (int i = 0; i < sets.size(); ++i) {
                HashSet<V> hashSet = new HashSet<>();
                for (int j = 0; j < sets.get(i).size(); ++j) {
                    hashSet.add(sets.get(i).get(j));
                }
                if (hashSet.contains(x)) {
                    listX = sets.get(i);
                }

                if (hashSet.contains(y)) {
                    listY = sets.get(i);
                }
            }

            if (listX == null || listY == null) {
                return;
            }

            for (int i = 0; i < listY.size(); ++i) {
                listX.add(listY.get(i));
            }

            sets.remove(listY);
        }

        public int sets() {
            return sets.size();
        }

        public List<V> getSet(V v) {
            List<V> ans = new LinkedList<>();
            int pos = -1;
            for (int i = 0; i < sets.size(); ++i) {
                for (int j = 0; j < sets.get(i).size(); ++j) {
                    if (sets.get(i).get(j) == v) {
                        pos = i;
                        break;
                    }
                }
            }

            if (pos == -1) {
                return ans;
            }

            for (int i = 0; i < sets.get(pos).size(); ++i) {
                ans.add(sets.get(pos).get(i));
            }
            return ans;
        }
    }

    public static List<Integer> generateRandomList(int minVal, int maxValue, int maxSize) {
        int size = (int)(Math.random() * (maxSize + 1));
        List<Integer> ans = new LinkedList<>();

        size = Math.min(maxValue - minVal, maxSize);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            int val = minVal + (int)(Math.random()*(maxValue - minVal));
            while (set.contains(val)) {
                val = minVal + (int)(Math.random()*(maxValue - minVal));
            }
            set.add(val);
            ans.add(val);
        }

        return ans;
    }

    public static int[] getTwoElement(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        return new int[] { (int)(Math.random() * list.size()), (int)(Math.random() * list.size())};
    }

    public static void print(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }



    public static void main(String[] args) {
        System.out.println("test start...");
        boolean success = true;
        int testTimes = 50000;
        int maxValue = 100;
        int minVal = 10;
        int maxSize = 20;
        for (int i = 0; i < testTimes; i++) {
            List<Integer> list = generateRandomList(minVal, maxValue, maxSize);
            UnionFind<Integer> union1 = new UnionFind<>(list);
            UnionFindTest<Integer> union2 = new UnionFindTest<>(list);
            for (int j = 0; j < 100; j++) {
                int[] ans = getTwoElement(list);
                if (ans == null) {
                    continue;
                }

                if (Math.random() < 0.33) {
                    union1.union(list.get(ans[0]), list.get(ans[1]));
                    union2.union(list.get(ans[0]), list.get(ans[1]));
                    if (union1.sets() != union2.sets()) {
                        success = false;
                        break;
                    }
                } else if(Math.random() < 0.66) {
                    if (union1.sets() != union2.sets()) {
                        success = false;
                        break;
                    }
                } else {
                    boolean ans1 = union1.isSameSet(list.get(ans[0]), list.get(ans[1]));
                    boolean ans2 = union2.isSameSet(list.get(ans[0]), list.get(ans[1]));
                    if (ans1 != ans2) {
                        success = false;
                        break;
                    }
                }
            }
            if (!success) {
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
