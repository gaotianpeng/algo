package tixi.daily16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
    拓扑排序
        1）在图中找到所有入度为0的点输出
        2）把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
        3）图的所有点都被删除后，依次输出的顺序就是拓扑排序

    要求：有向图且其中没有环
    应用：事件安排、编译顺序

 */
public class Code06_TopologySort {
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

    public static List<Node> sortedTopology(Graph graph) {
        if (graph == null) {
            return null;
        }
        // key 某个节点, value 剩余的入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        for (Node cur: graph.nodes.values()) {
            inMap.put(cur, cur.in);
            if (cur.in == 0) {
                zeroInQueue.add(cur);
            }
        }

        List<Node> ans = new LinkedList<>();
        while (!zeroInQueue.isEmpty()) {
            Node res = zeroInQueue.poll();
            ans.add(res);
            for (Node next: res.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    ans.add(next);
                }
            }
        }

        return ans;
    }

    public static List<Node> test(Graph graph) {
        if (graph == null) {
            return null;
        }

        List<Node> ans = new ArrayList<>();
        HashSet<Node> remainingNodes = new HashSet<>(graph.nodes.values());

        while (!remainingNodes.isEmpty()) {
            Node zeroInNode = null;
            for (Node node : remainingNodes) {
                if (node.in == 0) {
                    zeroInNode = node;
                    break;
                }
            }

            if (zeroInNode == null) {
                throw new RuntimeException("Graph has a cycle, no zero-in node found.");
            }

            ans.add(zeroInNode);
            remainingNodes.remove(zeroInNode);

            for (Node next : zeroInNode.nexts) {
                next.in--;
            }
        }

        return ans;
    }

    public static void main(String[] args) {

    }
}
