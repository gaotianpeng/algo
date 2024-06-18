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
public class Code05_Kruskal {
    public static class Node {
        public int value;
        public int in;
        public int out;
        public ArrayList<Node> nexts;
        public ArrayList<Edge> edges;

        public Node(int value) {
            this.value = value;
            in = 0;
            out = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }
    }

    public static class Graph {
        public HashMap<Integer, Node> nodes;
        public HashSet<Edge> edges;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public static class Edge {
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight, Node from, Node to) {
            this.weight = weight;
            this.from = from;
            this.to = to;
        }
    }

    public static class UnionFind {
        private HashMap<Node, Node> fatherMap;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node: nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }

            Node fA = findFather(a);
            Node fB = findFather(b);
            if (fA != fB) {
                int aSize = sizeMap.get(fA);
                int bSize = sizeMap.get(fB);
                if (aSize <= bSize) {
                    fatherMap.put(fA, fB);
                    sizeMap.put(fB, aSize + bSize);
                    sizeMap.remove(fA);
                } else {
                    fatherMap.put(fB, fA);
                    sizeMap.put(fA, aSize + bSize);
                    sizeMap.remove(fB);
                }
            }
        }

        private Node findFather(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != fatherMap.get(node)) {
                path.add(node);
                node = fatherMap.get(node);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), node);
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
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge: graph.edges) {
            priorityQueue.add(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()) {
            Edge edge = priorityQueue.poll();
            if (!unionFind.isSameSet(edge.from, edge.to)) {
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }

        return result;
    }


    // for test
    public static class UnionFindTest {
        private Map<Node, Node> parent;
        private Map<Node, Integer> size;

        public UnionFindTest() {
            parent = new HashMap<>();
            size = new HashMap<>();
        }

        public void makeSets(Collection<Node> nodes) {
            parent.clear();
            size.clear();
            for (Node node : nodes) {
                parent.put(node, node);
                size.put(node, 1);
            }
        }

        private Node find(Node node) {
            Stack<Node> path = new Stack<>();
            while (node != parent.get(node)) {
                path.push(node);
                node = parent.get(node);
            }
            while (!path.isEmpty()) {
                parent.put(path.pop(), node);
            }
            return node;
        }

        public boolean isSameSet(Node a, Node b) {
            return find(a) == find(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aHead = find(a);
            Node bHead = find(b);
            if (aHead != bHead) {
                int aSize = size.get(aHead);
                int bSize = size.get(bHead);
                if (aSize >= bSize) {
                    parent.put(bHead, aHead);
                    size.put(aHead, aSize + bSize);
                    size.remove(bHead);
                } else {
                    parent.put(aHead, bHead);
                    size.put(bHead, aSize + bSize);
                    size.remove(aHead);
                }
            }
        }
    }

    // Function to verify if the generated MST is valid
    public static boolean validateMST(Graph graph, Set<Edge> mstEdges) {
        if (graph == null || mstEdges == null) {
            return false;
        }

        // Verify if the number of edges in MST is exactly (number of nodes - 1)
        if (mstEdges.size() != graph.nodes.size() - 1) {
            return false;
        }

        UnionFindTest unionFind = new UnionFindTest();
        unionFind.makeSets(graph.nodes.values());
        for (Edge edge : mstEdges) {
            if (unionFind.isSameSet(edge.from, edge.to)) {
                return false; // A cycle was detected
            }
            unionFind.union(edge.from, edge.to);
        }

        // Verify if all nodes are connected
        Node representative = unionFind.find(graph.nodes.values().iterator().next());
        for (Node node : graph.nodes.values()) {
            if (unionFind.find(node) != representative) {
                return false; // Not all nodes are connected
            }
        }

        return true;
    }

    public static Graph graphGenerator(int[][] matrix) {
        if (matrix == null) {
            return null;
        }

        Graph graph = new Graph();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                int weight = matrix[i][j];
                int from = i;
                int to = j;
                if (!graph.nodes.containsKey(from)) {
                    graph.nodes.put(from, new Node(from));
                }
                if (!graph.nodes.containsKey(to)) {
                    graph.nodes.put(to, new Node(to));
                }
                Node fromNode = graph.nodes.get(from);
                Node toNode = graph.nodes.get(to);
                Edge newEdge = new Edge(weight, fromNode, toNode);
                fromNode.nexts.add(toNode);
                fromNode.out++;
                fromNode.in++;
                toNode.in++;
                fromNode.edges.add(newEdge);
                graph.edges.add(newEdge);
            }
        }
        return graph;
    }

    public static int[][] randomMatrixGenerator(int maxM, int maxN, int minVal, int maxVal) {
        Random rand = new Random();
        int rows = rand.nextInt(maxM) + 1; // 随机生成 1 到 maxM 之间的行数
        int cols = rand.nextInt(maxN) + 1; // 随机生成 1 到 maxN 之间的列数

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(maxVal - minVal + 1) + minVal; // 随机填充 minVal 到 maxVal 之间的值
            }
        }

        return matrix;
    }

    public static void main(String[] args) {
        System.out.println("test start");
        boolean success = true;
        int testTimes = 100000;
        int maxM = 30;
        int maxN = 30;
        int minVal = 1;
        int maxVal = 100;

        for (int i = 0; i < testTimes; ++i) {
            int[][] matrix = randomMatrixGenerator(maxM, maxN, minVal, maxVal);
            Graph graph = graphGenerator(matrix);
            Set<Edge> ans = kruskalMST(graph);
            if (!validateMST(graph, ans)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
