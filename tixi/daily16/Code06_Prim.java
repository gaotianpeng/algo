package tixi.daily16;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Code06_Prim {
    /*
        最小生成树算法之Prim
            1）可以从任意节点出发来寻找最小生成树
            2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
            3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
            4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
            5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
            6）当所有点都被选取，最小生成树就得到
     */
    public static Set<Edge> primMST(Graph graph) {
        if (graph == null) {
            return null;
        }
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        // 哪些点被解锁出来了
        HashSet<Node> nodeSet = new HashSet<>();
        Set<Edge> result = new HashSet<>();
        for (Node node: graph.nodes.values()) {
            nodeSet.add(node);
            for (Edge edge: node.edges) {
                priorityQueue.add(edge);
            }
            while (!priorityQueue.isEmpty()) {
                Edge edge = priorityQueue.poll();
                Node toNode = edge.to;
                if (!nodeSet.contains(toNode)) {
                    nodeSet.add(toNode);
                    result.add(edge);
                    for (Edge next_edge : toNode.edges) {
                        priorityQueue.add(next_edge);
                    }
                }
            }
        }
        return result;
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

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }

    // for test
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

    public static int[][] randomUndirectedMatrixGenerator(int numNodes, int minVal, int maxVal) {
        Random rand = new Random();
        int[][] matrix = new int[numNodes][numNodes];

        for (int i = 0; i < numNodes; i++) {
            for (int j = i + 1; j < numNodes; j++) {
                int val = rand.nextInt(maxVal - minVal + 1) + minVal; // 随机生成minVal到maxVal之间的值
                matrix[i][j] = val;
                matrix[j][i] = val; // 保持对称
            }
        }

        return matrix;
    }

    public static boolean validateMST(Graph graph, Set<Edge> mstEdges) {
        if (graph == null || mstEdges == null) {
            return false;
        }

        // Verify if the number of edges in MST is exactly (number of nodes - 1)
        if (mstEdges.size() != graph.nodes.size() - 1) {
            return false;
        }

        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        for (Edge edge : mstEdges) {
            if (unionFind.isSameSet(edge.from, edge.to)) {
                return false; // A cycle was detected
            }
            unionFind.union(edge.from, edge.to);
        }

        // Verify if all nodes are connected
        Node representative = unionFind.findFather(graph.nodes.values().iterator().next());
        for (Node node : graph.nodes.values()) {
            if (unionFind.findFather(node) != representative) {
                return false; // Not all nodes are connected
            }
        }

        return true;
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

    public static void main(String[] args) {
        System.out.println("test start");
        boolean success = true;
        int testTimes = 100000;
        int minVal = 1;
        int maxVal = 20;
        int maxNumNodes = 15;

        for (int i = 0; i < testTimes; ++i) {
            int numNodes = (int)(Math.random() * (maxNumNodes + 1));
            while (numNodes <= 1) {
                numNodes = (int)(Math.random() * (maxNumNodes + 1));
            }
            int[][] matrix = randomUndirectedMatrixGenerator(numNodes, minVal, maxVal);
            Graph graph = graphGenerator(matrix);
            Set<Edge> edges = primMST(graph);
            if (!validateMST(graph, edges)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
