package tixi.daily16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;

/*
    Dijkstra最短路径算法(计算图中，一个点到其他所有点的最短路径)
        1）Dijkstra算法必须指定一个源点
        2）生成一个源点到各个点的最小距离表，一开始只有一条记录，即原点到自己的最小距离为0，源点到其他所有点的最小距离都为正无穷大
        3）从距离表中拿出没拿过记录里的最小记录，通过这个点发出的边，更新源点到各个点的最小距离表，不断重复这一步
        4）源点到所有的点记录如果都被拿过一遍，过程停止，最小距离表得到了
*/
public class Code07_Dijkstra {
    /*
     *  求从图中from点出发，到图中所有其它点的最小距离
     *  HashMap<Node, Integer>
     *      key: 从haead出发到达key
     *      value: 从head出发到达key的最小距离
     */
    public static HashMap<Node, Integer> dijkstra(Node from) {
        // from 点到图中各个点的最短距离，包含from点本身
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 已经求过距离的节点，存在selectedNodes中，以后再也不碰
        HashSet<Node> selectedNodes = new HashSet<>();
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        while (minNode != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = distanceMap.get(minNode);

            for (Edge edge : minNode.edges) {
                Node toNode = edge.to;
                if (!distanceMap.containsKey(toNode)) {
                    // 原始点 到跳转点距离 + edge.weight
                    distanceMap.put(toNode, distance + edge.weight);
                } else { // toNode
                    distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
                }
            }
            selectedNodes.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
        }
        return distanceMap;
    }

    // distanceMap中哪个距离最近，并且没有打过对号的点
    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap,
                                                       HashSet<Node> touchedNodes) {
        Node minNode = null;
        int minDistance = Integer.MAX_VALUE;
        for (Entry<Node, Integer> entry: distanceMap.entrySet()) {
            Node node = entry.getKey();
            int distance = entry.getValue();
            if (!touchedNodes.contains(node) && distance < minDistance) {
                minNode = node;
                minDistance = distance;
            }
        }

        return minNode;
    }

    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap nodeHeap = new NodeHeap(size);
        nodeHeap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> result = new HashMap<>();
        while (!nodeHeap.isEmpty()) {
            NodeRecord record = nodeHeap.pop();
            Node cur = record.node;
            int distance = record.distance;
            for (Edge edge: cur.edges) {
                nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
            }
            result.put(cur, distance);
        }

        return result;
    }

    public static class NodeRecord {
        public Node node;
        public int distance;

        public NodeRecord(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeHeap {
        private Node[] nodes;
        // <某一个node, 堆上的位置>
        private HashMap<Node, Integer> heapIndexMap;
        // <某一个node，从源node出发到该节点的目前最小距离>
        private HashMap<Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            nodes = new Node[size];
            heapIndexMap = new HashMap<>();
            distanceMap = new HashMap<>();
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                insertHeapify(node, heapIndexMap.get(node));
            }
            if (!isEntered(node)) {
                nodes[size] = node;
                heapIndexMap.put(node, size);
                distanceMap.put(node, distance);
                insertHeapify(node, size++);
            }
        }

        public NodeRecord pop() {
            NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
            swap(0, size - 1);
            heapIndexMap.put(nodes[size - 1], -1);
            distanceMap.remove(nodes[size - 1]);
            // free C++同学还要把原本堆顶节点析构，对java同学不必
            nodes[size - 1] = null;
            heapify(0, --size);
            return nodeRecord;
        }

        private void insertHeapify(Node node, int index) {
            while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
                        ? left + 1
                        : left;
                smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
                if (smallest == index) {
                    break;
                }
                swap(smallest, index);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return heapIndexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && heapIndexMap.get(node) != -1;
        }

        private void swap(int index1, int index2) {
            heapIndexMap.put(nodes[index1], index2);
            heapIndexMap.put(nodes[index2], index1);
            Node tmp = nodes[index1];
            nodes[index1] = nodes[index2];
            nodes[index2] = tmp;
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

    public static class NodeDistance {
        public Node node;
        public int distance;

        public NodeDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }

    public static class NodeDistanceComparator implements Comparator<NodeDistance> {
        @Override
        public int compare(NodeDistance o1, NodeDistance o2) {
            return o1.distance - o2.distance;
        }
    }

    public static HashMap<Node, Integer> test(Node startNode) {
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(startNode, 0);
        PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(new NodeDistanceComparator());
        priorityQueue.add(new NodeDistance(startNode, 0));
        HashSet<Node> visitedNodes = new HashSet<>();

        while (!priorityQueue.isEmpty()) {
            NodeDistance current = priorityQueue.poll();
            Node currentNode = current.node;
            int currentDistance = current.distance;

            if (visitedNodes.contains(currentNode)) {
                continue;
            }
            visitedNodes.add(currentNode);

            for (Edge edge : currentNode.edges) {
                Node toNode = edge.to;
                if (!visitedNodes.contains(toNode)) {
                    int newDist = currentDistance + edge.weight;
                    if (!distanceMap.containsKey(toNode) || newDist < distanceMap.get(toNode)) {
                        distanceMap.put(toNode, newDist);
                        priorityQueue.add(new NodeDistance(toNode, newDist));
                    }
                }
            }
        }
        return distanceMap;
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

    public static boolean compareResults(HashMap<Node, Integer> result1, HashMap<Node, Integer> result2) {
        if (result1.size() != result2.size()) {
            return false;
        }

        for (Map.Entry<Node, Integer> entry : result1.entrySet()) {
            Node node = entry.getKey();
            Integer distance1 = entry.getValue();
            Integer distance2 = result2.get(node);

            if (distance2 == null || !distance1.equals(distance2)) {
                return false;
            }
        }

        return true;
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
            Node start = graph.nodes.values().iterator().next();
            HashMap<Node, Integer> ans1 = dijkstra(start);
            HashMap<Node, Integer> ans2 = dijkstra2(start, 100);
            HashMap<Node, Integer> ans3 = test(start);
            if (!compareResults(ans1, ans3)) {
                success = false;
                break;
            }
            if (!compareResults(ans2, ans3)) {
                success = false;
                break;
            }
        }

        System.out.println(success ? "success" : "failed");
        System.out.println("test end");
    }
}
