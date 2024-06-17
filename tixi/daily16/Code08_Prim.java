package tixi.daily16;
/*
    最小生成树算法之Prim
        1）可以从任意节点出发来寻找最小生成树
        2）某个点加入到被选取的点中后，解锁这个点出发的所有新的边
        3）在所有解锁的边中选最小的边，然后看看这个边会不会形成环
        4）如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3）
        5）如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2）
        6）当所有点都被选取，最小生成树就得到
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Code08_Prim {
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

    public static Set<Edge> primMST(Graph graph) {
        // 解锁的边进入小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
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
}
