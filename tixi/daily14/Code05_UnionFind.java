package tixi.daily14;

import com.sun.javaws.IconUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

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

    public static class Elem {
        public int value;
        public Elem(int val) {
            value = val;
        }
    }

    public static class UnionFind<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> size_map;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            size_map = new HashMap<>();
            for (V cur: values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                size_map.put(node, 1);
            }
        }

        public boolean isSameSet(V x, V y) {
            return findFather(nodes.get(x)) == findFather(nodes.get(y));
        }

        public void union(V x, V y) {
            Node<V> x_head = findFather(nodes.get(x));
            Node<V> y_head = findFather(nodes.get(y));
            if (x_head != y_head) {
                int x_set_size = size_map.get(x_head);
                int y_set_size = size_map.get(y_head);
                Node<V> big = x_set_size >= y_set_size ? x_head : y_head;
                Node<V> small = big == x_head ? y_head : x_head;
                parents.put(small, big);
                size_map.put(big, x_set_size + y_set_size);
                size_map.remove(small);
            }
        }

        private Node<V> findFather(Node<V> cur) {
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

        public int sets() {
            return size_map.size();
        }

        public List<V> getSet(V v) {
            List<V> ans = new LinkedList<>();
            for(V key: nodes.keySet()) {
                if (isSameSet(key, v)) {
                    if (v != key) {
                        ans.add(key);
                    }
                }
            }
            return ans;
        }
    }

    public static class UnionFindTest<V> {
        private List<List<V>> sets;
        private HashMap<V, List<V>> elem_set_map;

        public UnionFindTest(List<V> values) {
            sets = new LinkedList<>();
            elem_set_map = new HashMap<>();
            for (int i = 0; i < values.size(); i++) {
                List<V> set = new LinkedList<>();
                set.add(values.get(i));
                sets.add(set);
                elem_set_map.put(values.get(i), set);
            }
        }

        public boolean isSameSet(V x, V y) {
            return elem_set_map.get(x) == elem_set_map.get(y);
        }

        public void union(V x, V y) {
            if (isSameSet(x, y)) {
                return;
            }

            List<V> list_x = elem_set_map.get(x);
            List<V> list_y = elem_set_map.get(y);
            List<V> big = list_x.size() > list_y.size() ? list_x : list_y;
            List<V> small = big == list_x ? list_y : list_x;
            for (int i = 0; i < small.size(); i++) {
                big.add(small.get(i));
            }

            elem_set_map.put(x, null);
            elem_set_map.put(x, null);
            sets.remove(small);
            elem_set_map.put(x, big);
            elem_set_map.put(y, big);
        }

        public int sets() {
            return sets.size();
        }

        public List<V> getSet(V v) {
            return elem_set_map.get(v);
        }
    }

    public static List<Elem> generateRandomList(int max_value, int max_size) {
        int size = (int)(Math.random() * (max_size + 1));
        List<Elem> ans = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            ans.add(new Elem((int)(Math.random() * max_value)));
        }

        return ans;
    }

    public static List<Elem> copyList(List<Elem> list) {
        if (list == null) {
            return null;
        }

        List<Elem> ans = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            ans.add(new Elem(list.get(i).value));
        }

        return ans;
    }

    public static int[] getTwoElement(List<Elem> list) {
        if (list == null || list.size() == 0) {
            return null;
        }

        return new int[] { (int)(Math.random() * list.size()), (int)(Math.random() * list.size())};
    }

    public static void print(List<Elem> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).value + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
//        System.out.println("test start...");
//        boolean success = true;
//        int test_times = 100000;
//        int max_value = 300;
//        int max_size = 30;
//        for (int i = 0; i < test_times; i++) {
//            List<Elem> list1 = generateRandomList(max_value, max_size);
//            List<Elem> list2 = copyList(list1);
//            UnionFind<Elem> union1 = new UnionFind<>(list1);
//            UnionFindTest<Elem> union2 = new UnionFindTest<>(list2);
//            for (int j = 0; j < 100; j++) {
//                int[] ans = getTwoElement(list1);
//                if (ans == null) {
//                    continue;
//                }
//
//                if (Math.random() < 0.5) {
//                    union1.union(list1.get(ans[0]), list1.get(ans[1]));
//                    union2.union(list2.get(ans[0]), list2.get(ans[1]));
//                    if (union1.sets() != union2.sets()) {
//                        System.out.println("error 01");
//                        System.out.println(union1.sets());
//                        System.out.println(union2.sets());
//                        success = false;
//                        break;
//                    }
//                } else {
//                    boolean ans1 = union1.isSameSet(list1.get(ans[0]), list1.get(ans[1]));
//                    boolean ans2 = union2.isSameSet(list2.get(ans[0]), list2.get(ans[1]));
//                    if (ans1 != ans2) {
//                        List<Elem> set1 = union1.getSet(list1.get(ans[0]));
//                        List<Elem> set2 = union1.getSet(list1.get(ans[1]));
//                        List<Elem> set3 = union2.getSet(list2.get(ans[0]));
//                        List<Elem> set4 = union2.getSet(list2.get(ans[1]));
//                        print(set1);
//                        print(set2);
//                        print(set3);
//                        print(set4);
//                        success = false;
//                        break;
//                    }
//                }
//            }
//            if (!success) {
//                break;
//            }
//        }
//
//        System.out.println(success ? "success" : "failed");
//        System.out.println("test end");
    }
}
