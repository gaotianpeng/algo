package tixi.daily16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;


/*
    拓扑排序
        1）在图中找到所有入度为0的点输出
        2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
        3）图的所有点都被删除后，依次输出的顺序就是拓扑排序

    要求：有向图且其中没有环
    应用：事件安排、编译顺序

 */
public class Code04_TopologySort {
    public static List<Node> sortedTopology(Graph graph) {
        // key 某个节点   value 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
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


    public static Graph generateRandomDAG(int numNodes, int minVal, int maxVal) {
        Random rand = new Random();
        Graph graph = new Graph();

        // Create nodes
        for (int i = 0; i < numNodes; i++) {
            graph.nodes.put(i, new Node(i));
        }

        // Create edges ensuring no cycles
        for (int i = 0; i < numNodes; i++) {
            for (int j = i + 1; j < numNodes; j++) {
                if (rand.nextBoolean()) {
                    Node fromNode = graph.nodes.get(i);
                    Node toNode = graph.nodes.get(j);
                    int weight = rand.nextInt(maxVal - minVal + 1) + minVal;
                    Edge edge = new Edge(weight, fromNode, toNode);
                    fromNode.nexts.add(toNode);
                    fromNode.out++;
                    toNode.in++;
                    fromNode.edges.add(edge);
                    graph.edges.add(edge);
                }
            }
        }

        // Ensure all nodes have at least one edge (either in or out)
        for (int i = 0; i < numNodes; i++) {
            Node node = graph.nodes.get(i);
            if (node.nexts.isEmpty() && node.in == 0) {
                int target;
                do {
                    target = rand.nextInt(numNodes);
                } while (target == i);
                Node targetNode = graph.nodes.get(target);
                int weight = rand.nextInt(maxVal - minVal + 1) + minVal;
                if (i < target) {
                    Edge edge = new Edge(weight, node, targetNode);
                    node.nexts.add(targetNode);
                    node.out++;
                    targetNode.in++;
                    node.edges.add(edge);
                    graph.edges.add(edge);
                } else {
                    Edge edge = new Edge(weight, targetNode, node);
                    targetNode.nexts.add(node);
                    targetNode.out++;
                    node.in++;
                    targetNode.edges.add(edge);
                    graph.edges.add(edge);
                }
            }
        }

        return graph;
    }

    public static boolean validateTopologicalSort(Graph graph, List<Node> sortedList) {
        if (graph == null || sortedList == null) {
            return false;
        }
        HashMap<Node, Integer> positionMap = new HashMap<>();
        for (int i = 0; i < sortedList.size(); i++) {
            positionMap.put(sortedList.get(i), i);
        }
        for (Edge edge : graph.edges) {
            if (!positionMap.containsKey(edge.from) || !positionMap.containsKey(edge.to)) {
                return false;
            }
            if (positionMap.get(edge.from) >= positionMap.get(edge.to)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("test start...");
        int testTimes = 100000;
        boolean success = true;
        int minVal = 1;
        int maxVal = 20;
        int maxNumNodes = 15;
        for (int i = 0; i < testTimes; ++i) {
            int numNodes = (int)(Math.random() * (maxNumNodes + 1));
            while (numNodes <= 1) {
                numNodes = (int)(Math.random() * (maxNumNodes + 1));
            }
            Graph graph = generateRandomDAG(numNodes, minVal, maxVal);
            List<Node> nodes = sortedTopology(graph);
            if (!validateTopologicalSort(graph, nodes)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
