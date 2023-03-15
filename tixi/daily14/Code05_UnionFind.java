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
        private HashMap<Node<V>, Integer> size_map;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            size_map = new HashMap<>();
            for (V cur: values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                size_map.put(node, 1);
                parents.put(node, node);
            }
        }

        public boolean isSameSet(V x, V y) {
            return FindFather(nodes.get(x)) == FindFather(nodes.get(y));
        }

        public void union(V x, V y) {
            Node<V> x_head = FindFather(nodes.get(x));
            Node<V> y_head = FindFather(nodes.get(y));
            if (x_head != y_head) {
                int set_x_size = size_map.get(x_head);
                int set_y_size = size_map.get(y_head);
                Node<V> big = set_y_size >= set_y_size ? x_head : y_head;
                Node<V> small = big == x_head ? y_head : x_head;
                size_map.put(big, set_x_size + set_y_size);
                parents.put(small, big);
                size_map.remove(small);
            }
        }

        public int sets() {
            return size_map.size();
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
            List<V> list_x = null;
            List<V> list_y = null;
            for (int i = 0; i < sets.size(); ++i) {
                HashSet<V> hash_set = new HashSet<>();
                for (int j = 0; j < sets.get(i).size(); ++j) {
                    hash_set.add(sets.get(i).get(j));
                }
                if (hash_set.contains(x)) {
                    list_x = sets.get(i);
                }

                if (hash_set.contains(y)) {
                    list_y = sets.get(i);
                }
            }

            if (list_x == null || list_y == null) {
                return false;
            }

            return list_x == list_y;
        }

        public void union(V x, V y) {
            if (isSameSet(x, y)) {
                return;
            }
            List<V> list_x = null;
            List<V> list_y = null;

            for (int i = 0; i < sets.size(); ++i) {
                HashSet<V> hash_set = new HashSet<>();
                for (int j = 0; j < sets.get(i).size(); ++j) {
                    hash_set.add(sets.get(i).get(j));
                }
                if (hash_set.contains(x)) {
                    list_x = sets.get(i);
                }

                if (hash_set.contains(y)) {
                    list_y = sets.get(i);
                }
            }

            if (list_x == null || list_y == null) {
                return;
            }

            for (int i = 0; i < list_y.size(); ++i) {
                list_x.add(list_y.get(i));
            }

            sets.remove(list_y);
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

    public static List<Integer> generateRandomList(int min_val, int max_value, int max_size) {
        int size = (int)(Math.random() * (max_size + 1));
        List<Integer> ans = new LinkedList<>();

        size = Math.min(max_value - min_val, max_size);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            int val = min_val + (int)(Math.random()*(max_value - min_val));
            while (set.contains(val)) {
                val = min_val + (int)(Math.random()*(max_value - min_val));
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
        int test_times = 50000;
        int max_value = 100;
        int min_val = 10;
        int max_size = 20;
        for (int i = 0; i < test_times; i++) {
            List<Integer> list = generateRandomList(min_val, max_value, max_size);
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
