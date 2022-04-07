package tixi.daily16;

import java.util.*;

/*
    最小生成树算法之Kruskal
        1）总是从权值最小的边开始考虑，依次考察权值依次变大的边
        2）当前的边要么进入最小生成树的集合，要么丢弃
        3）如果当前的边进入最小生成树的集合中不会形成环，就要当前边
        4）如果当前的边进入最小生成树的集合中会形成环，就不要当前边
        5）考察完所有边之后，最小生成树的集合也得到了
 */
public class Code07_Kruskal {
    public static class UnionFind {
        private HashMap<Node, Node> father_map;
        private HashMap<Node, Integer> size_map;

        public UnionFind() {
            father_map = new HashMap<>();
            size_map = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            father_map.clear();
            size_map.clear();
            for (Node node: nodes) {
                father_map.put(node, node);
                size_map.put(node, 1);
            }
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }

            Node f_a = findFather(a);
            Node f_b = findFather(b);
            if (f_a != f_b) {
                int a_size = size_map.get(f_a);
                int b_size = size_map.get(f_b);
                if (a_size <= b_size) {
                    father_map.put(f_a, f_b);
                    size_map.put(f_b, a_size + b_size);
                    size_map.remove(f_a);
                } else {
                    father_map.put(f_b, f_a);
                    size_map.put(f_a, a_size + b_size);
                    size_map.remove(f_b);
                }
            }
        }

        private Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != father_map.get(node)) {
                path.add(node);
                node = father_map.get(node);
            }
            while (!path.isEmpty()) {
                father_map.put(path.pop(), node);
            }

            return node;
        }
    }

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind union_find = new UnionFind();
        union_find.makeSets(graph.nodes.values());
        PriorityQueue<Edge> priority_queue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge: graph.edges) {
            priority_queue.add(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!priority_queue.isEmpty()) {
            Edge edge = priority_queue.poll();
            if (!union_find.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                union_find.union(edge.from, edge.to);
            }
        }

        return result;
    }
}
